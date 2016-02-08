# Projects schema

# --- !Ups

CREATE SEQUENCE workStep_id_seq;
CREATE TABLE workStep (
    id integer NOT NULL DEFAULT nextval('workStep_id_seq'),
    header varchar(255) NOT NULL,
    description varchar(255),
    progress integer NOT NULL,
    pictures varchar(255),
    projectId integer NOT NULL
);

# --- !Downs

DROP TABLE workStep;
DROP SEQUENCE workStep_id_seq;