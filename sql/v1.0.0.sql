use towncrier;

CREATE TABLE things (
  id BIGINT NOT NULL,
   creator_id BIGINT NOT NULL,
   created_time  BIGINT NOT NULL,
   banner_image_path TEXT NOT NULL,
   kind varchar(255) NOT NULL,
   thing_date BIGINT NOT NULL,
   has_time BOOLEAN NOT NULL,
   title varchar(255) NOT NULL,
   description TEXT,
   like_count BIGINT NOT NULL DEFAULT 0,
   PRIMARY KEY (id)
);

CREATE TABLE users (
   id BIGINT NOT NULL,
   fName VARCHAR(255) NOT NULL,
   lName VARCHAR(255) NOT NULL,
   mobile VARCHAR(255) NOT NULL,
   pwd VARCHAR(255) NOT NULL,
   token VARCHAR(255) UNIQUE NOT NULL,
   PRIMARY KEY (id)
);
