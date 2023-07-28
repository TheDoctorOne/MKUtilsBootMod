package net.mahmutkocas.mkutils.server.web.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CRATE_CONTENT")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrateContentDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name="COMMAND")
    private String command; // pokegive %p% pikachu

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "COLOR")
    private Integer color;

    @Column(name = "CHANCE")
    private Integer chance;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "crateContents")
    private Set<CrateDAO> crates;
}
