package com.basarbk.editableprofile.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basarbk.editableprofile.domain.Profile;

public interface ProfileDao extends JpaRepository<Profile, Long> {

}
