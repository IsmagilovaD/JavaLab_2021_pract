import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.Course;
import models.Lesson;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try {
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));

        DataSource dataSource = new HikariDataSource(config);

        CourseRepository courseRepository = new CourseRepositoryJdbcTemplateImpl(dataSource);
        LessonRepository lessonRepository = new LessonRepositoryJdbcTemplateImpl(dataSource);


        System.out.println(courseRepository.findById(1));
        System.out.println();
        for (Course course : courseRepository.findByName("Английский язык")) {
            System.out.println(course);
        }


        Optional<Course> optionalCourse = courseRepository.findById(5);
        Course course = optionalCourse.get();
        course.setName("Лучший курс");
        course.setStart_date("29.06.2021");
        course.setEnd_date("12.07.2021");
        courseRepository.save(course);
        Course course1 = optionalCourse.get();
        course1.setName("Облачные технологии");
        courseRepository.update(course1);

        optionalCourse = courseRepository.findById(1);
        Course course2 = optionalCourse.get();

        Lesson lesson1 = new Lesson("Первый урок", "Понедельник", "13:00", course2);
        Lesson lesson2 = new Lesson("Неправильные глаголы", "Понедельник", "13:00", course2);
        Lesson lesson3 = new Lesson("Модальные глаголы", "Понедельник", "13:00", course2);

        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        lessonRepository.save(lesson3);

        lessonRepository.update(new Lesson(1, "Лучший урок", "Пятница", "16:00", course2));
        System.out.println(lessonRepository.findById(1));

        for (Lesson lesson : lessonRepository.findByName("Неправильные глаголы")) {
            System.out.println(lesson);
        }
    }
}
