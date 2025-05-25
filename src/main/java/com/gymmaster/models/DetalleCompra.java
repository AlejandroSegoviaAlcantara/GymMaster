package com.gymmaster.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "detalle_compra")
@ToString @EqualsAndHashCode
public class DetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id_detalle")
    private Long id;

    @Getter @Setter @Column(name = "id_compra")
    private Long idCompra;

    @Getter @Setter @Column(name = "id_producto")
    private Long idProducto;

    @Getter @Setter @Column(name = "cantidad")
    private Integer cantidad;

    @Getter @Setter @Column(name = "precio")
    private Double tipo;

}
