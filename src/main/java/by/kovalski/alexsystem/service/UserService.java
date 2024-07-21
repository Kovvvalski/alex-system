package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.UserDTO;
import by.kovalski.alexsystem.entity.User;
import by.kovalski.alexsystem.exception.ServiceException;

public interface UserService extends BaseService<User, String, UserDTO> {
  void save(User user) throws ServiceException;
}
