package com.brandeis.grant.repository;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.brandeis.grant.model.FacultyDocument;
import com.brandeis.grant.repository.customSearch.FacultySearchRepositoryCustom;


public interface FacultySearchRepository extends ElasticsearchRepository<FacultyDocument, String>, FacultySearchRepositoryCustom {
    List<FacultyDocument> findByDisplayNameContaining(String name);
    List<FacultyDocument> findByDisplayNameContainingIgnoreCase(String name);
}
