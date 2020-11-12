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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {
    public static List<Cell> search(Workbook workbook, String search) {
        List<Cell> results = new ArrayList<Cell>();
        for (Sheet sheet : workbook) {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cellContainsIgnoreCase(cell, search)) {
                        results.add(cell);
                    }
                }
            }
        }
        return results;
    }

    public static boolean isCellMatches(Cell cell, Pattern pattern) {
        if (!cell.getCellType().equals(CellType.STRING))
            return false;
        return pattern.matcher(cell.getStringCellValue()).find();
    }

    public static boolean isCellWithinDate(Cell cell, Date startDate, Date endDate) {
        if (!cell.getCellType().equals(CellType.NUMERIC) || !DateUtil.isCellDateFormatted(cell))
            return false;
        return DateTimeUtil.isInBetween(cell.getDateCellValue(), startDate, endDate);
    }

    public static void export(Workbook workbook, String exportPath) throws IOException {
        OutputStream outputStream = new FileOutputStream(new File(exportPath));
        workbook.write(outputStream);
        outputStream.close();
    }

    private static String getCellStringValue(Cell cell) {
        Object value;
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case NUMERIC:
                value = getCellNumericValue(cell);
                break;
            default:
                value = "";
                break;
        }
        return value.toString();
    }

    private static Object getCellNumericValue(Cell cell) {
        if (DateUtil.isCellDateFormatted(cell))    
            return cell.getDateCellValue();
        else
            return cell.getNumericCellValue();
    }

    private static boolean cellContainsIgnoreCase(Cell cell, String text) {
        String cellValue = getCellStringValue(cell);
        return cellValue.toLowerCase().contains(text.toLowerCase());
    }
}