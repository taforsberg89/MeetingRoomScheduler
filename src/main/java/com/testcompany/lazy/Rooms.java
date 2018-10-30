package com.testcompany.lazy;

import java.util.HashMap;
import java.util.Map;

public class Rooms {

    /*
        This mapping should be read from the DB
        but I'm taking some shortcuts
     */
    public static Map<Integer, String> getRoomMapping(){
        return new HashMap<Integer, String>(){{
           put(1, "conferenceroom");
           put(2, "coffeeroom");
           put(3, "projectorroom");
           put(4, "conferenceroom");
        }};
    }
}
