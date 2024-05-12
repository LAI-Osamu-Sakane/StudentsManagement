package raisetech.StudentsManagement.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentsManagement.controller.converter.StudentConverter;
import raisetech.StudentsManagement.data.Student;
import raisetech.StudentsManagement.data.StudentCourse;
import raisetech.StudentsManagement.domain.StudentDetail;
import raisetech.StudentsManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録、更新などを行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository,StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、受験指定は行いません。
   *
   * @return 受講生詳細一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細検索です。 IDに紐づく受個性情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param studentId 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudent(studentId);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getStudentId());
    return new StudentDetail(student, studentCourse);
  }

  /**
   * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値と、コース開始日、コース終了日を設定します。
   *
   * @param studentDetail　受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {

    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    studentDetail.getStudentsCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student);
      repository.registerStudentCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する
   *
   * @param studentCourse 受講生コース
   * @param student 受講生
   */
  private void initStudentsCourse(StudentCourse studentCourse, Student student) {

    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(student.getStudentId());
    studentCourse.setStartingDate(now);
    studentCourse.setScheduledEndDate(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新を行います。 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentsCourseList().forEach(studentCourse -> repository.updateStudentCourse(studentCourse));

  }

}