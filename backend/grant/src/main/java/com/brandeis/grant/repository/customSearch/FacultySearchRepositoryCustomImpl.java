package com.brandeis.grant.repository.customSearch;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import com.brandeis.grant.model.FacultyDocument;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FacultySearchRepositoryCustomImpl implements FacultySearchRepositoryCustom {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<FacultyDocument> fuzzySearch(String name) {
        // Build two fuzzy queries
        FuzzyQuery fuzzyDisplayName = FuzzyQuery.of(f -> f
            .field("displayName")
            .value(name)
        );

        FuzzyQuery fuzzyAltName = FuzzyQuery.of(f -> f
            .field("displayNameAlternatives")
            .value(name)
        );

        // Wrap them in a bool should query (OR logic)
        Query query = Query.of(q -> q.bool(b -> b
            .should(q1 -> q1.fuzzy(fuzzyDisplayName))
            .should(q2 -> q2.fuzzy(fuzzyAltName))
        ));

        NativeQuery nativeQuery = NativeQuery.builder()
            .withQuery(query)
            .build();

        SearchHits<FacultyDocument> searchHits = elasticsearchOperations.search(nativeQuery, FacultyDocument.class);

        return searchHits.stream()
            .map(hit -> hit.getContent())
            .collect(Collectors.toList());
    }
}


