CREATE SEQUENCE s_article;
CREATE TABLE t_article (
    article_id BIGINT NOT NULL,
    title NVARCHAR(100) NOT NULL,
    overview NVARCHAR(1000) NOT NULL,
    content CLOB NOT NULL,
    published_date DATE,
    author NVARCHAR(100) NOT NULL,
    recommended_star INT,
    CONSTRAINT pk_article PRIMARY KEY(article_id)
);
COMMIT;