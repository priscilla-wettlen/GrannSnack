package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.TimeSlots;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DBTimeSlotsInterface extends JpaRepository<TimeSlots, Integer> {

}
