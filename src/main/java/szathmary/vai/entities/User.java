package szathmary.vai.entities;

import com.sun.istack.NotNull;
import lombok.Data;
import java.util.Set;

import javax.persistence.*;
import java.util.LinkedHashSet;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @OneToMany
    @JoinColumn(name = "author")
    private Set<Blog> blogs = new LinkedHashSet<>();
}
