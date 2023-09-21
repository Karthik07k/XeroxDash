package com.college.GetTheCopy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.college.GetTheCopy.model.XeroxShop;

public interface XeroxShopRepository extends CrudRepository<XeroxShop, String> {

	XeroxShop findByUserName(String username);
	List<XeroxShop> findAll();
	XeroxShop findByShopId(String shopId);

}