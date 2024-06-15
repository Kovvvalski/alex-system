package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface GroupService {

  Group findByName(String name) throws ServiceException;

  List<Group> findAllByCourse(Course course) throws ServiceException;

  List<Group> findAll() throws ServiceException;

  List<Group> findAllActive() throws ServiceException;

  void save(Group group);
}
