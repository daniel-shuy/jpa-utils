package com.github.daniel.shuy;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JPAUtils {
    private final EntityManagerFactory entityManagerFactory;
    
    public JPAUtils(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    /**
     * Because {@link EntityManager} does not extend {@link AutoCloseable},
     * try-with-resources cannot be used to ensure an {@link EntityManager}
     * will always be properly closed after use.
     * <p>
     * This convenience method retrieves a new {@link EntityManager} and automatically
     * closes it afterwards.
     * @param <R> The return value type to return from the function specified.
     * @param function The function to execute.
     * @return The return value from the function specified.
     */
    public <R> R executeQuery(Function<EntityManager, R> function) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            return function.apply(entityManager);
        }
        finally {
            entityManager.close();
        }
    }
    /**
     * Because {@link EntityManager} does not extend {@link AutoCloseable},
     * try-with-resources cannot be used to ensure an {@link EntityManager}
     * will always be properly closed after use.
     * <p>
     * This convenience method retrieves a new {@link EntityManager} and automatically
     * closes it afterwards.
     * @param consumer The function to execute.
     */
    public void executeQuery(Consumer<EntityManager> consumer) {
        executeQuery((entityManager) -> {
            consumer.accept(entityManager);
            return null;
        });
    }
}
