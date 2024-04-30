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

  @Select("SELECT studentId FROM students")
  String[] checkStudentId();

//  @Insert("INSERT INTO students VALUE(student.studentId student.name, student.kana, student.nickname, student.mail, student.area, student.age, student.sex, student.remark, null )")

  @Insert("INSERT INTO students (name, kana, nickname, mail, area, age, sex, remark, is_deleted)"
      + " VALUE(#{name}, #{kana}, #{nickname}, #{mail}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true,keyProperty = "studentId")
  void registerStudent(Student student);
//  void insertData(@Param("studentId") String studentId, @Param("name") String name,
//      @Param("kana") String kana, @Param("nickname") String nickname, @Param("mail") String mail, @Param("area") String area,
//      @Param("age") int age, @Param("sex") String sex, @Param("remark") String remark, @Param("isDelete") boolean isDelete);
}
