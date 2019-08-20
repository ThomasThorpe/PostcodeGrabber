package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

class PostcodeGrabber {
    public static boolean responseValid(JSONObject jsonObject) throws Exception {
        String status = jsonObject.get("status").toString();
        switch (status) {
            case "200":
                return true;
            case "400":
                throw new Exception("400 Bad Request");
            case "404":
                throw new Exception("404 Not Found");
            case "500":
                throw new Exception("500 Server Error");
        }
        return false;
    }

    public static String[] getNearest(String postcode){
        Client client = ClientBuilder.newClient();
        String response = client.target("http://api.postcodes.io/postcodes/"+postcode+"/nearest").request(MediaType.APPLICATION_JSON).get(String.class);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        JSONArray jsonArray;
        String[] nearest = new String[3];
        try {
            jsonObject = (JSONObject) parser.parse(response);
            String result = jsonObject.get("result").toString();
            jsonArray = (JSONArray) parser.parse(result);
            for(int i = 0; i < 3; i++){
                JSONObject tmp;
                String foo = jsonArray.get(i).toString();
                tmp = (JSONObject) parser.parse(foo);
                String nearPostcode = tmp.get("postcode").toString();
                nearest[i] = nearPostcode;
            }
        } catch (ParseException err){
            System.out.println("Error parsing JSON string: " + err.toString());
        }
        return nearest;
    }

    public static String getAttribute(String postcode, String attribute) throws Exception{
        Client client = ClientBuilder.newClient();
        String response = client.target("http://api.postcodes.io/postcodes/"+postcode).request(MediaType.APPLICATION_JSON).get(String.class);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        String attributeValue;
        boolean b;

        try {
            jsonObject = (JSONObject) parser.parse(response);
        } catch (ParseException err){
            System.out.println("Error parsing JSON string: " + err.toString());
            throw new Exception("Error parsing JSON string: " + err.toString());
        }

        b = responseValid(jsonObject);
        if(b){
            String result = jsonObject.get("result").toString();
            try {
                jsonObject = (JSONObject) parser.parse(result);
            } catch (ParseException err){
                System.out.println("Error parsing JSON string: " + err.toString());
                throw new Exception("Error parsing JSON string: " + err.toString());
            }
            attributeValue = jsonObject.get(attribute).toString();
            return attributeValue;
        }
        return "";
    }

    public static boolean validatePostcode(String postcode) throws Exception{
        Client client = ClientBuilder.newClient();
        String response = client.target("http://api.postcodes.io/postcodes/"+postcode+"/validate").request(MediaType.APPLICATION_JSON).get(String.class);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        boolean b;

        try {
            jsonObject = (JSONObject) parser.parse(response);
        } catch (ParseException err){
            System.out.println("Error parsing JSON string: " + err.toString());
            throw new Exception("Error parsing JSON string: " + err.toString());
        }

        b = responseValid(jsonObject);

        return jsonObject.get("result").toString().equals("true") && b;
    }
}
