package com.gymmaster.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "entrenamientos")
@ToString @EqualsAndHashCode
public class Entrenamientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id_entrenamiento")
    private Long idEntrenamiento;

    @Getter @Setter @Column(name = "id_usuario")
    private Long idUsuario;

    @Getter @Setter @Column(name = "dia")
    private String Dia;

    @Getter @Setter @Column(name = "ejercicio ")
    private String ejercicio ;

    @Getter @Setter @Column(name = "repeticiones ")
    private Integer repeticiones ;

    @Getter @Setter @Column(name = "peso")
    private Double peso ;

}