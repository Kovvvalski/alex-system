package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.GroupDTO;
import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface GroupService {

  Group findByName(String name) throws ServiceException;

  List<Group> findAllByCourse(Course course);

  List<Group> findAll();

  List<Group> findAllActive();

  void save(Group group) throws ServiceException;

  void update(Group group) throws ServiceException;

  void deleteByName(String name) throws ServiceException;

  Group getFromDTO(GroupDTO groupDTO) throws ServiceException;
}
