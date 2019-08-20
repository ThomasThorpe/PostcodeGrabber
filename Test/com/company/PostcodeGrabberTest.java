package com.company;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostcodeGrabberTest {

    @Test
    void responseValid() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","200");
        try {
            assertTrue(PostcodeGrabber.responseValid(jsonObject));
        } catch (Exception err){
            err.printStackTrace();
        }
        jsonObject.put("status","400");
        try {
            PostcodeGrabber.responseValid(jsonObject);
        } catch (Exception err){
            assertEquals("400 Bad Request",err.getMessage());
        }
        jsonObject.put("status","404");
        try {
            PostcodeGrabber.responseValid(jsonObject);
        } catch (Exception err){
            assertEquals("404 Not Found",err.getMessage());
        }
        jsonObject.put("status","500");
        try {
            PostcodeGrabber.responseValid(jsonObject);
        } catch (Exception err){
            assertEquals("500 Server Error",err.getMessage());
        }
    }

    @Test
    void getAttribute() {
        try {
            assertEquals("England", PostcodeGrabber.getAttribute("CB30FA","country"));
            assertEquals("East of England", PostcodeGrabber.getAttribute("CB30FA","region"));
        } catch (Exception err){
            err.printStackTrace();
        }
    }

    @Test
    void validatePostcode() {
        try {
            assertTrue(PostcodeGrabber.validatePostcode("CB30FA"));
            assertFalse(PostcodeGrabber.validatePostcode("ThisIsNotAPostCode"));
        } catch (Exception err){
            err.printStackTrace();
        }
    }
}