package com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productoId;

    private String productoNombre;
    private String descripcion;
    private String imagen;
    private Integer cantidad;
    private double precio;
    private double descuento;
    private double especialPrecio;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
