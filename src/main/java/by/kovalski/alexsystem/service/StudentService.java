package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.StudentDTO;
import by.kovalski.alexsystem.entity.Student;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface StudentService {
  List<Student> findAll();

  List<Student> findAllActive();

  Student findById(Long id) throws ServiceException;

  void deleteById(Long id) throws ServiceException;

  void save(Student student) throws ServiceException;

  void update(Student student) throws ServiceException;

  Student getFromDTO(StudentDTO studentDTO) throws ServiceException;
}
