package com.degtyaruk.university.controller;

import com.degtyaruk.university.model.dto.ClassroomDto;
import com.degtyaruk.university.model.mapper.ClassroomDtoMapper;
import com.degtyaruk.university.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;
    private final ClassroomDtoMapper classroomDtoMapper;

    @Autowired
    public ClassroomController(ClassroomService classroomService, ClassroomDtoMapper classroomDtoMapper) {
        this.classroomService = classroomService;
        this.classroomDtoMapper = classroomDtoMapper;
    }

    @GetMapping
    public String getAllClassrooms(Model model) {
        model.addAttribute("classrooms", classroomService.getAll());
        return "classroom/classrooms";
    }

    @GetMapping("/{id}")
    public String getClassroomById(@PathVariable("id") int id, Model model) {
        model.addAttribute("classroom", classroomService.getById(id));
        return "classroom/classroom";
    }

    @PostMapping("/add")
    public String addClassroom(@ModelAttribute("classroom") ClassroomDto classroomDto) {
        classroomService.add(classroomDtoMapper.toEntity(classroomDto));
        return "redirect:/classrooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteClassroom(@PathVariable(value = "id") Integer id) {
        classroomService.removeById(id);
        return "redirect:/classrooms";
    }

    @GetMapping("/edit/{id}")
    public String editClassroom(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("classroom", classroomService.getById(id));
        return "classroom/editform";
    }

    @PatchMapping("/{id}")
    public String updateClassroom(@ModelAttribute("classroom") ClassroomDto classroomDto) {
        classroomService.update(classroomDtoMapper.toEntity(classroomDto));
        return "redirect:/classrooms";
    }
}

