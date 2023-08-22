package com.degtyaruk.university.controller;



import com.degtyaruk.university.model.dto.LectureDto;
import com.degtyaruk.university.model.mapper.LectureDtoMapper;
import com.degtyaruk.university.service.ClassroomService;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.GroupService;
import com.degtyaruk.university.service.LectureService;
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
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final ProfessorService professorService;
    private final ClassroomService classroomService;
    private final LectureDtoMapper lectureDtoMapper;

    @Autowired
    public LectureController(LectureService lectureService, CourseService courseService, GroupService groupService,
                             ProfessorService professorService, ClassroomService classroomService, LectureDtoMapper lectureDtoMapper) {
        this.lectureService = lectureService;
        this.courseService = courseService;
        this.groupService = groupService;
        this.professorService = professorService;
        this.classroomService = classroomService;
        this.lectureDtoMapper = lectureDtoMapper;
    }

    @GetMapping
    public String getAllLectures(Model model) {
        model.addAttribute("lectures", lectureService.getAll());
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("classrooms", classroomService.getAll());
        model.addAttribute("professors", professorService.getAll());
        return "lecture/lectures";
    }

    @GetMapping("/{id}")
    public String getLectureById(@PathVariable int id, Model model) {
        model.addAttribute("lecture", lectureService.getById(id));
        return "lecture/lecture";
    }

    @PostMapping("/add")
    public String addLecture(@ModelAttribute("lecture") LectureDto lectureDto) {
        lectureService.add(lectureDtoMapper.toEntity(lectureDto));
        return "redirect:/lectures";
    }

    @GetMapping("/delete/{id}")
    public String deleteLecture(@PathVariable(value = "id") Integer id) {
        lectureService.removeById(id);
        return "redirect:/lectures";
    }
}

