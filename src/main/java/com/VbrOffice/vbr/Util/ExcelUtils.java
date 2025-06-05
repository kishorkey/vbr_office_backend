package com.VbrOffice.vbr.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.VbrOffice.vbr.Entity.UserRole;





public class ExcelUtils {
	
	 public static final String[] userRole= {"username" ,"role"} ;

    public static ByteArrayInputStream generateExcelFile(List<UserRole> users) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("Result");

            // Create the header row
            CellStyle liteGreen = workbook.createCellStyle();
            liteGreen.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            liteGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < 2; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(userRole[i]);
            }

            // Create the data rows
            int rowIndex = 1;
            for (UserRole row : users) {
                XSSFRow Row = sheet.createRow(rowIndex++);
                Row.createCell(0).setCellValue(row.getUsername());
                Row.createCell(1).setCellValue(row.getRoles().toString());
                  
            }

            // Autosize the columns
//            for (int i = 0; i < columns.size(); i++) {
//                sheet.autoSizeColumn(i);
//            }

            // Write the workbook to the output stream
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
            
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }
}