package com.avathartech.sparkjavaorm.services;

import com.avathartech.sparkjavaorm.entidades.Profesor;

/**
 * Created by vacax on 04/06/16.
 */
public class ProfesorServices extends GestionDb<Profesor> {

    private static ProfesorServices instancia;

    private ProfesorServices(){
        super(Profesor.class);
    }

    public static ProfesorServices getInstancia(){
        if(instancia==null){
            instancia = new ProfesorServices();
        }
        return instancia;
    }


}
