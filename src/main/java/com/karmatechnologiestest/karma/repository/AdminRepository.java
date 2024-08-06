package com.karmatechnologiestest.karma.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import com.karmatechnologiestest.karma.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long>{

	public Admin findByUsername(String username);

}
