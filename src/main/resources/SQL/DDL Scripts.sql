DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS orders_meals;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS locations;

DROP SEQUENCE IF EXISTS reviews_id_seq;
DROP SEQUENCE IF EXISTS meals_id_seq;
DROP SEQUENCE IF EXISTS orders_id_seq;
DROP SEQUENCE IF EXISTS restaurants_id_seq;
DROP SEQUENCE IF EXISTS locations_id_seq;
DROP SEQUENCE IF EXISTS hibernate_sequence;

CREATE SEQUENCE hibernate_sequence;
CREATE SEQUENCE locations_id_seq;
CREATE SEQUENCE meals_id_seq;
CREATE SEQUENCE orders_id_seq;
CREATE SEQUENCE restaurants_id_seq;
CREATE SEQUENCE reviews_id_seq;

CREATE TABLE locations
(
    id bigint NOT NULL DEFAULT nextval('locations_id_seq'::regclass),
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    CONSTRAINT locations_pkey PRIMARY KEY (id)
);

CREATE TABLE restaurants
(
    id bigint NOT NULL DEFAULT nextval('restaurants_id_seq'::regclass),
    address character varying(255) COLLATE pg_catalog."default" NOT NULL,
    admin_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    commercial_email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    commercial_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    legal_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    logo character varying(255) COLLATE pg_catalog."default",
    rating numeric(19,2),
    location_id bigint,
    CONSTRAINT restaurants_pkey PRIMARY KEY (id),
    CONSTRAINT fk4yrawo6eulwjhc58gt5ndl5k4 FOREIGN KEY (location_id)
        REFERENCES locations (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE meals
(
    id bigint NOT NULL DEFAULT nextval('meals_id_seq'::regclass),
    cook_time integer NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    price numeric(19,2) NOT NULL,
    restaurant_id bigint,
    CONSTRAINT meals_pkey PRIMARY KEY (id),
    CONSTRAINT fkbgjh8qhpl94lso4d5l6nbiyry FOREIGN KEY (restaurant_id)
        REFERENCES restaurants (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE reviews
(
    id bigint NOT NULL DEFAULT nextval('reviews_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    rating integer NOT NULL,
    review character varying(255) COLLATE pg_catalog."default" NOT NULL,
    restaurant_id bigint,
    CONSTRAINT reviews_pkey PRIMARY KEY (id),
    CONSTRAINT fksu8ow2q842enesfbqphjrfi5g FOREIGN KEY (restaurant_id)
        REFERENCES restaurants (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE orders
(
    id bigint NOT NULL DEFAULT nextval('orders_id_seq'::regclass),
    address character varying(255) COLLATE pg_catalog."default" NOT NULL,
    eta timestamp without time zone NOT NULL,
    total_cost numeric(19,2),
    restaurant_id bigint,
    to_id bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT fk2m9qulf12xm537bku3jnrrbup FOREIGN KEY (restaurant_id)
        REFERENCES restaurants (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk99xhn2wr28tjjmgxialmc4iub FOREIGN KEY (to_id)
        REFERENCES locations (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE orders_meals
(
    order_id bigint NOT NULL,
    meals_id bigint NOT NULL,
    CONSTRAINT fk23en38xrrkiiv78l1tlx7fu1l FOREIGN KEY (order_id)
        REFERENCES orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fketdaqpg0mk119sm5cf1t3xm5g FOREIGN KEY (meals_id)
        REFERENCES meals (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);