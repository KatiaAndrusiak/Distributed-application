
CREATE SEQUENCE public.subsciber_nsubscriberid_seq;

CREATE TABLE public.subsciber (
                nSubscriberId INTEGER NOT NULL DEFAULT nextval('public.subsciber_nsubscriberid_seq'),
                sFirstName VARCHAR(50) NOT NULL,
                sLastName VARCHAR(50) NOT NULL,
                CONSTRAINT subsciber_pk PRIMARY KEY (nSubscriberId)
);


ALTER SEQUENCE public.subsciber_nsubscriberid_seq OWNED BY public.subsciber.nSubscriberId;

CREATE TABLE public.subscriberData (
                nSubscriberId INTEGER NOT NULL,
                sEmail VARCHAR(50) NOT NULL,
                sPassword VARCHAR(40) NOT NULL,
                CONSTRAINT subscriberdata_pk PRIMARY KEY (nSubscriberId)
);


CREATE TABLE public.subscriberInfo (
                nSubscriberId INTEGER NOT NULL,
                DOB DATE NOT NULL,
                sPhone VARCHAR(12) NOT NULL,
                sCountry VARCHAR(30) NOT NULL,
                sStreet VARCHAR(30) NOT NULL,
                sAdress VARCHAR(60) NOT NULL,
                nLatitude NUMERIC(10,8) NOT NULL,
                nLongitude NUMERIC(11,8) NOT NULL,
                CONSTRAINT subscriberinfo_pk PRIMARY KEY (nSubscriberId)
);


CREATE SEQUENCE public.category_ncategoryid_seq;

CREATE TABLE public.category (
                nCategoryId INTEGER NOT NULL DEFAULT nextval('public.category_ncategoryid_seq'),
                sCategoryName VARCHAR(50) NOT NULL,
                CONSTRAINT category_pk PRIMARY KEY (nCategoryId)
);


ALTER SEQUENCE public.category_ncategoryid_seq OWNED BY public.category.nCategoryId;

CREATE TABLE public.subscriberCategory (
                nCategoryId INTEGER NOT NULL,
                nSubscriberId INTEGER NOT NULL,
                CONSTRAINT subscribercategory_pk PRIMARY KEY (nCategoryId, nSubscriberId)
);


ALTER TABLE public.subscriberInfo ADD CONSTRAINT subsciber_subscriberinfo_fk
FOREIGN KEY (nSubscriberId)
REFERENCES public.subsciber (nSubscriberId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.subscriberData ADD CONSTRAINT subsciber_subscriberdata_fk
FOREIGN KEY (nSubscriberId)
REFERENCES public.subsciber (nSubscriberId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.subscriberCategory ADD CONSTRAINT subsciber_subscribercategory_fk
FOREIGN KEY (nSubscriberId)
REFERENCES public.subsciber (nSubscriberId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.subscriberCategory ADD CONSTRAINT category_subscribercategory_fk
FOREIGN KEY (nCategoryId)
REFERENCES public.category (nCategoryId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;