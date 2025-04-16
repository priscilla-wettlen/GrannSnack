package com.grannsnack.GrannSnack.Model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a temporary fix to make sure the frontend functionality is there without a db infusion.
 */
@Service
public class LaundryManager {

    private LaundryTime laundryTime;

    List<LaundryTime> laundryTimes = new ArrayList<>();

    public boolean addLaundryTime(int timeSlot, String laundryRoom, MyUser user) {
        boolean success = false;
        LaundryTime newTime = new LaundryTime(timeSlot, laundryRoom, user.getApartmentCode());
        laundryTimes.add(newTime);
        return laundryTimes.contains(newTime);
    }

    public List<LaundryTime> getTakenLaundryTimes() {
        return laundryTimes;
    }

    public boolean removeLaundryTime(int timeSlot, String laundryRoom) {
        boolean success = false;
        for (LaundryTime laundryTime : laundryTimes) {
            if(laundryTime.getTimeSlot() == timeSlot && laundryTime.getLaundryRoom().equals(laundryRoom)) {
                laundryTimes.remove(laundryTime);
                success = true;
            }
        }
        return success;
    }

    public class LaundryTime {

        /**
         * An int representing which timeslot is represented. Timeslots goes as follows:
         * 1: 07:00 - 10:00
         * 2: 10:00 - 13:00
         * 3: 13:00 - 16:00
         * 4: 16:00 - 19:00
         * 5: 19:00 - 21:00
         */
        int timeSlot;

        /**
         * Tells which laundry room is being booked by this timeslot.
         */
        String laundryRoom;

        /**
         * Tells which apartment has booked this time.
         */
        String apartmentCode;

        public LaundryTime(int timeSlot, String laundryRoom, String apartmentCode) {
            this.timeSlot = timeSlot;
            this.laundryRoom = laundryRoom;
            this.apartmentCode = apartmentCode;
        }

        public int getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(int timeSlot) {
            this.timeSlot = timeSlot;
        }

        public String getLaundryRoom() {
            return laundryRoom;
        }

        public void setLaundryRoom(String laundryRoom) {
            this.laundryRoom = laundryRoom;
        }

        public String getApartmentCode() {
            return apartmentCode;
        }

        public void setApartmentCode(String apartmentCode) {
            this.apartmentCode = apartmentCode;
        }

        public String toString() {
            return laundryRoom + "_" + apartmentCode + "_" + timeSlot;
        }
    }
}
