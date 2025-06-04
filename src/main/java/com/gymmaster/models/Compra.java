package com.gymmaster.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
    @Table(name = "compras")
    @ToString
    @EqualsAndHashCode
    public class Compra {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Getter @Setter @Column(name = "id_compra")
        private Long idCompra;

        @Getter @Setter @Column(name = "id_usuario")
        private Long idUsuario;

        @Getter @Setter @Column(name = "fecha")
        private Date fecha;

        @Getter @Setter @Column(name = "total")
        private Double total;

        @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @Getter @Setter
        private List<CompraProducto> productos;

}