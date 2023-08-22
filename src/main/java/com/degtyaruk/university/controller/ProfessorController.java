package com.degtyaruk.university.controller;

import com.degtyaruk.university.model.dto.ProfessorDto;
import com.degtyaruk.university.model.mapper.ProfessorDtoMapper;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorService professorService;
    private final CourseService courseService;
    private final ProfessorDtoMapper professorDtoMapper;

    @Autowired
    public ProfessorController(ProfessorService professorService, CourseService courseService, ProfessorDtoMapper professorDtoMapper) {
        this.professorService = professorService;
        this.courseService = courseService;
        this.professorDtoMapper = professorDtoMapper;
    }

    @GetMapping
    public String getAllTeachers(Model model) {
        model.addAttribute("professors", professorService.getAll());
        model.addAttribute("courses", courseService.getAll());
        return "professor/professors";
    }

    @GetMapping("/{id}")
    public String getTeacherById(@PathVariable int id, Model model) {
        model.addAttribute("professor", professorService.getById(id));
        return "professor/professor";
    }

    @PostMapping("/add")
    public String addTeacher(@ModelAttribute("professor") ProfessorDto professorDto) {
        professorService.add(professorDtoMapper.toEntity(professorDto));
        return "redirect:/professors";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable(value = "id") Integer id) {
        professorService.removeById(id);
        return "redirect:/professors";
    }
}

