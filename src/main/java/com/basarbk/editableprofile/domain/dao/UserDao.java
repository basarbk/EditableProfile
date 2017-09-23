package com.basarbk.editableprofile.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basarbk.editableprofile.domain.User;

public interface UserDao extends JpaRepository<User, Long>{

}
