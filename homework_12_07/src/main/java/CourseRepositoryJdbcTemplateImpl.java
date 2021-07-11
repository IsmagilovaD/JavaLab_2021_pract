import models.Course;
import models.Student;
import models.Teacher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class CourseRepositoryJdbcTemplateImpl implements CourseRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL =
            "select *, " +
                    " s.first_name as student_first_name, s.last_name as student_last_name," +
                    " t.first_name as teacher_first_name, t.last_name as teacher_last_name" +
                    " from course as c " +
                    "left join course_students cs on c.id = cs.course_id " +
                    "left join student s on cs.student_id = s.id " +
                    "left join teacher as t on t.id = c.teacher_id order by c.id";

    //language=SQL
    private static final String SQL_SELECT_ALL_BY_NAME =
            "select *, " +
                    " s.first_name as student_first_name, s.last_name as student_last_name," +
                    " t.first_name as teacher_first_name, t.last_name as teacher_last_name" +
                    " from course as c " +
                    "left join course_students cs on c.id = cs.course_id " +
                    "left join student s on cs.student_id = s.id " +
                    "left join teacher as t on t.id = c.teacher_id where c.name =? order by c.id ";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select *, " +
            " s.first_name as student_first_name, s.last_name as student_last_name," +
            " t.first_name as teacher_first_name, t.last_name as teacher_last_name" +
            " from course as c " +
            "left join course_students cs on c.id = cs.course_id " +
            "left join student s on cs.student_id = s.id " +
            "left join teacher as t on t.id = c.teacher_id where c.id =? order by c.id ";

    //language=SQL
    private static final String SQL_UPDATE_BY_ID =
            "update course set name = ?, start_date = ?, end_date = ?, teacher_id = ? where id = ?";

    //language=SQL
    private static final String SQL_INSERT =
            "insert into course(name, start_date, end_date, teacher_id) VALUES(?,?,?,?)";


    private final JdbcTemplate jdbcTemplate;

    public CourseRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    final Function<ResultSet, Teacher> teacherMapper = (row) -> {
        try {
            Integer id = row.getInt("teacher_id");
            String first_name = row.getString("teacher_first_name");
            String last_name = row.getString("teacher_last_name");
            Integer experience = row.getInt("experience");
            return new Teacher(id, first_name, last_name, experience);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    };
    final Function<ResultSet, Student> studentMapper = (row) -> {
        try {
            Integer id = row.getInt("student_id");
            String first_name = row.getString("student_first_name");
            String last_name = row.getString("student_last_name");
            Integer group = row.getInt("group_number");
            return new Student(id, first_name, last_name, group);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    };
    final Function<ResultSet, Course> courseMapper = (row) ->
    {
        try {
            Integer id = row.getInt("id");
            String name = row.getString("name");
            String start_date = row.getString("start_date");
            String end_date = row.getString("end_date");
            Teacher teacher = teacherMapper.apply(row);
            return new Course(id, name, start_date, end_date, teacher);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };


    private final ResultSetExtractor<ArrayList<Course>> courseResultSetExtractor = resultSet ->
    {
        Set<Integer> processedCourses = new HashSet<>();
        Course currentCourse = null;
        ArrayList<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            if (!processedCourses.contains(resultSet.getInt("id"))) {
                currentCourse = courseMapper.apply(resultSet);
                currentCourse.setStudents(new ArrayList<>());
                courses.add(currentCourse);
            }
            Integer student_id = resultSet.getObject("student_id", Integer.class);
            if (student_id != null) {
                Student student = studentMapper.apply(resultSet);
                currentCourse.getStudents().add(student);
            }
            processedCourses.add(currentCourse.getId());
        }
        return courses;
    };

    private final ResultSetExtractor<Course> oneCourseResultSetExtractor = resultSet ->
    {
        Course course = null;
        if (resultSet.next()) {

            course = courseMapper.apply(resultSet);
            course.setStudents(new ArrayList<>());
            do {
                Integer student_id = resultSet.getObject("student_id", Integer.class);
                if (student_id != null) {
                    Student student = studentMapper.apply(resultSet);
                    course.getStudents().add(student);
                }
            } while (resultSet.next());
        }

        return course;
    };


    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, courseResultSetExtractor);
    }

    @Override
    public Optional<Course> findById(Integer id) {
        try {

            return Optional.ofNullable(jdbcTemplate.query(SQL_SELECT_BY_ID, oneCourseResultSetExtractor, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Course> findByName(String searchName) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_NAME, courseResultSetExtractor, searchName);
    }

    @Override
    public void save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});

            statement.setString(1, course.getName());
            statement.setString(2, course.getStart_date());
            statement.setString(3, course.getEnd_date());
            statement.setInt(4, course.getTeacher().getId());

            return statement;
        }, keyHolder);

        course.setId(keyHolder.getKey().intValue());

    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID,
                course.getName(), course.getStart_date(), course.getEnd_date(), course.getTeacher().getId(),
                course.getId());
    }

}

