package app.dtrgenerator.constant;

import java.awt.Font;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class Constant {
    public static final String APP_TITLE = "DTR Generator";
    public static final String LBL_NAME = "Name: ";
    public static final String LBL_CUTOFF = "Cut-Off: ";
    public static final String LBL_TO = " ~ ";
    public static final String LBL_EXCEL_PATH = "Excel Data: ";
    public static final String LBL_SAVE_PATH = "Save As: ";
    public static final String BTN_OPEN = "Open";
    public static final String BTN_SAVE_AS = "Save As";
    public static final String BTN_EXPORT = "Export";

    public static final String NAME_TOOLTIP = "Name";
    public static final String EXCEL_PATH_TOOLTIP = "Excel Data";
    public static final String SAVE_PATH_TOOLTIP = "Save As";

    public static final String ERROR_TITLE = "Error";
    public static final String ERROR_MESSAGE = "Please input ";
    public static final String SUCCESS_TITLE = "Success";
    public static final String SUCCESS_MESSAGE = " successfully exported.";

    public static final String DEFAULT_PATH = System.getProperty("user.home") + "\\Downloads";
    public static final String DEFAULT_FILE_NAME = "DTR.xlsx";
    public static final FileNameExtensionFilter XLSX_FILTER = new FileNameExtensionFilter("Excel File", "xlsx");

    public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
    public static final Font BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    public static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);

    public static final String DATE_FORMAT_SLASH = "MM/dd/yyyy";
    public static final String DATE_FORMAT_DASH = "d-MMM-yyyy";
    public static final String DATE_FORMAT_FULL = "MMMM d, yyyy";
    public static final String DATE_FORMAT_SHORT = "MMM d, yyyy";
    public static final String DATE_FORMAT_MONTH_DAY = "MMMM d";
    public static final String TIME_FORMAT = "hh:mm";
    public static final String TIME_FORMAT_MERIDIEM = "a";

    private static final String TIME_IN_REGEX = "time in\\s*[:-]?\\s*(\\d{1,2}:\\d{2})";
    private static final String TIME_OUT_REGEX = "time out\\s*[:-]?\\s*(\\d{1,2}:\\d{2})";
    public static final Pattern TIME_IN_PATTERN = Pattern.compile(TIME_IN_REGEX, Pattern.CASE_INSENSITIVE);
    public static final Pattern TIME_OUT_PATTERN = Pattern.compile(TIME_OUT_REGEX, Pattern.CASE_INSENSITIVE);

    public static final Color GREEN = new XSSFColor(new java.awt.Color(0, 255, 0), null);
    public static final Color YELLOW = new XSSFColor(new java.awt.Color(255, 255, 0), null);
    public static final Color RED = new XSSFColor(new java.awt.Color(255, 0, 0), null);
}