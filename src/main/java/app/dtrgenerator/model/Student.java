package app.dtrgenerator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public int compareTo(Student other) {
        Session thisFirstSession = this.sessions.get(0);
        Session otherFirstSession = other.sessions.get(0);
        return thisFirstSession.compareTo(otherFirstSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Student))
            return false;
        Student other = (Student) obj;
        return Objects.equals(name, other.name);
    }
}