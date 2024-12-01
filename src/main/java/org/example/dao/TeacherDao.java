package org.example.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.example.model.Teacher;

/**
 * DAO for Teacher
 */
@Stateless
public class TeacherDao {
	@PersistenceContext(unitName = "Librarysys-persistence-unit")
	private EntityManager em;

	public void create(Teacher entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Teacher entity = em.find(Teacher.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Teacher findById(int id) {
		return em.find(Teacher.class, id);
	}

	public Teacher update(Teacher entity) {
		return em.merge(entity);
	}

	public List<Teacher> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Teacher> findAllQuery = em.createQuery(
				"SELECT DISTINCT t FROM Teacher t where t.id=:id ORDER BY t.id", Teacher.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
