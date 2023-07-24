package net.mahmutkocas.reservermod.web.dao;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CRATE")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrateDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID",updatable = false, nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "create_contents",
            joinColumns = @JoinColumn(name = "crate_id"),
            inverseJoinColumns = @JoinColumn(name = "content_id")
    )
    private Set<CrateContentDAO> crateContents;

    @OneToMany
    private Set<UserCrateDAO> userCrates;
}
