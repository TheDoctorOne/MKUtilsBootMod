package net.mahmutkocas.reservermod.web.dao;

import lombok.*;

import javax.persistence.*;

@Table(schema = "USER_CRATE")
@Entity
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCrateDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID",updatable = false, nullable = false)
    private Long id;

    @Column(name = "CLAIMED")
    private boolean claimed;

    @ManyToOne
    private CrateDAO crateDAO;

    @ManyToOne
    private UserDAO userDAO;
}
