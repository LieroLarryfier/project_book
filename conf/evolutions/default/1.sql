# Projects schema

# --- !Ups

CREATE TABLE project (
    id integer primary key AUTOINCREMENT,
    label varchar(255) NOT NULL,
    categories varchar(255),
    progress integer NOT NULL DEFAULT 0,
    description varchar(255),
    contributors varchar(255),
    tags varchar(255)
);

# --- !Downs

DROP TABLE project;
