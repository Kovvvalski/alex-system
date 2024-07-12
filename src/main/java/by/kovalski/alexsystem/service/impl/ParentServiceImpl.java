package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.dto.ParentDTO;
import by.kovalski.alexsystem.entity.Parent;
import by.kovalski.alexsystem.entity.Student;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.ParentRepository;
import by.kovalski.alexsystem.repository.StudentRepository;
import by.kovalski.alexsystem.service.ParentService;
import by.kovalski.alexsystem.service.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentServiceImpl implements ParentService {

  private final ParentRepository parentRepository;
  private final StudentRepository studentRepository;

  @Autowired
  public ParentServiceImpl(ParentRepository parentRepository, StudentRepository studentRepository) {
    this.parentRepository = parentRepository;
    this.studentRepository = studentRepository;
  }

  @Override
  public Parent findById(Long id) throws ServiceException {
    return parentRepository.findById(id).orElseThrow(() -> new ServiceException("No parent with id " + id));
  }

  @Override
  public List<Parent> findAll() {
    return parentRepository.findAll();
  }

  @Override
  public void update(Parent parent) throws ServiceException {
    if (!parentRepository.existsById(parent.getId())) {
      throw new ServiceException("No parent with id " + parent.getId());
    }
    ServiceUtil.validateAbstractPerson(parent);
    parentRepository.saveAndFlush(parent);
  }

  @Override
  public void save(Parent parent) throws ServiceException {
    ServiceUtil.validateAbstractPerson(parent);
    parentRepository.saveAndFlush(parent);
  }

  @Override
  public void deleteById(Long id) throws ServiceException {
    Parent parent = parentRepository.findById(id).orElseThrow(() -> new ServiceException("No parent with id " + id));
    if (!parent.getWards().isEmpty()) {
      throw new ServiceException("Can't delete this person: wards list is not empty");
    }
    parentRepository.deleteById(id);
  }

  @Override
  public Parent getFromDTO(ParentDTO parentDTO) {
    List<Student> wards = studentRepository.findAllByParentId(parentDTO.getId());
    return new Parent(parentDTO.getId(), parentDTO.getFirstName(), parentDTO.getSecondName(), parentDTO.getThirdName(),
            parentDTO.getTelephoneNumber(), parentDTO.getEmail(), parentDTO.getStatus(), wards);
  }
}
