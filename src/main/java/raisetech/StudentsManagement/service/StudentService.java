package raisetech.StudentsManagement.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentsManagement.controller.converter.StudentConverter;
import raisetech.StudentsManagement.data.Student;
import raisetech.StudentsManagement.data.StudentsCourses;
import raisetech.StudentsManagement.domain.StudentDetail;
import raisetech.StudentsManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録、更新などを行います。
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
   * 受講生一覧検索です。
   * 全件検索を行うので、受験指定は行いません。
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  /**
   * 受講生検索です。
   * IDに紐づく受個性情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param studentId 受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudent(studentId);
    List<StudentsCourses> studentsCourses = repository.searchStudentCourse(student.getStudentId());
    return new StudentDetail(student, studentsCourses);
  }

//  一連の登録情報がうまくいったときのみ有効とするためのTransactional
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {

    repository.registerStudent(studentDetail.getStudent());
    for(StudentsCourses studentCourse : studentDetail.getStudentsCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getStudentId());
      studentCourse.setStartingDate(LocalDateTime.now());
      studentCourse.setScheduledEndDate(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourses(studentCourse);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for(StudentsCourses studentCourse : studentDetail.getStudentsCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getStudentId());
      repository.updateStudentCourses(studentCourse);
    }

  }

}
