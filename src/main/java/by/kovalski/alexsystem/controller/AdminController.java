package by.kovalski.alexsystem.controller;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.service.CourseService;
import by.kovalski.alexsystem.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @PostMapping("/admin/new_course")
  public String saveNewCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes) {
    try {
      courseService.save(course);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/courses";
  }

  @GetMapping("admin/course/{name}")
  public String course(Model model, @PathVariable String name){
    Course course;
    try {
      course = courseService.findByName(name);
    } catch (ServiceException e){
      return ERROR_PAGE;
    }
    model.addAttribute(COURSE, course);
    return ADMIN_COURSE;
  }

  @PostMapping("/admin/courses/delete/{name}")
  public String deleteCourse(@PathVariable String name) {
    try {
      courseService.deleteByName(name);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
    }
    return "redirect:/admin/courses";
  }

  @GetMapping("/admin/groups")
  public String groups(Model model){
    List<Group> groups;
    List<Course> courses;
    try {
      groups = groupService.findAll();
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      groups = new ArrayList<>();
    }
    try {
      courses = courseService.findAll();
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      courses = new ArrayList<>();
    }

    model.addAttribute(GROUPS, groups);
    model.addAttribute(GROUP, new Group());
    model.addAttribute(COURSES, courses);
    return ADMIN_GROUPS;
  }

  @PostMapping("/admin/new_group")
  public String saveNewGroup(@ModelAttribute Group group, RedirectAttributes redirectAttributes) {
    try {
      groupService.save(group);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/groups";
  }

  @GetMapping("admin/group/{name}")
  public String group(Model model, @PathVariable String name){
    Group group;
    try {
      group = groupService.findByName(name);
    } catch (ServiceException e){
      return ERROR_PAGE;
    }
    model.addAttribute(GROUP, group);
    return ADMIN_GROUP;
  }
}
