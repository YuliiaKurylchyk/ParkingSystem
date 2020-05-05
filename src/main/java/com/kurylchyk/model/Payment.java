package com.kurylchyk.model;

import com.kurylchyk.model.parkingSlots.SizeOfSlot;

public class Payment {

    public  static double calculatePrice(long hours, SizeOfSlot sizeOfSlot){

         double priceForHour = 0.0;
         switch (sizeOfSlot) {
             case SMALL:
                 priceForHour = 15;
                 break;
             case MEDIUM:
                 priceForHour = 25;
                 break;
             case LARGE:
                 priceForHour = 35;
                break;
         }
         return (priceForHour*=hours);
    }
}
