package com.ashokit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.AdminBinding;
import com.ashokit.entity.CaseWorkersAcctEntity;
import com.ashokit.repositories.CaseWorkersAccRepo;
import com.ashokit.util.EmailUtil;




@Service
public class AdminImpl implements AdminService{
	
	@Autowired
	private CaseWorkersAccRepo repo;
	
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public String addCW(AdminBinding admin) {
		
		CaseWorkersAcctEntity entity = new CaseWorkersAcctEntity();
		BeanUtils.copyProperties(admin, entity);
		
		entity.setPazz(generatRandomPwd());
		entity.setActiveSW('Y');
		
		CaseWorkersAcctEntity savedEntity = repo.save(entity);
		
		String emailId=admin.getEmail();
		String fileName = "RECOVER-PASSWORD-EMAIL-BODY-TEMPLETE.txt";
		String body = readMailBodyContent(fileName, entity);
		String subject = "Recover the Password";

		boolean isSent = emailUtil.sendEmail(emailId, subject, body);
		if (isSent) {
			return "Password sent to registered email";
		}
		
		return "error";
	}

	@Override
	public List<CaseWorkersAcctEntity> getAllCW() {
		
		return repo.findAll();
	}

	@Override
	public String editCW(AdminBinding admin) {
		
		CaseWorkersAcctEntity entity = new CaseWorkersAcctEntity();
		BeanUtils.copyProperties(admin, entity);
		repo.save(entity);
		
		return "Success";
	}

	@Override
	public String softDelete(int accId) {
		
		Optional<CaseWorkersAcctEntity> findById = repo.findById(accId);
		
		if (findById.isPresent()) {
			CaseWorkersAcctEntity entity = findById.get();
			entity.setActiveSW('N');
			repo.save(entity);
		}
		
		return "Success";
	}

	@Override
	public String deleteCW(int accId) {
		
		repo.deleteById(accId);
		
		return "Success";
	}
	
	private String generatRandomPwd() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}
	
	
	private String readMailBodyContent(String fileName, CaseWorkersAcctEntity entity) {

		String mailBody = null;
		try {
			StringBuffer sb = new StringBuffer();

			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); // Reading first line data
			while (line != null) {
				sb.append(line); // appending line data to buffer object
				line = br.readLine(); // reading next line
			}

			mailBody = sb.toString();

			mailBody = mailBody.replace("{FNAME}", entity.getFullName());
			mailBody = mailBody.replace("{TEMP-PWD}", entity.getPazz());
			mailBody = mailBody.replace("{EMAIL}", entity.getEmail());
			mailBody = mailBody.replace("{PWD}", entity.getPazz());

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}
	

}
