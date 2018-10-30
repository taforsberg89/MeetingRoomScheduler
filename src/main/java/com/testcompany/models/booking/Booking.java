package com.testcompany.models.booking;

import java.util.Date;

public class Booking {

    Date startTime;
    Date endTime;
    int roomId;
    String roomType;

    public Booking(){

    }

    public Booking(int roomId, String roomType, Date startTime, Date endTime){
        this.roomId = roomId;
        setRoomType(roomType);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
