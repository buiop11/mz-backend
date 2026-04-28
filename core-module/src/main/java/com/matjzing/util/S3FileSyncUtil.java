package com.matjzing.util;

import com.matjzing.dto.file.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3FileSyncUtil {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${file.upload.public-s3-bucket}")
    private String publicS3Bucket;

    @Value("${file.upload.private-s3-bucket}")
    private String privateS3Bucket;

    @Value("${file.upload.temp-s3-bucket}")
    private String tempS3Bucket;


    @Value("${file.upload.shell-ec2-to-s3-tmp-sync}")
    private String shellEC2ToS3TmpSync;

    @Value("${file.upload.shell-s3-tmp-to-s3-sync}")
    private String shellS3TmpToS3Sync;

    @Value("${file.upload.shell-s3-to-ec2-sync}")
    private String shellS3ToEC2Sync;

    @Value("${file.upload.location}")
    private String location;
    
    /**
     * S3 file sync<br/>
     * 1. EC2 							-> S3 Tmp Bucket<br/>
     * 2. S3 Temp 						-> S3 Bucket(public/private)<br/>
     * 3. S3 S3 Bucket(public/private) 	-> EC2<br/>
     *
     * @param fileInfo
     * @param syncTypeCd
     * @return
     * @throws Exception
     */
    public String syncFile(FileUploadResponse fileInfo, S3FileSyncTypeCd syncTypeCd) {
        log.debug("fileInfo : {}", fileInfo);

        String savedS3Path = "";
        String savedPath = fileInfo.getFileFullPath();
        String savedName = fileInfo.getFileOriginalName();

        String s3BucketName = "";

        if (null == fileInfo.getOpenFileYn()) {
            s3BucketName = tempS3Bucket;
        } else if (fileInfo.getOpenFileYn().equals(Boolean.TRUE)) {
            s3BucketName = publicS3Bucket;
        } else if (fileInfo.getOpenFileYn().equals(Boolean.FALSE)) {
            s3BucketName = privateS3Bucket;
        }

        String shellScript = "";

        try {
            // shell arg Setting
            ArrayList<String> argList = new ArrayList<String>();

            if (syncTypeCd.equals(S3FileSyncTypeCd.EC2_TO_S3_TMP)) {
                // 01_ec2_to_s3_tmp_file_sync.sh [FILE_FULL_PATH] [S3_TEMP_BUCKET_NAME]
                argList.add(savedPath);                // FILE_FULL_PATH
                argList.add(tempS3Bucket);            // S3_TEMP_BUCKET_NAME

                savedS3Path = tempS3Bucket + "/" + savedName;
                shellScript = shellEC2ToS3TmpSync;

            } else if (syncTypeCd.equals(S3FileSyncTypeCd.S3_TMP_TO_S3)) {
                // 02_s3_tmp_to_s3_bucket_sync.sh [FILE_NAME] [S3_BUCKET_FROM_PATH] [S3_BUCKET_TO_PATH]
                savedS3Path = "/" + fileInfo.getTargetCd().getDesc() + "/" + calcS3YearMonthPath();

                argList.add(savedName);                        // FILE_NAME
                argList.add(tempS3Bucket);                    // S3_BUCKET_FROM_PATH
                argList.add(s3BucketName + savedS3Path);    // S3_BUCKET_TO_PATH

                shellScript = shellS3TmpToS3Sync;

            } else if (syncTypeCd.equals(S3FileSyncTypeCd.S3_TO_EC2)) {
                // 03_s3_to_ec2_file_sync.sh [S3_BUCKET_FULL_PATH] [EC2_FILE_PATH]
                argList.add(s3BucketName + fileInfo.getFilePath());        // S3_BUCKET_FULL_PATH
                argList.add(location + fileInfo.getFilePath());        // EC2_FILE_PATH

                savedS3Path = location + fileInfo.getFilePath();

                shellScript = shellS3ToEC2Sync;
            }

            log.debug("\n\n\nprofile : {}", profile);
            log.debug("S3FileSyncTypeCd : {}", syncTypeCd);
            log.debug("Shell File : {}", shellScript);
            log.debug("savedS3Path : {}", savedS3Path);
            log.debug("Args : {}\n\n\n", argList);

            if (null != argList && !argList.isEmpty() && null != profile && !profile.equals("local")) {
                RunShellScript.exec(shellScript, argList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("FILE UPLOAD : >>>>>>>>>>> " + e.getMessage());
        }

        return savedS3Path;
    }

    /**
     * S3 file folder date name create
     *
     * @return
     */
    private String calcS3YearMonthPath() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        return sd.format(new Date());
    }


    public enum S3FileSyncTypeCd {
        EC2_TO_S3_TMP("EC2에서 S3 tmp로 파일 복제 > 01_ec2_to_s3_tmp_file_sync.sh [FILE_PATH] [FILE_NAME] [S3_BUCKET_NAME]"),
        S3_TMP_TO_S3("S3 tmp에서 실제 사용 할 버킷으로 파일 이동 > 02_s3_tmp_to_s3_bucket_sync.sh [FILE_NAME] [S3_BUCKET_FROM_PATH] [S3_BUCKET_TO_PATH]"),
        S3_TO_EC2("S3에서 EC2로 파일 복제 > 03_s3_to_ec2_file_sync.sh [S3_BUCKET_PATH] [FILE_NAME] [EC2_PATH]");

        private final String desc;

        public String getDesc() {
            return desc;
        }

        S3FileSyncTypeCd(String desc) {
            this.desc = desc;
        }
    }

}
