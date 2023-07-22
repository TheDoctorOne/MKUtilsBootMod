package net.mahmutkocas.reservermod.web.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(schema = "CRATE")
@Entity
@Data
public class CrateDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID",updatable = false, nullable = false)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONTENT")
    private List<CrateContentDAO> crateContents;

}
