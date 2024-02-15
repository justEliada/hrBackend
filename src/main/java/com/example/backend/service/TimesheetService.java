package com.example.backend.service;

import com.example.backend.entity.Timesheet;
import com.example.backend.repository.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;

    @Autowired
    public TimesheetService(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
    }

    public Optional<Timesheet> findById(Long id) {
        return timesheetRepository.findById(id);
    }

    public Timesheet save(Timesheet timesheet) {
        return timesheetRepository.save(timesheet);
    }

    public void deleteById(Long id) {
        timesheetRepository.deleteById(id);
    }

    public Timesheet createTimesheet(Timesheet timesheet) {
        if (timesheetRepository.existsByUserIdAndFromDateAndToDate(timesheet.getUser().getId(), timesheet.getFromDate(), timesheet.getToDate())) {
            throw new IllegalStateException("Timesheet for these dates already exists.");
        }
        return timesheetRepository.save(timesheet);
    }


}
