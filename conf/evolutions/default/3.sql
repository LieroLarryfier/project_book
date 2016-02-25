# Projects schema

# --- !Ups

CREATE TABLE todo (
    id integer primary key autoincrement,
    priority integer NOT NULL,
    name varchar(255) NOT NULL,
    description varchar(255),
    doneAt TIMESTAMP,
    projectId integer NOT NULL
);

# --- !Downs

DROP TABLE todo;