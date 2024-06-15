package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.entity.Course;
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
    Course course = courseRepository.findByName(name);
    if(course == null){
      throw new ServiceException("No course with name " + name);
    }
    return course;
  }

  @Override
  public List<Course> findAll() throws ServiceException {
    List<Course> courses = courseRepository.findAll();
    if(courses.isEmpty()){
      throw new ServiceException("Repository is empty");
    }
    return courses;
  }

  @Override
  public void save(Course course) {
    courseRepository.saveAndFlush(course);
  }
}
