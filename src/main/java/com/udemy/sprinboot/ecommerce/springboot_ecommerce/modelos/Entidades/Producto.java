package com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 3,message = "El nombre debe contener minimo 3 caracteres")
    private String productoNombre;

    @NotBlank
    @Size(min = 6, message="La descripcion debe contenemor minimo 6 caracteres")
    private String descripcion;
    private String imagen;
    private Integer cantidad;
    private double precio;
    private double descuento;
    private double precioFinal;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public double calcularPrecioFinal(double precio, double descuento) {
        return precio - (precio * descuento / 100);
    }

}
