package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces.IArchivoServicio;

@Service
public class ArchivoServicio implements IArchivoServicio{

    @Override
    public String subirImagen(String ruta, MultipartFile archivo) throws IOException {
        // Nombre archivo actual / archivo original
        String nombreOriginal = archivo.getOriginalFilename();

        // Generar un guid para el nombre del archivo
        String randomId = UUID.randomUUID().toString();
        String archivoNombre = randomId.concat(nombreOriginal.substring(nombreOriginal.lastIndexOf('.')));
        String archivoRuta = ruta + File.separator + archivoNombre;

        // Chequear si la ruta existe y crear
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        // Subir al servidor
        Files.copy(archivo.getInputStream(), Paths.get(archivoRuta));

        return archivoNombre;
    }
}
