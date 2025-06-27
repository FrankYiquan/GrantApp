package com.brandeis.grant.repository.customSearch;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import com.brandeis.grant.model.FacultyDocument;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FacultySearchRepositoryCustomImpl implements FacultySearchRepositoryCustom {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<FacultyDocument> fuzzySearch(String name) {
       // Split the input name into individual words
    String[] words = name.split("\\s+");

    // Create a list of fuzzy queries for each word
    Query query = Query.of(q -> q.bool(b -> {
        for (String word : words) {
            // Fuzzy query for displayName field for each word
            b.should(s -> s.fuzzy(fuzzy -> fuzzy
                .field("displayName")
                .value(word)  // Match each word in the field displayName
                .fuzziness("AUTO")  // Fuzziness level (can be customized)
            ));

            // Fuzzy query for displayNameAlternatives field for each word
            b.should(s -> s.fuzzy(fuzzy -> fuzzy
                .field("displayNameAlternatives")
                .value(word)  // Match each word in the field displayNameAlternatives
                .fuzziness("AUTO")  // Fuzziness level (can be customized)
            ));
        }
        return b;
    }));

    // Build the native query with the query
    NativeQuery searchQuery = NativeQuery.builder()
        .withQuery(query)
        .build();

    // Execute the search query
    SearchHits<FacultyDocument> searchHits = elasticsearchOperations.search(searchQuery, FacultyDocument.class);

    // Return the search results
    return searchHits.stream()
        .map(hit -> hit.getContent())
        .collect(Collectors.toList());
    }
}


