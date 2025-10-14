package com.udemy.sprinboot.ecommerce.springboot_ecommerce.controladores;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.configuracion.AppConstantes;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.ProductoDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.ProductoRespuesta;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces.IProductoServicio;

import jakarta.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private IProductoServicio productoServicio;

    @PostMapping("/admin/categorias/{categoriaId}/productos")
    public ResponseEntity<ProductoDTO> agregarProducto(@Valid @RequestBody ProductoDTO productoDto,
            @PathVariable Long categoriaId) {
        ProductoDTO productoGuardadoDTO = productoServicio.agregarProducto(categoriaId, productoDto);
        return new ResponseEntity<>(productoGuardadoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/publico/productos")
    public ResponseEntity<ProductoRespuesta> obtenerProductos(
        @RequestParam(name = "paginaNumero", defaultValue = AppConstantes.PAGINA_NUMERO) Integer paginaNumero, 
        @RequestParam(name = "paginaTamano", defaultValue = AppConstantes.PAGINA_TAMANO) Integer paginaTamano,
        @RequestParam(name = "ordenarPor", defaultValue = AppConstantes.ORDENAR_PRODUCTOS_POR) String ordenarPor,
        @RequestParam(name = "ordenarDireccion", defaultValue = AppConstantes.ORDENAR_DIRECCION) String ordenarDireccion
        ) {
        ProductoRespuesta productoRespuesta = productoServicio.obtenerProductos(paginaNumero,paginaTamano,ordenarPor,ordenarDireccion);
        return new ResponseEntity<ProductoRespuesta>(productoRespuesta, HttpStatus.OK);
    }

    @GetMapping("/publico/categorias/{categoriaId}/productos")
    public ResponseEntity<ProductoRespuesta> obtenerProductosPorCategoria(
        @PathVariable Long categoriaId,
        @RequestParam(name = "paginaNumero", defaultValue = AppConstantes.PAGINA_NUMERO) Integer paginaNumero, 
        @RequestParam(name = "paginaTamano", defaultValue = AppConstantes.PAGINA_TAMANO) Integer paginaTamano,
        @RequestParam(name = "ordenarPor", defaultValue = AppConstantes.ORDENAR_CATEGORIAS_POR) String ordenarPor,
        @RequestParam(name = "ordenarDireccion", defaultValue = AppConstantes.ORDENAR_DIRECCION) String ordenarDireccion
        ) {
        ProductoRespuesta productoRespuesta = productoServicio.buscarPorCategoria(categoriaId,paginaNumero,paginaTamano,ordenarPor,ordenarDireccion);
        return new ResponseEntity<>(productoRespuesta, HttpStatus.OK);
    }

    @GetMapping("/publico/productos/busqueda/{palabra}")
    public ResponseEntity<ProductoRespuesta> obtenerProductosPorPalabra(
        @PathVariable String palabra,
        @RequestParam(name = "paginaNumero", defaultValue = AppConstantes.PAGINA_NUMERO) Integer paginaNumero, 
        @RequestParam(name = "paginaTamano", defaultValue = AppConstantes.PAGINA_TAMANO) Integer paginaTamano,
        @RequestParam(name = "ordenarPor", defaultValue = AppConstantes.ORDENAR_CATEGORIAS_POR) String ordenarPor,
        @RequestParam(name = "ordenarDireccion", defaultValue = AppConstantes.ORDENAR_DIRECCION) String ordenarDireccion
        ) {
        ProductoRespuesta productoRespuesta = productoServicio.buscarPorPalabra(palabra,paginaNumero,paginaTamano,ordenarPor,ordenarDireccion);
        return new ResponseEntity<>(productoRespuesta, HttpStatus.FOUND);
    }

    @PutMapping("/admin/productos/{productoId}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@Valid @RequestBody ProductoDTO productoDto,
            @PathVariable Long productoId) {

        ProductoDTO productoActualizadoDTO = productoServicio.actualizarProducto(productoId, productoDto);
        return new ResponseEntity<>(productoActualizadoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/productos/{productoId}")
    public ResponseEntity<ProductoDTO> eliminarProducto(@PathVariable Long productoId)  {
        ProductoDTO productoEliminado = productoServicio.eliminarProducto(productoId);

        return new ResponseEntity<>(productoEliminado, HttpStatus.OK);
    }

    @PutMapping("/admin/productos/{productoId}/imagen")
    public ResponseEntity<ProductoDTO> acutalizarImagenProducto(@PathVariable Long productoId,
            @RequestParam("imagen") MultipartFile imagen) throws IOException {
        ProductoDTO productoActualizado = productoServicio.actualizarImagenProducto(productoId, imagen);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }
}