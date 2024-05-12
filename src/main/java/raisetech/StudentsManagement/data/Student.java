package raisetech.StudentsManagement.data;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Student {

  @NotBlank
  @Pattern(regexp = "^\\d*$")
  private String studentId;

  @NotBlank
  private String name;

  @NotBlank
  private String kana;

  private String nickname;

  @NotBlank
  @Email
  private String mail;
  private String area;
  private int age;
  private String sex;
  private String remark;
  private boolean isDeleted;
}

