package com.degtyaruk.university.controller;

import com.degtyaruk.university.model.dto.FacultyDto;
import com.degtyaruk.university.model.mapper.FacultyDtoMapper;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.FacultyService;
import com.degtyaruk.university.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final FacultyDtoMapper facultyDtoMapper;

    @Autowired
    public FacultyController(FacultyService facultyService, CourseService courseService, GroupService groupService, FacultyDtoMapper facultyDtoMapper) {
        this.facultyService = facultyService;
        this.courseService = courseService;
        this.groupService = groupService;
        this.facultyDtoMapper = facultyDtoMapper;
    }

    @GetMapping
    public String getAllFaculties(Model model) {
        model.addAttribute("faculties", facultyService.getAll());
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("groups", groupService.getAll());
        return "faculty/faculties";
    }

    @GetMapping("/{id}")
    public String getFacultyById(@PathVariable int id, Model model) {
        model.addAttribute("faculty", facultyService.getById(id));
        return "faculty/faculty";
    }

    @PostMapping("/add")
    public String addFaculty(@ModelAttribute("faculty") FacultyDto facultyDto) {
        facultyService.add(facultyDtoMapper.toEntity(facultyDto));
        return "redirect:/faculties";
    }

    @GetMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable(value = "id") Integer id) {
        facultyService.removeById(id);
        return "redirect:/faculties";
    }
}

