package com.brandeis.grant.repository.customSearch;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import com.brandeis.grant.model.FunderDocument;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class FunderSearchRepositoryCustomImpl implements FunderSearchRepositoryCustom {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<FunderDocument> fuzzySearch(String funderName) {
       // Split the name into individual words (tokens)
        String[] words = funderName.split("\\s+");

        // Create a list of fuzzy queries for each word
        Query query = Query.of(q -> q.bool(b -> {
            for (String word : words) {
                // Apply fuzzy query for each word in the sentence
                b.should(s -> s.fuzzy(fuzzy -> fuzzy
                    .field("funderName")
                    .value(word)  // Match each word in the field
                    .fuzziness("AUTO")  // Fuzziness level (can be customized)
                ));
            }
            return b;
        }));

        // Build the native query
        NativeQuery searchQuery = NativeQuery.builder()
            .withQuery(query)
            .build();

        // Execute the search query
        SearchHits<FunderDocument> searchHits = elasticsearchOperations.search(searchQuery, FunderDocument.class);

        // Return the results as a list
        return searchHits.stream()
            .map(hit -> hit.getContent())
            .collect(Collectors.toList());
    }
            
    
}
