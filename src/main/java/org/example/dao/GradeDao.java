package org.example.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.example.model.Grade;

/**
 * DAO for Grade
 */
@Stateless
public class GradeDao {
	@PersistenceContext(unitName = "Librarysys-persistence-unit")
	private EntityManager em;

	public void create(Grade entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Grade entity = em.find(Grade.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Grade findById(int id) {
		return em.find(Grade.class, id);
	}

	public Grade update(Grade entity) {
		return em.merge(entity);
	}

	public List<Grade> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Grade> findAllQuery = em.createQuery(
				"SELECT DISTINCT g FROM Grade g LEFT JOIN FETCH g.subject LEFT JOIN FETCH g.student ORDER BY g.gradeId",
				Grade.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
