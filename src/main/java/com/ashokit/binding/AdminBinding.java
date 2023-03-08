package com.ashokit.binding;

import java.util.Date;

import lombok.Data;

@Data
public class AdminBinding {
	
	private int accId;
	
	private String fullName;
	
	private String email;
	
	private long mobileNo;
	
	private char gender;
	
	private Date dob;
	
	private long ssn;

}
