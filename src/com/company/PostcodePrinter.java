package com.company;

class PostcodePrinter {
    public static void printInfo(String[] postcodes, String[] countries, String[] regions){
        System.out.println("Postcode | Countries | Regions");
        for(int i = 0; i < postcodes.length; i++){
            System.out.println(postcodes[i] + "|" + countries[i] + "|" + regions[i]);
        }
    }
}
