package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Article;

/**
 * 文章信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-30
 * @since 1.0
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

}
