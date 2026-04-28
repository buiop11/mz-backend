package com.matjzing.util;

import com.matjzing.dto.common.enumeration.FileTargetCd;
import com.matjzing.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExcelDownloadUtil {

	@Value("${file.upload.temp}")
	private String temp;

	@Value("${file.upload.location}")
	private String location;

	private Path dirLocation;

	@PostConstruct
	public void init(){
		this.dirLocation = Paths.get(location).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.dirLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final CommonFileService commonFileService;

	/**
	 * @Code
	 * <pre>
	 *
	 *      List<SelectPageResponse> response = mapper.selectList(req);
	 *      List<List<String>> excelData = new ArrayList<>();
	 *
	 *      if (!ObjectUtils.isEmpty(response)) {
	 * 		    for (SelectPageResponse res : response) {
	 * 		        excelData.add(Arrays.asList(
	 * 		    	    String.valueOf(res.getCampaignSeq()),
	 * 		    	    exposureStatusCd,
	 * 		    	    adminExcelDownloadUtil.isNullDateFormat(res.getExposureBeginDt(), dtf),
	 * 		    	    adminExcelDownloadUtil.isNullDateFormat(res.getExposureEndDt(), dtf),
	 * 		    	    res.getCampaignTypeCd().getPath(),
	 * 		    	    osSectionCd,
	 * 		    	    res.getCampaignManagementTitle(),
	 * 		    	    String.valueOf(res.getTotalBudgetAmount()),
	 * 		    	    String.valueOf(exhaustionAmount),
	 * 		    	    String.valueOf(res.getDailyLimitSetupAmount())
	 * 		        ));
	 * 	        }
	 *       }
	 *
	 * 		List<String> headers = Arrays.asList("혜택 번호", "노출상태", "혜택 시작일", "혜택 종료일", "혜택 유형", "OS", "혜택명", "총 예산", "총 소진금액", "일별한도");
	 * 		String dateRange = excelDownloadUtil.makeDateRange(req.getBeginDt(), req.getEndDt());
	 *
	 * 		String fileName = "혜택목록";
	 * 		if (StringUtils.hasText(dateRange)) {
	 * 			fileName += "_" + dateRange;
	 *        }
	 * 		fileName += ".xlsx";
	 *
	 * 	    // fileName : 혜택목록_20230101~20231231.xlsx(한글 파일명은 이슈가 있어서 frontend 에서 파일명 만듦)
	 *
	 * 		return excelDownloadUtil.makeSimpleExcel(headers, excelData, fileName, dateRange);
	 * </pre>
	 *
	 *
	 *
	 * @param headers
	 * @param excelData
	 * @param fileName
	 * @param sheetName
	 * @return
	 */
	public ResponseEntity<?> makeSimpleExcel(final List<String> headers, final List<List<String>> excelData, final String fileName, final String sheetName) {

		LocalDate now = LocalDate.now();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = null;
		String filePath = "";

		String tempFileName = UUID.randomUUID() + ".xlsx";

		try {
			// 엑셀 파일 생성
			if (StringUtils.hasText(sheetName)) {
				sheet = workbook.createSheet(sheetName); // 시트 생성
			} else {
				sheet = workbook.createSheet(); // 시트 생성
			}

			// 컬럼 생성
			for (int i = 0; i < headers.size() ; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);
			}

			// 헤더 생성
			Row header = sheet.createRow(0);
			for (int i = 0; i < headers.size(); i++) {
				Cell cell = header.createCell(i);
				cell.setCellValue(headers.get(i));
			}

			int startRowNum = 1;

			boolean isExistNoHeader = "번호".equals(headers.get(0));

			if (!ObjectUtils.isEmpty(excelData)) {
				for (int i = 0; i < excelData.size(); i++) {

					Row dataRow = sheet.createRow(startRowNum);
					List<String> data = excelData.get(i);

					for (int j = 0; j < data.size(); j++) {
						Cell dataCell = dataRow.createCell(j);
						if (j == 0 && isExistNoHeader) {
							dataCell.setCellValue(Integer.parseInt(data.get(j)) - i);
						} else {
							dataCell.setCellValue(data.get(j));
						}
					}
					startRowNum++;
				}
			}

			filePath = location + "/" + temp + FileTargetCd.excel + "/" + now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/";

			// 임시 파일업로드 폴더에 저장
			commonFileService.makeDirectory(filePath);
			FileOutputStream fos = new FileOutputStream(filePath + tempFileName);
			workbook.write(fos);
			fos.close();

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return download(filePath, tempFileName, fileName);
	}

	private ResponseEntity<?> download(final String filePath, final String tempFileName, final String fileName) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		String fileNameOrg = null;
		Resource resource = null;
		String contentType = null;

		try {

			Path path = this.dirLocation.resolve(filePath + tempFileName).normalize();

			resource = new UrlResource(path.toUri());

			if (!(resource.exists() || resource.isReadable())) {
				throw new FileNotFoundException("Could not find file");
			}

			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			if(contentType == null) {
				contentType = "application/octet-stream";
			}

			String agent = request.getHeader("User-Agent");
			if (agent.contains("Edge")){
				fileNameOrg = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
			} else if (agent.contains("MSIE") || agent.contains("Trident")) { // IE 11버전부터 Trident로 변경되었기때문에 추가해준다.
				fileNameOrg = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
			} else {
				fileNameOrg = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
			}

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNameOrg + "\"")
				.body(resource);
	}

	public String makeDateRange(final LocalDate beginDt, final LocalDate endDt) {
		String dateRange = "";
		if (!ObjectUtils.isEmpty(beginDt) && !ObjectUtils.isEmpty(endDt)) {
			dateRange += beginDt;
			dateRange += "~";
			dateRange += endDt;
		}
		return dateRange;
	}

	public String isNullDateFormat(final LocalDateTime localDateTime, final DateTimeFormatter dtf) {
		if (ObjectUtils.isEmpty(localDateTime)) {
			return "";
		} else {
			return localDateTime.format(dtf);
		}
	}

	public String isNullDateFormat(final LocalDate localDate, final DateTimeFormatter dtf) {
		if (ObjectUtils.isEmpty(localDate)) {
			return "";
		} else {
			return localDate.format(dtf);
		}
	}

	public String isNullStringToHyphen(final String str) {
		if (StringUtils.hasText(str)) {
			return str;
		} else {
			return "-";
		}
	}

}
