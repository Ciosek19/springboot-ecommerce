package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.ProductoDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Producto;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.ProductoRespuesta;

public interface IProductoServicio {

    ProductoDTO agregarProducto(Long categoriaId, Producto producto);

    ProductoRespuesta obtenerProductos();

    ProductoRespuesta buscarPorCategoria(Long categoriaId);
}
