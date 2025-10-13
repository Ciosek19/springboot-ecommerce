package com.udemy.sprinboot.ecommerce.springboot_ecommerce.repositorios.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Categoria;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Producto;

@Repository
public interface IProductoRepositorio extends JpaRepository<Producto,Long>{
    List<Producto> findByCategoriaOrderByPrecioAsc(Categoria categoria);
}
