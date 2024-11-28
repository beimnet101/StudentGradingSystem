package org.example.model;
// Generated Jan 27, 2023, 3:03:53 PM by Hibernate Tools 5.2.11.Final


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Book generated by hbm2java
 */
@Entity
@Table(name="book"
    ,catalog="librarysys"
)
public class Book  implements java.io.Serializable {


     private int id;
     private Catagory catagory;
     private String title;
     private Set<Loanedbook> loanedbooks = new HashSet<Loanedbook>(0);

    public Book() {
    }

	
    public Book(int id, Catagory catagory, String title) {
        this.id = id;
        this.catagory = catagory;
        this.title = title;
    }
    public Book(int id, Catagory catagory, String title, Set<Loanedbook> loanedbooks) {
       this.id = id;
       this.catagory = catagory;
       this.title = title;
       this.loanedbooks = loanedbooks;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="catagory_id", nullable=false)
    public Catagory getCatagory() {
        return this.catagory;
    }
    
    public void setCatagory(Catagory catagory) {
        this.catagory = catagory;
    }

    
    @Column(name="title", nullable=false, length=64)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="book")
    public Set<Loanedbook> getLoanedbooks() {
        return this.loanedbooks;
    }
    
    public void setLoanedbooks(Set<Loanedbook> loanedbooks) {
        this.loanedbooks = loanedbooks;
    }




}


