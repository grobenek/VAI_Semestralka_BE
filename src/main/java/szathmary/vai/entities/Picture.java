package szathmary.vai.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "pictures")
public class Picture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "picture_id")
  private Integer pictureId;

  @NotNull(message = "Picture data cannot be null!")
  @Column(name = "data")
  private byte[] data;

  @NotNull(message = "Picture file_name cannot be null!")
  @NotBlank(message = "Picture file_name cannot be blank!")
  @Column(name = "file_name")
  private String fileName;
}
