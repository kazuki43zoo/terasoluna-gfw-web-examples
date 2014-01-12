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
CREATE TABLE t_member (
    member_id CHAR(36) NOT NULL,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    gender NVARCHAR(10) NOT NULL,
    email_address NVARCHAR(256) NOT NULL,
    phone_number NVARCHAR(20),
    address NVARCHAR(256),
    CONSTRAINT pk_member PRIMARY KEY(member_id)
);
COMMIT;