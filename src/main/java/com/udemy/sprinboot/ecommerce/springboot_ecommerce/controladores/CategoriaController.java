package com.udemy.sprinboot.ecommerce.springboot_ecommerce.controladores;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.configuracion.AppConstantes;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.CategoriaDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.CategoriaRespuesta;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces.ICategoriaServicio;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoriaController {

    @Autowired
    private ICategoriaServicio categoriaServicio;

    @GetMapping("/echo")
    public ResponseEntity<String> echoMensaje(@RequestParam(name = "mensaje",defaultValue = "Default") String mensaje){
        return new ResponseEntity<>("Echo mensaje: "+mensaje, HttpStatus.OK);
    }

    @RequestMapping(value = "/publico/categorias", method = RequestMethod.GET)
    public ResponseEntity<CategoriaRespuesta> getTodasCategorias(
            @RequestParam(name = "paginaNumero", defaultValue = AppConstantes.PAGINA_NUMERO) Integer paginaNumero, 
        @RequestParam(name = "paginaTamano", defaultValue = AppConstantes.PAGINA_TAMANO) Integer paginaTamano,
        @RequestParam(name = "ordenarPor", defaultValue = AppConstantes.ORDENAR_CATEGORIAS_POR) String ordenarPor,
        @RequestParam(name = "ordenarDireccion", defaultValue = AppConstantes.ORDENAR_DIRECCION) String ordenarDireccion
    ) {
        CategoriaRespuesta resultado = categoriaServicio.obtenerTodasCategorias(paginaNumero, paginaTamano, ordenarPor, ordenarDireccion);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @RequestMapping(value = "/publico/categorias", method = RequestMethod.POST)
    public ResponseEntity<CategoriaDTO> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaDtoGuardada = categoriaServicio.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(categoriaDtoGuardada, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/publico/categorias/{categoriaId}", method = RequestMethod.DELETE)
    public ResponseEntity<CategoriaDTO> eliminarCategoria(@PathVariable Long categoriaId) {
        CategoriaDTO categoriaEliminada = categoriaServicio.eliminarCategoria(categoriaId);
        return new ResponseEntity<>(categoriaEliminada, HttpStatus.OK);
    }

    @RequestMapping(value = "/publico/categorias/{categoriaId}", method = RequestMethod.PUT)
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@Valid @PathVariable Long categoriaId,
                                                      @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO catActualizada = categoriaServicio.actualizarCategoria(categoriaDTO, categoriaId);
        return new ResponseEntity<>(catActualizada, HttpStatus.OK);
    }
}
