package by.kovalski.alexsystem.controller;

import by.kovalski.alexsystem.dto.*;
import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.entity.Lesson;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.service.CourseService;
import by.kovalski.alexsystem.service.GroupService;
import by.kovalski.alexsystem.service.LecturerService;
import by.kovalski.alexsystem.service.LessonService;
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
  private final LessonService lessonService;

  @Autowired
  public AdminController(CourseService courseService, GroupService groupService,
                         LecturerService lecturerService, LessonService lessonService) {
    this.courseService = courseService;
    this.groupService = groupService;
    this.lecturerService = lecturerService;
    this.lessonService = lessonService;
  }

  @GetMapping("/admin")
  public String adminMain() {
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
    model.addAttribute(COURSE_DTO, new CourseDTO());
    return ADMIN_NEW_COURSE;
  }

  @PostMapping("/admin/new_course")
  public String saveNewCourse(@ModelAttribute(COURSE_DTO) CourseDTO courseDTO, RedirectAttributes redirectAttributes) {

    try {
      courseService.save(courseService.getFromDTO(courseDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
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
      logger.warn(e.getMessage(), e);
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }
    model.addAttribute(COURSE, course);
    model.addAttribute(COURSE_DTO, new CourseDTO(course));
    return ADMIN_COURSE;
  }

  @PostMapping("/admin/course/update/{name}")
  public String updateCourse(@PathVariable String name,
                             @ModelAttribute(COURSE_DTO) CourseDTO courseDTO,
                             RedirectAttributes redirectAttributes) {
    try {
      courseService.update(courseService.getFromDTO(courseDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/course/" + name;
  }

  @PostMapping("/admin/course/delete/{name}")
  public String deleteCourse(@PathVariable String name, RedirectAttributes redirectAttributes) {
    try {
      courseService.deleteByName(name);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
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
    model.addAttribute(GROUP_DTO, new GroupDTO());
    return ADMIN_NEW_GROUP;
  }

  @PostMapping("/admin/new_group")
  public String saveNewGroup(@ModelAttribute(GROUP_DTO) GroupDTO groupDTO, RedirectAttributes redirectAttributes) {
    try {
      groupService.save(groupService.getFromDTO(groupDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
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
      logger.warn(e.getMessage(), e);
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }

    List<Course> courses = courseService.findAll();
    if (!courses.contains(group.getCourse())) {
      courses.add(group.getCourse());
    }

    model.addAttribute(GROUP, group);
    model.addAttribute(GROUP_DTO, new GroupDTO(group));
    model.addAttribute(COURSES, courses);
    return ADMIN_GROUP;
  }

  @PostMapping("/admin/group/update/{name}")
  public String updateGroup(@PathVariable String name, @ModelAttribute(GROUP_DTO) GroupDTO groupDTO,
                            RedirectAttributes redirectAttributes) {
    try {
      groupService.update(groupService.getFromDTO(groupDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/group/" + name;
  }

  @PostMapping("/admin/group/delete/{name}")
  public String deleteGroup(@PathVariable String name, RedirectAttributes redirectAttributes) {
    try {
      groupService.deleteByName(name);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
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
      logger.warn(e.getMessage(), e);
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }
    model.addAttribute(COURSES, courseService.findAllActive());
    model.addAttribute(LECTURER, lecturer);
    model.addAttribute(LECTURER_DTO, new LecturerDTO(lecturer));
    return ADMIN_LECTURER;
  }

  @GetMapping("/admin/new_lecturer")
  public String newLecturer(Model model) {
    model.addAttribute(LECTURER_DTO, new LecturerDTO());
    model.addAttribute(COURSES, courseService.findAllActive());
    return ADMIN_NEW_LECTURER;
  }

  @PostMapping("/admin/new_lecturer")
  public String saveNewLecturer(@ModelAttribute(LECTURER_DTO) LecturerDTO lecturerDTO, RedirectAttributes redirectAttributes) {
    try {
      lecturerService.save(lecturerService.getFromDTO(lecturerDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/new_lecturer";
    }
    return "redirect:/admin";
  }

  @PostMapping("/admin/lecturer/update/{id}")
  public String updateLecturer(@ModelAttribute(LECTURER_DTO) LecturerDTO lecturerDTO, @PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
    try {
      lecturerService.update(lecturerService.getFromDTO(lecturerDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/lecturer/" + id;
  }

  @PostMapping("/admin/lecturer/delete/{id}")
  public String deleteLecturer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      lecturerService.deleteById(id);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/lecturer/" + id;
    }
    return "redirect:/admin/lecturers";
  }

  @GetMapping("/admin/lessons")
  public String lessons(Model model) {
    model.addAttribute(LESSONS, lessonService.findAll());
    return ADMIN_LESSONS;
  }

  @GetMapping("/admin/lesson/{id}")
  public String lesson(@PathVariable long id, Model model) {
    try {
      Lesson lesson = lessonService.findById(id);
      model.addAttribute(LESSON_DTO, new LessonDTO(lesson));
      model.addAttribute(GROUPS, groupService.findAllActive());
      model.addAttribute(LECTURERS, lecturerService.findAllActive());
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }
    return ADMIN_LESSON;
  }

  @PostMapping("/admin/lesson/update/{id}")
  public String updateLesson(@PathVariable long id, RedirectAttributes redirectAttributes, @ModelAttribute LessonDTO lessonDTO) {
    lessonDTO.setId(id);
    try {
      lessonService.update(lessonService.getFromDTO(lessonDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/lesson/" + id;
  }

  @GetMapping("/admin/new_lesson")
  public String newLesson(Model model) {
    model.addAttribute(LESSON_DTO, new LessonDTO());
    model.addAttribute(GROUPS, groupService.findAllActive());
    model.addAttribute(LECTURERS, lecturerService.findAllActive());
    return ADMIN_NEW_LESSON;
  }

  @PostMapping("/admin/new_lesson")
  public String saveNewLesson(@ModelAttribute(LESSON_DTO) LessonDTO lessonDTO, RedirectAttributes redirectAttributes) {
    try {
      lessonService.save(lessonService.getFromDTO(lessonDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/new_lesson";
    }
    return "redirect:/admin";
  }

  @PostMapping("/admin/lesson/delete/{id}")
  public String deleteLesson(@PathVariable long id, RedirectAttributes redirectAttributes) {
    try {
      lessonService.deleteById(id);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/lesson/" + id;
    }
    return "redirect:/admin";
  }

  @PostMapping("/admin/delete_group_lessons/{groupName}")
  public String deleteLessonsFromGroup(@PathVariable String groupName, RedirectAttributes redirectAttributes) {
    try {
      lessonService.deleteAllByGroupName(groupName);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/group/" + groupName;
  }

  @PostMapping("/admin/delete_lecturer_lessons/{lecturerId}")
  public String deleteLessonsFromLecturer(@PathVariable Long lecturerId, RedirectAttributes redirectAttributes) {
    try {
      lessonService.deleteAllByLecturerId(lecturerId);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/lecturer/" + lecturerId;
  }

  @GetMapping("/admin/schedule")
  public String schedule(Model model) {
    model.addAttribute(SCHEDULE_DTO, new ScheduleDTO());
    model.addAttribute(GROUPS, groupService.findAllActive());
    model.addAttribute(LECTURERS, lecturerService.findAllActive());
    return ADMIN_SCHEDULE;
  }

  @PostMapping("admin/schedule")
  public String createSchedule(RedirectAttributes redirectAttributes,
                               @ModelAttribute(SCHEDULE_DTO) ScheduleDTO scheduleDTO) {
    try {
      lessonService.createLessonsByScheduleDTO(scheduleDTO);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/schedule";
    }
    return "redirect:/admin";
  }
}
