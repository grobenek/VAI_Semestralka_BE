package szathmary.vai.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity(name = "blogs")
public class Blog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "blog_id")
  private Integer blogId;

  @ManyToOne(fetch = FetchType.LAZY)
  @EqualsAndHashCode.Exclude
  @JsonBackReference
  @JoinColumn(name = "author", nullable = false)
  private User author;

  @NotNull(message = "Blog text cannot be null!")
  @NotBlank(message = "Blog text cannot be blank!")
  @Size(max = 65535, message = "Blog text size can be maximum of 65535 characters!")
  @Column(name = "text")
  private String text;

  @NotNull(message = "Blog timestamp cannot be null!")
  @Column(name = "timestamp")
  private Timestamp timestamp;

  @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, orphanRemoval = true)
  @JsonManagedReference
  private List<Category> categories = new ArrayList<>();

  public void setComments(List<Comment> comments) {
    this.comments.retainAll(comments);
    this.comments.addAll(comments);
  }

  public void setCategories(List<Category> categories) {
    this.categories.retainAll(categories);
    this.categories.addAll(categories);
  }
}
