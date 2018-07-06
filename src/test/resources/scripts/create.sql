CREATE TABLE locations
(
    id bigint NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    CONSTRAINT locations_pkey PRIMARY KEY (id)
);

CREATE TABLE restaurants
(
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    admin_number character varying(255) NOT NULL,
    commercial_email character varying(255) NOT NULL,
    commercial_name character varying(255) NOT NULL,
    legal_name character varying(255) NOT NULL,
    logo character varying(255),
    rating numeric(19,2),
    location_id bigint,
    CONSTRAINT restaurants_pkey PRIMARY KEY (id),
    CONSTRAINT fk4yrawo6eulwjhc58gt5ndl5k4 FOREIGN KEY (location_id)
        REFERENCES locations (id)
);

