package com.brandeis.grant.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Faculty {
    @Id
    private String facultyId;

    private String displayName;

    private String orcid;

    @ManyToMany(mappedBy = "faculties")
    private List<Award> awards; // all the award for a faculty

    @ElementCollection
    @CollectionTable(name = "faculty_alternative_names", joinColumns = @JoinColumn(name = "faculty_id"))
    @Column(name = "alternative_name")
    private List<String> displayNameAlternatives; //all the alternative names for a faculty

}
