package org.example.dao;



import org.example.model.Users;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * DAO for User
 */
@Stateless
public class UserDao {
    @PersistenceContext(unitName = "Librarysys-persistence-unit")
    private EntityManager em;
    public int create(Users entity) {
        em.persist(entity);
        em.flush();
        return entity.getId(); // Assuming the ID field in `Users` is named `id`
    }

    public void deleteById(int id) {
        Users entity = em.find(Users.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }


        public List<Users> listAllUsersByRole(int roleId, int start, int max) {
            // Create a typed query to select users by role with pagination
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.role.id = :roleId", Users.class);
            query.setParameter("roleId", roleId);
            query.setFirstResult(start);
            query.setMaxResults(max);
            return query.getResultList();
        }










    public Users findById(int id)
    {
        return em.find(Users.class, id);
    }

    public Users findByIds(Long id) {
        List<Users> resultList = em.createQuery(
                "SELECT DISTINCT p FROM Users p where p.id=:id ORDER BY p.id",
                Users.class).setParameter("id",id).getResultList();
        if(!resultList.isEmpty()){
            return resultList.get(0);
        }
        return null;
    }


    public Users update(Users entity) {
        em.flush();
        return em.merge(entity);
    }

    public List<Users> listAll(Integer startPosition, Integer maxResult) {
        TypedQuery<Users> findAllQuery = em.createQuery(
                "SELECT u FROM Users u ORDER BY u.id", Users.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }






    public List<Users> userList(String searchQuery, Integer startPosition, Integer maxResult) {
        Optional.ofNullable(searchQuery).orElse("");
        TypedQuery<Users> findAllQuery = em.createQuery(
                "SELECT DISTINCT u FROM Users u where u.username like concat('%',:query,'%') ORDER BY u.id", Users.class).setParameter("query",searchQuery);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }

    public List<Users> userListCount(String searchQuery ) {
        Optional.ofNullable(searchQuery).orElse("");
     return em.createQuery(
                "SELECT DISTINCT u FROM Users u where u.username like concat('%',:query,'%')  ORDER BY u.id", Users.class)
            .setParameter("query",searchQuery).getResultList();

    }

    public Long countForListAll() {
        return em.createQuery(
                "SELECT DISTINCT count(c) FROM Users c",
                Long.class).getSingleResult();
    }



    public Users findUserByUserName(String userName) {
        TypedQuery<Users> findAllQuery = em.createQuery(
                "SELECT DISTINCT u FROM Users u WHERE u.username = :userName", Users.class);
        findAllQuery.setParameter("userName", userName);
        List<Users> users = findAllQuery.getResultList();
        return (users.isEmpty()) ? null : users.get(0);
    }
    public Users updateUser(Users entity)
    {
        Users user =em.merge(entity);
        em.flush();
        return user;
    }



}
