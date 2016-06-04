package com.avathartech.sparkjavaorm.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
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
     */
    public void crear(T entidad){
        EntityManager em = getEntityManager();
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
     * @param entidad
     */
    public void eliminar(T entidad){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
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
