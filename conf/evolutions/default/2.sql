# Projects schema

# --- !Ups

CREATE TABLE workStep (
    id integer primary key autoincrement,
    header varchar(255) NOT NULL,
    description varchar(255),
    progress integer NOT NULL,
    pictures varchar(255),
    projectId integer NOT NULL
);

# --- !Downs

DROP TABLE workStep;