package app.dtrgenerator.model;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class CellStyleBuilder {
    private HorizontalAlignment textAlignment;
    private Boolean bold;
    private Color fontColor;
    private Color fillColor;
    private FillPatternType fillPattern;

    public CellStyleBuilder() {
        this.textAlignment = HorizontalAlignment.LEFT;
        this.bold = false;
        this.fontColor = new XSSFColor(IndexedColors.BLACK, null);
        this.fillColor = new XSSFColor(IndexedColors.WHITE, null);
        this.fillPattern = FillPatternType.NO_FILL;
    }

    public CellStyleBuilder setTextAlign(HorizontalAlignment textAlignment) {
        this.textAlignment = textAlignment;
        return this;
    }

    public CellStyleBuilder setBold(Boolean bold) {
        this.bold = bold;
        return this;
    }

    public CellStyleBuilder setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public CellStyleBuilder setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public CellStyleBuilder setFillPattern(FillPatternType fillPattern) {
        this.fillPattern = fillPattern;
        return this;
    }

    public CellStyle build(Workbook workbook) {
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setAlignment(this.textAlignment);
        
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(this.bold);
        font.setColor((XSSFColor) this.fontColor);
        cellStyle.setFont(font);

        cellStyle.setFillForegroundColor((XSSFColor) this.fillColor);
        cellStyle.setFillPattern(this.fillPattern);

        return cellStyle;
    }
}