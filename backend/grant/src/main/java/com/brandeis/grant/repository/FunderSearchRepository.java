package com.brandeis.grant.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.brandeis.grant.model.FunderDocument;
import com.brandeis.grant.repository.customSearch.FunderSearchRepositoryCustom;

public interface FunderSearchRepository extends ElasticsearchRepository<FunderDocument, String>, FunderSearchRepositoryCustom {
    List<FunderDocument> findByFunderName(String funderName);

}