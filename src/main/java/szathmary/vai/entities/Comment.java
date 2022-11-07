package szathmary.vai.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Integer commentId;
  @NotNull(message = "Comment author cannot be null!")
  @ManyToOne(fetch = FetchType.LAZY)
  @EqualsAndHashCode.Exclude
  @JsonBackReference
  @JoinColumn(name = "author", nullable = false)
  private User author;
  @NotNull(message = "Comment timestamp cannot be null!")
  @Column(name = "timestamp")
  private Timestamp timestamp;
  @NotNull(message = "Comment blog cannot be null!")
  @ManyToOne(fetch = FetchType.LAZY)
  @EqualsAndHashCode.Exclude
  @JsonBackReference
  @JoinColumn(name = "blog_id", nullable = false)
  private Blog blog;
  @NotNull(message = "Comment text cannot be null!")
  @NotBlank(message = "Comment text cannot be blank!")
  @Size(max = 65535, message = "Comment text size can be maximum of 65535 characters!")
  @Column(name = "text")
  private String text;

}
