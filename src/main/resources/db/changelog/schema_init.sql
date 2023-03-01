
CREATE SEQUENCE conference_room_id_seq;
CREATE TABLE conference_room (
     id bigint PRIMARY KEY DEFAULT nextval('conference_room_id_seq'),
     title VARCHAR(100) NOT NULL UNIQUE,
     capacity int NOT NULL,
     location VARCHAR(255),
     status VARCHAR(200)
);

CREATE SEQUENCE conference_id_seq;
CREATE TABLE conference (
     id bigint PRIMARY KEY DEFAULT nextval('conference_id_seq'),
     title VARCHAR(100) NOT NULL,
     event_start TIMESTAMP WITHOUT TIME ZONE NOT NULL,
     event_end TIMESTAMP  WITHOUT TIME ZONE NOT NULL,
     room_id bigint NOT NULL REFERENCES conference_room ON DELETE CASCADE,
     description VARCHAR(400),
     UNIQUE(title, room_id)
);


CREATE SEQUENCE customer_id_seq;
CREATE TABLE customer (
     id bigint PRIMARY KEY DEFAULT nextval('customer_id_seq'),
     conference_id bigint NOT NULL REFERENCES conference ON DELETE CASCADE,
     firstname VARCHAR(100) NOT NULL,
     lastname VARCHAR(100) NOT NULL
);
CREATE UNIQUE INDEX lastname_idx ON customer (lastname);

CREATE SEQUENCE feedback_id_seq;
CREATE TABLE feedback (
     id bigint PRIMARY KEY DEFAULT nextval('feedback_id_seq'),
     conference_id bigint NOT NULL REFERENCES conference ON DELETE CASCADE,
     customer_id bigint NOT NULL  REFERENCES customer ON DELETE CASCADE,
     comment VARCHAR(400),
     created TIMESTAMP  WITHOUT TIME ZONE NOT NULL DEFAULT now()
);
