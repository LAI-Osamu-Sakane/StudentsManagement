package raisetech.StudentsManagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentsManagement.data.Student;
import raisetech.StudentsManagement.data.StudentsCourses;
import raisetech.StudentsManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //　24課題ここから
    List<Student>resultList = repository.search().stream()
        .filter(result -> result.getAge() >= 30 && result.getAge() <= 39)
        .collect(Collectors.toList());

    return resultList;
    // 24課題ここまで
//    return repository.search(); // 24課題作成時にコメントアウトすること
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    //　24課題ここから
    List<StudentsCourses> resultList = repository.searchStudentsCourses().stream()
        .filter(result -> result.getCourseName().matches(".*Java.*"))
        .collect(Collectors.toList());

    return resultList;
    // 24課題ここまで
//    return repository.searchStudentsCourses();  // 24課題作成時にコメントアウトすること
  }

}
