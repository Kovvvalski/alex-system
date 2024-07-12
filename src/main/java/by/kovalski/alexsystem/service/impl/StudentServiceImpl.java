package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.entity.Student;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.StudentRepository;
import by.kovalski.alexsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;

  @Autowired
  public StudentServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  @Override
  public Student findById(Long id) throws ServiceException {
    return studentRepository.findById(id).orElseThrow(() -> new ServiceException("No student with id " + id));
  }

  @Override
  public void deleteById(Long id) throws ServiceException {
    if (!studentRepository.existsById(id)) {
      throw new ServiceException("No student with id " + id);
    }
    studentRepository.deleteById(id);
  }

  @Override
  public void save(Student student) throws ServiceException {
    validate(student);
    studentRepository.saveAndFlush(student);
  }

  @Override
  public void update(Student student) throws ServiceException {
    if (!studentRepository.existsById(student.getId())) {
      throw new ServiceException("No student with id " + student.getId());
    }
    validate(student);
    studentRepository.saveAndFlush(student);
  }

  static void validate(Student student) throws ServiceException {

  }
}
