package org.example.dao;


import org.example.cassiomolin.user.domain.UserAuthority;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO for UserAuthority
 */
@Stateless
public class UserAuthorityDao {
	@PersistenceContext(unitName = "Librarysys-persistence-unit")
	private EntityManager em;

	public void create(UserAuthority entity) {
		em.persist(entity);
	}

	public void deleteById(Long id) {
		UserAuthority entity = em.find(UserAuthority.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public UserAuthority findById(Long id) {
		return em.find(UserAuthority.class, id);
	}

	public UserAuthority update(UserAuthority entity) {
		return em.merge(entity);
	}

	public List<UserAuthority> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<UserAuthority> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT u FROM UserAuthority u LEFT JOIN FETCH u.User ORDER BY u.id",
						UserAuthority.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
