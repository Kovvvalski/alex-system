package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.ParentDTO;
import by.kovalski.alexsystem.entity.Parent;
import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface ParentService {
  Parent findById(Long id) throws ServiceException;

  List<Parent> findAll();

  List<Parent> findAllActive();

  void update(Parent parent) throws ServiceException;

  void save(Parent parent) throws ServiceException;

  void deleteById(Long id) throws ServiceException;

  Parent getFromDTO(ParentDTO parentDTO);
}
