package net.mahmutkocas.reservermod.web.dao;

import javax.persistence.*;

@Table(schema = "CRATE_CONTENT")
public class CrateContentDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID",updatable = false, nullable = false)
    private int id;

    @Column(name="COMMAND")
    private String command;

    @Column(name = "CHANCE")
    private int chance;
}
