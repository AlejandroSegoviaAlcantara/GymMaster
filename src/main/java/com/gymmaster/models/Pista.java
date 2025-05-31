package com.gymmaster.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pistas")
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Column(name = "id_pista")
    private Long id;

    @Getter @Setter
    @Column(name = "nombre")
    private String nombre;
}
