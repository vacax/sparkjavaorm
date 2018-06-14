package com.avathartech.sparkjavaorm.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by vacax on 02/06/16.
 */
@Entity
public class Estudiante implements Serializable {

    @Id
    private int matricula;
    private String nombre;
    @Transient
    private int edad;

    @ManyToMany(mappedBy = "listaEstudiante", fetch = FetchType.EAGER) //indicando que la carga será en linea.
    private Set<Clase> listaClases; //La duena de la relación es la clase Clase


    public Estudiante(){  //Debo tener el constructor vacio...

    }

    public Estudiante(int matricula, String nombre) {
        this.setMatricula(matricula);
        this.setNombre(nombre);
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
       /* if(getMatricula() == 20011137){
              throw new RuntimeException("No puede ser esa matricula..");
        }*/
    }

    public Set<Clase> getListaClases() {
        return listaClases;
    }

    public void setListaClases(Set<Clase> listaClases) {
        this.listaClases = listaClases;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
