package by.kovalski.alexsystem.service.impl;

import by.kovalski.alexsystem.dto.UserDTO;
import by.kovalski.alexsystem.entity.User;
import by.kovalski.alexsystem.exception.ServiceException;
import by.kovalski.alexsystem.repository.UserRepository;
import by.kovalski.alexsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void save(User user) throws ServiceException {
    if (userRepository.existsById(user.getLogin())) {
      throw new ServiceException("User with login " + user.getLogin() + " already exists");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.saveAndFlush(user);
  }

  @Override
  public User findById(String id) throws ServiceException {
    return userRepository.findById(id).
            orElseThrow(() -> new ServiceException("No user with login " + id));
  }

  @Override
  public void update(User user) throws ServiceException {
    if (!userRepository.existsById(user.getLogin())) {
      throw new ServiceException("No user with login " + user.getLogin());
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.saveAndFlush(user);
  }

  @Override
  public void deleteById(String id) throws ServiceException {
    if (!userRepository.existsById(id)) {
      throw new ServiceException("No user with login " + id);
    }
    userRepository.deleteById(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User getFromDTO(UserDTO userDTO) {
    return new User(userDTO.getLogin(), userDTO.getPassword(), userDTO.getRole(), null);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new UserDetailsImpl(userRepository.findById(username).
            orElseThrow(() -> new UsernameNotFoundException("No user with login " + username))
    );
  }
}
