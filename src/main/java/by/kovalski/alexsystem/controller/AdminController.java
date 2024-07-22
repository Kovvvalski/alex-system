package by.kovalski.alexsystem.controller;

import by.kovalski.alexsystem.dto.*;
import by.kovalski.alexsystem.entity.*;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.service.*;
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
  private final StudentService studentService;
  private final ParentService parentService;
  private final UserService userService;

  @Autowired
  public AdminController(CourseService courseService, GroupService groupService, LecturerService lecturerService,
                         LessonService lessonService, StudentService studentService, ParentService parentService,
                         UserService userService) {
    this.courseService = courseService;
    this.groupService = groupService;
    this.lecturerService = lecturerService;
    this.lessonService = lessonService;
    this.studentService = studentService;
    this.parentService = parentService;
    this.userService = userService;
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
      course = courseService.findById(name);
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
      courseService.deleteById(name);
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
      group = groupService.findById(name);
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
      groupService.deleteById(name);
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

  @GetMapping("admin/new_parent")
  public String newParent(Model model) {
    model.addAttribute(PARENT_DTO, new ParentDTO());
    return ADMIN_NEW_PARENT;
  }

  @PostMapping("admin/new_parent")
  public String saveNewParent(@ModelAttribute(PARENT_DTO) ParentDTO parentDTO, RedirectAttributes redirectAttributes) {
    try {
      parentService.save(parentService.getFromDTO(parentDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/new_parent";
    }
    return "redirect:/admin";
  }

  @GetMapping("admin/parent/{id}")
  public String parent(@PathVariable Long id, Model model) {
    Parent parent;
    try {
      parent = parentService.findById(id);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }
    model.addAttribute(PARENT, parent);
    model.addAttribute(PARENT_DTO, new ParentDTO(parent));
    return ADMIN_PARENT;
  }

  @GetMapping("admin/parents")
  public String parents(Model model) {
    model.addAttribute(PARENTS, parentService.findAllActive());
    return ADMIN_PARENTS;
  }

  @PostMapping("/admin/parent/update/{id}")
  public String updateParent(@ModelAttribute(PARENT_DTO) ParentDTO parentDTO, @PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
    try {
      parentService.update(parentService.getFromDTO(parentDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/parent/" + id;
  }

  @PostMapping("/admin/parent/delete/{id}")
  public String deleteParent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      parentService.deleteById(id);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/parent/" + id;
    }
    return "redirect:/admin/parents";
  }

  @GetMapping("admin/new_student")
  public String newStudent(Model model) {
    model.addAttribute(GROUPS, groupService.findAllActive());
    model.addAttribute(PARENTS, parentService.findAllActive());
    model.addAttribute(STUDENT_DTO, new StudentDTO());
    return ADMIN_NEW_STUDENT;
  }

  @PostMapping("admin/new_student")
  public String saveNewStudent(@ModelAttribute(STUDENT_DTO) StudentDTO studentDTO, RedirectAttributes redirectAttributes) {
    try {
      studentService.save(studentService.getFromDTO(studentDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/new_student";
    }
    return "redirect:/admin";
  }

  @GetMapping("admin/student/{id}")
  public String student(Model model, @PathVariable Long id) {
    Student student;
    try {
      student = studentService.findById(id);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }
    model.addAttribute(GROUPS, groupService.findAllActive());
    model.addAttribute(PARENTS, parentService.findAllActive());
    model.addAttribute(STUDENT_DTO, new StudentDTO(student));
    model.addAttribute(STUDENT, student);
    return ADMIN_STUDENT;
  }

  @GetMapping("admin/students")
  public String students(Model model) {
    model.addAttribute(STUDENTS, studentService.findAllActive());
    return ADMIN_STUDENTS;
  }

  @PostMapping("admin/student/update/{id}")
  public String updateStudent(@ModelAttribute(STUDENT_DTO) StudentDTO studentDTO, @PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
    try {
      studentService.update(studentService.getFromDTO(studentDTO));
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
    }
    return "redirect:/admin/student/" + id;
  }

  @PostMapping("admin/student/delete/{id}")
  public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      studentService.deleteById(id);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/student/" + id;
    }
    return "redirect:/admin/students";
  }

  @GetMapping("admin/new_user/{userType}")
  public String newUser(Model model, @PathVariable String userType) {
    UserDTO userDTO = new UserDTO();
    if (userType.equals(Role.STUDENT.name())) {
      model.addAttribute(ABSTRACT_PERSONS, studentService.findAllActive());
      userDTO.setRole(Role.STUDENT);
    } else if (userType.equals(Role.LECTURER.name())) {
      model.addAttribute(ABSTRACT_PERSONS, lecturerService.findAllActive());
      userDTO.setRole(Role.LECTURER);
    } else if (userType.equals(Role.ADMIN.name())) {
      model.addAttribute(ABSTRACT_PERSONS, List.of());
      userDTO.setRole(Role.ADMIN);
    } else {
      throw new IllegalArgumentException("Unresolved user-type " + userType);
    }
    model.addAttribute(USER_TYPE, userType);
    model.addAttribute(USER_DTO, userDTO);
    return ADMIN_NEW_USER;
  }

  @PostMapping("admin/new_user/{userType}")
  public String saveNewUser(@ModelAttribute(USER_DTO) UserDTO userDTO, RedirectAttributes redirectAttributes,
                            @PathVariable String userType) {
    try {
      User user = userService.getFromDTO(userDTO);
      if (userType.equals(Role.STUDENT.name())) {
        user.setData(studentService.findById(userDTO.getDataId()));
      } else if (userType.equals(Role.LECTURER.name())) {
        user.setData(lecturerService.findById(userDTO.getDataId()));
      } else if (userType.equals(Role.ADMIN.name())) {
        user.setData(null);
      } else {
        throw new IllegalArgumentException("Unresolved user-type " + userType);
      }
      userService.save(user);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/new_user/" + userType;
    }
    return "redirect:/admin";
  }

  @GetMapping("admin/user/{login}")
  public String user(Model model, @PathVariable String login) {
    User user;
    try {
      user = userService.findById(login);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      model.addAttribute(ERROR_MSG, e.getMessage());
      return ERROR_PAGE;
    }
    model.addAttribute(USER, user);
    model.addAttribute(ABSTRACT_PERSON, user.getData());
    return ADMIN_USER;
  }

  @GetMapping("admin/users")
  public String users(Model model) {
    model.addAttribute(USERS, userService.findAll());
    return ADMIN_USERS;
  }

  @PostMapping("admin/user/delete/{login}")
  public String deleteUser(@PathVariable String login, RedirectAttributes redirectAttributes) {
    try {
      userService.deleteById(login);
    } catch (ServiceException e) {
      logger.warn(e.getMessage(), e);
      redirectAttributes.addFlashAttribute(ERROR_MSG, e.getMessage());
      return "redirect:/admin/user/" + login;
    }
    return "redirect:/admin/users";
  }
}
