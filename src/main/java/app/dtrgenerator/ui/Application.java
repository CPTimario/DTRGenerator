package app.dtrgenerator.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.dtrgenerator.constant.DateFormat;
import app.dtrgenerator.constant.Default;
import app.dtrgenerator.model.DateTimeRecord;
import app.dtrgenerator.model.Session;
import app.dtrgenerator.model.Student;
import app.dtrgenerator.util.DateTimeUtil;
import app.dtrgenerator.util.ExcelUtil;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Application extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "DTR Generator";

    private JPanel panel = new JPanel();

    private JLabel lblTitle;

    private JLabel lblName;
    private JTextField txtName;

    private JLabel lblCutOff;
    private JLabel lblTo;
    private JDatePickerImpl dpStartDate;
    private JDatePickerImpl dpEndDate;

    private JLabel lblExcelPath;
    private JTextField txtExcelPath;
    private JButton btnOpenExcelPath;

    private JLabel lblSavePath;
    private JTextField txtSavePath;
    private JButton btnOpenSavePath;

    private JButton btnExport;

    private int currentRowIndex;
    private int sessionCount;

    public static long SERIAL_VERSION_UID = serialVersionUID;

    public Application() {
        super(TITLE);
        try {
            setDefaultLookAndFeel();
            initializeComponents();
        } catch (Exception exception) {
            Prompt.displayError(Message.INITIALIZE_ERROR);
        }
    }

    private void setDefaultLookAndFeel() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    public static void run() {
        Application application = new Application();
        application.setSize(720, 280);
        application.setLocationRelativeTo(null);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setVisible(true);
    }

    private void initializeComponents() {
        UtilDateModel startDateModel = new UtilDateModel();
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel);
        UtilDateModel endDateModel = new UtilDateModel();
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel);

        panel = new JPanel();
        lblTitle = new JLabel(TITLE);

        lblName = new JLabel("Name: ");
        txtName = new JTextField();

        lblCutOff = new JLabel("Cut-Off: ");
        lblTo = new JLabel(" ~ ");
        dpStartDate = new JDatePickerImpl(startDatePanel);
        dpEndDate = new JDatePickerImpl(endDatePanel);

        lblExcelPath = new JLabel("Excel Data: ");
        txtExcelPath = new JTextField();
        btnOpenExcelPath = new JButton("Open");

        lblSavePath = new JLabel("Save As: ");
        txtSavePath = new JTextField();
        btnOpenSavePath = new JButton("Save As");

        btnExport = new JButton("Export");

        setLayout();
        setComponentFont();
        setActionListener();
    }

    private void setComponentFont() {
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel || component instanceof JButton) {
                component.setFont(Default.BOLD_FONT);
            } else if (component instanceof JDatePicker) {
                JDatePickerImpl datePicker = (JDatePickerImpl) component;
                JFormattedTextField textField = datePicker.getJFormattedTextField();
                textField.setFont(Default.FONT);
            } else {
                component.setFont(Default.FONT);
            }
        }
        lblTitle.setFont(Default.TITLE_FONT);
    }

    private void setActionListener() {
        btnOpenExcelPath.addActionListener(getOpenActionListener(txtExcelPath));
        btnOpenSavePath.addActionListener(getSaveActionListener(txtSavePath));
        btnExport.addActionListener(getExportActionListener());
    }

    private ActionListener getOpenActionListener(JTextField textField) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(Default.SAVE_PATH);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(Default.FILE_FILTER);
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getPath());
                }
            }
        };
    }

    private ActionListener getSaveActionListener(JTextField textField) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(Default.SAVE_PATH);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(Default.FILE_FILTER);
                fileChooser.setSelectedFile(new File(Default.FILE_NAME));
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getPath());
                }
            }
        };
    }

    private ActionListener getExportActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtName.getText().equals(""))
                    Prompt.displayError(String.format(Message.INPUT_ERROR, "'Name'"));
                else if (Objects.isNull(dpStartDate.getModel().getValue()))
                    Prompt.displayError(String.format(Message.INPUT_ERROR, "'Cut-Off'"));
                else if (Objects.isNull(dpEndDate.getModel().getValue()))
                    Prompt.displayError(String.format(Message.INPUT_ERROR, "'Cut-Off'"));
                else if (txtExcelPath.getText().equals(""))
                    Prompt.displayError(String.format(Message.INPUT_ERROR, "'Excel Data'"));
                else if (txtSavePath.getText().equals(""))
                    Prompt.displayError(String.format(Message.INPUT_ERROR, "'Save As'"));
                else
                    export();
            }
        };
    }

    private void setLayout() {
        GroupLayout layout = getApplicationLayout();
        panel.setLayout(layout);
        add(panel);
    }

    private GroupLayout getApplicationLayout() {
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        setHorizontalLayout(layout);
        setVerticalLayout(layout);
        return layout;
    }

    private void setHorizontalLayout(GroupLayout layout) {
        Group horizontalGroup = layout.createParallelGroup(Alignment.CENTER);
        Group firstSubGroup = getFirstLayoutSubGroup(layout, true);
        Group secondSubGroup = getSecondLayoutSubGroup(layout, true);
        Group thirdSubGroup = getThirdLayoutSubGroup(layout, true);
        horizontalGroup.addComponent(lblTitle);
        horizontalGroup.addGroup(firstSubGroup);
        horizontalGroup.addGroup(secondSubGroup);
        horizontalGroup.addGroup(thirdSubGroup);
        horizontalGroup.addComponent(btnExport);
        layout.setHorizontalGroup(horizontalGroup);
    }

    private void setVerticalLayout(GroupLayout layout) {
        Group verticalGroup = layout.createSequentialGroup();
        Group firstSubGroup = getFirstLayoutSubGroup(layout, false);
        Group secondSubGroup = getSecondLayoutSubGroup(layout, false);
        Group thirdSubGroup = getThirdLayoutSubGroup(layout, false);
        verticalGroup.addGap(20, 20, 20);
        verticalGroup.addComponent(lblTitle);
        verticalGroup.addGap(20, 20, 20);
        verticalGroup.addGroup(firstSubGroup);
        verticalGroup.addGroup(secondSubGroup);
        verticalGroup.addGroup(thirdSubGroup);
        verticalGroup.addGap(15, 15, 15);
        verticalGroup.addComponent(btnExport);
        layout.setVerticalGroup(verticalGroup);
    }

    private Group getFirstLayoutSubGroup(GroupLayout layout, boolean isSequential) {
        Group group;
        if (isSequential) {
            group = layout.createSequentialGroup();
            group.addComponent(lblName);
            group.addComponent(txtName, 200, 200, 200);
            group.addGap(20, 20, 20);
            group.addComponent(lblCutOff);
            group.addComponent((Component) dpStartDate, 150, 150, 150);
            group.addComponent(lblTo);
            group.addComponent((Component) dpEndDate, 150, 150, 150);
        } else {
            group = layout.createParallelGroup(Alignment.BASELINE);
            group.addComponent(lblName);
            group.addComponent(txtName);
            group.addComponent(lblCutOff);
            group.addComponent((Component) dpStartDate);
            group.addComponent(lblTo);
            group.addComponent((Component) dpEndDate);
        }
        return group;
    }

    private Group getSecondLayoutSubGroup(GroupLayout layout, boolean isSequential) {
        Group group;
        if (isSequential) {
            group = layout.createSequentialGroup();
            group.addComponent(lblExcelPath);
            group.addComponent(txtExcelPath, 500, 500, 500);
            group.addComponent(btnOpenExcelPath);
        } else {
            group = layout.createParallelGroup(Alignment.BASELINE);
            group.addComponent(lblExcelPath);
            group.addComponent(txtExcelPath);
            group.addComponent(btnOpenExcelPath);
        }
        return group;
    }

    private Group getThirdLayoutSubGroup(GroupLayout layout, boolean isSequential) {
        Group group;
        if (isSequential) {
            group = layout.createSequentialGroup();
            group.addComponent(lblSavePath);
            group.addComponent(txtSavePath, 500, 500, 500);
            group.addComponent(btnOpenSavePath);
        } else {
            group = layout.createParallelGroup(Alignment.BASELINE);
            group.addComponent(lblSavePath);
            group.addComponent(txtSavePath);
            group.addComponent(btnOpenSavePath);
        }
        return group;
    }

    private void export() {
        try {
            DateTimeRecord dateTimeRecord = getDateTimeRecord();
            Workbook workbook = generateWorkbook(dateTimeRecord);
            ExcelUtil.export(workbook, txtSavePath.getText());
            Path fileSavePath = Paths.get(txtSavePath.getText());
            String successMessage = String.format(Message.EXPORT_SUCCESS, fileSavePath.getFileName().toString());
            Prompt.displayInformation(successMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DateTimeRecord getDateTimeRecord() throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(txtExcelPath.getText());
        List<Cell> searchResults = ExcelUtil.search(workbook, txtName.getText());
        Date startDate = DateTimeUtil.parseDate(dpStartDate.getJFormattedTextField().getText(), DateFormat.SHORT);
        Date endDate = DateTimeUtil.parseDate(dpEndDate.getJFormattedTextField().getText(), DateFormat.SHORT);
        return new DateTimeRecord(startDate, endDate, searchResults);
    }

    private Workbook generateWorkbook(DateTimeRecord dateTimeRecord) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DTR");
        CellStyle studentNameStyle = getStudentNameStyle(workbook);
        CellStyle defaultStyle = getDefaultStyle(workbook);
        currentRowIndex = 0;
        sessionCount = 0;
        writeDateHeader(sheet, dateTimeRecord.getStartDate(), dateTimeRecord.getEndDate());
        for (Student student : dateTimeRecord.getStudents()) {
            writeStudentName(sheet, studentNameStyle, student.getName());
            writeSessionHeader(sheet);
            for (Session session : student.getSessions())
                writeSessionData(sheet, defaultStyle, session);
            sessionCount += student.getSessions().size();
        }
        writeSummary(sheet, currentRowIndex + 1, defaultStyle, sessionCount);
        sheet.autoSizeColumn(CellReference.convertColStringToIndex("A"));
        return workbook;
    }

    private CellStyle getStudentNameStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        return style;
    }

    private CellStyle getDefaultStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private void writeDateHeader(Sheet sheet, Date startDate, Date endDate) {
        int firstRow = 0;
        Row row = sheet.createRow(firstRow);
        Cell cell = row.createCell(CellReference.convertColStringToIndex("A"));
        CellStyle style = sheet.getWorkbook().createCellStyle();
        String value = "Cut Off: " + DateTimeUtil.dateRangeToString(startDate, endDate);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        cell.setCellValue(value);
        cell.setCellStyle(style);
        currentRowIndex++;
    }

    private void writeStudentName(Sheet sheet, CellStyle style, String name) {
        String studentNameRange = String.format("A%1$s:D%1$s", currentRowIndex + 1);
        Row row = sheet.createRow(currentRowIndex);
        Cell cell = row.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue(name);
        cell.setCellStyle(style);
        sheet.addMergedRegion(CellRangeAddress.valueOf(studentNameRange));
        currentRowIndex++;
    }

    private void writeSessionHeader(Sheet sheet) {
        List<String> sessionHeaders = List.of("DATE", "IN", "OUT");
        Row row = sheet.createRow(currentRowIndex);
        Font font = sheet.getWorkbook().createFont();
        CellStyle style = sheet.getWorkbook().createCellStyle();
        font.setBold(true);
        font.setColor(IndexedColors.RED.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        for (String header : sessionHeaders) {
            Cell cell = row.createCell(sessionHeaders.indexOf(header));
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }
        currentRowIndex++;
    }

    private void writeSessionData(Sheet sheet, CellStyle style, Session session) {
        Row row = sheet.createRow(currentRowIndex);
        Map<String, String> dataByColumn = session.toDataByColumn();
        for (String column : dataByColumn.keySet()) {
            int columnIndex = CellReference.convertColStringToIndex(column);
            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(dataByColumn.get(column));
            cell.setCellStyle(style);
        }
        currentRowIndex++;
    }

    private void writeSummary(Sheet sheet, int rowIndex, CellStyle style, int sessionCount) {
        Row row = sheet.createRow(rowIndex);
        Cell totalLabel = row.createCell(CellReference.convertColStringToIndex("A"));
        Cell totalValue = row.createCell(CellReference.convertColStringToIndex("B"));
        totalLabel.setCellValue("Total:");
        totalLabel.setCellStyle(style);
        totalValue.setCellValue(sessionCount);
        totalValue.setCellStyle(style);
    }
}