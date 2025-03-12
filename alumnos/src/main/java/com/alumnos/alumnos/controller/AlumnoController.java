package com.alumnos.alumnos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alumnos.alumnos.model.Alumno;
import com.alumnos.alumnos.services.AlumnoServices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;



@RestController
@RequestMapping("/bienvenido")
public class AlumnoController {
    


    @GetMapping(value = "/verAlumnos", produces = MediaType.TEXT_HTML_VALUE)
public ResponseEntity<String> verAlumnos() {
    try {
        Resource resource = new ClassPathResource("static/get.html");
        InputStream inputStream = resource.getInputStream();
        String html = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        return ResponseEntity.ok().body(html);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar la página");


    }
}

    @Autowired
    private AlumnoServices alumnoServices;

    @GetMapping("/traer_alumnos")
    public List<Alumno> listarAlumno() {
        return alumnoServices.ListarAlumnos();
    }
    
    @PostMapping("/insertar_alumnos")
    public ResponseEntity<String> guardarAlumno(@RequestBody Alumno alumno) {
        try {
            alumnoServices.guardarAlumno(alumno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Alumno creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el alumno: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarAlumno(@RequestBody Alumno alumno, @PathVariable Integer id) {
        try {
            Alumno alumnoExiste = alumnoServices.obtenerAlumno(id);
            if (alumnoExiste == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
            }

            alumnoExiste.setNombre(alumno.getNombre());
            alumnoExiste.setApellidoPaterno(alumno.getApellidoPaterno());
            alumnoExiste.setApellidoMaterno(alumno.getApellidoMaterno());
            alumnoExiste.setNc(alumno.getNc());
            alumnoExiste.setSemestre(alumno.getSemestre());
            alumnoExiste.setCarrera(alumno.getCarrera());
            alumnoExiste.setDireccion(alumno.getDireccion());

            alumnoServices.guardarAlumno(alumnoExiste);
            return ResponseEntity.ok(alumnoExiste);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el alumno: " + e.getMessage());
        }
    }
}
