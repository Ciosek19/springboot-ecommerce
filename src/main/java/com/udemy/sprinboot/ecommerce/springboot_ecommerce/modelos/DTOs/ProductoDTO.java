package com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long productoId;
    private String productoNombre;
    private String imagen;
    private String descripcion;
    private Integer cantidad;
    private Double precio;
    private Double descuento;
    private Double precioFinal;
    
}
