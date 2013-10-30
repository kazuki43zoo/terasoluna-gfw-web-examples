package org.terasoluna.gfw.examples.common.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SequenceGenerator(name = "articleSequenceGenerator", sequenceName = "s_article")
@Table(name = "t_article")
@Entity
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articleSequenceGenerator")
    @Id
    @Column(name = "article_id")
    private Long articleId;

    @Column
    private String title;

    @Column
    private String overview;

    @Column
    private String content;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "recommended_star")
    private Integer recommendedStar;

    @Column
    private String author;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getRecommendedStar() {
        return recommendedStar;
    }

    public void setRecommendedStar(Integer recommendedStar) {
        this.recommendedStar = recommendedStar;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
