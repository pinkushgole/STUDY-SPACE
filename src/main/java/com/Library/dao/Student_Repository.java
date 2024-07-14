package com.Library.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Library.model.Student;

public interface Student_Repository extends JpaRepository<Student, Integer>{

	 List<Student> findAllByLibraryId(int libraryId);
	
}
