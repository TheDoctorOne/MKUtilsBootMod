package net.mahmutkocas.reservermod.web.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(schema = "CRATE_CONTENT")
@Entity
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrateContentDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID",updatable = false, nullable = false)
    private Long id;

    @Column(name="COMMAND")
    private String command;

    @Column(name = "CHANCE")
    private int chance;

    @ManyToMany(mappedBy = "crateContents")
    private Set<CrateDAO> crates;
}
