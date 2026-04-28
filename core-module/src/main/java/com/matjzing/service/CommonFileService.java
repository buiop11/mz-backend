package com.matjzing.service;

import com.matjzing.dto.common.RedisCacheKey;
import com.matjzing.dto.common.enumeration.FileTargetCd;
import com.matjzing.dto.file.*;
import com.matjzing.mapper.AttachingFileMapper;
import com.matjzing.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonFileService {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${file.upload.public-url}")
    private String publicUrl;

    @Value("${file.upload.private-url}")
    private String privateUrl;

    @Value("${file.upload.temp}")
    private String temp;

    @Value("${file.upload.location}")
    private String location;

    private Path dirLocation;

    private final AttachingFileMapper attachingFileMapper;

    private final S3FileSyncUtil s3FileSyncUtil;

    @PostConstruct
    public void init() {
        dirLocation = Paths.get(location).toAbsolutePath().normalize();
        try {
            Files.createDirectories(dirLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T insertFile(Boolean openFileYn, FileTargetCd targetCd, FileUploadDto dto, Long targetSeq, Class<T> clazz) {
        if (!Integer.class.equals(clazz) && !FileInsertRequest.class.equals(clazz)) {
            throw new RuntimeException();
        } else {
            log.debug("insertFile 1 >> openFileYn : {}\n filePath : {}\n dtos : {}\n targetSeq : {}", openFileYn, targetCd, dto, targetSeq);

            FileInsertRequest insert = RestUtil.convert(dto, FileInsertRequest.class);
            insert.setOpenFileYn(openFileYn);
            insert.setTargetSeq(targetSeq);
            insert.setTargetCd(targetCd);
            insert.setFilePath(dto.getFilePath());
            insert.setFileExtensionName(dto.getFileExtensionName());
            insert.setFileOriginalName(dto.getFileOriginalName());
            insert.setUseYn(true);

            MapperUtil.setBaseRequest(insert);

            setFileInfo(insert);

            int cnt = attachingFileMapper.insertAttachingFile(insert);

            if (clazz.equals(Integer.class)) {
                Integer response = cnt;
                return (T) response;
            } else {
                return (T) insert;
            }
        }
    }

    private void setFileInfo(FileInsertRequest insert) {
        log.debug("setFileInfo >> openFileYn : {}\n filePath : {}\n dtos : {}\n targetCd : {}", insert.getOpenFileYn(), insert.getTargetCd().getDesc(), insert.getTargetSeq());

        if ("local".equals(profile)) {
            // Local 파일 이동 public/private
            moveFile(insert);
        } else {
            // S3 File Move(temp to public/private)
            moveS3TempBucketToS3Bucket(insert);
        }
    }

    public <T> T insertFileList(Boolean openFileYn, FileTargetCd targetCd, FileUploadDto[] dtos, Long targetSeq, Class<T> clazz) {
        if (!Integer.class.equals(clazz) && !FileInsertRequest.class.equals(clazz)) {
            throw new RuntimeException();
        } else {
            log.debug("insertFile Multi >> openFileYn : {}\n targetCd : {}\n dtos : {}\n targetSeq : {}", openFileYn, targetCd, dtos, targetSeq);

            Integer response = 0;
            List<FileInsertRequest> listFile = new ArrayList<>();
            for (FileUploadDto dto : dtos) {
                if (null == dto.getAttachingFileSeq() && (null == dto.getDelYn() || !dto.getDelYn())) {
                    //파일등록
                    FileInsertRequest FileInsertRequest = insertFile(openFileYn, targetCd, dto, targetSeq, FileInsertRequest.class);
                    listFile.add(FileInsertRequest);
                } else {
                    //파일 삭제 처리
                    if (null != dto.getAttachingFileSeq() && null != dto.getDelYn() && dto.getDelYn()) {
                        try {
                            FileInsertRequest delete = RestUtil.convert(dto, FileInsertRequest.class);
                            MapperUtil.setBaseRequest(delete);
                            attachingFileMapper.deleteAttachingFile(delete);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
            }

            if (clazz.equals(Integer.class)) {
                response = listFile.size();
                return (T) response;
            } else {
                return (T) listFile;
            }
        }
    }


    public String insertEditorFileList(Long targetSeq, FileTargetCd targetCd, String bodyText, List<FileUploadDto> bodyTextImageList) {
        String replacedBodyText = bodyText;
        if (!ObjectUtils.isEmpty(bodyTextImageList)) {
            List<FileInsertRequest> bodyFileList = (List<FileInsertRequest>) insertFileList(true, targetCd, bodyTextImageList.toArray(new FileUploadDto[bodyTextImageList.size() - 1]), targetSeq, FileInsertRequest.class);
            for (FileInsertRequest fileInsertRequest : bodyFileList) {
                replacedBodyText = replacedBodyText.replaceAll("src=['\"](/temp/.+?)['\"]", "src='" + publicUrl + fileInsertRequest.getFilePath() + "'");
            }
        }
        return replacedBodyText;
    }

    private void moveFile(FileInsertRequest insert) {
        log.debug("moveFile insertVal : {}", insert);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.now();

        String filePath = insert.getFilePath().substring(insert.getFilePath().lastIndexOf("/") + 1);

        makeDirectory(insert.getTargetCd().getDesc() + "/" + now.format(formatter));
        String newFilePath = insert.getTargetCd().getDesc() + "/" + now.format(formatter) + "/" + filePath;

        Path tmpFile = dirLocation.resolve(insert.getFilePath().substring(1));
        Path newFile = dirLocation.resolve(newFilePath).normalize();

        try {
            Files.move(tmpFile, newFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        insert.setFilePath("/" + newFilePath);
    }

    /**
     * S3 Temp Bucket to S3 Bucket(private / public)
     * @param insertVal
     */
    private void moveS3TempBucketToS3Bucket(FileInsertRequest insertVal) {

        log.debug("moveFile insertVal : {}", insertVal);

        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setFileOriginalName(insertVal.getFilePath().substring(insertVal.getFilePath().lastIndexOf("/")+1));
        fileUploadResponse.setTargetCd(insertVal.getTargetCd());
        fileUploadResponse.setOpenFileYn(insertVal.getOpenFileYn());

        String newFilePath = s3FileSyncUtil.syncFile(fileUploadResponse, S3FileSyncUtil.S3FileSyncTypeCd.S3_TMP_TO_S3);

        insertVal.setFilePath(newFilePath+"/"+fileUploadResponse.getFileOriginalName());
    }

    public ResponseEntity<?> downloadFile(FileInsertRequest dto) throws FileNotFoundException {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            FileSelectRequest userFile = null;
            MapperUtil.setBaseRequest(dto);
            attachingFileMapper.selectFile(dto);

            Resource resource = null;
            if (null == userFile) {
                throw new FileNotFoundException("Could not download file");
            } else {

                FileUploadResponse fileInfo = RestUtil.convert(userFile, FileUploadResponse.class);
                fileInfo.setOpenFileYn(userFile.getOpenFileYn());

                log.debug("userFile = {}", userFile);
                log.debug("fileInfo = {}", fileInfo);

                //Path filePath = dirLocation.resolve(userFile.getFilePath().substring(1)).normalize();
                Path filePath = dirLocation.resolve(location + fileInfo.getFilePath()).normalize();
                resource = new UrlResource(filePath.toUri());

                if (resource.exists() || resource.isReadable()) {

                } else {
                    throw new FileNotFoundException("Could not find file");
                }
            }

            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                log.info("Could not determine file type.");
            }
            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            String fileName = userFile.getFileName();

            String header = request.getHeader("User-Agent");
            String name = "";
            if (header.contains("Edge")) {
                name = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            } else if (header.contains("MSIE") || header.contains("Trident")) { // IE 11버전부터 Trident로 변경되었기때문에 추가해준다.
                name = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            } else {
                name = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeDirectory(String path) {
        Path location = dirLocation.resolve(path);
        try {
            Files.createDirectories(location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileUploadResponse saveTemp(MultipartFile file) throws Exception {
        FileUploadResponse fileUploadResponse = new FileUploadResponse();

        ArrayList<MultipartFile> listFile = new ArrayList<MultipartFile>();
        listFile.add(file);

        ArrayList<FileUploadResponse> listFileResponse = saveTemp(listFile);

        if (!listFileResponse.isEmpty()) {
            return listFileResponse.get(0);
        }

        return fileUploadResponse;
    }

    public ArrayList<FileUploadResponse> saveTemp(List<MultipartFile> files) throws Exception {
        ArrayList<FileUploadResponse> listFile = new ArrayList<>();
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                FileUploadResponse fileInfo = new FileUploadResponse();
                if (!file.isEmpty()) {
                    String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                    String urlFilePath = temp + UUID.randomUUID() + "." + ext;
                    Path location = dirLocation.resolve(urlFilePath);

                    makeDirectory(temp);
                    /* 실제 파일이 upload 되는 부분 */
                    Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);

                    fileInfo.setFileExtensionName(file.getContentType());
                    fileInfo.setFilePath("/" + urlFilePath);
                    fileInfo.setFileOriginalName(file.getOriginalFilename());
                    fileInfo.setFileSize(file.getSize());
                    fileInfo.setFileFullPath(location.toFile().getPath());

                    listFile.add(fileInfo);
                }
            }
        }
        return listFile;
    }

    @Cacheable(value = RedisCacheKey.FILE, cacheManager = "cacheManager10minutes", keyGenerator = "redisCacheKeyGenerator")
    public List<FileSelectResponse> getFileList(boolean openFileYn, FileTargetCd targetCd, Long targetSeq) {
        FileSelectRequest req = FileSelectRequest.builder()
                .targetCd(targetCd)
                .targetSeq(targetSeq)
                .openFileYn(openFileYn)
                .build();
        if (openFileYn) {
            req.setFileUrlPrefix(publicUrl);
        } else {
            req.setFileUrlPrefix(privateUrl + "/api/file/download/");
        }

        return attachingFileMapper.getFileList(req);
    }

    public List<FileSelectResponse> getFile(boolean openFileYn, FileTargetCd targetCd, Long targetSeq) {
        FileSelectRequest req = FileSelectRequest.builder()
                .targetCd(targetCd)
                .targetSeq(targetSeq)
                .openFileYn(openFileYn)
                .build();
        if (openFileYn) {
            req.setFileUrlPrefix(publicUrl);
        } else {
            req.setFileUrlPrefix(privateUrl + "/api/file/download/");
        }

        return attachingFileMapper.getFile(req);
    }

    /**
     * S3 파일 업로드 단건
     * @param file
     * @return
     */
    public FileUploadResponse uploadS3(FileUploadResponse file) throws Exception {
        ArrayList<FileUploadResponse> files = new ArrayList<FileUploadResponse>();
        files.add(file);

        List<FileUploadResponse> listFileResponse = this.uploadS3(files);

        if(!listFileResponse.isEmpty()){
            return listFileResponse.get(0);
        }
        return null;
    }

    /**
     * S3 파일 업로드 Multi
     * @param files
     * @return
     */
    public List<FileUploadResponse> uploadS3(List<FileUploadResponse> files) throws Exception {
        List<FileUploadResponse> listFileResponse = new ArrayList<>();
        if(!files.isEmpty()){
            for (FileUploadResponse file : files) {
                s3FileSyncUtil.syncFile(file, S3FileSyncUtil.S3FileSyncTypeCd.EC2_TO_S3_TMP);

                FileUploadResponse fileUploadResponse = new FileUploadResponse();
                fileUploadResponse.setFileOriginalName(file.getFileOriginalName());
                fileUploadResponse.setFileSize(file.getFileSize());
                fileUploadResponse.setFilePath(file.getFilePath());
                fileUploadResponse.setFileExtensionName(file.getFileExtensionName());

                listFileResponse.add(fileUploadResponse);
            }
        }
        return listFileResponse;
    }

    /**
     * 웹에디터 S3 파일 업로드 단건
     * @param file
     * @return
     */
    public FileUploadResponse uploadS3WebEditor(FileUploadResponse file) throws Exception {
        ArrayList<FileUploadResponse> files = new ArrayList<FileUploadResponse>();
        files.add(file);

        List<FileUploadResponse> listFileResponse = this.uploadS3WebEditor(files);

        if(!listFileResponse.isEmpty()){
            return listFileResponse.get(0);
        }
        return null;
    }

    /**
     * 웹에디터 S3 파일 업로드 Multi
     * @param files
     * @return
     */
    public List<FileUploadResponse> uploadS3WebEditor(List<FileUploadResponse> files) throws Exception {
        List<FileUploadResponse> listFileResponse = new ArrayList<>();
        if(!files.isEmpty()){
            for (FileUploadResponse file : files) {

                FileInsertRequest insertRequest = new FileInsertRequest();
                insertRequest.setFilePath(file.getFilePath());
                insertRequest.setTargetCd(FileTargetCd.webeditor_temp);
                insertRequest.setOpenFileYn(true);

                if ("local".equals(profile)) {
                    // Local 파일 이동 public/private
                    moveFile(insertRequest);
                } else {
                    s3FileSyncUtil.syncFile(file, S3FileSyncUtil.S3FileSyncTypeCd.EC2_TO_S3_TMP);

                    // S3 File Move(temp to public/private)
                    moveS3TempBucketToS3Bucket(insertRequest);
                }
                log.debug("file = {}", file);
                log.debug("insertRequest = {}", insertRequest);

                FileUploadResponse fileUploadResponse = new FileUploadResponse();
                fileUploadResponse.setFileOriginalName(file.getFileOriginalName());
                fileUploadResponse.setFileSize(file.getFileSize());
                fileUploadResponse.setFilePath(publicUrl+insertRequest.getFilePath());
                fileUploadResponse.setFileExtensionName(file.getFileExtensionName());

                log.debug("insertRequest = {}", fileUploadResponse);
                listFileResponse.add(fileUploadResponse);
            }
        }
        return listFileResponse;
    }

}
