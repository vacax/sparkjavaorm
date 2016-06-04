package com.avathartech.sparkjavaorm.services;

import com.avathartech.sparkjavaorm.entidades.Estudiante;


/**
 *
 * Created by vacax on 03/06/16.
 */
public class EstudianteServices extends GestionDb<Estudiante> {

    private static EstudianteServices instancia;

    private EstudianteServices() {
        super(Estudiante.class);
    }

    public static EstudianteServices getInstancia(){
        if(instancia==null){
            instancia = new EstudianteServices();
        }
        return instancia;
    }


}
