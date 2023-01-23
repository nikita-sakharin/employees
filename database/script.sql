-- sudo --user=postgres psql --file=script.sql
DROP DATABASE IF EXISTS employees;
DROP USER IF EXISTS employees_user;

-- Just for example. You shouldn't save password to git.
CREATE USER employees_user PASSWORD 'a2{O)9U%V+Lt~dnMykSPG1?sF8Wf:p0H';
CREATE DATABASE employees WITH
    OWNER employees_user
    ENCODING 'UTF8';

-- psql --dbname=employees --username=employees_user --password

COPY employee(id, supervisor, full_name)
    FROM 'employee.csv'
        WITH DELIMITER AS ',' NULL AS  '0' CSV HEADER ENCODING 'UTF8';
