package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Status;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.GroupRepository;
import by.kovalski.alexsystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
  private final GroupRepository groupRepository;

  @Autowired
  public GroupServiceImpl(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  @Override
  public List<Group> findAll() throws ServiceException {
    List<Group> groups = groupRepository.findAll();
    if (groups.isEmpty()) {
      throw new ServiceException("No groups in repository");
    }
    return groups;
  }

  @Override
  public List<Group> findAllActive() throws ServiceException {
    List<Group> groups = findAll();
    List<Group> filtered = groups.stream().filter(group -> group.getStatus() == Status.ACTIVE).toList();
    if (filtered.isEmpty()) {
      throw new ServiceException("No active groups in repository");
    }
    return filtered;
  }

  @Override
  public Group findByName(String name) throws ServiceException {
    Group group = groupRepository.findByName(name);
    if (group == null) {
      throw new ServiceException("No group with name " + name);
    }
    return group;
  }

  @Override
  public void save(Group group) {
    groupRepository.saveAndFlush(group);
  }

  @Override
  public List<Group> findAllByCourse(Course course) throws ServiceException {
    List<Group> groups = groupRepository.findAllByCourse(course);
    if (groups.isEmpty()) {
      throw new ServiceException("No groups on course " + course.getName());
    }
    return groups;
  }
}
