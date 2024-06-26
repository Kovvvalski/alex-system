package by.kovalski.alexsystem.controller;

import by.kovalski.alexsystem.controller.form.admin.CourseForm;
import by.kovalski.alexsystem.controller.form.admin.GroupForm;
import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.service.CourseService;
import by.kovalski.alexsystem.service.GroupService;
import by.kovalski.alexsystem.service.LecturerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static by.kovalski.alexsystem.controller.util.Attributes.*;
import static by.kovalski.alexsystem.controller.util.Pages.*;

import java.util.List;

@Controller
public class AdminController {
  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  private final CourseService courseService;
  private final GroupService groupService;
  private final LecturerService lecturerService;

  @Autowired
  public AdminController(CourseService courseService, GroupService groupService, LecturerService lecturerService) {
    this.courseService = courseService;
    this.groupService = groupService;
    this.lecturerService = lecturerService;
  }

  @GetMapping("/admin")
  public String admin_main() {
    return ADMIN_MAIN;
  }

  @GetMapping("/admin/courses")
  public String courses(Model model) {
    List<Course> courses = courseService.findAllActive();
    model.addAttribute(COURSES, courses);
    return ADMIN_COURSES;
  }

  @GetMapping("/admin/new_course")
  public String newCourse(Model model) {
    model.addAttribute(COURSE, new Course());
    return ADMIN_NEW_COURSE;
  }

  @PostMapping("/admin/new_course")
  public String saveNewCourse(@ModelAttribute(COURSE) Course course, RedirectAttributes redirectAttributes) {
    try {
      courseService.save(course);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/new_course";
    }
    return "redirect:/admin";
  }

  @GetMapping("admin/course/{name}")
  public String course(Model model, @PathVariable String name) {
    Course course;
    try {
      course = courseService.findByName(name);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }
    model.addAttribute(COURSE, course);
    model.addAttribute(COURSE_FORM, new CourseForm(course));
    return ADMIN_COURSE;
  }

  @PostMapping("/admin/course/update/{name}")
  public String updateCourse(@PathVariable String name,
                             @ModelAttribute(COURSE_FORM) CourseForm courseForm,
                             RedirectAttributes redirectAttributes) {
    try {
      System.out.println("getting");
      Course course = courseService.findByName(name);
      course.setStatus(courseForm.getStatus());
      course.setDescription(courseForm.getDescription());
      courseService.update(course);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/course/" + name;
  }

  @PostMapping("/admin/course/delete/{name}")
  public String deleteCourse(@PathVariable String name, RedirectAttributes redirectAttributes) {
    try {
      courseService.deleteByName(name);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/course/" + name;
    }
    return "redirect:/admin/courses";
  }

  @GetMapping("/admin/groups")
  public String groups(Model model) {
    model.addAttribute(GROUPS, groupService.findAllActive());
    return ADMIN_GROUPS;
  }

  @GetMapping("/admin/new_group")
  public String newGroup(Model model) {
    List<Course> courses = courseService.findAllActive();
    model.addAttribute(COURSES, courses);
    model.addAttribute(GROUP, new Group());
    return ADMIN_NEW_GROUP;
  }

  @PostMapping("/admin/new_group")
  public String saveNewGroup(@ModelAttribute(GROUP) Group group, RedirectAttributes redirectAttributes) {
    try {
      groupService.save(group);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/new_group";
    }
    return "redirect:/admin";
  }

  @GetMapping("admin/group/{name}")
  public String group(Model model, @PathVariable String name) {
    Group group;
    try {
      group = groupService.findByName(name);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }

    List<Course> courses = courseService.findAll();
    if (!courses.contains(group.getCourse())) {
      courses.add(group.getCourse());
    }

    model.addAttribute(GROUP, group);
    model.addAttribute(GROUP_FORM, new GroupForm(group));
    model.addAttribute(COURSES, courses);
    return ADMIN_GROUP;
  }

  @PostMapping("/admin/group/update/{name}")
  public String updateGroup(@PathVariable String name, @ModelAttribute(GROUP_FORM) GroupForm groupForm,
                            RedirectAttributes redirectAttributes) {
    try {
      Group group = groupService.findByName(name);
      group.setCourse(groupForm.getCourse());
      group.setStatus(groupForm.getStatus());
      groupService.update(group);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/group/" + name;
  }

  @PostMapping("/admin/group/delete/{name}")
  public String deleteGroup(@PathVariable String name, RedirectAttributes redirectAttributes) {
    try {
      groupService.deleteByName(name);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/group/" + name;
    }
    return "redirect:/admin/groups";
  }

  @GetMapping("/admin/lecturers")
  public String lecturers(Model model) {
    model.addAttribute(LECTURERS, lecturerService.findAllActive());
    return ADMIN_LECTURERS;
  }

  @GetMapping("/admin/lecturer/{id}")
  public String lecturer(Model model, @PathVariable Long id) {
    Lecturer lecturer;
    try {
      lecturer = lecturerService.findById(id);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }
    model.addAttribute(COURSES, courseService.findAllActive());
    model.addAttribute(LECTURER, lecturer);
    return ADMIN_LECTURER;
  }

  @GetMapping("/admin/new_lecturer")
  public String newLecturer(Model model) {
    model.addAttribute(LECTURER, new Lecturer());
    model.addAttribute(COURSES, courseService.findAllActive());
    return ADMIN_NEW_LECTURER;
  }

  @PostMapping("/admin/lecturer/update/{id}")
  public String updateLecturer(@ModelAttribute(LECTURER) Lecturer lecturer, @PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
    lecturer.setId(id);
    try {
      lecturerService.update(lecturer);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/lecturer/" + id;
  }

  @PostMapping("/admin/lecturer/delete/{id}")
  public String deleteLecturer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      lecturerService.deleteById(id);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/lecturer/" + id;
    }
    return "redirect:/admin/lecturers";
  }

  @PostMapping("/admin/new_lecturer")
  public String saveNewLecturer(@ModelAttribute(LECTURER) Lecturer lecturer, RedirectAttributes redirectAttributes) {
    try {
      lecturerService.save(lecturer);
    } catch (ServiceException e) {
      logger.warn(e.getMessage());
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/new_lecturer";
    }
    return "redirect:/admin";
  }
}
