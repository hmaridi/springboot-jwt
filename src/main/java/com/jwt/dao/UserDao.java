package com.jwt.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.model.User;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);
    
    @Modifying
	@Query(value = "DELETE FROM users u WHERE u.id=:id", nativeQuery = true)
	int deleteByUserId(@Param("id") Long id);
    
    @Query(value="SELECT name as roleName FROM roles r JOIN user_roles ur ON ur.role_id = r.id JOIN users u ON u.id = ur.user_id where u.username =?1",nativeQuery=true)
    Object findByRoleName(String usernam);
    
    
}
