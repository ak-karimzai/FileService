CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE file_info (
   id uuid primary key default gen_random_uuid(),
   title varchar not null,
   description varchar not null,
   upload_date timestamp default now()
);