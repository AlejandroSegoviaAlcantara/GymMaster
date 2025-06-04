package com.gymmaster.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "compra_productos")
public class CompraProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    @Getter @Setter
    private Compra compra;

    @Getter @Setter
    private Long productoId;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    private Double precio;

    @Getter @Setter
    private int cantidad;
}
