package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Booking;
import com.grannsnack.GrannSnack.Model.TimeSlots;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DBLaundryInterface extends JpaRepository<Booking, Integer> {

    List<Booking> findByDate(LocalDate date);
    List<Booking> findByUserId(Integer userId);

}