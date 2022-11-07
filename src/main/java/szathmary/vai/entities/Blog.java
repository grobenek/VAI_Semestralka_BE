package szathmary.vai.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity(name = "blogs")
public class Blog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "blog_id")
  private Integer blogId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author", nullable = false)
  private User author;

  @NotNull(message = "Blog text cannot be null!")
  @NotBlank(message = "Blog text cannot be blank!")
  @Column(name = "text")
  private String text;

  @NotNull(message = "Blog timestamp cannot be null!")
  @Column(name = "timestamp")
  private Timestamp timestamp;

  @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "blog")
  private List<Category> categories = new ArrayList<>();

  public void setComments(List<Comment> comments) {
    this.comments.retainAll(comments);
    this.comments.addAll(comments);
  }
}
