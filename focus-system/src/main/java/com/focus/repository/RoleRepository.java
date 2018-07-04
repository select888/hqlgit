package com.focus.repository;

import org.springframework.stereotype.Repository;

import com.focus.entity.Role;
import com.focus.repository.core.CommonRepository;

@Repository
public interface RoleRepository extends CommonRepository<Role, String>{

}
