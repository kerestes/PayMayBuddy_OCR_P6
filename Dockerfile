FROM mysql:8.0.36

COPY ./spring.api/src/main/resources/scriptSQL.sql/ /docker-entrypoint-initdb.d/