package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.exception.ServiceException;

import java.util.List;

public interface BaseService<T, ID, DTO> {
  T findById (ID id) throws ServiceException;

  void save(T obj) throws ServiceException;

  void update(T obj) throws ServiceException;

  void deleteById(ID id) throws ServiceException;

  List<T> findAll();

  T getFromDTO(DTO DTO) throws ServiceException;
}
