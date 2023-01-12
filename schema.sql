create database second_hand;

use second_hand;

create table postings (

	posting_id varchar(128) not null,

	posting_date varchar(32) not null,

	name varchar(64) not null,

	email varchar(128) not null,

	phone varchar(16),

	title varchar(256) not null,

	description text not null,

	image varchar(256) not null,

	primary key(posting_id)
);