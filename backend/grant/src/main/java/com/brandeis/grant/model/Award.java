package com.brandeis.grant.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Award {


    @Id
    private String awardId;
    private LocalDate startDate;
    private int amount;
    
    @ManyToMany
    @JoinTable(
        name = "award_faculty",
        joinColumns = @JoinColumn(name = "award_id"),
        inverseJoinColumns = @JoinColumn(name = "faculty_id")
    )
    private List<Faculty> faculties; 


    @ManyToMany
    @JoinTable(
        name = "award_article",
        joinColumns = @JoinColumn(name = "award_id"),
        inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<Article> articles;

    @ManyToOne
    @JoinColumn(name = "funder_id")
    private Funder funderId;

    

    
}
