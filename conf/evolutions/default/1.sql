# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table medication (
  id                        bigint not null,
  name                      varchar(255),
  strength                  float,
  unit                      varchar(255),
  frequency                 varchar(255),
  begin_date                timestamp,
  last_taken_date           timestamp,
  current_taking            boolean,
  physician                 varchar(255),
  condition                 varchar(255),
  constraint pk_medication primary key (id))
;

create table task (
  id                        bigint not null,
  label                     varchar(255),
  constraint pk_task primary key (id))
;

create sequence medication_seq;

create sequence task_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists medication;

drop table if exists task;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists medication_seq;

drop sequence if exists task_seq;

