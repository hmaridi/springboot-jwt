package com.jwt.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jwt.model.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {

	@Query(value = "SELECT * FROM Roles r where r.name=:name", nativeQuery = true)
	Set<Role> find(@Param("name") String name);
}
