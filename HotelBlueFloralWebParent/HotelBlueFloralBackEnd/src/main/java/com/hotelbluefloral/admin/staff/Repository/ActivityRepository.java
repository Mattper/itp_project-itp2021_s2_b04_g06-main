package com.hotelbluefloral.admin.staff.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelbluefloral.comman.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long>{

}
