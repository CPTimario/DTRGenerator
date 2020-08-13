package app.dtrgenerator.ui;

import java.awt.Component;
import java.awt.HeadlessException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.dtrgenerator.constant.Constant;
import app.dtrgenerator.model.DTR;
import app.dtrgenerator.ui.Action.ActionType;
import app.dtrgenerator.ui.ComponentBuilder.ComponentType;
import app.dtrgenerator.util.DateTimeUtil;
import app.dtrgenerator.util.ExcelUtil;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

public class Window extends JFrame {

    private static final long serialVersionUID = 1L;

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

    public Window(String title) throws HeadlessException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        super(title);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        this.initComponents();
        this.initLayout();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void run() {
        this.add(this.panel);
        this.setSize(880, 280);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initComponents() {
        this.panel = new JPanel();

        this.lblTitle = (JLabel) new ComponentBuilder().setText(Constant.APP_TITLE).build(ComponentType.LABEL);

        this.lblName = (JLabel) new ComponentBuilder().setText(Constant.LBL_NAME).build(ComponentType.LABEL);
        this.txtName = (JTextField) new ComponentBuilder().setTooltip(Constant.NAME_TOOLTIP)
                .build(ComponentType.TEXT_FIELD);

        this.lblCutOff = (JLabel) new ComponentBuilder().setText(Constant.LBL_CUTOFF).build(ComponentType.LABEL);
        this.lblTo = (JLabel) new ComponentBuilder().setText(Constant.LBL_TO).build(ComponentType.LABEL);
        this.dpStartDate = (JDatePickerImpl) new ComponentBuilder().build(ComponentType.DATE_PICKER);
        this.dpEndDate = (JDatePickerImpl) new ComponentBuilder().build(ComponentType.DATE_PICKER);

        this.lblExcelPath = (JLabel) new ComponentBuilder().setText(Constant.LBL_EXCEL_PATH).build(ComponentType.LABEL);
        this.txtExcelPath = (JTextField) new ComponentBuilder().setTooltip(Constant.EXCEL_PATH_TOOLTIP)
                .setEnabled(false).build(ComponentType.TEXT_FIELD);
        this.btnOpenExcelPath = (JButton) new ComponentBuilder().setText(Constant.BTN_OPEN)
                .setAction(ActionType.OPEN, this.txtExcelPath).build(ComponentType.BUTTON);

        this.lblSavePath = (JLabel) new ComponentBuilder().setText(Constant.LBL_SAVE_PATH).build(ComponentType.LABEL);
        this.txtSavePath = (JTextField) new ComponentBuilder().setTooltip(Constant.SAVE_PATH_TOOLTIP).setEnabled(false)
                .build(ComponentType.TEXT_FIELD);
        this.btnOpenSavePath = (JButton) new ComponentBuilder().setText(Constant.BTN_SAVE_AS)
                .setAction(ActionType.SAVE, this.txtSavePath).build(ComponentType.BUTTON);

        this.btnExport = (JButton) new ComponentBuilder().setText(Constant.BTN_EXPORT)
                .setAction(ActionType.EXPORT, this).build(ComponentType.BUTTON);
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(this.panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(this.lblTitle)
                        .addGroup(layout.createSequentialGroup().addComponent(this.lblName)
                                .addComponent(this.txtName, 300, 300, 300).addGap(20, 20, 20)
                                .addComponent(this.lblCutOff).addComponent((Component) this.dpStartDate)
                                .addComponent(this.lblTo).addComponent((Component) this.dpEndDate))
                        .addGroup(layout.createSequentialGroup().addComponent(this.lblExcelPath)
                                .addComponent(this.txtExcelPath).addComponent(this.btnOpenExcelPath))
                        .addGroup(layout.createSequentialGroup().addComponent(this.lblSavePath)
                                .addComponent(this.txtSavePath).addComponent(this.btnOpenSavePath))
                        .addComponent(this.btnExport)));

        layout.setVerticalGroup(
                layout.createSequentialGroup().addGap(20, 20, 20).addComponent(this.lblTitle).addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.lblName)
                                .addComponent(this.txtName).addComponent(this.lblCutOff)
                                .addComponent((Component) this.dpStartDate).addComponent(this.lblTo)
                                .addComponent((Component) this.dpEndDate))
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.lblExcelPath)
                                .addComponent(this.txtExcelPath).addComponent(this.btnOpenExcelPath))
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.lblSavePath)
                                .addComponent(this.txtSavePath).addComponent(this.btnOpenSavePath))
                        .addGap(15, 15, 15).addComponent(this.btnExport));

        this.initFont();
    }

    private void initFont() {
        for (Component component : this.panel.getComponents()) {
            if (component instanceof JLabel || component instanceof JButton) {
                component.setFont(Constant.BOLD_FONT);
            } else if (component instanceof JDatePicker) {
                JDatePickerImpl datePicker = (JDatePickerImpl) component;
                JFormattedTextField textField = datePicker.getJFormattedTextField();
                textField.setFont(Constant.DEFAULT_FONT);
            } else {
                component.setFont(Constant.DEFAULT_FONT);
            }
        }
        this.lblTitle.setFont(Constant.TITLE_FONT);
    }

    public void export() {
        try {
            Workbook workbook = new XSSFWorkbook(this.txtExcelPath.getText());
            List<Cell> searchResults = ExcelUtil.search(workbook, this.txtName.getText());
            Date startDate = DateTimeUtil.parseDate(dpStartDate.getJFormattedTextField().getText(),
                    Constant.DATE_FORMAT_SHORT);
            Date endDate = DateTimeUtil.parseDate(dpEndDate.getJFormattedTextField().getText(),
                    Constant.DATE_FORMAT_SHORT);
            DTR dtr = new DTR(startDate, endDate, searchResults);
            workbook.close();

            ExcelUtil.export(dtr, this.txtSavePath.getText());

            Path fileSavePath = Paths.get(txtSavePath.getText());
            this.displayMessage(Constant.SUCCESS_TITLE,
                    fileSavePath.getFileName().toString() + Constant.SUCCESS_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayMessage(String title, String message, int messageType) {
        JLabel label = new JLabel(message);
        label.setFont(Constant.DEFAULT_FONT);
        JOptionPane.showMessageDialog(null, label, title, messageType);
    }
}