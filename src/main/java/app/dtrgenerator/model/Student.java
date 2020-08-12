package app.dtrgenerator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student implements Comparable<Student> {
    private String name;
    private List<Session> sessions;

    public Student(String name) {
        this.name = name;
        this.sessions = new ArrayList<Session>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSchedules(List<Session> schedules) {
        this.sessions = schedules;
    }

    public void addSession(Session session) {
        sessions.add(session);
        Collections.sort(sessions);
    }

    @Override
    public int compareTo(Student compareStudent) {
        int result = this.sessions.get(0).getDate().compareTo(compareStudent.sessions.get(0).getDate());
        return result != 0 ? result : this.sessions.get(0).getTimeIn().compareTo(compareStudent.sessions.get(0).getTimeIn());
    }
}