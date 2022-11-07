package szathmary.vai.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
@Data
@Entity(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  private Integer userId;
  @NotNull(message = "User login cannot be null!")
  @NotBlank(message = "User login cannot be blank!")
  @Column(name = "login")
  private String login;
  @NotNull(message = "User password cannot be null!")
  @NotBlank(message = "User password cannot be blank!")
  @Column(name = "password")
  private String password;
  @NotNull(message = "User isAdmin cannot be null!")
  @Column(name = "is_admin")
  private Boolean isAdmin;
  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Blog> blogs = new ArrayList<>();
  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();
}
