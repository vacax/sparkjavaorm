package com.avathartech.sparkjavaorm.main;

import com.avathartech.sparkjavaorm.entidades.Estudiante;
import com.avathartech.sparkjavaorm.entidades.Profesor;
import com.avathartech.sparkjavaorm.services.BootStrapServices;
import com.avathartech.sparkjavaorm.services.EstudianteServices;
import com.avathartech.sparkjavaorm.services.ProfesorServices;
import com.avathartech.sparkjavaorm.transformaciones.JsonTransformer;
import spark.Filter;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by vacax on 02/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //Iniciando la base de datos.
        BootStrapServices.getInstancia().init();

        //Inicializar las funcionalidades de Spark.
        initSpark();

        //Ejemplos sueltos del ORM
        //ejemplosSueltos();


    }

    private static void initSpark() {
        //Linea para agregar la pantalla de debug. En productivo se debe quitar.
        enableDebugScreen();

        staticFiles.location("/publico");

        JsonTransformer jsonTransformer = new JsonTransformer();

        for(int i=0;i<50;i++){
            EstudianteServices.getInstancia().crear(new Estudiante(i, "nombre "+i));
            ProfesorServices.getInstancia().crear(new Profesor("Profesor "+i));
        }

        /**
         * Inicio de la aplicación.
         */
        get("/",(request, response) ->{
            return "Ejemplo de SparkJava con JPA";
        });

        /**
         * Consulta de un estudiante por matricula
         * Convierte a JSON la salida del objeto.
         */
        get("/estudiante/:matricula",(request, response) ->{
            Integer matricula = Integer.parseInt(request.params("matricula")); //omitiendo control de errores.
            Estudiante estudiante = EstudianteServices.getInstancia().find(matricula);
            return estudiante;
        }, jsonTransformer);

        /**
         *Listar todos los estudiantes de la base da datos.
         */
        get("/estudiante",(request, response) ->{
            return EstudianteServices.getInstancia().findAll();
        }, jsonTransformer);

        /**
         * Lista los estudiantes que inicien con el
         */
        get("/estudiantePorNombre/:nombre",(request, response) ->{
            return EstudianteServices.getInstancia().findAllByNombre(request.params("nombre"));
        }, jsonTransformer);

        /**
         * Creación de
         */
        post("/estudiante", (request, response) -> {
            Estudiante estudiante = new Estudiante();
            estudiante.setMatricula(Integer.parseInt(request.queryParams("matricula")));
            estudiante.setNombre(request.queryParams("nombre"));
            EstudianteServices.getInstancia().crear(estudiante);
            return estudiante;
        }, jsonTransformer);

        delete("/estudiante/:matricula",(request, response) ->{
            Integer matricula = Integer.parseInt(request.params("matricula")); //omitiendo control de errores.
            Estudiante estudiante = EstudianteServices.getInstancia().find(matricula);
            EstudianteServices.getInstancia().eliminar(estudiante.getMatricula());
            HashMap<String, String> hashMap=new HashMap<String, String>();
            hashMap.put("accion", "Estudiante borrado");
            return hashMap;
        }, jsonTransformer);

        /**
         *
         */
        get("/profesor/:id",(request, response) ->{
            Integer id = Integer.parseInt(request.params("id")); //omitiendo control de errores.
            Profesor profesor = ProfesorServices.getInstancia().find(id);
            return profesor;
        }, jsonTransformer);

        get("/profesoresPorNombre/:nombre",(request, response) ->{
            return ProfesorServices.getInstancia().findAllByNombre(request.params("nombre"));
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

        get("/profesor",(request, response) ->{
            return ProfesorServices.getInstancia().findAll();
        }, jsonTransformer);
    }

    private static void notificarAdministradores(){
        System.out.println("Aplicando el filtro...");
    }

    private static void registroLog(String url){
        System.out.println("Aplicando el log");
    }

    private static void ejemplosSueltos(){

        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Estudiante estudiante = new Estudiante(20011136, "Carlos Camacho");
        entityManager.persist(estudiante);
        entityManager.getTransaction().commit();

        //Estudiante otroEstudiante2 = entityManager.find(Estudiante.class, 20011136);

        //JPQL o HQL
        List<Estudiante> lista = entityManager.createQuery("select e from Estudiante e", Estudiante.class)
                .getResultList();
        System.out.println("La cantidad de estudiantes es: "+lista.size());
        for(Estudiante e: lista){
            System.out.printf("Matricula: %d - Nombre: %s \n", e.getMatricula(), e.getNombre());
        }

        
        entityManager.detach(estudiante); //quitando de la gestión el objeto.

        Estudiante otroEstudiante = entityManager.find(Estudiante.class, 20011136);
        System.out.println("El nombre es: "+otroEstudiante.getNombre());

        entityManager.detach(otroEstudiante);

        //Actualizando una entidad desconectada haciendo merge.
        entityManager.getTransaction().begin();
        estudiante.setNombre("Otro Nombre");
        entityManager.merge(estudiante);
        entityManager.flush();
        entityManager.detach(estudiante);
        entityManager.getTransaction().commit();

        //actualizando el otro objeto y refrescando los datos.
        otroEstudiante = entityManager.find(Estudiante.class, 20011136);
        System.out.println("El nombre del objeto: "+otroEstudiante.getNombre());
        entityManager.close();
        //emf.close();

        emf.close();
    }
}
