package com.example.backend.repository;

import com.example.backend.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findByUserId(Long userId);
    boolean existsByUserIdAndFromDateAndToDate(Long userId, LocalDate fromDate, LocalDate toDate);
}