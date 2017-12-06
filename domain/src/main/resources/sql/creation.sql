use findit2_test;

create table user (
    id integer not null auto_increment,
    version integer not null,
    username varchar(50) not null,
    password varchar(100) not null,

    primary key (id)
);

create table role (
    id integer not null auto_increment,
    version integer not null,
    authority varchar(20) not null,

    primary key (id)
);

create table user_role (
    user_id integer not null,
    role_id integer not null,

    foreign key (user_id) references user(id),
    foreign key (role_id) references role(id)
);

create table team (
    id integer not null auto_increment,
    version integer not null,
    team_name varchar(50) not null,
    creator integer not null,

    primary key (id),
    foreign key (creator) references user(id)
);

create table team_user (
    team_id integer not null,
    user_id integer not null,

    foreign key (team_id) references team(id),
    foreign key (user_id) references user(id)
);

create table item (
  id integer not null auto_increment,
  version integer not null,
  owner_id integer not null,
  name varchar(200) not null,
  date_created date not null,
  date_updated date not null,
  event_start_time date,
  event_end_time date,
  people varchar(5000),
  places varchar(5000),
  description varchar(5000),

  primary key (id),
  foreign key (owner_id) references user(id)
);

create table digital_item (
    id integer not null,
    original_file_name varchar(200) not null,
    file_name varchar(200) not null,
    file_type varchar(20),
    file_size integer,

    primary key (id),
    foreign key (id) references item(id)
);

create table physical_item (
    id integer not null,
    length float,
    width float,
    height float,

    primary key (id),
    foreign key (id) references item(id)
);

create table team_item (
  team_id integer not null,
  item_id integer not null,

  foreign key (team_id) references team(id),
  foreign key (item_id) references item(id)
);