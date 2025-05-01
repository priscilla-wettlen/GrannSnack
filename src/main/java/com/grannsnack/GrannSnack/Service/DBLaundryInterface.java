package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DBLaundryInterface extends JpaRepository<Booking, Long> {

    List<Booking> findByMonthAndYear(int month, int year);

    List<Booking> findByUserIdAndMonthAndYear(int userId, int month, int year);

    List<Booking> findByDayAndTimeSlotAndMonthAndYear(int day, int timeSlot, int month, int year);

    int month(int month);
}