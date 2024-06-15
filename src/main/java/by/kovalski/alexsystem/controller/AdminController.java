package by.kovalski.alexsystem.controller;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.service.CourseService;
import by.kovalski.alexsystem.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static by.kovalski.alexsystem.controller.util.Attributes.*;
import static by.kovalski.alexsystem.controller.util.Pages.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  private final CourseService courseService;
  private final GroupService groupService;

  @Autowired
  public AdminController(CourseService courseService, GroupService groupService) {
    this.courseService = courseService;
    this.groupService = groupService;
  }

  @GetMapping("/admin/courses")
  public String courses(Model model) {
    List<Course> courses;
    try {
      courses = courseService.findAll();
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      courses = new ArrayList<>();
    }
    model.addAttribute(COURSES, courses);
    model.addAttribute(COURSE, new Course());
    return ADMIN_COURSES;
  }

  @PostMapping("/admin/courses")
  public String saveNewCourse(@ModelAttribute Course course){
    courseService.save(course);
    return "redirect:/admin/courses";
  }
}
