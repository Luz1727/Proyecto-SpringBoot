package com.alumnos.alumnos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alumnos.alumnos.model.Alumno;
import com.alumnos.alumnos.repository.AlumnoRepository;

@Service
public class AlumnoServices {

    @Autowired
    private AlumnoRepository alumnoRepository;
    public List<Alumno> ListarAlumnos(){
        return alumnoRepository.findAll();
    
    }

    public void guardarAlumno(Alumno alumno){
        alumnoRepository.save(alumno);
    }

    public Alumno obtenerAlumno(Integer id){
return alumnoRepository.findById(id).get();
    }

    public void eliminarAlumno(Integer id){
        alumnoRepository.deleteById(id);
    }

}
