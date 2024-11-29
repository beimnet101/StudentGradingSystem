package org.example.model;
// Generated Jan 27, 2023, 3:03:53 PM by Hibernate Tools 5.2.11.Final


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name="role"
    ,catalog="librarysys"
)
public class Role  implements java.io.Serializable {


     private int id;
     private String role;
     private Set<Users> userses = new HashSet<Users>(0);

    public Role() {
    }

	
    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }
    public Role(int id, String role, Set<Users> userses) {
       this.id = id;
       this.role = role;
       this.userses = userses;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="role", nullable=false, length=64)
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="role")
    public Set<Users> getUserses() {
        return this.userses;
    }
    
    public void setUserses(Set<Users> userses) {
        this.userses = userses;
    }




}

