package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.dto.StudentDTO;
import by.kovalski.alexsystem.entity.*;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.GroupRepository;
import by.kovalski.alexsystem.repository.ParentRepository;
import by.kovalski.alexsystem.repository.StudentRepository;
import by.kovalski.alexsystem.service.ServiceUtil;
import by.kovalski.alexsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final ParentRepository parentRepository;
  private final GroupRepository groupRepository;

  @Autowired
  public StudentServiceImpl(StudentRepository studentRepository, ParentRepository parentRepository,
                            GroupRepository groupRepository) {
    this.studentRepository = studentRepository;
    this.parentRepository = parentRepository;
    this.groupRepository = groupRepository;
  }

  @Override
  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  @Override
  public List<Student> findAllActive() {
    return studentRepository.findAllByStatus(Status.ACTIVE);
  }

  @Override
  public Student findById(Long id) throws ServiceException {
    return studentRepository.findById(id).orElseThrow(() -> new ServiceException("No student with id " + id));
  }

  @Override
  public void deleteById(Long id) throws ServiceException {
    if (!studentRepository.existsById(id)) {
      throw new ServiceException("No student with id " + id);
    }
    studentRepository.deleteById(id);
  }

  @Override
  public void save(Student student) throws ServiceException {
    validate(student);
    studentRepository.saveAndFlush(student);
  }

  @Override
  public void update(Student student) throws ServiceException {
    if (!studentRepository.existsById(student.getId())) {
      throw new ServiceException("No student with id " + student.getId());
    }
    validate(student);
    studentRepository.saveAndFlush(student);
  }

  @Override
  public Student getFromDTO(StudentDTO studentDTO) throws ServiceException {
    Student student = new Student(studentDTO.getId(), studentDTO.getFirstName(), studentDTO.getSecondName(),
            studentDTO.getThirdName(), studentDTO.getTelephoneNumber(), studentDTO.getEmail(),
            studentDTO.getStatus(), null,
            studentDTO.getParentId() != null ? parentRepository.findById(studentDTO.getParentId()).
                    orElseThrow(() -> new ServiceException("No parent with id " + studentDTO.getParentId())) : null,
            null, studentDTO.getBirthDate());

    List<Group> groups = new ArrayList<>(studentDTO.getGroups().size());
    for (String groupName : studentDTO.getGroups()) {
      groups.add(groupRepository.findById(groupName).orElseThrow(() -> new ServiceException("No group with name " + groupName)));
    }
    student.setGroups(groups);
    return student;
  }

  static void validate(Student student) throws ServiceException {
    //TODO validation logic
    ServiceUtil.validateAbstractPerson(student);
  }
}
