--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: application_borrower; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_borrower (
    application_id bigint NOT NULL,
    borrower_id bigint NOT NULL,
    application_type character varying(255) NOT NULL
);


ALTER TABLE public.application_borrower OWNER TO postgres;

--
-- Name: application_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.application_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE public.application_seq OWNER TO postgres;

--
-- Name: borrower; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.borrower (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    age integer NOT NULL,
    city character varying(255) NOT NULL,
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    relationship character varying(255) NOT NULL,
    ssn integer UNIQUE NOT NULL,
    state character varying(255) NOT NULL,
    zip integer NOT NULL,
    
    CONSTRAINT borrower_age_check CHECK ((age >= 18))
);


ALTER TABLE public.borrower OWNER TO postgres;

--
-- Name: borrower_employment; Type: TABLE; Schema: public; Owner: postgres
--

--CREATE TABLE public.borrower_employment (
--    borrower_id bigint NOT NULL,
--    employments_id integer NOT NULL
--);


--ALTER TABLE public.borrower_employment OWNER TO postgres;

--
-- Name: borrower_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.borrower_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.borrower_id_seq OWNER TO postgres;

--
-- Name: borrower_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.borrower_id_seq OWNED BY public.borrower.id;


--
-- Name: borrower_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.borrower_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE public.borrower_seq OWNER TO postgres;

--
-- Name: creditcardapplication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.creditcardapplication (
    id bigint NOT NULL,
    cardtype character varying(255) NOT NULL,
    requestedcreditlimit numeric(19,2) NOT NULL,
    CONSTRAINT creditcardapplication_requestedcreditlimit_check CHECK ((requestedcreditlimit <= (10000)::numeric))
);


ALTER TABLE public.creditcardapplication OWNER TO postgres;

CREATE SEQUENCE public.creditcardapplication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.creditcardapplication_id_seq OWNER TO postgres;


ALTER SEQUENCE public.creditcardapplication_id_seq OWNED BY public.creditcardapplication.id;


CREATE SEQUENCE public.creditcardapplication_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE public.creditcardapplication_seq OWNER TO postgres;

--
-- Name: employment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employment (
    id integer NOT NULL,
    employername character varying(255) NOT NULL,
    employerphone bigint NOT NULL,
    enddate date NOT NULL,
    startdate date NOT NULL,
    borrower_id bigint
);


ALTER TABLE public.employment OWNER TO postgres;

--
-- Name: employment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employment_id_seq OWNER TO postgres;

--
-- Name: employment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employment_id_seq OWNED BY public.employment.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

--CREATE SEQUENCE public.hibernate_sequence
--   START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;


--ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: mortgageapplication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mortgageapplication (
    id bigint NOT NULL,
    lengthofmortgageyears integer NOT NULL,
    loanamount numeric(19,2) NOT NULL,
    mortgagetype character varying(255) NOT NULL
);


ALTER TABLE public.mortgageapplication OWNER TO postgres;
--ALTER SEQUENCE public.creditcardapplication_id_seq OWNED BY public.mortgageapplication.id;
--
-- Name: borrower id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.borrower ALTER COLUMN id SET DEFAULT nextval('public.borrower_id_seq'::regclass);

ALTER TABLE ONLY public.creditcardapplication ALTER COLUMN id SET DEFAULT nextval('public.creditcardapplication_id_seq'::regclass);
ALTER TABLE ONLY public.mortgageapplication ALTER COLUMN id SET DEFAULT nextval('public.creditcardapplication_id_seq'::regclass);


--
-- Name: employment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employment ALTER COLUMN id SET DEFAULT nextval('public.employment_id_seq'::regclass);


--
-- Name: application_borrower application_borrower_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_borrower
    ADD CONSTRAINT application_borrower_pkey PRIMARY KEY (application_id, borrower_id);


--
-- Name: borrower borrower_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.borrower
    ADD CONSTRAINT borrower_pkey PRIMARY KEY (id);


--
-- Name: creditcardapplication creditcardapplication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.creditcardapplication
    ADD CONSTRAINT creditcardapplication_pkey PRIMARY KEY (id);


--
-- Name: employment employment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employment
    ADD CONSTRAINT employment_pkey PRIMARY KEY (id);


--
-- Name: mortgageapplication mortgageapplication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mortgageapplication
    ADD CONSTRAINT mortgageapplication_pkey PRIMARY KEY (id);


--
-- Name: borrower_employment uk_5ewhd4eb4x5axl0r4wdxpgc55; Type: CONSTRAINT; Schema: public; Owner: postgres
--
--
--ALTER TABLE ONLY public.borrower_employment
--    ADD CONSTRAINT uk_5ewhd4eb4x5axl0r4wdxpgc55 UNIQUE (employments_id);


--
-- Name: borrower_employment fkdopnbjp4v0grhg3u9a5g5pjfv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

--ALTER TABLE ONLY public.borrower_employment
--   ADD CONSTRAINT fkdopnbjp4v0grhg3u9a5g5pjfv FOREIGN KEY (borrower_id) REFERENCES public.borrower(id);


--
-- Name: borrower_employment fkh6ido48smld4r2j03aneawtss; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

--ALTER TABLE ONLY public.borrower_employment
--    ADD CONSTRAINT fkh6ido48smld4r2j03aneawtss FOREIGN KEY (employments_id) REFERENCES public.employment(id);


--
-- Name: application_borrower fkk5lqn18kvpql3cn1iahooaofm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_borrower
    ADD CONSTRAINT fkk5lqn18kvpql3cn1iahooaofm FOREIGN KEY (borrower_id) REFERENCES public.borrower(id);


--
-- Name: employment fklqtt3y7rpti44bc38rh2gbbvi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employment
    ADD CONSTRAINT fklqtt3y7rpti44bc38rh2gbbvi FOREIGN KEY (borrower_id) REFERENCES public.borrower(id);


--
-- PostgreSQL database dump complete
--

