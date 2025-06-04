package com.gymmaster.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "producto")
@ToString @EqualsAndHashCode
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Column(name = "id_producto")
    private Long id;

    @Getter @Setter @Column(name = "nombre")
    private String nombre;

    @Getter @Setter @Column(name = "descripcion")
    private String descripcion;

    @Getter @Setter @Column(name = "precio")
    private Double precio;

    @Getter @Setter @Column(name = "stock")
    private Integer stock;

    @Getter @Setter @Column(name = "imagen")
    private String imagen;

}
