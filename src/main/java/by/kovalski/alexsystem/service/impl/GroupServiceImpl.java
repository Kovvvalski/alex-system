package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Status;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.CourseRepository;
import by.kovalski.alexsystem.repository.GroupRepository;
import by.kovalski.alexsystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
  private final GroupRepository groupRepository;
  private final CourseRepository courseRepository;

  @Autowired
  public GroupServiceImpl(GroupRepository groupRepository, CourseRepository courseRepository) {
    this.groupRepository = groupRepository;
    this.courseRepository = courseRepository;
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
    return groupRepository.findById(name).orElseThrow(() -> new ServiceException("No group with name " + name));
  }

  @Override
  public void save(Group group) throws ServiceException {
    List<Course> courses = courseRepository.findAll();
    if (courses.stream().noneMatch(course -> course.getName().equals(group.getCourse().getName()))) {
      throw new ServiceException("No course with name " + group.getCourse().getName());
    }

    if (groupRepository.existsById(group.getName())) {
      throw new ServiceException("Group with name " + group.getName() + " already exists");
    }
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
