--
-- Name: country; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE country
(
    country_id  integer auto_increment NOT NULL,
    country     character varying(50)  NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

--
-- Name: country_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE country
    ADD CONSTRAINT country_pkey PRIMARY KEY (country_id);
