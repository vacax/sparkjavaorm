package com.avathartech.sparkjavaorm.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by vacax on 03/06/16.
 */
public class GestionDb<T> {

    private static EntityManagerFactory emf;
    private Class<T> claseEntidad;


    public GestionDb(Class<T> claseEntidad) {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        }
        this.claseEntidad = claseEntidad;

    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    /**
     *
     * @param entidad
     * @return
     */
    private Object getValorCampo(T entidad){
        if(entidad == null){
            return null;
        }
        //aplicando la clase de reflexión.
        for(Field f : entidad.getClass().getDeclaredFields()) {  //tomando todos los campos privados.
            if (f.isAnnotationPresent(Id.class)) {
                try {
                    f.setAccessible(true);
                    Object valorCampo = f.get(entidad);

                    System.out.println("nombre del campo: "+f.getName());
                    System.out.println("Tipo del campo: "+f.getType().getName());
                    System.out.println("Valor del campo: "+valorCampo );

                    return valorCampo;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     *
     * @param entidad
     */
    public void crear(T entidad){
        EntityManager em = getEntityManager();

        try {
            if (em.find(claseEntidad, getValorCampo(entidad)) != null) {
                System.out.println("La entidad a guardar existe, no creada.");
                return;
            }
        }catch (IllegalArgumentException ie){
            //
            System.out.println("Parametro ilegal.");
        }
        
        em.getTransaction().begin();
        try {
            em.persist(entidad);
            em.getTransaction().commit();

        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param entidad
     */
    public void editar(T entidad){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entidad);
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param entidadId
     */
    public void eliminar(Object  entidadId){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entidad = em.find(claseEntidad, entidadId);
            em.remove(entidad);
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public T find(Object id) {
        EntityManager em = getEntityManager();
        try{
            return em.find(claseEntidad, id);
        } catch (Exception ex){
            throw  ex;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public List<T> findAll(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex){
            throw  ex;
        }finally {
            em.close();
        }
    }
}
