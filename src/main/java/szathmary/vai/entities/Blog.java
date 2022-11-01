package szathmary.vai.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "blog_id")
    private Integer blogId;

    @ManyToOne
    @JoinColumn(name = "author")
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "timestamp")
    private Timestamp timestamp;
}
