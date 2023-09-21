package com.college.GetTheCopy.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.college.GetTheCopy.model.Role;
import com.college.GetTheCopy.model.User;


public interface UserRepository extends CrudRepository<User, String> {

	boolean existsByUserName(String username);

	User findByUserName(String username);

	List<User> findAllByRoles(Role rolename);

//	User findByEmailId(String email);

	@Transactional
	void deleteByName(String username);
}