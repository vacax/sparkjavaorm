package com.avathartech.sparkjavaorm.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;

/**
 * Created by vacax on 02/06/16.
 */
@Entity
public class Estudiante implements Serializable {

    @Id
    int matricula;
    String nombre;

    public Estudiante(){

    }

    public Estudiante(int matricula, String nombre) {
        this.matricula = matricula;
        this.nombre = nombre;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @PreUpdate
    @PrePersist
    private void cancelarMatricula(){
        if(matricula == 20011137){
              throw new RuntimeException("No puede ser esa matricula..");
        }
    }
}
