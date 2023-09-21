package com.college.GetTheCopy.repository;

import org.springframework.data.repository.CrudRepository;

import com.college.GetTheCopy.model.FileDetail;
public interface FileDetailRepository extends CrudRepository<FileDetail, String> {

	FileDetail findByUrl(String url);

}
