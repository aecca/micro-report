--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.3
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: db_bbva_dev; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE db_bbva_dev WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';


ALTER DATABASE db_bbva_dev OWNER TO postgres;

\connect db_bbva_dev

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: tbl_account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_account (
    id bigint NOT NULL,
    client_id bigint,
    user_id bigint,
    type character varying(15),
    active boolean DEFAULT true
);


ALTER TABLE tbl_account OWNER TO postgres;

--
-- Name: tbl_account_card; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_account_card (
    id bigint NOT NULL,
    number character varying(255),
    key character varying(255),
    balance_available double precision,
    balance_used double precision DEFAULT 0 NOT NULL,
    type character varying(255),
    account_id integer,
    active boolean DEFAULT true
);


ALTER TABLE tbl_account_card OWNER TO postgres;

--
-- Name: tbl_account_card_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_account_card_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_account_card_id_seq OWNER TO postgres;

--
-- Name: tbl_account_card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_account_card_id_seq OWNED BY tbl_account_card.id;


--
-- Name: tbl_account_cts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_account_cts (
    account_id integer NOT NULL,
    ammount numeric
);


ALTER TABLE tbl_account_cts OWNER TO postgres;

--
-- Name: tbl_account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_account_id_seq OWNER TO postgres;

--
-- Name: tbl_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_account_id_seq OWNED BY tbl_account.id;


--
-- Name: tbl_client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_client (
    id bigint NOT NULL,
    code character varying(255),
    user_id bigint,
    name character varying(255),
    email character varying(255),
    lastname character varying(255)
);


ALTER TABLE tbl_client OWNER TO postgres;

--
-- Name: tbl_client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_client_id_seq OWNER TO postgres;

--
-- Name: tbl_client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_client_id_seq OWNED BY tbl_client.id;


--
-- Name: tbl_transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_transaction (
    id bigint NOT NULL,
    detail character varying(255),
    amount numeric(6,2),
    operation_number integer,
    currency character varying(255),
    transaction_type_id bigint,
    account_id bigint,
    date_transaction timestamp without time zone
);


ALTER TABLE tbl_transaction OWNER TO postgres;

--
-- Name: tbl_transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_transaction_id_seq OWNER TO postgres;

--
-- Name: tbl_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_transaction_id_seq OWNED BY tbl_transaction.id;


--
-- Name: tbl_transaction_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_transaction_type (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE tbl_transaction_type OWNER TO postgres;

--
-- Name: tbl_transaction_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_transaction_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_transaction_type_id_seq OWNER TO postgres;

--
-- Name: tbl_transaction_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_transaction_type_id_seq OWNED BY tbl_transaction_type.id;


--
-- Name: tbl_account id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_account ALTER COLUMN id SET DEFAULT nextval('tbl_account_id_seq'::regclass);


--
-- Name: tbl_account_card id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_account_card ALTER COLUMN id SET DEFAULT nextval('tbl_account_card_id_seq'::regclass);


--
-- Name: tbl_client id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_client ALTER COLUMN id SET DEFAULT nextval('tbl_client_id_seq'::regclass);


--
-- Name: tbl_transaction id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_transaction ALTER COLUMN id SET DEFAULT nextval('tbl_transaction_id_seq'::regclass);


--
-- Name: tbl_transaction_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_transaction_type ALTER COLUMN id SET DEFAULT nextval('tbl_transaction_type_id_seq'::regclass);


--
-- Data for Name: tbl_account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_account (id, client_id, user_id, type, active) FROM stdin;
1	1	1	AHORRO	t
2	1	1	CTS	t
3	1	1	CORRIENTE	f
4	1	1	CORRIENTE	f
5	1	1	CORRIENTE	t
\.


--
-- Data for Name: tbl_account_card; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_account_card (id, number, key, balance_available, balance_used, type, account_id, active) FROM stdin;
3	00001236	1992	20000	0	CREDITO	3	\N
4	00001236	1992	30000	0	CREDITO	4	\N
2	00001235	1993	20000	80000	CREDITO	5	t
1	00001234	1993	2000	0	DEBITO	1	t
\.


--
-- Name: tbl_account_card_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_account_card_id_seq', 4, true);


--
-- Data for Name: tbl_account_cts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_account_cts (account_id, ammount) FROM stdin;
2	30000.0000
\.


--
-- Name: tbl_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_account_id_seq', 5, true);


--
-- Data for Name: tbl_client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_client (id, code, user_id, name, email, lastname) FROM stdin;
1	C01	1	Andy	andy.ecca@gmail.com	Ecca Campaña
2	C02	2	Ernesto Anaya	ernestex@gmail.com	Anaya Ruiz
\.


--
-- Name: tbl_client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_client_id_seq', 2, true);


--
-- Data for Name: tbl_transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_transaction (id, detail, amount, operation_number, currency, transaction_type_id, account_id, date_transaction) FROM stdin;
1	SPOTIFY STOCOLM	23.00	1	USD	8	1	2017-05-05 00:40:20.771
2	NETFLIX	32.00	2	USD	8	1	2017-05-05 00:41:29.296
3	LATAM AIR	225.00	3	PEN	8	1	2017-05-07 00:42:36.172
4	CINEPLANET SALAVERRY	65.23	4	PEN	8	1	2017-06-07 07:46:45.526
5	RETIRO	500.00	5	PEN	1	1	2017-06-07 12:30:18.129
\.


--
-- Name: tbl_transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_transaction_id_seq', 5, true);


--
-- Data for Name: tbl_transaction_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_transaction_type (id, name) FROM stdin;
1	RETIRO
2	TRANSFERENCIA
3	SERVICIOS
4	PAGO TARJETA
5	PAGO CREDITO
6	ABONO TCS
7	RETIRO CTS
8	PAGO
\.


--
-- Name: tbl_transaction_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_transaction_type_id_seq', 8, true);


--
-- Name: tbl_account_card tbl_account_card_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_account_card
    ADD CONSTRAINT tbl_account_card_pkey PRIMARY KEY (id);


--
-- Name: tbl_account tbl_account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_account
    ADD CONSTRAINT tbl_account_pkey PRIMARY KEY (id);


--
-- Name: tbl_client tbl_client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_client
    ADD CONSTRAINT tbl_client_pkey PRIMARY KEY (id);


--
-- Name: tbl_transaction tbl_transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_transaction
    ADD CONSTRAINT tbl_transaction_pkey PRIMARY KEY (id);


--
-- Name: tbl_transaction_type tbl_transaction_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_transaction_type
    ADD CONSTRAINT tbl_transaction_type_pkey PRIMARY KEY (id);


--
-- Name: tbl_account fk51elkvb01o38w25jt3fp76gqi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_account
    ADD CONSTRAINT fk51elkvb01o38w25jt3fp76gqi FOREIGN KEY (user_id) REFERENCES tbl_client(id);


--
-- Name: tbl_transaction fk_account_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_transaction
    ADD CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES tbl_account(id);


--
-- Name: tbl_transaction fk_transaction_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_transaction
    ADD CONSTRAINT fk_transaction_type FOREIGN KEY (transaction_type_id) REFERENCES tbl_transaction_type(id);


--
-- Name: tbl_account fkkdg83xiuq40y5d9qh1sc6yuyu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_account
    ADD CONSTRAINT fkkdg83xiuq40y5d9qh1sc6yuyu FOREIGN KEY (client_id) REFERENCES tbl_client(id);


--
-- Name: tbl_account_card tbl_account_card_account_id__fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_account_card
    ADD CONSTRAINT tbl_account_card_account_id__fk FOREIGN KEY (account_id) REFERENCES tbl_account(id);


--
-- Name: tbl_account_cts tbl_account_cts_tbl_account_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_account_cts
    ADD CONSTRAINT tbl_account_cts_tbl_account_id_fk FOREIGN KEY (account_id) REFERENCES tbl_account(id);


--
-- PostgreSQL database dump complete
--

