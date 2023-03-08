package com.ashokit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.binding.AdminBinding;
import com.ashokit.entity.CaseWorkersAcctEntity;
import com.ashokit.service.AdminService;

@RestController
public class AdminController {

	@Autowired
	private AdminService service;

	@GetMapping("/getall")
	public List<CaseWorkersAcctEntity> getAllCw() {

		List<CaseWorkersAcctEntity> allCW = service.getAllCW();

		return allCW;
	}

	@PostMapping("/addcw")
	public ResponseEntity<String> addCW(@RequestBody AdminBinding admin) {
		
		String addCW = service.addCW(admin);
		
		return new ResponseEntity<>(addCW,HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/editcw")
	public String editCw(@RequestBody AdminBinding admin) {
		
		service.editCW(admin);
		
		return "CW Updated";
	}
	
	@PostMapping("/sdelete/{accId}")
	public String softDeleteCw(@PathVariable int accId) {
		
		service.softDelete(accId);
		
		return "cw soft Deleted";
	}
	
	@DeleteMapping("/deletecw/{accId}")
	public ResponseEntity<String> deleteCw(@PathVariable int accId) {
		
		String deleteCW = service.deleteCW(accId);
		
		return new ResponseEntity<>(deleteCW,HttpStatus.OK);
	}
	

}
