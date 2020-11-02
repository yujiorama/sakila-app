--
-- Name: actor; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE actor
(
    actor_id    integer auto_increment NOT NULL,
    first_name  character varying(45)  NOT NULL,
    last_name   character varying(45)  NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

--
-- Name: country; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE address
(
    address_id  integer auto_increment NOT NULL,
    address     character varying(50)  NOT NULL,
    address2    character varying(50),
    district    character varying(20)  NOT NULL,
    city_id     smallint               NOT NULL,
    postal_code character varying(10),
    phone       character varying(20)  NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

--
-- Name: city; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE city
(
    city_id     integer auto_increment NOT NULL,
    city        character varying(50)  NOT NULL,
    country_id  smallint               NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

--
-- Name: address; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE country
(
    country_id  integer auto_increment NOT NULL,
    country     character varying(50)  NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

--
-- Name: store; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE staff
(
    staff_id    integer auto_increment NOT NULL,
    first_name  character varying(45)  NOT NULL,
    last_name   character varying(45)  NOT NULL,
    address_id  smallint               NOT NULL,
    email       character varying(50),
    store_id    smallint               NOT NULL,
    active      boolean DEFAULT true   NOT NULL,
    username    character varying(16)  NOT NULL,
    password    character varying(40),
    last_update timestamp without time zone DEFAULT now() NOT NULL,
    picture bytea
);

--
-- Name: staff; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE store
(
    store_id         integer auto_increment NOT NULL,
    manager_staff_id smallint               NOT NULL,
    address_id       smallint               NOT NULL,
    last_update      timestamp without time zone DEFAULT now() NOT NULL
);

--
-- Name: actor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE actor
    ADD CONSTRAINT actor_pkey PRIMARY KEY (actor_id);

--
-- Name: address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE address
    ADD CONSTRAINT address_pkey PRIMARY KEY (address_id);

--
-- Name: city_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE city
    ADD CONSTRAINT city_pkey PRIMARY KEY (city_id);

--
-- Name: country_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE country
    ADD CONSTRAINT country_pkey PRIMARY KEY (country_id);

--
-- Name: staff_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE staff
    ADD CONSTRAINT staff_pkey PRIMARY KEY (staff_id);

--
-- Name: store_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE store
    ADD CONSTRAINT store_pkey PRIMARY KEY (store_id);
