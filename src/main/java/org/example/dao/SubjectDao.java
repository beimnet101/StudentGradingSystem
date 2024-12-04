package org.example.dao;

import org.example.model.Subject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * DAO for User
 */
@Stateless

public class SubjectDao {
    @PersistenceContext(unitName = "Librarysys-persistence-unit")
    private EntityManager em;
    public int create(Subject entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity.getSubjectId(); // Assuming the ID field in `Users` is named `id`
    }

        public Subject findById(int id) {
            return em.find(Subject.class, id);
        }
      public Subject findBySubjectCode(String subjectCode) {
        TypedQuery<Subject> query = em.createQuery("SELECT s FROM Subject s WHERE s.subjectCode = :subjectCode", Subject.class);
        query.setParameter("subjectCode", subjectCode);
        return query.getSingleResult();
    }


    public int findCreditHoursBySubjectId(int subjectId) {
        TypedQuery<Integer> query = em.createQuery(
                "SELECT s.credithour FROM Subject s WHERE s.id = :subjectId", Integer.class
        );
        query.setParameter("subjectId", subjectId);

        try {
            Integer result = query.getSingleResult();
            return result != null ? result : 0; // Return 0 if the result is null
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Subject not found with ID: " + subjectId);
        }
    }





    public List<Subject> listAll(int start, int max) {
            TypedQuery<Subject> query = em.createQuery("SELECT s FROM Subject s", Subject.class);
            query.setFirstResult(start);
            query.setMaxResults(max);
            return query.getResultList();
        }

        @Transactional
        public void update(Subject entity) {

        em.merge(entity);
        }

        @Transactional
        public void delete(int id) {
            Subject entity = em.find(Subject.class, id);
            if (entity != null) {
                em.remove(entity);
            }
        }














    }


