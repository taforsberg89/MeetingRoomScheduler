package com.testcompany.controllers;


import com.testcompany.models.booking.Booking;
import com.testcompany.models.error.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.testcompany.store.Store;

import java.text.ParseException;

import static com.testcompany.lazy.Rooms.getRoomMapping;


@RestController
@RequestMapping(value = "/RoomScheduler", produces = {"application/json", MediaType.APPLICATION_JSON_VALUE})
public class MeetingRoomController {

    @Autowired
    Store store;

    @RequestMapping(
            value = "/{room}",
            method = RequestMethod.POST)
    ResponseEntity<Object> postBooking(@PathVariable String room,
                                       @RequestBody Booking booking) {

        ResponseEntity.BodyBuilder resp;
        try {
            validateRequest(booking.getRoomId(), room, booking);
            store.bookRoom(booking.getRoomId(), room, booking.getStartTime(), booking.getEndTime());
            booking.setRoomType(room); //assign the room directly from path to the booking before returning the booking
                                       //at this point the room has been validated and thus will always be correct
            resp = ResponseEntity.status(HttpStatus.CREATED);
            return resp.body(booking);
        } catch (RuntimeException e) {
            return returnBadRequest(e.getMessage());
        }
    }

    @RequestMapping(
            value = "/{room}",
            method = RequestMethod.GET)
    ResponseEntity<Object> getBookingsForRoom(@PathVariable String room) {

        ResponseEntity.BodyBuilder resp;

        try {
            resp = ResponseEntity.status(HttpStatus.OK);
            return resp.body(store.getBookingList(room));
        } catch (DataAccessException | ParseException e) {
            return returnBadRequest(e.getMessage());
        }
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET)
    ResponseEntity<Object> getListOfRooms() {
        ResponseEntity.BodyBuilder resp;

        try {
            resp = ResponseEntity.status(HttpStatus.OK);
            return resp.body(store.getRooms());
        } catch (DataAccessException e) {
            return returnBadRequest(e.getMessage());
        }
    }

    private ResponseEntity<Object> returnBadRequest(String message) {
        ResponseEntity.BodyBuilder resp = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        return resp.body(new ErrorMessage(message));
    }

    private void validateRequest(int roomId, String roomType, Booking booking) {
        if (booking.getEndTime() == null ||
                booking.getStartTime() == null ||
                booking.getRoomId() == 0)
            throw new RuntimeException("Invalid booking");
        if (!getRoomMapping().get(roomId).equalsIgnoreCase(roomType))
            throw new RuntimeException("Invalid room id/room type combination");
    }

}
