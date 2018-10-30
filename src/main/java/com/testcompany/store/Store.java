package com.testcompany.store;

import com.testcompany.models.booking.Booking;
import com.testcompany.models.meetingrooms.MeetingRoom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class Store {

    @Autowired
    JdbcTemplate db;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void bookRoom(int roomId, String roomType, Date startTime, Date endTime) {
        String startTimeStr = DATE_FORMAT.format(startTime);
        String endTimeStr = DATE_FORMAT.format(endTime);
        String sql = String.format("INSERT INTO roombookings VALUES('%s', '%s', '%s', '%s')",
                roomId, roomType,
                startTimeStr, endTimeStr);
        db.execute(sql);
    }

    public List<MeetingRoom> getRooms() throws DataAccessException {

        String sql = "select * from rooms";
        return processRoomList(db.queryForList(sql));

    }

    public List<Booking> getBookingList(String roomType) throws ParseException, DataAccessException {

        String sql = String.format("select * from roombookings where roomtype = '%s'", roomType);
        return processBookingList(db.queryForList(sql));
    }

    private List<MeetingRoom> processRoomList(List<Map<String, Object>> list){
        List<MeetingRoom> meetingRoomList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            int id = Integer.parseInt(map.get("id").toString());
            String roomType = map.get("roomtype").toString();
            meetingRoomList.add(
                    new MeetingRoom(id, roomType)
            );
        }
        return meetingRoomList;
    }

    private List<Booking> processBookingList(List<Map<String, Object>> list) throws ParseException {

        List<Booking> bookingList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            int roomId = Integer.parseInt(map.get("roomid").toString());
            String localRoomType = map.get("roomtype").toString();
            Date startDate = DATE_FORMAT.parse(map.get("startbookingtime").toString());
            Date endDate = DATE_FORMAT.parse(map.get("endbookingtime").toString());
            bookingList.add(new Booking(roomId, localRoomType, startDate, endDate));
        }
        return bookingList;
    }


}
