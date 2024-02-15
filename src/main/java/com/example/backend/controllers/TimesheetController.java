package com.example.backend.controllers;

import com.example.backend.entity.Timesheet;
import com.example.backend.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {

    private final TimesheetService timesheetService;

    @Autowired
    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @GetMapping
    public ResponseEntity<List<Timesheet>> getAllTimesheets() {
        return ResponseEntity.ok(timesheetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> getTimesheetById(@PathVariable Long id) {
        return timesheetService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Timesheet> createTimesheet(@RequestBody Timesheet timesheet) {
        if (timesheet.getUser() == null || timesheet.getFromDate() == null || timesheet.getToDate() == null) {
            // Assuming you want to return a bad request status if any of these fields are null
            return ResponseEntity.badRequest().build();
        }
        Timesheet savedTimesheet = timesheetService.save(timesheet);
        if (savedTimesheet == null) {
            // Assuming you want to return a 'unprocessable entity' status if the timesheet is not saved
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(savedTimesheet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Timesheet> updateTimesheet(@PathVariable Long id, @RequestBody Timesheet timesheet) {
        if (!timesheetService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        timesheet.setId(id);
        return ResponseEntity.ok(timesheetService.save(timesheet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable Long id) {
        if (!timesheetService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        timesheetService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
