package app.dtrgenerator.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.dtrgenerator.constant.Constant;
import app.dtrgenerator.model.CellStyleBuilder;
import app.dtrgenerator.model.DTR;
import app.dtrgenerator.model.Session;
import app.dtrgenerator.model.Student;

public class ExcelUtil {
    public static List<Cell> search(Workbook workbook, String search) {
        List<Cell> results = new ArrayList<Cell>();

        for (Sheet sheet : workbook) {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType().equals(CellType.STRING)
                            && cell.getStringCellValue().toLowerCase().contains(search.toLowerCase())) {
                        results.add(cell);
                    }
                }
            }
        }

        return results;
    }

    public static boolean isCellMatches(Cell cell, Pattern pattern) {
        return cell.getCellType().equals(CellType.STRING) && pattern.matcher(cell.getStringCellValue()).find();
    }

    public static boolean isCellWithinDate(Cell cell, Date startDate, Date endDate) {
        return cell.getCellType().equals(CellType.NUMERIC) && DateUtil.isCellDateFormatted(cell)
                && DateTimeUtil.isDateInBetween(cell.getDateCellValue(), startDate, endDate);
    }

    public static void export(DTR dtr, String exportPath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DTR");

        List<String> sessionHeaders = new ArrayList<String>(List.of("DATE", "IN", "OUT"));

        int rowIndex = 0;
        int sessionCount = 0;
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(0);
        cell.setCellValue("Cut Off: " + DateTimeUtil.dateRangeToString(dtr.getStartDate(), dtr.getEndDate()));
        cell.setCellStyle(new CellStyleBuilder().setFillPattern(FillPatternType.SOLID_FOREGROUND)
                .setFillColor(Constant.GREEN).build(workbook));

        for (Student student : dtr.getStudents()) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(student.getName());
            cell.setCellStyle(new CellStyleBuilder().setFillPattern(FillPatternType.SOLID_FOREGROUND)
                    .setFillColor(Constant.YELLOW).build(workbook));
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 3));

            rowIndex++;
            row = sheet.createRow(rowIndex);
            for (String header : sessionHeaders) {
                cell = row.createCell(sessionHeaders.indexOf(header));
                cell.setCellValue(header);
                cell.setCellStyle(new CellStyleBuilder().setBold(true).setFontColor(Constant.RED)
                        .setTextAlign(HorizontalAlignment.CENTER).build(workbook));
            }

            for (Session session : student.getSessions()) {
                rowIndex++;
                sessionCount++;
                row = sheet.createRow(rowIndex);

                cell = row.createCell(0);
                cell.setCellValue(DateTimeUtil.formatDate(session.getDate(), Constant.DATE_FORMAT_DASH));
                cell.setCellStyle(new CellStyleBuilder().setTextAlign(HorizontalAlignment.CENTER).build(workbook));

                cell = row.createCell(1);
                cell.setCellValue(DateTimeUtil.formatTime(session.getTimeIn(), Constant.TIME_FORMAT));
                cell.setCellStyle(new CellStyleBuilder().setTextAlign(HorizontalAlignment.CENTER).build(workbook));

                cell = row.createCell(2);
                cell.setCellValue(DateTimeUtil.formatTime(session.getTimeOut(), Constant.TIME_FORMAT));
                cell.setCellStyle(new CellStyleBuilder().setTextAlign(HorizontalAlignment.CENTER).build(workbook));

                cell = row.createCell(3);
                cell.setCellValue(DateTimeUtil.formatTime(session.getTimeIn(), Constant.TIME_FORMAT_MERIDIEM));
                cell.setCellStyle(new CellStyleBuilder().setTextAlign(HorizontalAlignment.CENTER).build(workbook));
            }
        }

        row = sheet.createRow(rowIndex + 2);
        cell = row.createCell(0);
        cell.setCellValue("Total:");
        cell = row.createCell(1);
        cell.setCellValue(sessionCount);
        cell.setCellStyle(new CellStyleBuilder().setTextAlign(HorizontalAlignment.CENTER).build(workbook));

        sheet.autoSizeColumn(0);

        OutputStream outputStream = new FileOutputStream(new File(exportPath));
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}