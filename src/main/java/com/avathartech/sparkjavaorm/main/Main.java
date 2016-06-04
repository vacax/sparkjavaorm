package com.avathartech.sparkjavaorm.main;

import com.avathartech.sparkjavaorm.entidades.Estudiante;
import com.avathartech.sparkjavaorm.entidades.Profesor;
import com.avathartech.sparkjavaorm.services.EstudianteServices;
import com.avathartech.sparkjavaorm.services.ProfesorServices;
import com.avathartech.sparkjavaorm.transformaciones.JsonTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by vacax on 02/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //Linea para agregar la pantalla de debug. En productivo se debe quitar.
        enableDebugScreen();

        JsonTransformer jsonTransformer = new JsonTransformer();

        get("/",(request, response) ->{
            return "Ejemplo de SparkJava con JPA";
        });

        /**
         * Convierte a JSON la salida del objeto.
         */
        get("/estudiante/:matricula",(request, response) ->{
            Integer matricula = Integer.parseInt(request.params("matricula")); //omitiendo control de errores.
            Estudiante estudiante = EstudianteServices.getInstancia().find(matricula);
            return estudiante;
        }, jsonTransformer);

        /**
         *
         */
        get("/estudiante",(request, response) ->{
            return EstudianteServices.getInstancia().findAll();
        }, jsonTransformer);

        /**
         *
         */
        post("/estudiante", (request, response) -> {
            Estudiante estudiante = new Estudiante();
            estudiante.setMatricula(Integer.parseInt(request.queryParams("matricula")));
            estudiante.setNombre(request.queryParams("nombre"));
            EstudianteServices.getInstancia().crear(estudiante);
            return estudiante;
        }, jsonTransformer);

        /**
         *
         */
        get("/profesor/:id",(request, response) ->{
            Integer id = Integer.parseInt(request.params("id")); //omitiendo control de errores.
            Profesor profesor = ProfesorServices.getInstancia().find(id);
            return profesor;
        }, jsonTransformer);

        /**
         *
         */
        post("/profesor", (request, response) -> {
            Profesor profesor = new Profesor();
            profesor.setNombre(request.queryParams("nombre"));
            ProfesorServices.getInstancia().crear(profesor);
            return profesor;
        }, jsonTransformer);

    }

    private static void ejemplosSueltos(){
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Estudiante estudiante = new Estudiante(20011136, "Carlos Camacho");
        entityManager.persist(estudiante);
        entityManager.getTransaction().commit();

        //JPQL o HQL
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
        //emf.close();

        emf.close();
    }
}
