package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.GroupDTO;
import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface GroupService extends BaseService<Group, String, GroupDTO>{
  List<Group> findAllByCourse(Course course);

  List<Group> findAllActive();
}
