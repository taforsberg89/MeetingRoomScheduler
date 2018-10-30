#maven/java image from dockerhub
FROM maven:3.5.3-jdk-8-slim 

#expose 8080 to outside world
EXPOSE 8080 

#create workdir
WORKDIR /app

#Install postgresql
RUN apt update && \
    mkdir -p /usr/share/man/man1 &&\
    mkdir -p /usr/share/man/man7 &&\
    apt install -y postgresql-9.6

#Update config so postgres won't ask for PW (not good in production but saves us a lot of setup for this simple demo)
RUN rm /etc/postgresql/9.6/main/pg_hba.conf && \
	echo "host  all all 127.0.0.1/32  trust" >> /etc/postgresql/9.6/main/pg_hba.conf

#Add files app to workdir
ADD . /app
RUN chmod 775 /app/run.sh
CMD ./run.sh