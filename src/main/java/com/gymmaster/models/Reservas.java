package com.gymmaster.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "reservas")
@ToString @EqualsAndHashCode
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id_reserva")
    private Long idReserva;

    @Getter @Setter @Column(name = "id_usuario")
    private Long idUsuario;

    @Getter @Setter @Column(name = "pista")
    private String pista;

    @Getter @Setter @Column(name = "fecha")
    private Date fecha;

    @Getter @Setter @Column(name = "hora")
    private String hora;
}
