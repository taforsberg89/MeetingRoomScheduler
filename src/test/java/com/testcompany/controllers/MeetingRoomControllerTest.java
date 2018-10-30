package com.testcompany.controllers;

import com.testcompany.MeetingRoomApplication;
import com.testcompany.store.Store;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MeetingRoomApplication.class)
public class MeetingRoomControllerTest {

    @Autowired
    MeetingRoomController controller;

    @Autowired
    WebApplicationContext wac;

    @Mock
    Store store;

    MockMvc mockMvc;

    /*
        We can also call the controller methods directly but with mockMvc we can simulate headers etc,
        as well as invoking interceptors/filters if any. In addition this tests the mapping in the rest controller
     */

    @Before
    public void setup(){
        initMocks(this);
        this.mockMvc = webAppContextSetup(this.wac).build();
        setField(controller, "store", store);
    }

    @Test
    public void whenPostingBookingRequestReturnHttpStatusOK() throws Exception{
        String url = "/RoomScheduler/conferenceroom";

        String jsonBody = "{\n" +
                "        \"startTime\": \"1990-05-23T05:34:24.000+0000\",\n" +
                "        \"endTime\": \"1992-05-23T06:34:24.000+0000\",\n" +
                "        \"roomId\":4\n" +
                "}";

        doNothing().when(store).bookRoom(anyInt(), any(), any(), any());


        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonBody.getBytes())).
                andExpect(status().is(HttpStatus.CREATED.value())).andReturn();
    }

    @Test
    public void whenPostingBookingWithInvalidRoomIdReturnBadRequest() throws Exception{
        String url = "/RoomScheduler/conferenceroom";

        String jsonBody = "{\n" +
                "        \"startTime\": \"1990-05-23T05:34:24.000+0000\",\n" +
                "        \"endTime\": \"1992-05-23T06:34:24.000+0000\",\n" +
                "        \"roomId\":99\n" +
                "}";

        doNothing().when(store).bookRoom(anyInt(), any(), any(), any());

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonBody.getBytes())).
                andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    public void whenPostingBookingWithoutEndTimeReturnBadRequest() throws Exception{
        String url = "/RoomScheduler/conferenceroom";

        String jsonBody = "{\n" +
                "        \"startTime\": \"1990-05-23T05:34:24.000+0000\",\n" +
                "        \"roomId\":1\n" +
                "}";

        doNothing().when(store).bookRoom(anyInt(), any(), any(), any());

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonBody.getBytes())).
                andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    public void whenGettingBasePathReturnListOfRooms() throws Exception{
        String url = "/RoomScheduler/";

        when(store.getRooms()).thenReturn(null);

        mockMvc.perform(get(url)).andExpect(status().is(HttpStatus.OK.value())).andReturn();
    }

    @Test
    public void whenGettingRoomReturnListOfBookingsForThatRoom() throws Exception{
        String url = "/RoomScheduler/conferenceroom";

        when(store.getBookingList(anyString())).thenReturn(null);

        mockMvc.perform(get(url)).andExpect(status().is(HttpStatus.OK.value())).andReturn();
    }

    @Test
    public void whenGettingRoomListButQueryTimesOutReturnBadRequest() throws Exception{
        String url = "/RoomScheduler/";

        when(store.getRooms()).thenThrow(new QueryTimeoutException("Query Timed out"));

        mockMvc.perform(get(url)).andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    public void whenGettingBookingsForARoomButQueryTimesOutReturnBadRequest() throws Exception{
        String url = "/RoomScheduler/conferenceroom";

        when(store.getBookingList(anyString())).thenThrow(new QueryTimeoutException("Query Timed out"));

        mockMvc.perform(get(url)).andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }
}
