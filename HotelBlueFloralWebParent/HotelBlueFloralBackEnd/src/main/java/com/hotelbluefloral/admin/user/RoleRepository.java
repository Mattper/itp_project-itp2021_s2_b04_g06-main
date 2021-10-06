package com.hotelbluefloral.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelbluefloral.comman.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
