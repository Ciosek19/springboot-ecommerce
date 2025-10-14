package com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta;

import java.util.List;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.ProductoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRespuesta {
    private List<ProductoDTO> contenido;
    private Integer numPagina;
    private Integer tamPagina;
    private Long totalElementos;
    private Integer totalPaginas;
    private boolean esUltimaPagina;
}
