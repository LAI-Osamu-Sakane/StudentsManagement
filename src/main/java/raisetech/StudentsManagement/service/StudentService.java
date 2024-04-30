package raisetech.StudentsManagement.service;


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

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
//    student.setStudentId((String)((int)repository.checkStudentId() + 1));
    repository.registerStudent(studentDetail.getStudent());
    //todo:コース情報の登録
//    repository.insertData(student.getStudentId(), student.getName(), student.getKana(),
//        student.getNickname(), student.getMail(), student.getArea(), student.getAge(), student.getSex(), student.getRemark(), false);
  }

}
