package com.Over2Cloud.common.excel;

import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenericReadExcel7 {
	private static final Log log = LogFactory.getLog(GenericReadExcel7.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public XSSFSheet readExcel(InputStream excelStream) {

		XSSFWorkbook workBook = null;
		XSSFSheet sheet = null;

		try {

			workBook = new XSSFWorkbook(excelStream);
			sheet = (XSSFSheet) workBook.getSheetAt(0);
		} catch (Exception e) {
			log
					.error(
							"@MHDM::>> Problem in GenericReadExcel7 at method readExcel()",
							e);
			e.printStackTrace();
		}

		return sheet;
	}

	public String readExcelSheet(XSSFRow row, int cellIndex) {

		String cellValue = "NA";
		try {
			XSSFCell cell = row.getCell((short) cellIndex);
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				cellValue = "NA";
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					String time = TIME_FORMAT.format(cell.getDateCellValue())
							.substring(11);
					if (time != null && time.equals("00:00:00"))
						cellValue = DATE_FORMAT.format(cell.getDateCellValue());
					else
						cellValue = time;

				} else
					cellValue = new Double(cell.getNumericCellValue())
							.longValue()
							+ "";

			}
		} catch (NullPointerException ne) {
		}

		catch (Exception e) {
			log
					.error(
							"@MHDM::>> Problem in GenericReadExcel7 at method readExcelSheet()",
							e);
		}
		return cellValue;
	}

}
