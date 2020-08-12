package app.dtrgenerator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import app.dtrgenerator.constant.Constant;
import app.dtrgenerator.util.DateTimeUtil;
import app.dtrgenerator.util.ExcelUtil;

public class DTR {
    private Date startDate;
    private Date endDate;
    private List<Student> students;

    public DTR(Date startDate, Date endDate, List<Cell> searchResults) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.students = new ArrayList<Student>();
        
        for (Cell resultCell : searchResults) {
            Sheet sheet = resultCell.getSheet();
            Session session = new Session();

            for (int i = 1; i < 5; i++) {
                Row row = sheet.getRow(resultCell.getRowIndex() - i);
                Cell cell = row.getCell(resultCell.getColumnIndex());

                if (ExcelUtil.isCellMatches(cell, Constant.TIME_IN_PATTERN)) {
                    Matcher matcher = Constant.TIME_IN_PATTERN.matcher(cell.getStringCellValue());
                    if (matcher.find()) {
                        String timeString = matcher.group(1);
                        session.setTimeIn(DateTimeUtil.parseTime(timeString));
                    }
                } else if (ExcelUtil.isCellMatches(cell, Constant.TIME_OUT_PATTERN)) {
                    Matcher matcher = Constant.TIME_OUT_PATTERN.matcher(cell.getStringCellValue());
                    if (matcher.find()) {
                        String timeString = matcher.group(1);
                        session.setTimeOut(DateTimeUtil.parseTime(timeString));
                    }
                } else if (ExcelUtil.isCellWithinDate(cell, this.startDate, this.endDate)) {
                    session.setDate(cell.getDateCellValue());
                }
            }

            if (session.isValid()) {
                this.addSession(sheet.getSheetName(), session);
            }
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    private void addSession(String name, Session session) {
        // Search if student already exists
        for (Student student : students) {
            if (student.getName().equals(name)) {
                student.addSession(session);
                Collections.sort(students);
                return;
            }
        }

        // Add new student, if student does not exist.
        Student newStudent = new Student(name);
        newStudent.addSession(session);
        students.add(newStudent);
        Collections.sort(students);
    }
}