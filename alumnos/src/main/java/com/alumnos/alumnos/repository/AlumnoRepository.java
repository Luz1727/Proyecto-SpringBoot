package com.alumnos.alumnos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alumnos.alumnos.model.Alumno;
public interface AlumnoRepository extends JpaRepository <Alumno, Integer>{

}
