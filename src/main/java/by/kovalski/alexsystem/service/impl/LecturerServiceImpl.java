package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.dto.LecturerDTO;
import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.entity.Lesson;
import by.kovalski.alexsystem.entity.Status;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.CourseRepository;
import by.kovalski.alexsystem.repository.LecturerRepository;
import by.kovalski.alexsystem.repository.LessonRepository;
import by.kovalski.alexsystem.service.LecturerService;
import by.kovalski.alexsystem.service.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LecturerServiceImpl implements LecturerService {

  private final LecturerRepository lecturerRepository;
  private final CourseRepository courseRepository;
  private final LessonRepository lessonRepository;

  @Autowired
  public LecturerServiceImpl(LecturerRepository lecturerRepository, CourseRepository courseRepository, LessonRepository lessonRepository) {
    this.lecturerRepository = lecturerRepository;
    this.courseRepository = courseRepository;
    this.lessonRepository = lessonRepository;
  }

  @Override
  public void save(Lecturer lecturer) throws ServiceException {
    validate(lecturer);

    if (lecturerRepository.existsByTelephoneNumber(lecturer.getTelephoneNumber())) {
      throw new ServiceException("Lecturer with telephone number " + lecturer.getTelephoneNumber() +
              " already exists");
    }

    lecturerRepository.saveAndFlush(lecturer);
  }

  @Override
  public void update(Lecturer lecturer) throws ServiceException {
    validate(lecturer);

    if (lecturer.getId() == null) {
      throw new ServiceException("Null id value");
    }

    if (!lecturerRepository.existsById(lecturer.getId())) {
      throw new ServiceException("Lecturer with id " + lecturer.getId() + " not exists");
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
  public List<Lecturer> findAllActive() {
    return lecturerRepository.findAllByStatus(Status.ACTIVE);
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

  @Override
  public Lecturer getFromDTO(LecturerDTO lecturerDTO) throws ServiceException {
    Lecturer lecturer = new Lecturer(lecturerDTO.getId(), lecturerDTO.getFirstName(), lecturerDTO.getSecondName(),
            lecturerDTO.getThirdName(), lecturerDTO.getTelephoneNumber(), lecturerDTO.getEmail(),
            lecturerDTO.getStatus(), null, null);

    List<Course> courses = new ArrayList<>(lecturerDTO.getCourses().size());
    for (String course : lecturerDTO.getCourses()) {
      courses.add(courseRepository.findById(course).orElseThrow(() -> new ServiceException("No course with name " + course)));
    }

    lecturer.setCourses(courses);
    return lecturer;
  }

  static void validate(Lecturer lecturer) throws ServiceException {
    ServiceUtil.validateAbstractPerson(lecturer);

    if (lecturer.getStatus() == Status.NON_ACTIVE &&
            lecturer.getLessons().stream().anyMatch(l -> l.getBegin().isAfter(LocalDateTime.now()))) {
      throw new ServiceException("Can't do this lecturer non active: lecturer has future lessons");
    }
  }
}
