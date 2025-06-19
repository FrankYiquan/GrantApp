package com.brandeis.grant.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "faculties")
    private List<Award> awards; // all the award for a faculty

}
