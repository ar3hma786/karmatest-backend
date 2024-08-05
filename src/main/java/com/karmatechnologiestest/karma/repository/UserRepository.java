package com.karmatechnologiestest.karma.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import com.karmatechnologiestest.karma.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{

	public User findByUsername(String username);

}
