package raisetech.StudentsManagement.data;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentsCourses {

  private String courseId;
  private String studentsId;
  private String courseName;
  private LocalDateTime startingDate;
  private LocalDateTime scheduledEndDate;
}
