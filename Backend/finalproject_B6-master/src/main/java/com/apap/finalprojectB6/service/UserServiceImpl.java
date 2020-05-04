package com.apap.finalprojectB6.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.finalprojectB6.model.RoleModel;
import com.apap.finalprojectB6.model.UserModel;
import com.apap.finalprojectB6.repository.UserRoleDB;
<<<<<<< HEAD
=======
import java.util.UUID;
>>>>>>> 2fb8e35b368097eff3d56d45efd10d644c03a3b2


@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRoleDB userdb;
	
	@Override
	public void createNip(UserModel user) {
		user.setNip(user.CreateNIP());
	}
	
<<<<<<< HEAD
//	@Override
//	public UserModel addUser(UserModel user) {
//		String pass = encrypt(user.getPassword());
//		user.setPassword(pass);
//		this.createNip(user);
//		return userdb.save(user);
//	}
=======
	@Override
	public UserModel addUser(UserModel user) {
//		String pass = encrypt(user.getPassword());
		String uuid = UUID.randomUUID().toString().replace("-", "");
		UserModel uuidcheck = this.getUserByUuid(uuid);
		while (uuidcheck != null) {
			uuid = UUID.randomUUID().toString().replace("-", "");
		}
//		user.setPassword(pass);
		user.setUuid(uuid);
		this.createNip(user);
		return userdb.save(user);
	}
>>>>>>> 2fb8e35b368097eff3d56d45efd10d644c03a3b2
//	
//	@Override
//	public String encrypt(String password) {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String hashedPassword = passwordEncoder.encode(password);
//		return hashedPassword;
//	}

	
	@Override
	public List<UserModel> getAllUser() {
		return userdb.findAll();
	}
	
	
	@Override
	public UserModel getUserById(int id) {
		return userdb.findById(id);
	}
	
	@Override
<<<<<<< HEAD
	public UserModel getUserByPass(String pass) {
		return userdb.findByPassword(pass);
=======
	public UserModel getUserByUuid(String uuid) {
		return userdb.findByUuid(uuid);
	}
	
	@Override
	public UserModel getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userdb.findByUsername(username);
>>>>>>> 2fb8e35b368097eff3d56d45efd10d644c03a3b2
	}

	@Override
	public UserModel getUser(String user) {
		return userdb.findByUsername(user);
	}
	
	@Override
	public boolean validate(String username) {
		List<UserModel> userList = userdb.validate(username);
		if (userList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

}
