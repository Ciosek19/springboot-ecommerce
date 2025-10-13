package com.udemy.sprinboot.ecommerce.springboot_ecommerce.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.ProductoDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Producto;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.ProductoRespuesta;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces.IProductoServicio;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private IProductoServicio productoServicio;

    @PostMapping("/admin/categorias/{categoriaId}/productos")
    public ResponseEntity<ProductoDTO> agregarProducto(@RequestBody Producto producto, @PathVariable Long categoriaId) {
        ProductoDTO productoDTO = productoServicio.agregarProducto(categoriaId, producto);
        return new ResponseEntity<>(productoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/publico/productos")
    public ResponseEntity<ProductoRespuesta> obtenerProductResponseEntity() {
        ProductoRespuesta productoRespuesta = productoServicio.obtenerProductos();
        return new ResponseEntity<ProductoRespuesta>(productoRespuesta, HttpStatus.OK);
    }
    
    
    @GetMapping("/publico/categorias/{categoriaId}/productos")
    public ResponseEntity<ProductoRespuesta> obtenerProductosPorCategoria(@PathVariable Long categoriaId){
        ProductoRespuesta productoRespuesta = productoServicio.buscarPorCategoria(categoriaId);
        return new ResponseEntity<>(productoRespuesta,HttpStatus.OK);
    }
}
