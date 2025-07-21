package com.VbrOffice.vbr.Entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "case_category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CaseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String categoryName;

//    if you dont want to display all subcatogeries in response
    
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<CaseSubType> subTypes;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
//
//    public List<CaseSubType> getSubTypes() {
//        return subTypes;
//    }
//
//    public void setSubTypes(List<CaseSubType> subTypes) {
//        this.subTypes = subTypes;
//    }
}
