package com.Library.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Library.dao.Library_Repository;
import com.Library.dao.Student_Repository;
import com.Library.model.Library;
import com.Library.model.Student;

@Controller
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	private Library_Repository library_Repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private Student_Repository student_Repository;

	@GetMapping("/home")
	public String libraryHome(Model model, Principal principal) {
		String name = principal.getName();
		// System.out.println(name);
		Library library = this.library_Repository.findByEmail(name);

		model.addAttribute("library", library);
		return "library/libraryHome";
	}

	@GetMapping("/setting")
	public String open_settingPage() {
		return "library/setting";
	}

	@GetMapping("/profile")
	public String open_ProfilePage(Model model, Principal principal) {
		String name = principal.getName();
		Library library = this.library_Repository.findByEmail(name);

		model.addAttribute("library", library);
		return "library/profile";
	}

	@GetMapping("/add_Student")
	public String open_AddStudentPage() {
		return "library/add_Student";
	}

	@GetMapping("/add_more_details")
	public String open_AddMoreDetailsPage(Model model, Principal principal) {

		String name = principal.getName();
		// System.out.println(name);
		Library library = this.library_Repository.findByEmail(name);

		model.addAttribute("library", library);

		return "library/add_more_details";
	}

	@GetMapping("/total_student")
	public String open_totalStudentPage(Principal principal, Model model) {
		String name = principal.getName();
		Library library = this.library_Repository.findByEmail(name);
		int lid = library.getId();
		List<Student> students = this.student_Repository.findAllByLibraryId(lid);

		if (students == null) {
			return "library/total_student";
		} else {
			model.addAttribute("students", students);
			// System.out.println(student);
		}
		return "library/total_student";
	}

	@PostMapping("/add_student_info")
	public String addStudent(Principal principal, @RequestParam("sname") String sname,
			@RequestParam("contact") String contact, @RequestParam("sdate") String sdate,
			@RequestParam("edate") String edate, @RequestParam("payment") double payment,
			@RequestParam("remain_payment") double remain_payment) {

		String name = principal.getName();
		Library library = this.library_Repository.findByEmail(name);

		System.out.println(sname);
		System.out.println(contact);
		System.out.println(sdate);
		System.out.println(edate);
		System.out.println(payment);
		System.out.println(remain_payment);

		Student s = new Student();
		s.setSname(sname);
		s.setContact(contact);
		s.setSdate(sdate);
		s.setEdate(edate);
		s.setPayment(payment);
		s.setRemain_payment(remain_payment);
		s.setLibrary(library);
		library.getStudent().add(s);

		this.library_Repository.save(library);
		// Student stu = this.student_Repository.save(s);

		return "library/add_Student";
	}

	@PostMapping("/add_more_data")
	public String setMoreData(@RequestParam("image") MultipartFile image, Model model, Principal principal,
			@RequestParam("totalSeat") int seat, @RequestParam("hours24") double hours24,
			@RequestParam("hours12") double hours12, @RequestParam("hours8") double hours8,
			@RequestParam("reserve") double reserve) {

		try {
			String name = principal.getName();
			Library library = this.library_Repository.findByEmail(name);
			library.setSeat(seat);
			library.setHours24(hours24);
			library.setHours12(hours12);
			library.setHours8(hours8);
			library.setReserve(reserve);

			if (image.isEmpty()) {
				System.out.println("image is empty");
			} else {
				// upload the file to folder

				File file = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(file.getAbsolutePath() + File.separator + image.getOriginalFilename());

				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("image upload");
				library.setImage(image.getOriginalFilename());
			}
			Library l = this.library_Repository.save(library);
			model.addAttribute("library", library);

		} catch (Exception e) {
			System.out.println(e);
			// error message
		}

//		System.out.println(seat);
//		System.out.println(hours24);
//		System.out.println(hours12);
//		System.out.println(hours8);
//		System.out.println(reserve);
//		System.out.println(image.getOriginalFilename());

		return "library/add_more_details";
	}

	@GetMapping("/delete")
	public String deleteStudent(@RequestParam("id") int id) {
		try {
			Student st = this.student_Repository.findById(id).get();
			student_Repository.delete(st);
			System.out.println("deletion succesfull");

		} catch (Exception e) {
			System.out.println(e);
		}

		return "redirect:/library/total_student";
	}

	@GetMapping("/edit")
	public String editStudent(@RequestParam("id") int id, Model model) {

		try {
			Student student = this.student_Repository.findById(id).get();
			model.addAttribute("student", student);
		} catch (Exception e) {
			System.out.println(e);
		}

		return "library/Edit_Student";
	}

	@PostMapping("/edit_student")
	public String editStudentInfo(@RequestParam("id") int id, @RequestParam("sname") String sname,
			@RequestParam("contact") String contact, @RequestParam("sdate") String sdate,
			@RequestParam("edate") String edate, @RequestParam("payment") double payment,
			@RequestParam("remain_payment") double remain_payment) {

		Student student = this.student_Repository.findById(id).get();
		student.setSname(sname);
		student.setContact(contact);
		student.setSdate(sdate);
		student.setEdate(edate);
		student.setPayment(payment);
		student.setRemain_payment(remain_payment);

		Student st = this.student_Repository.save(student);

		return "redirect:/library/total_student";
	}

	@PostMapping("/library_edit")
	public String editlibrary(@RequestParam(value = "image", required = false) MultipartFile image, Model model,
			@RequestParam("id") int id, @RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "contact", required = false) String contact,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "pincode", required = false) String pincode,
			@RequestParam(value = "totalSeat", required = false) int seat,
			@RequestParam(value = "hours24", required = false) double hours24,
			@RequestParam(value = "hours12", required = false) double hours12,
			@RequestParam(value = "hours8", required = false) double hours8,
			@RequestParam(value = "reserve", required = false) double reserve) {

		String address = location + "," + city + "," + state + pincode;
		try {
			Library li = this.library_Repository.findById(id).get();
			Path imagePath = Paths.get("static/img/" + li.getImage());
			if (imagePath == null) {
				Files.delete(imagePath);
			} else {
				System.out.println("image not found");
			}

			File file = new ClassPathResource("static/img").getFile();

			Path path = Paths.get(file.getAbsolutePath() + File.separator + image.getOriginalFilename());

			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			li.setImage(image.getOriginalFilename());

			li.setPassword(password);

			li.setCity(city);

			li.setContact(contact);

			li.setLocation(location);

			li.setHours12(hours12);

			li.setHours24(hours24);

			li.setHours8(hours8);

			li.setReserve(reserve);

			li.setSeat(seat);

			Library lib = this.library_Repository.save(li);

		} catch (Exception e) {
			System.out.println(e);
		}

		return "redirect:/library/home";
	}

}
