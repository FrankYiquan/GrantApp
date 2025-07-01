package com.brandeis.grant.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandeis.grant.model.Article;
import com.brandeis.grant.service.FunderService;

public interface ArticleRepository extends JpaRepository<Article, String> {
    List<FunderService> getAwardsByArticleId(String articleId);
    List<Article> findByPublicationYear(int publicationYear);
   
    List<Article> findByTitleContaining(String keyword);
    int countByPublicationYear(int publicationYear);
    
    @Query("SELECT COUNT(a) FROM Article a WHERE SIZE(a.awards) > 0")
    long countArticlesWithAwards();

    @Query("""
    SELECT a FROM Article a
    JOIN a.authors f
    WHERE f.facultyId = :facultyId
    """)
    List<Article> findArticlesByFacultyId(@Param("facultyId") String facultyId);

    @Query("""
    SELECT a FROM Article a
    JOIN a.authors f
    WHERE f.facultyId = :facultyId AND a.publicationYear = :year
    """)
    List<Article> findArticlesByFacultyIdAndByYear(@Param("facultyId") String facultyId, @Param("year") int year);

    @Query("""
    SELECT COUNT(a)
    FROM Article a
    JOIN a.authors f
    WHERE f.facultyId = :facultyId
      AND a.awards IS NOT EMPTY
    """)
    int countArticlesByFacultyWithAwards(@Param("facultyId") String facultyId);

    @Query("""
    SELECT COUNT(a)
    FROM Article a
    JOIN a.authors f
    WHERE f.facultyId = :facultyId
      AND a.awards IS NOT EMPTY
    """)
    int countArticlesByFacultyWithAwardsAndByYear(@Param("facultyId") String facultyId, @Param("year") int year);

    @Query("""
    SELECT a.articleId, a.title, a.publicationYear, SUM(aw.amount)
    FROM Article a
    JOIN a.authors au
    JOIN a.awards aw
    WHERE au.facultyId = :facultyId
    GROUP BY a.articleId, a.title, a.publicationYear
    ORDER BY SUM(aw.amount) DESC
    """)
    List<Object[]> findTopArticlesByFacultyAggregated(@Param("facultyId") String facultyId, Pageable pageable);



    @Query("""
    SELECT a.articleId, a.title, a.publicationYear, SUM(aw.amount)
    FROM Article a
    JOIN a.authors au
    JOIN a.awards aw
    WHERE au.facultyId = :facultyId AND a.publicationYear = :year
    GROUP BY a.articleId, a.title, a.publicationYear
    ORDER BY SUM(aw.amount) DESC
    """)
    List<Object[]> findTopArticlesByFacultyAggregatedAndByYear(@Param("facultyId") String facultyId, @Param("year") int year, Pageable pageable);
    



    
}
