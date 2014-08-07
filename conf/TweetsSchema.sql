CREATE TABLE tweets (
                id BIGINT NOT NULL,
                twitter_id BIGINT NOT NULL,
                tweet VARCHAR,
                image_url VARCHAR NOT NULL,
                CONSTRAINT tweets_pk PRIMARY KEY (id)
);