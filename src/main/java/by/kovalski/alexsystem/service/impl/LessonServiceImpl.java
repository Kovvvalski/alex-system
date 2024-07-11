package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.dto.LessonDTO;
import by.kovalski.alexsystem.dto.ScheduleDTO;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.entity.Lesson;
import by.kovalski.alexsystem.entity.Student;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.GroupRepository;
import by.kovalski.alexsystem.repository.LecturerRepository;
import by.kovalski.alexsystem.repository.LessonRepository;
import by.kovalski.alexsystem.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LessonServiceImpl implements LessonService {

  private final LessonRepository lessonRepository;
  private final LecturerRepository lecturerRepository;
  private final GroupRepository groupRepository;

  @Autowired
  public LessonServiceImpl(LessonRepository lessonRepository, LecturerRepository lecturerRepository,
                           GroupRepository groupRepository) {
    this.lessonRepository = lessonRepository;
    this.lecturerRepository = lecturerRepository;
    this.groupRepository = groupRepository;
  }

  @Override
  public void save(Lesson lesson) throws ServiceException {
    validate(lesson);
    lessonRepository.saveAndFlush(lesson);
  }

  @Override
  public void update(Lesson lesson) throws ServiceException {
    if (!lessonRepository.existsById(lesson.getId())) {
      throw new ServiceException("No lesson with id " + lesson.getId());
    }
    validate(lesson);
    save(lesson);
  }

  @Override
  public void deleteById(Long id) {
    lessonRepository.deleteById(id);
  }

  @Override
  public void deleteAllByGroupName(String groupName) throws ServiceException {
    if (!groupRepository.existsById(groupName)) {
      throw new ServiceException("No group with name " + groupName);
    }
    lessonRepository.deleteAllByGroupName(groupName);
  }

  @Override
  public void deleteAllByLecturerId(Long lecturerId) throws ServiceException {
    if (!lecturerRepository.existsById(lecturerId)) {
      throw new ServiceException("No lecturer with id " + lecturerId);
    }
    lessonRepository.deleteAllByLecturerId(lecturerId);
  }

  @Override
  public List<Lesson> findAll() {
    return lessonRepository.findAll();
  }

  @Override
  public List<Lesson> findAllNotStarted() {
    return lessonRepository.findAllByBeginAfter(LocalDateTime.now());
  }

  @Override
  public Lesson findById(Long id) throws ServiceException {
    return lessonRepository.findById(id).orElseThrow(() -> new ServiceException("No lesson with id " + id));
  }

  @Override
  public List<Lesson> findByGroup(Group group) {
    return lessonRepository.findAllByGroup(group);
  }

  @Override
  public List<Lesson> findByLecturer(Lecturer lecturer) {
    return lessonRepository.findAllByLecturer(lecturer);
  }

  @Override
  public Lesson getFromDTO(LessonDTO lessonDTO) throws ServiceException {
    Group group = groupRepository.findById(lessonDTO.getGroupName()).
            orElseThrow(() -> new ServiceException("No group with name " + lessonDTO.getGroupName()));

    Lecturer lecturer = lecturerRepository.findById(lessonDTO.getLecturerId()).
            orElseThrow(() -> new ServiceException("No lecturer with id " + lessonDTO.getLecturerId()));

    if (lessonDTO.getDuration() > 600) {
      throw new ServiceException("You can't create lesson with duration more than 10 hours");
    }

    return new Lesson(lessonDTO.getId(), group, lessonDTO.getBegin(), lessonDTO.getBegin().plusMinutes(lessonDTO.getDuration()),
            lecturer, lessonDTO.getHomeTask());
  }

  @Override
  public void createLessonsByScheduleDTO(ScheduleDTO scheduleDTO) throws ServiceException {
    Group group = groupRepository.findById(scheduleDTO.getGroupName()).
            orElseThrow(() -> new ServiceException("No group with name " + scheduleDTO.getGroupName()));

    Lecturer lecturer = lecturerRepository.findById(scheduleDTO.getLecturerId()).
            orElseThrow(() -> new ServiceException("No lecturer with id " + scheduleDTO.getLecturerId()));

    LocalDate start = scheduleDTO.getStartDate();
    LocalDate end = scheduleDTO.getEndDate();

    long daysBetween = ChronoUnit.DAYS.between(start, end);
    if (daysBetween > 365) {
      throw new ServiceException("You can't make schedule more than 365 days");
    }

    if (start.isAfter(end) || start.isEqual(end)) {
      throw new ServiceException("Start date equals or after end date");
    }

    Set<Lesson> lessons = new HashSet<>();

    for (LocalDate beginOfEducationalWeek = start; beginOfEducationalWeek.isBefore(end);
         beginOfEducationalWeek = beginOfEducationalWeek.plusWeeks(scheduleDTO.getPeriodicity().weeksNumber())) {

      for (LocalDate dayOfEducationalWeek = beginOfEducationalWeek;
           dayOfEducationalWeek.isBefore(beginOfEducationalWeek.plusDays(7));
           dayOfEducationalWeek = dayOfEducationalWeek.plusDays(1)) {

        if (scheduleDTO.getDaysOfWeek().contains(dayOfEducationalWeek.getDayOfWeek())) {
          LocalDateTime beginDateTime = LocalDateTime.of(dayOfEducationalWeek, scheduleDTO.getBegin());
          LocalDateTime endDateTime = beginDateTime.plusMinutes(scheduleDTO.getDuration());
          Lesson lesson = new Lesson(null, group, beginDateTime, endDateTime, lecturer, null);
          validate(lesson);
          lessons.add(lesson);
        }
      }
    }
    lessonRepository.saveAllAndFlush(lessons);
  }

  static void validate(Lesson lesson) throws ServiceException {
    if (lesson.getBegin().isAfter(lesson.getEnd())) {
      throw new ServiceException("Begin is after end");
    }
    //TODO refactor time logic
    if (lesson.getLecturer().getLessons().stream().anyMatch(l -> !l.getId().equals(lesson.getId()) && isTimeIntersection(lesson, l))) {
      throw new ServiceException("This lesson has time intersection with selected lecturer");
    }

    for (Student student : lesson.getGroup().getStudents()) {
      for (Group group : student.getGroups()) {
        if (group.getLessons().stream().anyMatch(l -> isTimeIntersection(lesson, l))) {
          throw new ServiceException("This lesson has time intersection with schedule of group " + group.getName());
        }
      }
    }
  }

  static boolean isTimeIntersection(Lesson lesson1, Lesson lesson2) {
    return !(lesson1.getEnd().isBefore(lesson2.getBegin()) || lesson1.getBegin().isAfter(lesson2.getEnd()));
  }
}
