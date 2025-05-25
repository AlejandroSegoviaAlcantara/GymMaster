package com.gymmaster.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
    @Table(name = "compras")
    @ToString
    @EqualsAndHashCode
    public class Compras {

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
}
