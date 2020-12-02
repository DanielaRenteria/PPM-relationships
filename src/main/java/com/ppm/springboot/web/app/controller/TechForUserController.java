package com.ppm.springboot.web.app.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ppm.springboot.web.app.dao.TechnologiesForUserDAO;
import com.ppm.springboot.web.app.entity.TechForUser;

@Controller
@RequestMapping("/techForUser")
public class TechForUserController {

	@Autowired
	private TechnologiesForUserDAO technologiesForUserDAO;
	
	@PostMapping
	public ResponseEntity<TechForUser> createTechForUser(@RequestBody TechForUser techForUser) {
		TechForUser savedTechForUser = technologiesForUserDAO.save(techForUser);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(savedTechForUser.getId()).toUri();
		
		return ResponseEntity.created(location).body(savedTechForUser);
	}
	
	@PutMapping(path = "/{id}")
    public ResponseEntity<TechForUser> updateTechForUser(@PathVariable Long id, @RequestBody TechForUser techForUser){
		Optional<TechForUser> optionalTechForUser= technologiesForUserDAO.findById(id);
        if (!optionalTechForUser.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        
        techForUser.setId(optionalTechForUser.get().getId());
        technologiesForUserDAO.save(techForUser);
        
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping
    public List< TechForUser > getTechForUser() {
        return technologiesForUserDAO.findAll();
    }

    @RequestMapping(path = "/{id}")// /products/{productId} -> /products/1
    public ResponseEntity<TechForUser> getTechForUserById(@PathVariable("id") Long id){
        Optional<TechForUser> optionalTechForUser= technologiesForUserDAO.findById(id);
        if(optionalTechForUser.isPresent()){
            return ResponseEntity.ok(optionalTechForUser.get());
        }else {
            return ResponseEntity.noContent().build();
        }
    }
	
	

	
}
