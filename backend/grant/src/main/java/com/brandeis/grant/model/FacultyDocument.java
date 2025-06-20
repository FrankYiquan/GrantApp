package com.brandeis.grant.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "faculty")
public class FacultyDocument {
    @Id
    private String facultyId;

    private String displayName;
    private String orcid;
    private List<String> displayNameAlternatives;

}
