create database second_hand;

use second_hand;

create table postings (

	posting_id varchar(128) not null,

	posting_date Date not null,

	name varchar(128) not null,

	phone varchar(16),

	description text not null,

	image varchar(256) not null,

	primary key(posting_id)
);