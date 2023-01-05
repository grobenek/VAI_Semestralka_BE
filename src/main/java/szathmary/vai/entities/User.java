package szathmary.vai.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
@Getter
@Setter
@Entity(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
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
  @NotNull(message = "User email cannot be null!")
  @NotBlank(message = "User email cannot be blank!")
  @Column(name = "email")
  private String email;

  @NotNull(message = "About user text cannot be null!")
  @NotBlank(message = "About user text cannot be blank!")
  @Size(max = 200, message = "About user text must be maximum of 200 characters!")
  @Column(name = "about_user")
  private String aboutUser;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Blog> blogs = new ArrayList<>();
  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  public void setBlogs(List<Blog> blogs) {
    this.blogs.retainAll(blogs);
    this.blogs.addAll(blogs);
  }

  public void setComments(List<Comment> comments) {
    this.comments.retainAll(comments);
    this.comments.addAll(comments);
  }
}
