package szathmary.vai.entities;

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
import lombok.Data;

@Data
@Entity(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Integer commentId;
  @NotNull(message = "Comment author cannot be null!")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author", nullable = false)
  private User author;
  @NotNull(message = "Comment timestamp cannot be null!")
  @Column(name = "timestamp")
  private Timestamp timestamp;
  @NotNull(message = "Comment blog cannot be null!")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blog_id", nullable = false)
  private Blog blog;
  @NotNull(message = "Comment text cannot be null!")
  @NotBlank(message = "Comment text cannot be blank!")
  @Column(name = "text")
  private String text;

}
