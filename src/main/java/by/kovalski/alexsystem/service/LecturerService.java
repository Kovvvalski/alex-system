package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.LecturerDTO;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface LecturerService extends BaseService<Lecturer, Long, LecturerDTO> {

  List<Lecturer> findAllActive();

  List<Lecturer> findByCourse(String courseName) throws ServiceException;
}
