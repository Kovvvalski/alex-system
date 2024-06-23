package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.exception.ImpossibleRemoveException;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.CourseRepository;
import by.kovalski.alexsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;

  @Autowired
  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course findByName(String name) throws ServiceException {
    return courseRepository.findById(name).orElseThrow(() -> new ServiceException("No course with name " + name));
  }

  @Override
  public List<Course> findAll() {
    return courseRepository.findAll();
  }

  @Override
  public void save(Course course) throws ServiceException {
    if (courseRepository.existsById(course.getName())) {
      throw new ServiceException("Course with name " + course.getName() + " already exists");
    }
    courseRepository.saveAndFlush(course);
  }

  @Override
  public void deleteByName(String name) throws ServiceException{
    try {
      courseRepository.deleteById(name);
    } catch (ImpossibleRemoveException e) {
      throw new ServiceException(e.getMessage(), e);
    }
  }
}
