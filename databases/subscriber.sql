
CREATE SEQUENCE public.subscriber_nsubscriberid_seq;

CREATE TABLE public.subscriber (
                nSubscriberId INTEGER NOT NULL DEFAULT nextval('public.subscriber_nsubscriberid_seq'),
                sFirstName VARCHAR(50) NOT NULL,
                sLastName VARCHAR(50) NOT NULL,
                CONSTRAINT subscriber_pk PRIMARY KEY (nSubscriberId)
);


ALTER SEQUENCE public.subscriber_nsubscriberid_seq OWNED BY public.subscriber.nSubscriberId;

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


CREATE TABLE public.subscriberData (
                nSubscriberId INTEGER NOT NULL,
                sEmail VARCHAR(50) NOT NULL,
                sPassword VARCHAR(40) NOT NULL,
                CONSTRAINT subscriberdata_pk PRIMARY KEY (nSubscriberId)
);


CREATE SEQUENCE public.category_ncategoryid_seq;

CREATE TABLE public.category (
                nCategoryId INTEGER NOT NULL DEFAULT nextval('public.category_ncategoryid_seq'),
                sCategoryName VARCHAR(50) NOT NULL,
                CONSTRAINT category_pk PRIMARY KEY (nCategoryId)
);


ALTER SEQUENCE public.category_ncategoryid_seq OWNED BY public.category.nCategoryId;

CREATE SEQUENCE public.problem_nproblemid_seq;

CREATE TABLE public.problem (
                nProblemId INTEGER NOT NULL DEFAULT nextval('public.problem_nproblemid_seq'),
                nCategoryId INTEGER NOT NULL,
                sProblemName VARCHAR NOT NULL,
                CONSTRAINT problem_pk PRIMARY KEY (nProblemId, nCategoryId)
);


ALTER SEQUENCE public.problem_nproblemid_seq OWNED BY public.problem.nProblemId;

CREATE TABLE public.subscriberCategory (
                nCategoryId INTEGER NOT NULL,
                nSubscriberId INTEGER NOT NULL,
                CONSTRAINT subscribercategory_pk PRIMARY KEY (nCategoryId, nSubscriberId)
);


ALTER TABLE public.subscriberCategory ADD CONSTRAINT subsciber_subscribercategory_fk
FOREIGN KEY (nSubscriberId)
REFERENCES public.subscriber (nSubscriberId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.subscriberData ADD CONSTRAINT subscriber_subscriberdata_fk
FOREIGN KEY (nSubscriberId)
REFERENCES public.subscriber (nSubscriberId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.subscriberInfo ADD CONSTRAINT subscriber_subscriberinfo_fk
FOREIGN KEY (nSubscriberId)
REFERENCES public.subscriber (nSubscriberId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.subscriberCategory ADD CONSTRAINT category_subscribercategory_fk
FOREIGN KEY (nCategoryId)
REFERENCES public.category (nCategoryId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.problem ADD CONSTRAINT category_problem_fk
FOREIGN KEY (nCategoryId)
REFERENCES public.category (nCategoryId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

----------------
insert into subscriber (sFirstName, sLastName) VALUES
    ('test', 'test');

select * from subscriber;

insert into subscriberInfo (nSubscriberId, DOB, sPhone, sCountry, sStreet, sAdress, nLatitude, nLongitude) VALUES
    (1, '2021-10-13', '+48574450911', 'Polska', 'Dworska', '1A', 50.046006, 19.930139);

select * from subscriberInfo;

insert into subscriberData (nSubscriberId, sEmail, sPassword) values
    (1, 'tester@gmail.com', 'tester123');

select * from subscriberData;