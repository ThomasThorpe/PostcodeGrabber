package com.company;

import static com.company.PostcodeGrabber.*;
import static com.company.PostcodePrinter.printInfo;

class Main {
    public static void main(String[] args) {
        String postcode = args[0];
        boolean validPostcode = false;

        //Validate postcode, if invalid print error message
        try {
            validPostcode = validatePostcode(postcode);
        } catch (Exception err){
            err.printStackTrace();
        }

        if(validPostcode){
            String[] nearest;
            nearest = getNearest(postcode);
            String[] nearestCountry = new String[3];
            String[] nearestRegion = new String[3];
            for (int i = 0; i < nearest.length; i++){
                try {
                    nearestCountry[i] = getAttribute(nearest[i],"country");
                    nearestRegion[i] = getAttribute(nearest[i],"region");
                } catch (Exception err){
                    err.printStackTrace();
                }
            }
            printInfo(nearest,nearestCountry,nearestRegion);
        }
    }
}
