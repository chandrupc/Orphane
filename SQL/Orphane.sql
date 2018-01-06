-- CREATING DATABASE

create database orphane;


--USING THE CREATED DATABASE

use orphane;


--CREATING TABLES

--ORPHANAGE DETAILS

create table orp_orphanage(
    orp_id              bigint          auto_increment,
    file_id             bigint,
    orphanage_name      varchar(50)     not null,
    ph_num              bigint          not null,
    alt_ph_num          bigint,
    orp_address         text,
    city                varchar(50)     not null,
    state               varchar(50)     not null,
    pincode             int             not null,
    website             varchar(100),
    constraint          orp_orphanage_orp_id_pk         primary     key         (orp_id),
    constraint          orp_orphanage_file_id_fk        foreign     key         (file_id)       references     orp_file_details(file_id),
    constraint          orp_orphanage_ph_num_unq        unique                  (ph_num),
    constraint          orp_orphanage_alt_ph_num_unq    unique                  (alt_ph_num),
    constraint          orp_orphanage_address_unq       unique                  (house_no,street_name,area_name,city,state,pincode)
);

--ORP ID GENERATOR

/*create table  orp_id_gen(
    orp_id          varchar(30),
    cur_id          bigint,     
);*/


--ORPHANAGE TRUSTEE DETAILS

create table orp_trustee(
    trustee_id          bigint          auto_increment,
    file_id             bigint,
    first_name          varchar(30)     not null,
    last_name           varchar(30)     not null,
    ph_num              bigint          not null,
    alt_ph_num          bigint,
    trustee_address     text,
    city                varchar(50)     not null,
    state               varchar(50)     not null,
    pincode             int             not null,
    constraint          orp_trustee_trustee_id_pk     primary     key         (trustee_id),
    constraint          orp_trustee_file_id_fk        foreign     key         (file_id)       references     orp_file_details(file_id),
    constraint	orp_trustee_address_unq	unique	(house_no,street_name,area_name,city,state,pincode)
);


--ORPHANAGE TRUSTEE MAPPING

create  table orp_mapping_table(
    orp_id          bigint,
    trustee_id      bigint,
    constraint      orp_mapping_table_orp_id_fk             foreign         key         (orp_id)        references      orp_orphanage(orp_id),
    constraint      orp_mapping_table_trustee_id_fk         foreign         KEY         (trustee_id)    references      orp_trustee(trustee_id)
);

--FILE DETAILS

create table orp_file_details(
    file_id             bigint          auto_increment,
    file_name           varchar(100)    not null,
    file_location       varchar(100)    not null,
    user_type           enum            ("ORPHANAGE","REGULAR","TRUSTEE"),
    constraint          orp_file_details_file_id_pk             primary         key         (file_id)
);

--FILE ID GENERATOR

/*create table  file_id_gen(
    file_id         varchar(30),
    cur_id          bigint,     
);*/


--REGULAR USERS DETAILS

create table orp_reg_user(
    orp_id              bigint          auto_increment,
    file_id             bigint,
    card_id             bigint,
    first_name          varchar(30)     not null,
    last_name           varchar(30)     not null,
    dob                 date;
    ph_num              bigint          not null,
    alt_ph_num          bigint,
    reg_address         text,
    city                varchar(50)     not null,
    state               varchar(50)     not null,
    pincode             int             not null,
    constraint          orp_reg_user_orp_id_pk         primary     key          (orp_id),
    constraint          orp_reg_user_address_unq       unique                  (house_no,street_name,area_name,city,state,pincode),
    constraint          orp_reg_user_file_id_fk        foreign     key         (file_id)       references     orp_file_details(file_id),
    constraint          orp_reg_user_ph_num_unq        unique                  (ph_num),
    constraint          orp_reg_user_alt_ph_num_unq    unique                  (alt_ph_num)
);


--CARD DETAILS 

create  table   orp_card_details(
    card_id             bigint          auto_increment,
    name_on_card        varchar(30)     not null,
    expiry_date         date            not null,
    card_no             bigint          not null,
    constraint          orp_card_details_card_id_pk         primary     key         (card_id),
    constraint          orp_card_details_unq                unique                  (name_on_card,expiry_date,card_no)
);

--CARD ID GENERATOR

/*create table  card_id_gen(
    card_id         varchar(30),
    cur_id          bigint,     //current_id
);*/



--USERS CREDENTIAL

create  table   orp_credential(
    email       varchar(50),
    password    varchar(30),
    auth_key    varchar(30),
    user_type   enum                ("ORPHANAGE","TRUSTEE"),
    constraint          orp_credential_email_pk             primary         key         (email)
);



--NOTIFY USERS


create  table  orp_notify(
    email       varchar(50),
    status      enum            ("SUBSCRIBED","UNSUBSCRIBED"),
    constraint          orp_notify_email_pk             primary         key             (email)
);  



-- EVENT MANAGEMENT

CREATE TABLE   orp_event_mgmt(
    event_id        bigint      auto_increment,
    event_date      date,
    event_name      enum            ("BREAKFAST","SNACKS","LUNCH","DINNER"),
    event_status    enum            ("BOOKED","CANCELLED","OVER"),
    constraint      orp_event_mgmt_event_id_pk          primary             KEY             (event_id)
);
