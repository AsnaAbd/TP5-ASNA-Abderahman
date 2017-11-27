package org.opendevup.service;

import java.util.List;

import org.opendevup.dao.RoleRepository;
import org.opendevup.dao.UserRepository;
import org.opendevup.entities.Role;
import org.opendevup.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured(value={"ROLE_ADMIN"})
public class UserRestService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository RoleRepository;
	@RequestMapping(value="/addUser")
	public User save(User u){
		return userRepository.save(u);
	}
	@RequestMapping(value="/findUsers")
	public List<User> findAll(){
		return userRepository.findAll();
	}
	@RequestMapping(value="/addRole")
	public Role saveRole(Role r){
		return RoleRepository
				.save(r);
	}
	
	@RequestMapping(value="/findRoles")
	public List<Role> findRoles(){
		return RoleRepository.findAll();
	}
	@RequestMapping(value="addRoleToUser")
	public User addRoleToUser(String username,String  role){
		User u=userRepository.findOne(username);
		Role r=RoleRepository.findOne(role);
		u.getRoles().add(r);
		userRepository.save(u);
		return u;
	}
}
