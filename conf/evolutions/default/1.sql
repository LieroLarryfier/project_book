# Projects schema

# --- !Ups

CREATE SEQUENCE project_id_seq;
CREATE TABLE project (
    id integer NOT NULL DEFAULT nextval('project_id_seq'),
    label varchar(255) NOT NULL,
    categories varchar(255),
    progress integer NOT NULL DEFAULT 0,
    description varchar(255),
    contributors varchar(255),
    tags varchar(255)
);

# --- !Downs

DROP TABLE project;
DROP SEQUENCE project_id_seq;