package com.degtyaruk.university.controller;

import com.degtyaruk.university.model.dto.StudentDto;
import com.degtyaruk.university.model.mapper.StudentDtoMapper;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final StudentDtoMapper studentDtoMapper;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService, StudentDtoMapper studentDtoMapper) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.studentDtoMapper = studentDtoMapper;
    }

    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.getAll());
        model.addAttribute("courses", courseService.getAll());
        return "student/students";
    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable int id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student/student";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute("student") StudentDto studentDto) {
        studentService.add(studentDtoMapper.toEntity(studentDto));
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(value = "id") Integer id) {
        studentService.removeById(id);
        return "redirect:/students";
    }
}

