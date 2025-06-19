package com.brandeis.grant.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funder {
    
    @Id
    private String funderId; //id (ROR id)
    private String funderName; //funder_display_name

    @OneToMany(mappedBy = "funderId")
    private List<Award> awards;


}
