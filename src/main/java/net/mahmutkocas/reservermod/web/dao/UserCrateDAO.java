package net.mahmutkocas.reservermod.web.dao;

import lombok.Data;

import javax.persistence.*;

@Table(schema = "USER_CRATE")
@Entity
@Data
public class UserCrateDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID",updatable = false, nullable = false)
    private int id;

}
