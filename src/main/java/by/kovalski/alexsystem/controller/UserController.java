package by.kovalski.alexsystem.controller;

import by.kovalski.alexsystem.controller.util.Attributes;
import by.kovalski.alexsystem.controller.util.Pages;
import by.kovalski.alexsystem.dto.UserDTO;
import by.kovalski.alexsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String login(Model model){
    model.addAttribute(Attributes.USER_DTO, new UserDTO());
    return Pages.LOGIN;
  }
}
