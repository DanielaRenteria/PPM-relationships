package com.ppm.springboot.web.app.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForMinguoDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ppm.springboot.web.app.dao.TechnologiesForUserDAO;
import com.ppm.springboot.web.app.dao.TechnologyDAO;
import com.ppm.springboot.web.app.entity.Technology;

@Controller
@RequestMapping("/technology")
public class TechnologyController {

	@Autowired
	private TechnologyDAO technologyDAO;

	@PostMapping // /products (POST)
    public ResponseEntity<Technology> createTechnologies(@RequestBody Technology technology){
        Technology newTechnology = technologyDAO.save(technology);
        return ResponseEntity.ok(newTechnology);
    }
	@PutMapping
    public ResponseEntity<Technology> updateTechnology(@RequestBody Technology technology){
        Optional<Technology> optionalTechnology= technologyDAO.findById(technology.getId());
        if(optionalTechnology.isPresent()) {
        	Technology updateTechnology = optionalTechnology.get();
            updateTechnology.setName(technology.getName());
            updateTechnology.setDescription(technology.getDescription());
            updateTechnology.setActive(technology.getActive());
            technologyDAO.save(updateTechnology);
            return ResponseEntity.ok(updateTechnology);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@GetMapping
    public ResponseEntity<List<Technology>> getTechnology(){
        List<Technology> technology = technologyDAO.findAll();
        return ResponseEntity.ok(technology);
    }

    @RequestMapping(value="{technologyId}")// /products/{productId} -> /products/1
    public ResponseEntity<Technology> getTechnologyById(@PathVariable("technologyId") Long technologyId){
        Optional<Technology> optionalTechnology= technologyDAO.findById(technologyId);
        if(optionalTechnology.isPresent()){
            return ResponseEntity.ok(optionalTechnology.get());
        }else {
            return ResponseEntity.noContent().build();
        }
    }

}
