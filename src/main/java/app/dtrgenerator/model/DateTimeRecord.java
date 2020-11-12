package app.dtrgenerator.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;

import app.dtrgenerator.constant.Regex;
import app.dtrgenerator.util.DateTimeUtil;
import app.dtrgenerator.util.ExcelUtil;

public class DateTimeRecord {
    private Date startDate;
    private Date endDate;
    private SortedSet<Student> students;

    public DateTimeRecord(Date startDate, Date endDate, List<Cell> searchResults) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.students = new TreeSet<>();
        initializeSessions(searchResults);
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

    public SortedSet<Student> getStudents() {
        return students;
    }

    public void setStudents(SortedSet<Student> students) {
        this.students = students;
    }

    private void initializeSessions(List<Cell> searchResults) {
        for (Cell result : searchResults) {
            Sheet sheet = result.getSheet();
            Session session = getSessionFromReferenceCell(result);
            if (session.isValid()) {
                Student student = getStudent(sheet.getSheetName());
                student.addSession(session);
                students.add(student);
            }
        }
    }

    private Session getSessionFromReferenceCell(Cell reference) {
        Session session = new Session();
        Sheet sheet = reference.getSheet();
        int maxRowsToLookup = 5;
        int rowIndex = reference.getRowIndex();
        int columnIndex = reference.getColumnIndex();
        for (int index = 1; index < maxRowsToLookup; index++) {
            Row previousRow = sheet.getRow(rowIndex - index);
            if (Objects.nonNull(previousRow)) {
                Cell previousCell = previousRow.getCell(columnIndex, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                setSessionDetailsFromCell(session, previousCell);
            }
        }
        return session;
    }

    private void setSessionDetailsFromCell(Session session, Cell cell) {
        if (ExcelUtil.isCellMatches(cell, Regex.TIME_IN)) {
            String timeString = getTimeStringFromCell(cell, Regex.TIME_IN);
            session.setTimeIn(DateTimeUtil.parseTime(timeString));
        } else if (ExcelUtil.isCellMatches(cell, Regex.TIME_OUT)) {
            String timeString = getTimeStringFromCell(cell, Regex.TIME_OUT);
            session.setTimeOut(DateTimeUtil.parseTime(timeString));
        } else if (ExcelUtil.isCellWithinDate(cell, startDate, endDate)) {
            session.setDate(cell.getDateCellValue());
        }
    }

    private String getTimeStringFromCell(Cell cell, Pattern pattern) {
        Matcher matcher = pattern.matcher(cell.getStringCellValue());
        matcher.find();
        return matcher.group("time");
    }

    private Student getStudent(String name) {
        for (Student student : students) {
            if (student.getName().equals(name))
                return student;
        }
        return new Student(name);
    }
}