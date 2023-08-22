package com.degtyaruk.university.controller;

import com.degtyaruk.university.model.Timetable;
import com.degtyaruk.university.model.dto.TimetableDto;
import com.degtyaruk.university.model.mapper.TimetableDtoMapper;
import com.degtyaruk.university.service.LectureService;
import com.degtyaruk.university.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/timetables")
public class TimetableController {

    private final TimetableService timetableService;
    private final LectureService lectureService;
    private final TimetableDtoMapper timetableDtoMapper;

    @Autowired
    public TimetableController(TimetableService timetableService, LectureService lectureService, TimetableDtoMapper timetableDtoMapper) {
        this.timetableService = timetableService;
        this.lectureService = lectureService;
        this.timetableDtoMapper = timetableDtoMapper;
    }

    @GetMapping
    public String getAllTimetables(Model model) {
        model.addAttribute("timetables", timetableService.getAll());
        model.addAttribute("lectures", lectureService.getAll());
        return "timetable/timetables";
    }

    @GetMapping("/{id}")
    public String getTimetableById(@PathVariable int id, Model model) {
        model.addAttribute("timetable", timetableService.getById(id));
        return "timetable/timetable";
    }

    @PostMapping("/add")
    public String addTimetable(@ModelAttribute("timetable") TimetableDto timetableDto) {
        timetableService.add(timetableDtoMapper.toEntity(timetableDto));
        return "redirect:/timetables";
    }

    @GetMapping("/delete/{id}")
    public String deleteTimetable(@PathVariable(value = "id") Integer id) {
        timetableService.removeById(id);
        return "redirect:/timetables";
    }

    @GetMapping("/lectures/{id}")
    public String getLectures(@PathVariable(value = "id") Integer id, Model model) {
        Timetable timetable = timetableService.getById(id);
        model.addAttribute("lectures", timetable.getLectures());
        model.addAttribute("date", timetable.getDate());
        return "timetable/lectures";
    }
}

