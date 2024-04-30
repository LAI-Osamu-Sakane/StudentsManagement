package raisetech.StudentsManagement.repository;


import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentsManagement.data.Student;
import raisetech.StudentsManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索
   *
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  @Select("SELECT * FROM students where studentId = #{studentId}")
  Student selectStudent(Student student);

// INSERT文の書き方注意
  @Insert("INSERT INTO students (name, kana, nickname, mail, area, age, sex, remark, is_deleted)"
      + " VALUE(#{name}, #{kana}, #{nickname}, #{mail}, #{area}, #{age}, #{sex}, #{remark}, false)")
  //MyBatis用の設定、studentIdについて
  @Options(useGeneratedKeys = true,keyProperty = "studentId")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses (studentId, course_name, starting_date, scheduled_end_date)"
      + " VALUE(#{studentId}, #{courseName}, #{startingDate}, #{scheduledEndDate})")
  @Options(useGeneratedKeys = true,keyProperty = "courseId")
  void registerStudentCourses(StudentsCourses studentsCourse);


}
