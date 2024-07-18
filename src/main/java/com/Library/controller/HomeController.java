package com.Library.controller;

import java.util.List;
import java.util.Random;

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
import com.Library.service.Email_Service;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Library_Repository library_Repository;
	
	@Autowired
	private Contact_Repo contact_Repo;
	
	@Autowired
	private Email_Service email_Service;
	
	private static int otp;
	private static int id;
	
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
		
		
		List<String> city=this.library_Repository.findAllCity();
		//System.out.println(city);
		
		model.addAttribute("city",city);
		
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
		l.setCity(city.toUpperCase());
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
		c.setName(name.toUpperCase());
		c.setEmail(email);
		c.setContact(contact);
		c.setMessage(message.toUpperCase());
		Contact contact1=this.contact_Repo.save(c);
		return "redirect:contact";
	}
	
	@GetMapping("/city_filter")
	public String cityfilter(@RequestParam("city")String city,Model model) {
		
		List<Library> library=this.library_Repository.findAllByCity(city);
		model.addAttribute("libraries",library);
		System.out.println(city);
		
		List<String> city1=this.library_Repository.findAllCity();
		//System.out.println(city);
		
		model.addAttribute("city",city1);
		return "total_library";
	}
	
	@GetMapping("/forget_password")
	public String email_verfiy_page() {
		return "send_otp";
	}
	
	@PostMapping("/email")
	public String opt_send(@RequestParam("email")String Email,Model model) {
		
		Library library=this.library_Repository.findByEmail(Email);
		if(library!=null) {
			//System.out.println("email is available");
			
			otp=this.email_Service.opt_Generted();
			
			System.out.println(otp);
			
			String message = "OTP : " +otp;
			String subject = "OTP";
			String to = Email;
			String from = "xyz@gmail.com";
			
			this.email_Service.sendEmail(to, from, subject, message);
			
			id=library.getId();
			
		}else {
			
			
			System.out.println("email is not available");
		}
		
		return "send_otp";
	}
	
	@PostMapping("/otpSend")
	public String otp_Verification(@RequestParam("otp")int otpsend,Model model) {
		
		if(otpsend==otp) {
			
			return "new_password";
		}
		
		return "send_otp";
		
		
	}
	
	@PostMapping("/new_Password")
	public String new_Password(@RequestParam("password")String password,@RequestParam("confirm_password")String confirm_password) {
				
		System.out.println(id);
		System.out.println(password);
		System.out.println(confirm_password);
		if(password.equals(confirm_password)) {
			
			Library li=this.library_Repository.findById(id).get();
			
			li.setPassword(passwordEncoder.encode(confirm_password));
			
			Library l=this.library_Repository.save(li);
			
			System.out.println("password change");
			
		}else {
			System.out.println("password not same");
		}
		
		return "send_otp";
	}
	
}
