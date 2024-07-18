package com.Library.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Library.model.Library;

public interface Library_Repository extends JpaRepository<Library, Integer> {

	public Library findByEmail(String email);

	@Query("SELECT DISTINCT l.city FROM Library l")
	List<String> findAllCity();
	
	 List<Library> findAllByCity(String city);
	 
	 List<Library> findAllByEmail(String email);

}
