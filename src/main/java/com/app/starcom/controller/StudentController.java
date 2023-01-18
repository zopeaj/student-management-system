package com.app.starcom.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping(path="/api/v1/")
public class StudentController {
	Map<String, String> params = new HashMap<String, String>(); 
	
	@GetMapping(path="getdata")
	public void getStudents() {
		System.out.println("Getting Students Data");
	}
	
	@PostMapping(path="postdata")
	public void postStudents() {
		System.out.println("Posting Students Data");
	}
	
	@PutMapping(path="putdata")
	public void putStudent() {
		System.out.println("Updating Students Data");
	}
	
	@DeleteMapping(path="deletedata")
	public void deleteStudent() {
		System.out.println("Deleting Students Data");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="getdataprams")
	public ResponseEntity<Map<String, String>> getPrams(
			@RequestParam(name="id", required=true) String productId, 
			@RequestParam(name="ref", required=true) String ref,
			@RequestParam(name="name", required=true) String name){
		params.put("id", productId);
		params.put("ref", ref);
		params.put("name", name);
		return new ResponseEntity(params, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="getaddedParams")
	public ResponseEntity<Map<String, String>> getaddedParams(){
		return new ResponseEntity(params, HttpStatus.OK);
	}
}
