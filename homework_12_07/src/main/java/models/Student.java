package models;

import java.util.ArrayList;

public class Student {
    private Integer id;
    private String first_name;
    private String last_name;
    private Integer group_number;
    private ArrayList<Course> courses;

    public Student(Integer id, String first_name, String last_name, Integer group_number) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.group_number = group_number;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", group_number=" + group_number +
                ", courses=" + courses +
                '}';
    }
}
