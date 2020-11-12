package app.dtrgenerator.constant;

import java.awt.Font;

import javax.swing.filechooser.FileNameExtensionFilter;

public class Default {
    public static final String SAVE_PATH = System.getProperty("user.home") + "\\Downloads";
    public static final String FILE_NAME = "DTR.xlsx";
    public static final FileNameExtensionFilter FILE_FILTER = new FileNameExtensionFilter("Excel File", "xlsx");
    public static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
    public static final Font BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    public static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
}