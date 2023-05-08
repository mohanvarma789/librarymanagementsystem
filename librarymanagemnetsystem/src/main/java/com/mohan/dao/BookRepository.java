package com.mohan.dao;


import org.springframework.data.jpa.repository.JpaRepository;



import com.mohan.entities.Books;

public interface BookRepository extends JpaRepository<Books, Integer>{
	

}
