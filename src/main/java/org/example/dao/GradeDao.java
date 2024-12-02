package org.example.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;

import org.example.model.Grade;
import org.example.model.Subject;

/**
 * DAO for Grade
 */
@Stateless
public class GradeDao {
	@EJB
	SubjectDao subjectDao;
	@PersistenceContext(unitName = "Librarysys-persistence-unit")
	private EntityManager em;

	public int create(Grade entity) {
		em.persist(entity);
		em.flush();
		em.refresh(entity);

		return entity.getGradeId() ;
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

	// Save or update grade
	public void saveOrUpdate(Grade grade) {
		if (grade.getGradeId() == 0) {
			// If grade ID is 0, it's a new record, so persist it
			em.persist(grade);
		}
		else {
			// If grade ID is non-zero, update the existing record
			em.merge(grade);
		}
	}

	public Grade findByStudentAndSubject(int studentId, String subjectCode) {
		try {
			// First, get the Subject by subjectCode
			Subject subject = subjectDao.findBySubjectCode(subjectCode);
			if (subject == null) {
				throw new IllegalArgumentException("Subject not found with code: " + subjectCode);
			}

			// Now, use subjectId to find the Grade
			String jpql = "SELECT g FROM Grade g WHERE g.student.studentId = :studentId AND g.subject.subjectId = :subjectId";
			return em.createQuery(jpql, Grade.class)
					.setParameter("studentId", studentId)
					.setParameter("subjectId", subject.getSubjectId())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;  // Return null if no grade is found
		}
		catch (NonUniqueResultException e) {
			throw new IllegalStateException("More than one grade found for studentId: " + studentId + " and subjectCode: " + subjectCode);
		}
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

	public List<Grade> findGradesByStudentId(int studentId) {
		TypedQuery<Grade> query = em.createQuery(
				"SELECT g FROM Grade g WHERE g.student.id = :studentId", Grade.class
		);
		query.setParameter("studentId", studentId);

		return query.getResultList(); // Returns the list of grades for the student
	}

}
