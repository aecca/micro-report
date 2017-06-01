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

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: tbl_report; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_report (
    id integer NOT NULL,
    name character varying(40) NOT NULL,
    content character varying(15000) NOT NULL,
    created_at timestamp without time zone,
    type character varying(255)
);


ALTER TABLE tbl_report OWNER TO postgres;

--
-- Name: tbl_report_source_param; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_report_source_param (
    id integer NOT NULL,
    name character varying(30),
    value character varying(200),
    required boolean,
    report_source_id integer
);


ALTER TABLE tbl_report_source_param OWNER TO postgres;

--
-- Name: tbl_report_param_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_report_param_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_report_param_id_seq OWNER TO postgres;

--
-- Name: tbl_report_param_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_report_param_id_seq OWNED BY tbl_report_source_param.id;


--
-- Name: tbl_report_source; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_report_source (
    id integer NOT NULL,
    report_id integer NOT NULL,
    report_source_type_id integer,
    name character varying(40),
    content character varying(15000),
    type_id integer,
    collection boolean DEFAULT false
);


ALTER TABLE tbl_report_source OWNER TO postgres;

--
-- Name: tbl_report_source_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_report_source_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_report_source_id_seq OWNER TO postgres;

--
-- Name: tbl_report_source_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_report_source_id_seq OWNED BY tbl_report_source.id;


--
-- Name: tbl_report_source_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_report_source_type (
    name character varying(30),
    id integer NOT NULL
);


ALTER TABLE tbl_report_source_type OWNER TO postgres;

--
-- Name: tbl_report_source_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_report_source_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_report_source_type_id_seq OWNER TO postgres;

--
-- Name: tbl_report_source_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_report_source_type_id_seq OWNED BY tbl_report_source_type.id;


--
-- Name: tbl_resource_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_resource_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_resource_id_seq OWNER TO postgres;

--
-- Name: tbl_resource_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_resource_id_seq OWNED BY tbl_report.id;


--
-- Name: tbl_report id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report ALTER COLUMN id SET DEFAULT nextval('tbl_resource_id_seq'::regclass);


--
-- Name: tbl_report_source id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source ALTER COLUMN id SET DEFAULT nextval('tbl_report_source_id_seq'::regclass);


--
-- Name: tbl_report_source_param id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source_param ALTER COLUMN id SET DEFAULT nextval('tbl_report_param_id_seq'::regclass);


--
-- Name: tbl_report_source_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source_type ALTER COLUMN id SET DEFAULT nextval('tbl_report_source_type_id_seq'::regclass);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Data for Name: tbl_report; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_report (id, name, content, created_at, type) FROM stdin;
2	Listado Transaccion Cliente	Template:\n      \n      Deee.\n{{#clientExtras}}\n  {{name}}\n{{/clientExtras}}\n\n{{#clientData}}\n  <b> {{name}} </b>\n{{/clientData}}	2017-05-25 02:54:36.403	HTML
3	Demo Birt Integraciom	<?xml version="1.0" encoding="UTF-8"?>\n<report xmlns="http://www.eclipse.org/birt/2005/design" version="3.2.23" id="1">\n    <property name="createdBy">Eclipse BIRT Designer Version 4.6.0.v201606072122</property>\n    <property name="units">in</property>\n    <property name="iconFile">/templates/blank_report.gif</property>\n    <property name="bidiLayoutOrientation">ltr</property>\n    <property name="imageDPI">72</property>\n    <data-sources>\n        <oda-data-source extensionID="org.eclipse.birt.report.data.oda.xml" name="Eclipse News Feed" id="48">\n            <text-property name="displayName">Eclipse News Feed</text-property>\n            <property name="FILELIST">http://www.eclipse.org/home/eclipsenews.rss</property>\n            <property name="SCHEMAFILELIST"></property>\n        </oda-data-source>\n    </data-sources>\n    <data-sets>\n        <oda-data-set extensionID="org.eclipse.birt.report.data.oda.xml.dataSet" name="Eclise News Headlines" id="49">\n            <text-property name="displayName">Eclise News Headlines</text-property>\n            <structure name="cachedMetaData">\n                <list-property name="resultSet">\n                    <structure>\n                        <property name="position">1</property>\n                        <property name="name">Channel Title</property>\n                        <property name="dataType">string</property>\n                    </structure>\n                    <structure>\n                        <property name="position">2</property>\n                        <property name="name">Channel URL</property>\n                        <property name="dataType">string</property>\n                    </structure>\n                    <structure>\n                        <property name="position">3</property>\n                        <property name="name">Channel Description</property>\n                        <property name="dataType">string</property>\n                    </structure>\n                    <structure>\n                        <property name="position">4</property>\n                        <property name="name">Channel Image URL</property>\n                        <property name="dataType">string</property>\n                    </structure>\n                    <structure>\n                        <property name="position">5</property>\n                        <property name="name">News Item Headline</property>\n                        <property name="dataType">string</property>\n                    </structure>\n                    <structure>\n                        <property name="position">6</property>\n                        <property name="name">Link To News Item</property>\n                        <property name="dataType">string</property>\n                    </structure>\n                    <structure>\n                        <property name="position">7</property>\n                        <property name="name">Short Description Of News Item</property>\n                        <property name="dataType">string</property>\n                    </structure>\n                    <structure>\n                        <property name="position">8</property>\n                        <property name="name">Publish Date of News Item</property>\n                        <property name="dataType">string</property>\n                    </structure>\n                </list-property>\n            </structure>\n            <property name="dataSource">Eclipse News Feed</property>\n            <property name="queryText">table0#-TNAME-#table0#:#[/rss/channel/item]#:#{Channel Title;String;../title},{Channel URL;String;../link},{Channel Description;String;../description},{Channel Image URL;String;../image/url},{News Item Headline;String;/title},{Link To News Item;String;/link},{Short Description Of News Item;String;/description},{Publish Date of News Item;String;/pubDate}</property>\n            <property name="MAX_ROW">-1</property>\n            <property name="XML_FILE">http://www.eclipse.org/home/eclipsenews.rss</property>\n        </oda-data-set>\n    </data-sets>\n    <page-setup>\n        <simple-master-page name="Simple MasterPage" id="2">\n            <page-footer>\n                <text id="3">\n                    <property name="contentType">html</property>\n                    <text-property name="content"><![CDATA[<value-of>new Date()</value-of>]]></text-property>\n                </text>\n            </page-footer>\n        </simple-master-page>\n    </page-setup>\n    <body>\n        <grid id="4">\n            <property name="width">100%</property>\n            <column id="5"/>\n            <column id="6"/>\n            <row id="7">\n                <cell id="8">\n                    <image id="10">\n                        <expression name="uri">"Classic-Models-Minimal-M-Trans (smaller).png"</expression>\n                    </image>\n                </cell>\n                <cell id="9">\n                    <text id="11">\n                        <property name="fontFamily">"Arial"</property>\n                        <property name="fontSize">x-large</property>\n                        <property name="fontWeight">bold</property>\n                        <property name="textAlign">right</property>\n                        <property name="contentType">html</property>\n                        <text-property name="content"><![CDATA[News Feed Using XML Data Source]]></text-property>\n                    </text>\n                </cell>\n            </row>\n        </grid>\n        <table id="50">\n            <property name="fontFamily">"Arial"</property>\n            <property name="width">100%</property>\n            <property name="dataSet">Eclise News Headlines</property>\n            <list-property name="boundDataColumns">\n                <structure>\n                    <property name="name">Channel Title</property>\n                    <expression name="expression">dataSetRow["Channel Title"]</expression>\n                    <property name="dataType">string</property>\n                </structure>\n                <structure>\n                    <property name="name">Channel URL</property>\n                    <expression name="expression">dataSetRow["Channel URL"]</expression>\n                    <property name="dataType">string</property>\n                </structure>\n                <structure>\n                    <property name="name">Channel Description</property>\n                    <expression name="expression">dataSetRow["Channel Description"]</expression>\n                    <property name="dataType">string</property>\n                </structure>\n                <structure>\n                    <property name="name">Channel Image URL</property>\n                    <expression name="expression">dataSetRow["Channel Image URL"]</expression>\n                    <property name="dataType">string</property>\n                </structure>\n                <structure>\n                    <property name="name">News Item Headline</property>\n                    <expression name="expression">dataSetRow["News Item Headline"]</expression>\n                    <property name="dataType">string</property>\n                </structure>\n                <structure>\n                    <property name="name">Link To News Item</property>\n                    <expression name="expression">dataSetRow["Link To News Item"]</expression>\n                    <property name="dataType">string</property>\n                </structure>\n                <structure>\n                    <property name="name">Short Description Of News Item</property>\n                    <expression name="expression">dataSetRow["Short Description Of News Item"]</expression>\n                    <property name="dataType">string</property>\n                </structure>\n                <structure>\n                    <property name="name">Publish Date of News Item</property>\n                    <expression name="expression">dataSetRow["Publish Date of News Item"]</expression>\n                    <property name="dataType">string</property>\n                </structure>\n            </list-property>\n            <column id="63">\n                <property name="width">2.989in</property>\n            </column>\n            <column id="64">\n                <property name="width">3in</property>\n            </column>\n            <header>\n                <row id="51">\n                    <cell id="52">\n                        <property name="colSpan">2</property>\n                        <property name="rowSpan">1</property>\n                        <grid id="66">\n                            <property name="width">100%</property>\n                            <column id="67">\n                                <property name="width">1.583in</property>\n                            </column>\n                            <column id="68">\n                                <property name="width">4.333in</property>\n                            </column>\n                            <row id="69">\n                                <cell id="70">\n                                    <image id="75">\n                                        <expression name="uri">row["Channel Image URL"]</expression>\n                                    </image>\n                                </cell>\n                                <cell id="71">\n                                    <data id="76">\n                                        <property name="fontWeight">bold</property>\n                                        <property name="resultSetColumn">Channel Title</property>\n                                        <structure name="action">\n                                            <property name="linkType">hyperlink</property>\n                                            <expression name="uri">row["Channel URL"]</expression>\n                                            <property name="targetWindow">_blank</property>\n                                        </structure>\n                                    </data>\n                                </cell>\n                            </row>\n                            <row id="72">\n                                <cell id="73">\n                                    <property name="colSpan">2</property>\n                                    <property name="rowSpan">1</property>\n                                    <data id="77">\n                                        <property name="fontWeight">bold</property>\n                                        <property name="resultSetColumn">Channel Description</property>\n                                    </data>\n                                </cell>\n                            </row>\n                        </grid>\n                    </cell>\n                </row>\n            </header>\n            <detail>\n                <row id="54">\n                    <cell id="55">\n                        <grid id="78">\n                            <property name="width">100%</property>\n                            <column id="79"/>\n                            <row id="80">\n                                <cell id="81">\n                                    <data id="84">\n                                        <property name="fontWeight">bold</property>\n                                        <property name="resultSetColumn">News Item Headline</property>\n                                        <structure name="action">\n                                            <property name="linkType">hyperlink</property>\n                                            <expression name="uri">row["Link To News Item"]</expression>\n                                            <property name="targetWindow">_blank</property>\n                                        </structure>\n                                    </data>\n                                </cell>\n                            </row>\n                            <row id="82">\n                                <cell id="83">\n                                    <data id="85">\n                                        <property name="fontSize">8pt</property>\n                                        <property name="fontStyle">italic</property>\n                                        <property name="resultSetColumn">Publish Date of News Item</property>\n                                    </data>\n                                </cell>\n                            </row>\n                        </grid>\n                    </cell>\n                    <cell id="56">\n                        <text-data id="87">\n                            <property name="fontSize">8pt</property>\n                            <expression name="valueExpr">row["Short Description Of News Item"]</expression>\n                            <property name="contentType">html</property>\n                        </text-data>\n                    </cell>\n                </row>\n                <row id="57">\n                    <cell id="58">\n                        <property name="colSpan">2</property>\n                        <property name="rowSpan">1</property>\n                        <text id="65">\n                            <property name="contentType">html</property>\n                            <text-property name="content"><![CDATA[<HR>]]></text-property>\n                        </text>\n                    </cell>\n                </row>\n            </detail>\n            <footer>\n                <row id="60">\n                    <cell id="61"/>\n                    <cell id="62"/>\n                </row>\n            </footer>\n        </table>\n    </body>\n</report>	\N	XML
4	Demo Birt Imagen	<?xml version="1.0" encoding="UTF-8"?>\n<report xmlns="http://www.eclipse.org/birt/2005/design" version="3.2.23" id="1">\n    <property name="createdBy">Eclipse BIRT Designer Version 3.7.2.v20120213 Build &lt;3.7.2.v20120214-1408></property>\n    <property name="units">in</property>\n    <property name="iconFile">/templates/blank_report.gif</property>\n    <property name="bidiLayoutOrientation">ltr</property>\n    <list-property name="cssStyleSheets">\n        <structure>\n            <property name="fileName">Spring.css</property>\n        </structure>\n    </list-property>\n    <styles>\n        <style name="crosstab" id="5">\n            <property name="borderBottomColor">#CCCCCC</property>\n            <property name="borderBottomStyle">solid</property>\n            <property name="borderBottomWidth">1pt</property>\n            <property name="borderLeftColor">#CCCCCC</property>\n            <property name="borderLeftStyle">solid</property>\n            <property name="borderLeftWidth">1pt</property>\n            <property name="borderRightColor">#CCCCCC</property>\n            <property name="borderRightStyle">solid</property>\n            <property name="borderRightWidth">1pt</property>\n            <property name="borderTopColor">#CCCCCC</property>\n            <property name="borderTopStyle">solid</property>\n            <property name="borderTopWidth">1pt</property>\n        </style>\n        <style name="crosstab-cell" id="6">\n            <property name="borderBottomColor">#CCCCCC</property>\n            <property name="borderBottomStyle">solid</property>\n            <property name="borderBottomWidth">1pt</property>\n            <property name="borderLeftColor">#CCCCCC</property>\n            <property name="borderLeftStyle">solid</property>\n            <property name="borderLeftWidth">1pt</property>\n            <property name="borderRightColor">#CCCCCC</property>\n            <property name="borderRightStyle">solid</property>\n            <property name="borderRightWidth">1pt</property>\n            <property name="borderTopColor">#CCCCCC</property>\n            <property name="borderTopStyle">solid</property>\n            <property name="borderTopWidth">1pt</property>\n        </style>\n    </styles>\n    <page-setup>\n        <simple-master-page name="Simple MasterPage" id="2">\n            <page-header>\n                <image id="33">\n                    <property name="height">0.24166666666666667in</property>\n                    <property name="width">2.7583333333333333in</property>\n                    <property name="source">file</property>\n                    <expression name="uri" type="constant">logo_springsource_community.png</expression>\n                </image>\n            </page-header>\n            <page-footer>\n                <text id="3">\n                    <property name="contentType">html</property>\n                    <text-property name="content"><![CDATA[<value-of>new Date()</value-of>]]></text-property>\n                </text>\n            </page-footer>\n        </simple-master-page>\n    </page-setup>\n    <body>\n        <grid id="20">\n            <property name="borderBottomColor">#008040</property>\n            <property name="borderBottomStyle">solid</property>\n            <property name="borderBottomWidth">thin</property>\n            <property name="borderLeftColor">#008040</property>\n            <property name="borderLeftStyle">solid</property>\n            <property name="borderLeftWidth">thin</property>\n            <property name="borderRightColor">#008040</property>\n            <property name="borderRightStyle">solid</property>\n            <property name="borderRightWidth">thin</property>\n            <property name="borderTopColor">#008040</property>\n            <property name="borderTopStyle">solid</property>\n            <property name="borderTopWidth">thin</property>\n            <column id="21"/>\n            <row id="22">\n                <property name="style">special-header</property>\n                <cell id="23">\n                    <label id="32">\n                        <property name="fontSize">large</property>\n                        <property name="textAlign">center</property>\n                        <text-property name="text">Spring Bean Example Report</text-property>\n                    </label>\n                </cell>\n            </row>\n            <row id="24">\n                <property name="style">special-detail</property>\n                <cell id="25">\n                    <data id="19">\n                        <list-property name="boundDataColumns">\n                            <structure>\n                                <property name="name">Column Binding</property>\n                                <expression name="expression" type="javascript">var mypojo = spring.getBean("carService");&#13;\nmypojo.getAllCars().get(0).getMake();</expression>\n                                <property name="dataType">string</property>\n                            </structure>\n                        </list-property>\n                        <property name="resultSetColumn">Column Binding</property>\n                    </data>\n                </cell>\n            </row>\n            <row id="26">\n                <property name="style">special-detail</property>\n                <cell id="27">\n                    <data id="18">\n                        <list-property name="boundDataColumns">\n                            <structure>\n                                <property name="name">Column Binding</property>\n                                <expression name="expression" type="javascript">var mypojo = spring.getBean("carService");&#13;\nmypojo.getAllCars().get(0).getModel();</expression>\n                                <property name="dataType">string</property>\n                            </structure>\n                        </list-property>\n                        <property name="resultSetColumn">Column Binding</property>\n                    </data>\n                </cell>\n            </row>\n            <row id="28">\n                <property name="style">special-detail</property>\n                <cell id="29">\n                    <data id="17">\n                        <list-property name="boundDataColumns">\n                            <structure>\n                                <property name="name">Column Binding</property>\n                                <expression name="expression" type="javascript">var mypojo = spring.getBean("carService");&#13;\nmypojo.getAllCars().get(0).getYear();</expression>\n                                <property name="dataType">string</property>\n                            </structure>\n                        </list-property>\n                        <property name="resultSetColumn">Column Binding</property>\n                    </data>\n                </cell>\n            </row>\n            <row id="30">\n                <property name="style">special-detail</property>\n                <cell id="31">\n                    <data id="7">\n                        <list-property name="boundDataColumns">\n                            <structure>\n                                <property name="name">Column Binding</property>\n                                <expression name="expression" type="javascript">var mypojo = spring.getBean("carService");&#13;\nmypojo.getAllCars().get(0).toString();</expression>\n                                <property name="dataType">string</property>\n                            </structure>\n                        </list-property>\n                        <property name="resultSetColumn">Column Binding</property>\n                    </data>\n                </cell>\n            </row>\n        </grid>\n    </body>\n</report>	\N	XML
5	Demo Birt Uno	<?xml version="1.0" encoding="UTF-8"?>\n<report xmlns="http://www.eclipse.org/birt/2005/design" version="3.2.23" id="1">\n    <property name="createdBy">Eclipse BIRT Designer Version 4.6.0.v201606072122</property>\n    <property name="units">in</property>\n    <property name="iconFile">/templates/blank_report.gif</property>\n    <property name="bidiLayoutOrientation">ltr</property>\n    <property name="imageDPI">72</property>\n    <page-setup>\n        <simple-master-page name="Simple MasterPage" id="2">\n            <page-footer>\n                <text id="3">\n                    <property name="contentType">html</property>\n                    <text-property name="content"><![CDATA[<value-of>new Date()</value-of>]]></text-property>\n                </text>\n            </page-footer>\n        </simple-master-page>\n    </page-setup>\n    <body>\n        <text id="5">\n            <property name="fontSize">xx-large</property>\n            <property name="fontWeight">bold</property>\n            <property name="textAlign">center</property>\n            <property name="contentType">auto</property>\n            <text-property name="content"><![CDATA[Bienvenido al BBVA Reporter V0.1]]></text-property>\n        </text>\n        <label id="6">\n            <property name="textAlign">center</property>\n            <structure name="toc"/>\n            <text-property name="text">Esto es una prueba de reporte :-)</text-property>\n        </label>\n        <image id="4">\n            <property name="marginLeft">100pt</property>\n            <property name="display">inline</property>\n            <property name="height">1.25in</property>\n            <property name="width">4.944444444444445in</property>\n            <structure name="toc"/>\n            <property name="source">url</property>\n            <expression name="uri" type="constant">https://www.bbvacontinental.pe/fbin/mult/logoperu_tcm1105-418187.png</expression>\n        </image>\n        <text id="7">\n            <property name="fontSize">12pt</property>\n            <property name="paddingTop">20pt</property>\n            <property name="paddingLeft">20pt</property>\n            <property name="paddingBottom">20pt</property>\n            <property name="paddingRight">20pt</property>\n            <property name="whiteSpace">normal</property>\n            <property name="contentType">auto</property>\n            <text-property name="content"><![CDATA[Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultricies est a finibus convallis. Morbi vulputate eu odio vel vehicula. Duis dignissim auctor ornare. Nulla facilisi. Mauris blandit, turpis vel malesuada rhoncus, urna ipsum aliquet arcu, vel egestas leo est eget elit. Integer at nisi eu risus faucibus maximus. Nullam sed dictum mi, vitae hendrerit enim. Aliquam metus ex, rutrum a malesuada ut, bibendum eget neque. Suspendisse at facilisis mi. Morbi vulputate consectetur justo, ut dignissim purus condimentum sed.\n\nIn pretium condimentum pulvinar. Curabitur tempus egestas justo, nec rutrum sem condimentum a. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed fermentum enim odio, et commodo velit tempor ultricies. Ut nec lobortis magna. Curabitur elementum fringilla facilisis. Proin nec cursus tortor.\n\nSuspendisse vulputate dui non nulla tincidunt pharetra. Sed non felis hendrerit urna gravida consequat. Nunc vel tellus in neque aliquam varius. Aenean quis dictum dui. Vivamus tincidunt tincidunt turpis quis scelerisque. Curabitur a commodo lorem. Aliquam interdum dolor in purus pellentesque eleifend. Donec a mi ut lorem pellentesque interdum et in nisl.\n\nDonec et orci ut nulla pretium sagittis. Nulla facilisi. Nullam imperdiet gravida mauris et aliquam. Pellentesque laoreet scelerisque tellus vitae hendrerit. Nam nec elit condimentum, pulvinar risus sed, tincidunt ipsum. Vestibulum velit enim, ornare eget consequat a, sodales at lorem. Curabitur sit amet dolor hendrerit, luctus dui in, consequat lectus. In sed mi dictum velit consectetur sodales quis at sem. Nam eget elit diam. Morbi euismod mauris vitae eros pellentesque, nec venenatis nisi vulputate. Quisque aliquam et tortor sit amet commodo.\n\nVivamus hendrerit tortor a massa pellentesque, ut pulvinar risus rutrum. Pellentesque lacinia, quam condimentum suscipit iaculis, dui eros fermentum justo, nec porta metus metus eget augue. Donec vehicula consectetur tempor. Suspendisse potenti. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque venenatis sollicitudin augue sed feugiat. Vestibulum ultricies augue nibh, a condimentum lorem consequat eu. Fusce sollicitudin pulvinar turpis in fringilla. In sodales, arcu quis egestas blandit, ante felis suscipit metus, eget malesuada urna velit non elit. Donec quis dapibus dui, vel rhoncus libero.\n\n]]></text-property>\n        </text>\n    </body>\n</report>\n	\N	XML
\.


--
-- Name: tbl_report_param_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_report_param_id_seq', 2, true);


--
-- Data for Name: tbl_report_source; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_report_source (id, report_id, report_source_type_id, name, content, type_id, collection) FROM stdin;
2	2	3	clientExtras	{ "name":"pablito"}	\N	t
1	2	1	clientData	SELECT * FROM tbl_report_source_type WHERE id=:type_id	\N	t
\.


--
-- Name: tbl_report_source_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_report_source_id_seq', 2, true);


--
-- Data for Name: tbl_report_source_param; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_report_source_param (id, name, value, required, report_source_id) FROM stdin;
2	type_id	Codigo de aplicacion	t	1
1	codCliente	Codigo del cliente	f	2
\.


--
-- Data for Name: tbl_report_source_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_report_source_type (name, id) FROM stdin;
SQL	1
VIEW	2
JSON	3
\.


--
-- Name: tbl_report_source_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_report_source_type_id_seq', 3, true);


--
-- Name: tbl_resource_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_resource_id_seq', 5, true);


--
-- Name: tbl_report_source_param tbl_report_param_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source_param
    ADD CONSTRAINT tbl_report_param_pkey PRIMARY KEY (id);


--
-- Name: tbl_report_source tbl_report_source_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source
    ADD CONSTRAINT tbl_report_source_pkey PRIMARY KEY (id);


--
-- Name: tbl_report_source_type tbl_report_source_type_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source_type
    ADD CONSTRAINT tbl_report_source_type_id_pk PRIMARY KEY (id);


--
-- Name: tbl_report tbl_resource_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report
    ADD CONSTRAINT tbl_resource_pkey PRIMARY KEY (id);


--
-- Name: tbl_report_source fk2ft9jvxb2hxkqgt7rqxecbvp8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source
    ADD CONSTRAINT fk2ft9jvxb2hxkqgt7rqxecbvp8 FOREIGN KEY (type_id) REFERENCES tbl_report_source_type(id);


--
-- Name: tbl_report_source_param fkbls74ecxm5tmb5cuyouabjx0c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source_param
    ADD CONSTRAINT fkbls74ecxm5tmb5cuyouabjx0c FOREIGN KEY (report_source_id) REFERENCES tbl_report_source(id);


--
-- Name: tbl_report_source fkgx6ntltmejolmf3xr1n7gwoc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source
    ADD CONSTRAINT fkgx6ntltmejolmf3xr1n7gwoc FOREIGN KEY (report_id) REFERENCES tbl_report(id);


--
-- Name: tbl_report_source fkig0a199i4c2rsi3xry6g1aloh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source
    ADD CONSTRAINT fkig0a199i4c2rsi3xry6g1aloh FOREIGN KEY (report_source_type_id) REFERENCES tbl_report_source_type(id);


--
-- Name: tbl_report_source_param fkrqjkbcfgowoqujnwdkjmflvcu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source_param
    ADD CONSTRAINT fkrqjkbcfgowoqujnwdkjmflvcu FOREIGN KEY (report_source_id) REFERENCES tbl_report_source(id);


--
-- Name: tbl_report_source tbl_report_source_tbl_report_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source
    ADD CONSTRAINT tbl_report_source_tbl_report_id_fk FOREIGN KEY (report_id) REFERENCES tbl_report(id);


--
-- Name: tbl_report_source tbl_report_source_tbl_report_source_type_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_report_source
    ADD CONSTRAINT tbl_report_source_tbl_report_source_type_id_fk FOREIGN KEY (report_source_type_id) REFERENCES tbl_report_source_type(id);


--
-- PostgreSQL database dump complete
--

