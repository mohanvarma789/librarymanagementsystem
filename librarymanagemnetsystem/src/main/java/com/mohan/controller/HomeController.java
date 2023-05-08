package com.mohan.controller;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mohan.dao.BookRepository;
import com.mohan.entities.Books;
import com.mohan.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
@Controller

 class HomeController {
	@Autowired
	private BookRepository bookRepository;
	@RequestMapping("/")
	
	public String home(Model model) {
		model.addAttribute("title","Home-Library Management System");
		return "home";
	}
    @RequestMapping("/about")
	
	public String about(Model model) {
		model.addAttribute("title","About-Library Management System");
		return "about";
	}
    
  //open add form handler
    @GetMapping("/add-book")
	public String openAddBookForm(Model model)
    {
    	model.addAttribute("title","Add Books");
    	model.addAttribute("book",new Books());
		return "normal/add_book_form" ;
	}
    //processing add books form
    @PostMapping("process-book")
    public String processBooks(@ModelAttribute Books book,HttpSession session) {
    	try {
    	System.out.println("DATA"+book);
    	bookRepository.save(book);

    	   session.setAttribute("message",new Message("Your Book is Added!! Add new one", "Success"));
    	}catch(Exception e) {
    		System.out.println("ERROR"+e.getMessage());
    		e.printStackTrace();
    		session.setAttribute("message",new Message("Something Went Wrong!! Try Again...", "Danger"));
    		
    	}
    	
    	
    	return "normal/add_book_form";
    	
    }
    //show books handler
    @GetMapping("/show-books")
    public String showBooks(Model m) {
    	List<Books>book=bookRepository.findAll();
    	m.addAttribute("book",book);
    	   	
    	return "normal/show_books";
    	
    }
    //delete
    @GetMapping("/delete/{bookid}")
    public String delete(@PathVariable("bookid")Integer bookid,Model m) {
    	Optional<Books>bookOptional=bookRepository.findById(bookid);
    	Books book=bookOptional.get();
    	bookRepository.delete(book);
    	return "redirect:/show-books";
    }
    //open update form handler
    @PostMapping("/update-book/{bookid}")
    public String updateForm(@PathVariable("bookid") Integer bookid,Model m) {
    	m.addAttribute("title", "Update Books");
        Books book=this.bookRepository.findById(bookid).get();
    	m.addAttribute("book",book);
    	return "normal/update_form";
    }
    //update book handler
    @RequestMapping(value="/process-update",method=RequestMethod.POST)
    public String updateHandler(@ModelAttribute Books book,Model m,HttpSession session) {
    	try {
    		this.bookRepository.save(book);
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("BOOK NAME"+book.getBookname());
    	System.out.println("BOOK ID"+book.getBookid());
    	
    	return "redirect:/show-books";
    	
    }
    

    
    
  	
    	
    }
    
	

