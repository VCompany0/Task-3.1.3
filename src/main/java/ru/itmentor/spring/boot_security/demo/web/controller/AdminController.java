package ru.itmentor.spring.boot_security.demo.web.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repositories.UserRepository;
import ru.itmentor.spring.boot_security.demo.role.RoleEnum;
import ru.itmentor.spring.boot_security.demo.user.User;
import ru.itmentor.spring.boot_security.demo.user.UserDto;

import java.util.*;


@Controller
public class AdminController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/admin")
    public String adminPage(ModelMap model) {
        List<UserDto> userList = new LinkedList<>();
        userRepository.findAll().forEach(user -> {userList.add(new UserDto(user));});
        model.addAttribute("users", userList);

        UserDto currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", new UserDto());
        return "admin_page";
    }

    private UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new UserDto(userRepository.findByName(authentication.getName()));
    }

    @GetMapping(value = {"/index", "/", "/welcome"})
    public String viewMainPage(ModelMap model) {
        //service.createTables();
        return "welcome";
    }

    @GetMapping("/admin/addUser")
    public String addUser(Model model) {
        // create model attribute to bind form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);

        model.addAttribute("currentUser", getCurrentUser());
        return "add_user";
    }

    @RequestMapping(value = "/admin/updateUser", method = RequestMethod.POST)
    public String updateUser(UserDto updatedUser) {
        // Call the UserService implementation to update the user
        Optional<User> userOptional = userRepository.findById(updatedUser.getId());
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            if (!updatedUser.getPassword().isBlank()) {
                existingUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getRoles() != null && !updatedUser.getRoles().isEmpty()) {
                existingUser.setRoles(updatedUser.getRoles());
            }
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAge(updatedUser.getAge());
            userRepository.save(existingUser);

        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/saveUser")
    public String saveNewUser(@ModelAttribute("user") @Valid UserDto userDto) {
        User presentUser = userRepository.findByName(userDto.getName());
        if (presentUser == null) {
            if(userDto.getRoles() == null || userDto.getRoles().isEmpty()){
                userDto.setRoles(List.of(RoleEnum.ROLE_USER));
            }
            presentUser = new User(userDto);
            userRepository.save(presentUser);
        } else {
            return "redirect:/admin/addUser";
        }
        return "redirect:/admin";
    }


    @RequestMapping(value = "/admin/deleteUser", method = RequestMethod.DELETE)
    public String deleteUser(UserDto userDto) {
        User user = userRepository.findByName(userDto.getName());
        userRepository.delete(user);
        return "redirect:/admin";
    }

}
