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
  public List<Group> findAll() {
    return groupRepository.findAll();
  }

  @Override
  public List<Group> findAllActive() {
    List<Group> groups = findAll();
    return groups.stream().filter(group -> group.getStatus() == Status.ACTIVE).toList();
  }

  @Override
  public Group findByName(String name) throws ServiceException {
    return groupRepository.findById(name).orElseThrow(() -> new ServiceException("No group with name " + name));
  }

  @Override
  public void save(Group group) throws ServiceException {
    if (!courseRepository.existsById(group.getCourse().getName())) {
      throw new ServiceException("No course with name " + group.getCourse().getName());
    }

    if (groupRepository.existsById(group.getName())) {
      throw new ServiceException("Group with name " + group.getName() + " already exists");
    }
    groupRepository.saveAndFlush(group);
  }

  @Override
  public List<Group> findAllByCourse(Course course) {
    return groupRepository.findAllByCourse(course);
  }
}
