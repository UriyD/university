package com.degtyaruk.university.controller;

import com.degtyaruk.university.model.dto.GroupDto;
import com.degtyaruk.university.model.mapper.GroupDtoMapper;
import com.degtyaruk.university.service.GroupService;
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
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    private final StudentService studentService;
    private final GroupDtoMapper groupDtoMapper;

    @Autowired
    public GroupController(GroupService groupService, StudentService studentService, GroupDtoMapper groupDtoMapper) {
        this.groupService = groupService;
        this.studentService = studentService;
        this.groupDtoMapper = groupDtoMapper;
    }

    @GetMapping
    public String getAllGroups(Model model) {
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("students", studentService.getAll());
        return "group/groups";
    }

    @GetMapping("/{id}")
    public String getGroupById(@PathVariable int id, Model model) {
        model.addAttribute("group", groupService.getById(id));
        return "group/group";
    }

    @PostMapping("/add")
    public String addGroup(@ModelAttribute("group") GroupDto groupDto) {
        groupService.add(groupDtoMapper.toEntity(groupDto));
        return "redirect:/groups";
    }

    @GetMapping("/delete/{id}")
    public String deleteGroup(@PathVariable(value = "id") Integer id) {
        groupService.removeById(id);
        return "redirect:/groups";
    }
}

