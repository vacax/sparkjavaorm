package com.avathartech.sparkjavaorm.main;

import com.avathartech.sparkjavaorm.entidades.Estudiante;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by vacax on 02/06/16.
 */
public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Estudiante estudiante = new Estudiante(20011136, "Carlos Camacho");
        entityManager.persist(estudiante);
        entityManager.getTransaction().commit();

        List<Estudiante> lista = entityManager.createQuery("select e from Estudiante e", Estudiante.class)
                .getResultList();
        System.out.println("La cantidad de estudiantes es: "+lista.size());

        entityManager.detach(estudiante); //quitando de la gestión el objeto.

        Estudiante otroEstudiante = entityManager.find(Estudiante.class, 20011136);
        System.out.println("El nombre es: "+otroEstudiante.getNombre());

        entityManager.detach(otroEstudiante);

        //Actualizando una entidad desconectada haciendo merge.
        estudiante.setNombre("Otro Nombre");
        entityManager.merge(estudiante);
        entityManager.detach(estudiante);

        //actualizando el otro objeto y refrescando los datos.
        otroEstudiante = entityManager.find(Estudiante.class, 20011136);
        System.out.println("El nombre del objeto: "+otroEstudiante.getNombre());

        entityManager.close();
        emf.close();
    }
}
