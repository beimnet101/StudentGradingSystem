package org.example.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.example.model.Student;
import org.example.model.Users;

/**
 * DAO for Student
 */
@Stateless
public class StudentDao {
	@PersistenceContext(unitName = "Librarysys-persistence-unit")
	private EntityManager em;

	public void create(Student entity) {


		// Persist the student entity
		em.persist(entity);
		em.flush();
	}




	public void deleteById(int id) {
		Student entity = em.find(Student.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Student findById(int id) {
		return em.find(Student.class, id);
	}

	public Student update(Student entity) {
		return em.merge(entity);
	}

	public List<Student> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Student> findAllQuery = em.createQuery(
				"SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.user ORDER BY s.studentId", Student.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
