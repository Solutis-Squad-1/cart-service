package br.com.solutis.squad1.cartservice.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConfig {
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("cart-service");

    public EntityManager createEntityManager(){
        return FACTORY.createEntityManager();
    }
    public void closeEntityManager(EntityManager entityManager){
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    public void closeFactory(){
        if (FACTORY != null && FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}
