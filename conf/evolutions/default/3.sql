# Projects schema

# --- !Ups

INSERT INTO project (label, categories, progress, description, contributors, tags) VALUES
  ('label', 'categories', 0, 'description', 'contributors', 'tags');

INSERT INTO project (label, categories, progress, description, contributors, tags) VALUES
  ('Project2', 'blablub, projects', 0, 'blablub Projects', 'Ich, Du, Wir', 'blablub');

INSERT INTO workStep (header, description, progress, pictures, projectId) VALUES
  ('header', 'description', 22, 'pictures', 1);

INSERT INTO workStep (header, description, progress, pictures, projectId) VALUES
  ('Workstep', 'toll', 10, ' ', 2);
INSERT INTO workStep (header, description, progress, pictures, projectId) VALUES
  ('Workstep2', 'supertoll', 10, ' ', 2);
INSERT INTO workStep (header, description, progress, pictures, projectId) VALUES
  ('Workstep3', 'megatoller', 32, ' ', 2);

# --- !Downs

DELETE FROM workStep;
DELETE FROM project;

