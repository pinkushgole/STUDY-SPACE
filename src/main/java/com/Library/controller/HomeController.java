package com.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Library.dao.Contact_Repo;
import com.Library.dao.Library_Repository;
import com.Library.model.Contact;
import com.Library.model.Library;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Library_Repository library_Repository;
	
	@Autowired
	private Contact_Repo contact_Repo;
	
	@GetMapping("/")
	public String home() {
		
		return "index";
		
	}
	
	@GetMapping("/login")
	public String show_Login_page()
	{
		return "login";
	}
	
	@GetMapping("/signup")
	public String show_SignUp_page()
	{
		return "signup";
	}
	
	@GetMapping("/about")
	public String show_about_page()
	{
		return "about";
	}
	
	@GetMapping("/total_library")
	public String show_total_library_page(Model model)
	{
		
		List<Library> librarys=this.library_Repository.findAll();		
		
		model.addAttribute("libraries",librarys);
		
		return "total_library";
	}
	
	@GetMapping("/contact")
	public String show_contact_page()
	{
		return "contact";
	}

	
	@PostMapping("/signup1")
	public String librarySignUp(@RequestParam("name") String name, @RequestParam("email")String email,
			@RequestParam("password")String password,@RequestParam("contact")String contact,@RequestParam("location")String location,@RequestParam("city")String city
			,@RequestParam("state")String state,@RequestParam("pincode")String pincode) {
		
		String address=location +","+ city+","+ state+pincode;
		/*
		 * System.out.println("name"+name); System.out.println("email"+email);
		 * System.out.println("password"+passwordEncoder.encode(password));
		 * System.out.println("contact"+contact); System.out.println("addres"+address);
		 */

		Library l=new Library();
		l.setName(name.toUpperCase());
		l.setEmail(email);
		l.setPassword(passwordEncoder.encode(password));
		l.setContact(contact);
		l.setLocation(address.toUpperCase());
		l.setCity(city);
		l.setRole("ROLE_USER");
		Library result=this.library_Repository.save(l);
		
		return "redirect:signup";
	}
	
	
	@PostMapping("/message_Send")
	public String messagesSend(@RequestParam("fname") String fname,@RequestParam("lname") String lname,@RequestParam("email") String email
			,@RequestParam("contact") String contact,@RequestParam("message") String message)
	{
		String name=fname+" "+lname;
		Contact c=new Contact();
		c.setName(name);
		c.setEmail(email);
		c.setContact(contact);
		c.setMessage(message);
		Contact contact1=this.contact_Repo.save(c);
		return "redirect:contact";
	}
	
}
