package web.controllers;

import org.springframework.ui.ModelMap;
import web.model.User;
import web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello! (Привет!)");
		messages.add("I'm Spring MVC application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "index";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}

	@PostMapping("/add")
	public String addUser(@RequestParam("name") String name,
						  @RequestParam("lastName") String lastName,
						  @RequestParam("age") int age) {
		User user = new User(name, lastName, age);
		userService.addUser(user);
		return "redirect:/users";
	}

	@GetMapping("/edit")
	public String editUser(@RequestParam("id") Long id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "edit";
	}

	@PostMapping("/update")
	public String updateUser(@RequestParam("id") Long id,
							 @RequestParam("name") String name,
							 @RequestParam("lastName") String lastName,
							 @RequestParam("age") int age) {
		User user = new User(name, lastName, age);
		user.setId(id);
		userService.updateUser(user);
		return "redirect:/users";
	}

	@PostMapping("/delete")
	public String deleteUser(@RequestParam("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}
}