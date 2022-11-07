package szathmary.vai.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "categories")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Integer categoryId;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_name_blog_id")
  Blog blog;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_name_id")
  private CategoryName categoryName;
}
