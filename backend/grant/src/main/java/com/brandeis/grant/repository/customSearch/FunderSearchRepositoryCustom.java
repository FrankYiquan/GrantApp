package com.brandeis.grant.repository.customSearch;

import java.util.List;

import com.brandeis.grant.model.FunderDocument;

public interface FunderSearchRepositoryCustom {
    List<FunderDocument> fuzzySearch(String funderName);
    
}
