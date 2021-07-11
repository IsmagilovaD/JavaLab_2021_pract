package models;

public class Lesson {
    private Integer id;
    private String name;
    private String weekday;
    private String time;
    private Course course;

    public Lesson(Integer id, String name, String weekday, String time) {
        this.id = id;
        this.name = name;
        this.weekday = weekday;
        this.time = time;
    }

    public Lesson(String name, String weekday, String time, Course course) {
        this.name = name;
        this.weekday = weekday;
        this.time = time;
        this.course = course;
    }

    public Lesson(Integer id, String name, String weekday, String time, Course course) {
        this.id = id;
        this.name = name;
        this.weekday = weekday;
        this.time = time;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weekday='" + weekday + '\'' +
                ", time='" + time + '\'' +
                ", course=" + course.toString() +
                '}';
    }
}
