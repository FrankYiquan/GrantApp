package com.brandeis.grant.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    private String articleId; //openAlex id
    private String title; //title
    private int publicationYear; //publication_year
    private LocalDate publicationDate; //publication_date
   

    @ManyToMany(mappedBy = "articles")
    private List<Award> awards;

    @ManyToMany
    @JoinTable(
        name = "faculty_article",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "faculty_id")
    )
    private List<Faculty> authors;


    
}
