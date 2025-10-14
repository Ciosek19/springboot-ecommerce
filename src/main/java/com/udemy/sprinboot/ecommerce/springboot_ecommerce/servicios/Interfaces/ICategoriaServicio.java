package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.CategoriaDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.CategoriaRespuesta;

public interface ICategoriaServicio {
    CategoriaRespuesta obtenerTodasCategorias(Integer paginaNumero, Integer paginaTamano, String ordenarPor, String ordenarDireccion);
    CategoriaDTO crearCategoria(CategoriaDTO categoria);
    CategoriaDTO eliminarCategoria(Long categoriaId);
    CategoriaDTO actualizarCategoria(CategoriaDTO categoria, Long categoriaid);

}
