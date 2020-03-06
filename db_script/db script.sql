-- Table: user_role

-- DROP TABLE user_role;

CREATE TABLE user_role
(
  id bigint NOT NULL,
  role character varying(255),
  user_id bigint,
  CONSTRAINT user_role_pkey PRIMARY KEY (id),
  CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_role
  OWNER TO postgres;


**********************************************************

-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  id bigint NOT NULL,
  password character varying(255),
  username character varying(255),
  CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;

**********************************************************

INSERT INTO users(
            id, password, username)
    VALUES (1, '$2a$10$PBzfA.mpP3mogAYo5F39guSL2pF2sVs.0cJP4zuAWeJbPNUDNbAEi', 'admin');


***********************************************************

INSERT INTO user_role(
            id, role, user_id)
    VALUES (1, 'ROLE_ADMIN', 1);
