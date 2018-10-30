package com.testcompany.store;

import com.testcompany.MeetingRoomApplication;
import com.testcompany.models.booking.Booking;
import com.testcompany.models.meetingrooms.MeetingRoom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MeetingRoomApplication.class)
public class StoreTest {

    @Autowired
    Store store;

    @Mock
    private JdbcTemplate mockJdbc;

    @Before
    public void setup() {
        initMocks(this);
        setField(store, "db", mockJdbc);
    }

    @Test
    public void whenGettingRoomsItShouldReturnANonEmptyListOfRooms(){
        List<Map<String, Object>> fakeList = createFakeRoomList();
        when(mockJdbc.queryForList(any())).thenReturn(fakeList);
        List<MeetingRoom> returnedList = store.getRooms();
        assert(returnedList.size() != 0);
    }

    @Test
    public void whenGettingBookingListItShouldReturnANonEmptyListOfBookings() throws Exception{

        List<Map<String, Object>> fakeList = createFakeBookingList();
        when(mockJdbc.queryForList(any())).thenReturn(fakeList);
        List<Booking> returnedList = store.getBookingList("conferenceroom");
        assert (returnedList.size() != 0);

    }

    @Test
    public void whenBookingRoomNoExceptionsShouldBeThrown(){
        doNothing().when(mockJdbc).execute(anyString());
        store.bookRoom(1, "roomType", new Date(), new Date());

    }

    private List<Map<String, Object>> createFakeRoomList(){
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("roomtype", "conferenceroom");
        list.add(map);
        return list;
    }

    private List<Map<String, Object>> createFakeBookingList(){
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("roomid", 1);
        map.put("roomtype", "conferenceroom");
        map.put("startbookingtime", "1990-05-23 05:34:24");
        map.put("endbookingtime", "1990-05-23 05:34:24");
        list.add(map);
        return list;
    }
}
