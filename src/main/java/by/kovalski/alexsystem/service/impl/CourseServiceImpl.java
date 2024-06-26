package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Status;
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
  public List<Course> findAllActive() {
    return courseRepository.findAllByStatus(Status.ACTIVE);
  }

  @Override
  public void save(Course course) throws ServiceException {
    if (courseRepository.existsById(course.getName())) {
      throw new ServiceException("Course with name " + course.getName() + " already exists");
    }
    courseRepository.saveAndFlush(course);
  }

  @Override
  public void deleteByName(String name) throws ServiceException {
    Course course = courseRepository.findById(name).orElseThrow(() -> new ServiceException("No course with name " +
            name));
    if (!course.getGroups().isEmpty()) {
      throw new ServiceException("Impossible delete: this course has groups");
    }
    courseRepository.deleteById(name);
  }

  @Override
  public void update(Course course) throws ServiceException {
    if (!courseRepository.existsById(course.getName())) {
      throw new ServiceException("Course with name " + course.getName() + " not exists");
    }

    if (course.getStatus() == Status.NON_ACTIVE &&
            course.getGroups().stream().anyMatch(g -> g.getStatus() == Status.ACTIVE)) {
      throw new ServiceException("Can not make status non-active: this course contains active groups");
    }
    courseRepository.saveAndFlush(course);
  }
}
