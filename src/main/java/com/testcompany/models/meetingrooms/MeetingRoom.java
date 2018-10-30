package com.testcompany.models.meetingrooms;

public class MeetingRoom {


    int id;

    String roomType;

    public MeetingRoom(int id, String roomType){
        this.id = id;
        this.roomType = roomType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
