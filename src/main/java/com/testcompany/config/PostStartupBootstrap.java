package com.testcompany.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PostStartupBootstrap {

    @Autowired
    JdbcTemplate db;

    @Bean
    @EventListener(ApplicationReadyEvent.class)
    public int bootstrapData(){

        /*
        This would typically be done in some deployment/cicd script
        but doing it here for simplicity

        NOTE: this will wipe the DB every time you restart the application
        */
        db.execute("DROP TABLE IF EXISTS rooms");
        db.execute("CREATE TABLE rooms(id integer, roomtype varchar(255))");
        db.execute("insert into rooms values ('1', 'conferenceroom')");
        db.execute("insert into rooms values ('2', 'coffeeroom')");
        db.execute("insert into rooms values ('3', 'projectorroom')");
        db.execute("insert into rooms values ('4', 'conferenceroom')");

        db.execute("DROP TABLE IF EXISTS roomBookings");
        db.execute("CREATE TABLE roomBookings(roomid INTEGER, roomtype VARCHAR(255), startbookingtime TIMESTAMP, endbookingtime TIMESTAMP)" );
        return 0;
    }

}
