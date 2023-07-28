package net.mahmutkocas.mkutils.server.web.dao;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_CRATE")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCrateDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CLAIMED")
    private boolean claimed;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private CrateDAO crateDAO;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private UserDAO userDAO;
}
