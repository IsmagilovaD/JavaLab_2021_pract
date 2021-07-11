package models;

import java.util.ArrayList;

public class Teacher {
    private Integer id;
    private String first_name;
    private String last_name;
    private Integer experience;
    private ArrayList<Course> courses;

    public Teacher(Integer id, String first_name, String last_name, Integer experience) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.experience = experience;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", experience=" + experience +
                ", courses=" + courses +
                '}';
    }
}
