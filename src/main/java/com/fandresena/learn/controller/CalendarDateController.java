package com.fandresena.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.service.CalendarDateService;

@RestController
@RequestMapping(path = "/")
public class CalendarDateController {
    private CalendarDateService calendarDateService = new CalendarDateService();

    @Autowired
    public CalendarDateController(CalendarDateService calendarDateService) {
        this.calendarDateService = calendarDateService;
    }
    @GetMapping(path = "/calendar",produces = "application/json")
    public ResponseEntity<String> getCalendarDate(@RequestParam("year") int year, @RequestParam("month") int month) {

        if (year < 0 || month < 1 || month > 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid year or month");
        }
        String calendarData = calendarDateService.createCalendarDate(year, month).toString();

        return ResponseEntity.ok(calendarData);
    }
}

// Test avec curl : curl -X GET "http://localhost:8080/calendar?year=2022&month=1" -H "accept: application/json"