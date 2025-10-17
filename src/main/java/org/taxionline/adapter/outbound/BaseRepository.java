package org.taxionline.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.taxionline.config.dao.DataSourceManager;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class BaseRepository<T> {

    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseRepository() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    public void persist(T entity) {
        executeInTransaction(em -> em.persist(entity));
    }

    public void update(T entity) {
        executeInTransaction(em -> em.merge(entity));
    }

    public void delete(Object id) {
        executeInTransaction(em -> {
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
        });
    }

    public Optional<T> findById(Object id) throws SQLException {
        try (EntityManager em = DataSourceManager.getInstance().getEntityManager()) {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        }
    }

    public List<T> findAll() throws SQLException {
        try (EntityManager em = DataSourceManager.getInstance().getEntityManager()) {
            TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
            return query.getResultList();
        }
    }

    public QueryResult<T> find(String condition, Object... params) {
        EntityManager em = DataSourceManager.getInstance().getEntityManager();

        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE " + condition;
        TypedQuery<T> query = em.createQuery(jpql, entityClass);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        return new QueryResult<>(em, query);
    }

    public long count(String condition, Object... params) {
        EntityManager em = DataSourceManager.getInstance().getEntityManager();

        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE " + condition;
        TypedQuery<T> query = em.createQuery(jpql, entityClass);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        return query.getResultList().size();
    }

    private void executeInTransaction(Consumer<EntityManager> action) {
        try (EntityManager em = DataSourceManager.getInstance().getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                action.accept(em);
                tx.commit();
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw new RuntimeException("1", e);
            }
        }
    }

    public record QueryResult<T>(EntityManager em, TypedQuery<T> query) {

        public List<T> list() {
            try {
                return query.getResultList();
            } finally {
                em.close();
            }
        }

        public Optional<T> singleResultOptional() {
            try {
                query.setMaxResults(1);
                List<T> result = query.getResultList();
                return result.isEmpty() ? Optional.empty() : Optional.of(result.getFirst());
            } finally {
                em.close();
            }
        }
    }
}
