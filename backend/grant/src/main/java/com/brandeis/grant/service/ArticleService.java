package com.brandeis.grant.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    // Count articles by publication year
    public int countArticlesByPublicationYear(int year) {
    if (year == -1) {
        return (int) articleRepository.count();  
    }
    return articleRepository.countByPublicationYear(year);
    }

    // Count articles with awards
    public int countArticlesWithAwards(int year) {
        if (year == -1) {
            return (int) articleRepository.countArticlesWithAwards();
        }
        return (int) articleRepository.countByPublicationYear(year);
    }

    // Get articles by faculty and year
    public List<Article> getArticleByFacultyAndYear(int year, String facultyId) {
        if (year == -1) {
            return articleRepository.findArticlesByFacultyId(facultyId);
        }
        return articleRepository.findArticlesByFacultyIdAndByYear(facultyId, year);
    }

    // Count Number article contains funding(by Year)
    public int countArticlesByFacultyWithAwards(String facultyId, int year) {
        if (year == -1) {
            return articleRepository.countArticlesByFacultyWithAwards(facultyId);
        }
        return articleRepository.countArticlesByFacultyWithAwardsAndByYear(facultyId, year);
    }


    // Get top articles by faculty aggregated by total award amount
    public List<Object[]> getTopArticlesByFacultyAggregated(String facultyId, int limit, int year) {
        if (year == -1) {
            return articleRepository.findTopArticlesByFacultyAggregated(facultyId, Pageable.ofSize(limit));
        }
        return articleRepository.findTopArticlesByFacultyAggregatedAndByYear(facultyId, year, Pageable.ofSize(limit));
    }
}
