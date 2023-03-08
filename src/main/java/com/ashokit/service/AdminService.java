package com.ashokit.service;

import java.util.List;

import com.ashokit.binding.AdminBinding;
import com.ashokit.entity.CaseWorkersAcctEntity;

public interface AdminService {
	
	public String addCW(AdminBinding admin);
	
	public List<CaseWorkersAcctEntity> getAllCW();
	
	public String editCW(AdminBinding admin);
	
	public String softDelete(int accId);
	
	public String deleteCW(int accId);

}
