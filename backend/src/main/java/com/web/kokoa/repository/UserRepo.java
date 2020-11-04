package com.web.kokoa.repository;

import com.web.kokoa.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo  extends JpaRepository<user, String> {

    user findUserByEmail(String email);


}
