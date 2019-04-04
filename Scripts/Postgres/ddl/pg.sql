-- Create database before running the Script
-- The below script will generate different tables as listed below
        -- 1. Users
        -- 2. Items
        -- 3. Customer_Bill
        -- 4. Customer_Bill_Details

CREATE SCHEMA dbo;

CREATE SEQUENCE dbo.users_seq;

CREATE TABLE dbo.users
(
    id integer NOT NULL DEFAULT nextval('dbo.users_seq'::regclass),
    user_name character varying(500) NOT NULL,
	password character varying(100) NOT NULL,
    phone_number character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (user_name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE dbo.users
    OWNER to postgres;
	
	
CREATE SEQUENCE dbo.items_seq;

CREATE TABLE dbo.items
(
    id integer NOT NULL DEFAULT nextval('dbo.items_seq'::regclass),
    name character varying(500) NOT NULL,
	description character varying(100),
    rate NUMERIC (5, 2) NOT NULL,
    created_datetime timestamp(6) without time zone DEFAULT now(),
    last_updated_datetime timestamp(6) without time zone,
	is_available boolean NOT NULL DEFAULT true,
    CONSTRAINT items_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE dbo.items
    OWNER to postgres;

	
CREATE SEQUENCE dbo.customer_bill_seq;

CREATE TABLE dbo.customer_bill
(
    id integer NOT NULL DEFAULT nextval('dbo.customer_bill_seq'::regclass),
    bill_date timestamp(6) without time zone DEFAULT now(),
    total NUMERIC (5, 2) NOT NULL,
	user_name character varying(500) NOT NULL,
    CONSTRAINT customer_bill_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE dbo.customer_bill
    OWNER to postgres;


CREATE SEQUENCE dbo.customer_bill_details_seq;

CREATE TABLE dbo.customer_bill_details
(
    id integer NOT NULL DEFAULT nextval('dbo.customer_bill_seq'::regclass),
    bill_id integer NOT NULL,
    item_id integer NOT NULL,
	quantity integer NOT NULL,
	total NUMERIC(5,2) NOT NULL,
    CONSTRAINT customer_bill_details_pkey PRIMARY KEY (id),
	CONSTRAINT customer_bill_id_ref FOREIGN KEY (bill_id)
        REFERENCES dbo.customer_bill (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT items_id_ref FOREIGN KEY (item_id)
        REFERENCES dbo.items (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE dbo.customer_bill_details
    OWNER to postgres;
