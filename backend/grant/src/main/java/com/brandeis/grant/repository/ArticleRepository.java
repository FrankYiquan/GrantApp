package com.brandeis.grant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brandeis.grant.model.Article;
import com.brandeis.grant.service.FunderService;

public interface ArticleRepository extends JpaRepository<Article, String> {
    List<FunderService> getAwardsByArticleId(String articleId);
    List<Article> findByPublicationYear(int publicationYear);
    List<Article> findByTitleContaining(String keyword);
    int countByPublicationYear(int publicationYear);
    
    @Query("SELECT COUNT(a) FROM Article a WHERE SIZE(a.awards) > 0")
    long countArticlesWithAwards();
}
