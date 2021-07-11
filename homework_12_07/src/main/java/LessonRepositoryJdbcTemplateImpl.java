import models.Course;
import models.Lesson;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

public class LessonRepositoryJdbcTemplateImpl implements LessonRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL_BY_NAME =
            "select *,c.name as course_name from lesson as l left join course c on l.course = c.id where l.name = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_ID =
            "select *,c.name as course_name from lesson as l left join course c on l.course = c.id where l.id = ?";

    //language=SQL
    private static final String SQL_UPDATE_BY_ID =
            "update lesson set name = ?, weekday = ?, time = ?, course = ? where id = ?";

    //language=SQL
    private static final String SQL_INSERT =
            "insert into lesson(name, weekday, time, course) VALUES(?,?,?,?)";


    private final JdbcTemplate jdbcTemplate;

    public LessonRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    final Function<ResultSet, Course> courseMapper = (row) ->
    {
        try {
            Integer id = row.getInt("course");
            String name = row.getString("course_name");
            String start_date = row.getString("start_date");
            String end_date = row.getString("end_date");
            return new Course(id, name, start_date, end_date);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    private final Function<ResultSet, Lesson> lessonMapper = (row) ->
    {
        try {
            Integer id = row.getInt("id");
            String name = row.getString("name");
            String weekday = row.getString("weekday");
            String time = row.getString("time");
            return new Lesson(id, name, weekday, time);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    private final ResultSetExtractor<ArrayList<Lesson>> lessonResultSetExtractor = resultSet ->
    {
        Set<Integer> processedLessons = new HashSet<>();
        Lesson currentLesson = null;
        ArrayList<Lesson> lessons = new ArrayList<>();
        while (resultSet.next()) {
            if (!processedLessons.contains(resultSet.getInt("id"))) {
                currentLesson = lessonMapper.apply(resultSet);
                currentLesson.setCourse(new Course());
                lessons.add(currentLesson);
            }
            Integer course_id = resultSet.getObject("course", Integer.class);
            if (course_id != null) {
                Course course = courseMapper.apply(resultSet);
                currentLesson.setCourse(course);
            }
            processedLessons.add(currentLesson.getId());
        }
        return lessons;
    };

    private final ResultSetExtractor<Lesson> oneLessonResultSetExtractor = resultSet ->
    {
        Lesson lesson = null;
        if (resultSet.next()) {
            lesson = lessonMapper.apply(resultSet);
            lesson.setCourse(new Course());
            do {
                Integer course_id = resultSet.getObject("course", Integer.class);
                if (course_id != null) {
                    Course course = courseMapper.apply(resultSet);
                    lesson.setCourse(course);
                }
            } while (resultSet.next());
        }
        return lesson;
    };

    @Override
    public Optional<Lesson> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.query(SQL_SELECT_BY_ID, oneLessonResultSetExtractor, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Lesson> findByName(String searchName) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_NAME, lessonResultSetExtractor, searchName);
    }

    @Override
    public void save(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});

            statement.setString(1, lesson.getName());
            statement.setString(2, lesson.getWeekday());
            statement.setString(3, lesson.getTime());
            statement.setInt(4, lesson.getCourse().getId());

            return statement;
        }, keyHolder);

        lesson.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID,
                lesson.getName(), lesson.getWeekday(), lesson.getTime(), lesson.getCourse().getId(),
                lesson.getId());
    }
}
