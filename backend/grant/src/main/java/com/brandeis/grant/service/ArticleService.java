package com.brandeis.grant.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandeis.grant.model.Article;
import com.brandeis.grant.model.Award;
import com.brandeis.grant.repository.ArticleRepository;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Award> getAwardsForArticle(String articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        return article.getAwards();
    }

    public List<Article> getArticlesByPublicationYear(int year) {
        return articleRepository.findByPublicationYear(year);
    }

    //search articles by keywords in title
    public List<Article> getArticlesByTitleKeyword(List<String> keywords) {
        Set<Article> articles = new HashSet<>();
        for (String keyword : keywords) {
            articles.addAll(articleRepository.findByTitleContaining(keyword));
        }
        return new ArrayList<>(articles);
    }

    
}
