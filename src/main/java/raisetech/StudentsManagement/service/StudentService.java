package raisetech.StudentsManagement.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentsManagement.data.Student;
import raisetech.StudentsManagement.data.StudentsCourses;
import raisetech.StudentsManagement.domain.StudentDetail;
import raisetech.StudentsManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourses();
  }

  public String[] checkStudentId() {
    return repository.checkStudentId();
  }

//  一連の登録情報がうまくいったときのみ有効とするためのTransactional
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
//    student.setStudentId((String)((int)repository.checkStudentId() + 1));
    repository.registerStudent(studentDetail.getStudent());
    //todo:コース情報の登録
    for(StudentsCourses studentCourse : studentDetail.getStudentsCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getStudentId());
      studentCourse.setStartingDate(LocalDateTime.now());
      studentCourse.setScheduledEndDate(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourses(studentCourse);
    }
//    repository.insertData(student.getStudentId(), student.getName(), student.getKana(),
//        student.getNickname(), student.getMail(), student.getArea(), student.getAge(), student.getSex(), student.getRemark(), false);
  }

}
