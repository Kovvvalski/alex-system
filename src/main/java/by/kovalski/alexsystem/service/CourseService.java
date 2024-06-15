package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface CourseService {
  Course findByName(String name) throws ServiceException;

  List<Course> findAll() throws ServiceException;

  void save(Course course);
}
