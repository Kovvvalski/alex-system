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
      throw new ServiceException("User with login already exists");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  public User findById(String s) throws ServiceException {
    return null;
  }

  @Override
  public void update(User obj) throws ServiceException {

  }

  @Override
  public void deleteById(String s) throws ServiceException {

  }

  @Override
  public List<User> findAll() {
    return null;
  }

  @Override
  public User getFromDTO(UserDTO userDTO) throws ServiceException {
    return new User(userDTO.getLogin(), userDTO.getPassword(), userDTO.getRole(), null);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new UserDetailsImpl(userRepository.findById(username).
            orElseThrow(() -> new UsernameNotFoundException("No user with login " + username))
    );
  }
}
