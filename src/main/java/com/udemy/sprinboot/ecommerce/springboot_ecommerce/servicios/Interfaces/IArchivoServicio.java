package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IArchivoServicio {
    String subirImagen(String ruta, MultipartFile archivo) throws IOException;
}
