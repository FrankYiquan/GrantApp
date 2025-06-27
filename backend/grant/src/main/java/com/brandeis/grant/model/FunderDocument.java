package com.brandeis.grant.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "funder")
public class FunderDocument {
    @Id
    private String funderId;
     @Field(type = FieldType.Text)
    private String funderName; 
    
}
