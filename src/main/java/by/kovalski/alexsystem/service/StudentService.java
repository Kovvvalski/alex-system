package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.StudentDTO;
import by.kovalski.alexsystem.entity.Student;

import java.util.List;

public interface StudentService extends BaseService<Student, Long, StudentDTO> {
  List<Student> findAllActive();
}
