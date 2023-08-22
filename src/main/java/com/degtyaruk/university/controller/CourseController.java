package com.degtyaruk.university.controller;

import com.degtyaruk.university.model.dto.CourseDto;
import com.degtyaruk.university.model.mapper.CourseDtoMapper;
import com.degtyaruk.university.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseDtoMapper courseDtoMapper;

    @Autowired
    public CourseController(CourseService courseService, CourseDtoMapper courseDtoMapper) {
        this.courseService = courseService;
        this.courseDtoMapper = courseDtoMapper;
    }

    @GetMapping
    public String getAllCourses(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "course/courses";
    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable int id, Model model) {
        model.addAttribute("course", courseService.getById(id));
        return "course/course";
    }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute("course") CourseDto courseDto) {
        courseService.add(courseDtoMapper.toEntity(courseDto));
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable(value = "id") Integer id) {
        courseService.removeById(id);
        return "redirect:/courses";
    }
}

