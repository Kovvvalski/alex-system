package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface CourseService {
  Course findByName(String name) throws ServiceException;

  List<Course> findAllActive();

  List<Course> findAll();

  void save(Course course) throws ServiceException;

  void deleteByName(String name) throws ServiceException;

  void update(Course course) throws ServiceException;
}
