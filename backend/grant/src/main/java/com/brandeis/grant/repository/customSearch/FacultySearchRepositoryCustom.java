package com.brandeis.grant.repository.customSearch;

import java.util.List;

import com.brandeis.grant.model.FacultyDocument;

public interface FacultySearchRepositoryCustom {
    List<FacultyDocument> fuzzySearch(String name);
}
