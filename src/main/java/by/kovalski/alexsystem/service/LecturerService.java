package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface LecturerService {
  void save(Lecturer lecturer) throws ServiceException;

  void update(Lecturer lecturer) throws ServiceException;

  void deleteById(Long id) throws ServiceException;

  List<Lecturer> findAll();

  Lecturer findById(Long id) throws ServiceException;

  List<Lecturer> findByCourse(String courseName) throws ServiceException;
}
