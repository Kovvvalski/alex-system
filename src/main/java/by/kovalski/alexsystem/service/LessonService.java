package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.LessonDTO;
import by.kovalski.alexsystem.dto.ScheduleDTO;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.entity.Lesson;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface LessonService {
  void save(Lesson lesson) throws ServiceException;

  void update(Lesson lesson) throws ServiceException;

  void deleteById(Long id) throws ServiceException;

  void deleteAllByGroupName(String groupName) throws ServiceException;

  void deleteAllByLecturerId(Long lecturerId) throws ServiceException;

  List<Lesson> findAll();

  List<Lesson> findAllNotStarted();

  Lesson findById(Long id) throws ServiceException;

  List<Lesson> findByGroup(Group group) throws ServiceException;

  List<Lesson> findByLecturer(Lecturer lecturer) throws ServiceException;

  Lesson getFromDTO(LessonDTO lessonDTO) throws ServiceException;

  void createLessonsByScheduleDTO(ScheduleDTO scheduleDTO) throws ServiceException;
}
