# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table medical_file (
  id                        bigint not null,
  alias                     varchar(255),
  source                    varchar(255),
  upload_date               timestamp,
  constraint pk_medical_file primary key (id))
;

create table medication (
  id                        bigint not null,
  file_id                   bigint not null,
  name                      varchar(255),
  prescribed                boolean,
  source                    varchar(255),
  category                  varchar(255),
  prescription_num          varchar(255),
  strength                  varchar(255),
  dose                      varchar(255),
  unit                      varchar(255),
  frequency                 varchar(255),
  remaining                 integer,
  start_date                timestamp,
  last_taken_date           timestamp,
  current_taking            boolean,
  physician                 varchar(255),
  condition                 varchar(255),
  reason_for_taking         varchar(255),
  pharmacy_id               bigint,
  constraint pk_medication primary key (id))
;

create table pharmacy (
  id                        bigint not null,
  name                      varchar(255),
  phone                     varchar(255),
  constraint pk_pharmacy primary key (id))
;

create table task (
  id                        bigint not null,
  label                     varchar(255),
  constraint pk_task primary key (id))
;

create sequence medical_file_seq;

create sequence medication_seq;

create sequence pharmacy_seq;

create sequence task_seq;

alter table medication add constraint fk_medication_medical_file_1 foreign key (file_id) references medical_file (id) on delete restrict on update restrict;
create index ix_medication_medical_file_1 on medication (file_id);
alter table medication add constraint fk_medication_pharmacy_2 foreign key (pharmacy_id) references pharmacy (id) on delete restrict on update restrict;
create index ix_medication_pharmacy_2 on medication (pharmacy_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists medical_file;

drop table if exists medication;

drop table if exists pharmacy;

drop table if exists task;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists medical_file_seq;

drop sequence if exists medication_seq;

drop sequence if exists pharmacy_seq;

drop sequence if exists task_seq;

