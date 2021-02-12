package app.dtrgenerator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Student implements Comparable<Student> {
    private final String name;
    private final List<Session> sessions;

    public Student(String name) {
        this.name = name;
        this.sessions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Session> getSessions() {
        return sessions;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}