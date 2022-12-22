package szathmary.vai.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "category_names")
public class CategoryName {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "categoryNameId")
  @Column(name = "category_name_id", nullable = false)
  private Integer categoryNameId;

  @Column(name = "name")
  @NotNull(message = "CategoryName name cannot be null!")
  @NotBlank(message = "CategoryName name cannot be blank!")
  @Size(max = 65535, message = "CategoryName name size can be maximum of 30 characters!")
  private String name;

  @OneToMany(mappedBy = "categoryName", fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Category> categories;

  public void setCategories(List<Category> categories) {
    this.categories.retainAll(categories);
    this.categories.addAll(categories);
  }
}