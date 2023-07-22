package net.mahmutkocas.reservermod.web.dao;

import lombok.Data;

import javax.persistence.*;

@Table(schema = "Token")
@Entity
@Data
public class TokenDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID",updatable = false, nullable = false)
    private Long id;
    
    
}
