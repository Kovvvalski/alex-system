package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.entity.Status;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.CourseRepository;
import by.kovalski.alexsystem.repository.LecturerRepository;
import by.kovalski.alexsystem.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static by.kovalski.alexsystem.service.ServiceUtil.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LecturerServiceImpl implements LecturerService {

  private final LecturerRepository lecturerRepository;
  private final CourseRepository courseRepository;

  @Autowired
  public LecturerServiceImpl(LecturerRepository lecturerRepository, CourseRepository courseRepository) {
    this.lecturerRepository = lecturerRepository;
    this.courseRepository = courseRepository;
  }

  @Override
  public void save(Lecturer lecturer) throws ServiceException {
    checkValidity(lecturer);

    if (lecturerRepository.existsByEmail(lecturer.getEmail())) {
      throw new ServiceException("Lecturer with email " + lecturer.getEmail() + " already exists");
    }

    if (lecturerRepository.existsByTelephoneNumber(lecturer.getTelephoneNumber())) {
      throw new ServiceException("Lecturer with telephone number " + lecturer.getTelephoneNumber() +
              " already exists");
    }

    lecturerRepository.saveAndFlush(lecturer);
  }

  @Override
  public void update(Lecturer lecturer) throws ServiceException {
    checkValidity(lecturer);

    if (lecturer.getId() == null) {
      throw new ServiceException("Null id value");
    }

    if (!lecturerRepository.existsById(lecturer.getId())) {
      throw new ServiceException("Lecturer with id " + lecturer.getId() + " not exists");
    }

    if (lecturerRepository.existsByEmailAndIdNot(lecturer.getEmail(), lecturer.getId())) {
      throw new ServiceException("Lecturer with email " + lecturer.getEmail() + " already exists");
    }

    if (lecturerRepository.existsByTelephoneNumberAndIdNot(lecturer.getTelephoneNumber(), lecturer.getId())) {
      throw new ServiceException("Lecturer with phone " + lecturer.getTelephoneNumber() + " already exists");
    }

    lecturerRepository.saveAndFlush(lecturer);
  }

  @Override
  public void deleteById(Long id) throws ServiceException {
    Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new ServiceException("Lecturer with id " + id
            + " not exists"));

    if (lecturer.getLessons().stream().anyMatch(l -> l.getBegin().isAfter(LocalDateTime.now()))) {
      throw new ServiceException("Impossible delete: lecturer has future lessons");
    }

    lecturerRepository.deleteById(id);
  }

  @Override
  public List<Lecturer> findAll() {
    return lecturerRepository.findAll();
  }

  @Override
  public Lecturer findById(Long id) throws ServiceException {
    return lecturerRepository.findById(id).orElseThrow(() -> new ServiceException("No lecturer with id " + id));
  }

  @Override
  public List<Lecturer> findByCourse(String courseName) throws ServiceException {
    Course course = courseRepository.findById(courseName).orElseThrow(() ->
            new ServiceException("No course with name " + courseName));
    return lecturerRepository.findLecturersByCourses(List.of(course));
  }

  static void checkValidity(Lecturer lecturer) throws ServiceException {
    if (!lecturer.getEmail().matches(EMAIL_REGEX)) {
      throw new ServiceException("Not valid email");
    }

    if (!lecturer.getTelephoneNumber().matches(TELEPHONE_NUMBER_REGEX)) {
      throw new ServiceException("Not valid telephone number");
    }

    if (lecturer.getStatus() == Status.NON_ACTIVE &&
            lecturer.getLessons().stream().anyMatch(l -> l.getBegin().isAfter(LocalDateTime.now()))) {
      throw new ServiceException("Can't do this lecturer non active: lecturer has future lessons");
    }
  }
}
