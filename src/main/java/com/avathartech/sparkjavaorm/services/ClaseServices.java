package com.avathartech.sparkjavaorm.services;

import com.avathartech.sparkjavaorm.entidades.Clase;
import com.avathartech.sparkjavaorm.entidades.Profesor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vacax on 04/06/16.
 */
public class ClaseServices extends GestionDb<Profesor> {

    private static ClaseServices instancia;

    private ClaseServices(){
        super(Profesor.class);
    }

    public static ClaseServices getInstancia(){
        if(instancia==null){
            instancia = new ClaseServices();
        }
        return instancia;
    }



    /**
     *
     * @param nombre
     * @return
     */
    public List<Clase> findAllByProfesorClase(long id){
       /* EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Clase.findAllProfesorId");
        query.setParameter("id", id);
        List<Clase> lista = query.getResultList();
        em.create*/
        return null;
    }
}
