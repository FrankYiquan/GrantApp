package com.brandeis.grant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandeis.grant.model.Article;
import com.brandeis.grant.service.FunderService;

public interface ArticleRepository extends JpaRepository<Article, String> {
    List<FunderService> getAwardsByArticleId(String articleId);
    List<Article> findByPublicationYear(int publicationYear);
    List<Article> findByTitleContaining(String keyword);
}
