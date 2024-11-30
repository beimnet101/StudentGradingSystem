package org.example.dao;

import org.example.model.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DAO for Loanedbook
 */
@Stateless
public class RoleDao {
	@PersistenceContext(unitName = "Librarysys-persistence-unit")
	private EntityManager em;

	public void create(Role entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Role entity = em.find(Role.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Role findById(int id) {
		return em.find(Role.class, id);
	}

	public Role findByName(String name) {
		return em.find(Role.class, name);
	}



	public Role update(Role entity) {
		return em.merge(entity);
	}

//	public List<Loanedbook> listAll(Integer startPosition, Integer maxResult) {
//		TypedQuery<Loanedbook> findAllQuery = em.createQuery(
//				"SELECT DISTINCT l FROM Loanedbook l LEFT JOIN FETCH l.book LEFT JOIN FETCH l.users ORDER BY l.id",
//				Loanedbook.class);
//		if (startPosition != null) {
//			findAllQuery.setFirstResult(startPosition);
//		}
//		if (maxResult != null) {
//			findAllQuery.setMaxResults(maxResult);
//		}
//		return findAllQuery.getResultList();
//	}

}
