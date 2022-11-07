package szathmary.vai.entities;

import java.util.List;
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

@Data
@Entity(name = "category_names")
public class CategoryName {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_name_id", nullable = false)
  private Integer categoryNameId;

  @Column(name = "name")
  @NotNull(message = "CategoryName name cannot be null!")
  @NotBlank(message = "CategoryName name cannot be blank!")
  private String name;

  @OneToMany(fetch = FetchType.LAZY)
  private List<Category> categories;
}