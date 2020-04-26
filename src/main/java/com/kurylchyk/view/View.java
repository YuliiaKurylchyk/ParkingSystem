package com.kurylchyk.view;

import java.util.LinkedHashMap;
import java.util.Map;

public class View {

    private Map<Integer,String> menu = new LinkedHashMap<>();

    {
        menu.put(1,"Show all active tickets on the parking slot");
        menu.put(2,"Show all past tickets");
        menu.put(3,"Add new vehicle");
        menu.put(4,"Remove the vehicle");
        menu.put(5,"Exit");
    }


    public void showMenu(){
        System.out.println("Choose the option");
        for(Map.Entry<Integer,String> entry: menu.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }
}
