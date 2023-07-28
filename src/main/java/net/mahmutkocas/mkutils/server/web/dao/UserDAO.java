package net.mahmutkocas.mkutils.server.web.dao;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER_TABLE")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MAIL")
    private String mail;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "TOKEN_EXP_DATE")
    private LocalDateTime tokenExpDate;

    @OneToMany
    private List<UserCrateDAO> crates;

}
