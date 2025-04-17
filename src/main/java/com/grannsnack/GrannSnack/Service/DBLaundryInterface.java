package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DBLaundryInterface extends JpaRepository<Booking, Long> {

    List<Booking> findByWeekAndYear(int week, int year);

    List<Booking> findByUserIdAndWeekAndYear(int userId, int week, int year);

    List<Booking> findByDayAndTimeSlotAndWeekAndYear(int day, int timeSlot, int week, int year);
}