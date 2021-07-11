import models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findAll();

    Optional<Course> findById(Integer id);
    List<Course> findByName(String searchName);

    void save(Course course);

    void update(Course course);
}
