package szathmary.vai.entities;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @Column(name = "text")
    private String text;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public void setComments(List<Comment> comments) {
        this.comments.retainAll(comments);
        this.comments.addAll(comments);
    }
}
