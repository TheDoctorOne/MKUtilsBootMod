package net.mahmutkocas.mkutils.server.web.dao;

import lombok.*;

import javax.persistence.*;
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
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "COLOR")
    private Integer color;

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
