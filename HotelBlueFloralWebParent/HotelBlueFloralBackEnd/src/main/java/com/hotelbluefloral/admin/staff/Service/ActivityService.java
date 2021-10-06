package com.hotelbluefloral.admin.staff.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hotelbluefloral.comman.entity.Activity;


public interface ActivityService {
	List<Activity> getAllActivity();
	void saveActivity(Activity activity);
	Activity getActivityeById(long acid);
	void deleteActivityById(long acid);
	Page<Activity> findPaginated(int pageNo,int pageSize,String sortField,String sortDirection);
}
