package com.hotelbluefloral.admin.staff.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotelbluefloral.comman.entity.Activity;
import com.hotelbluefloral.admin.staff.Repository.ActivityRepository;

@Service
public class ActivityServiceImpl implements ActivityService{
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Override
	public List<Activity> getAllActivity() {
		return activityRepository.findAll();
	}

	@Override
	public void saveActivity(Activity activity) {
		this.activityRepository.save(activity);
	}

	@Override
	public Activity getActivityeById(long acid) {
		Optional<Activity> optional = activityRepository.findById(acid);
		Activity activity=null;
		if(optional.isPresent()) {
			activity=optional.get();
		}else {
			throw new RuntimeException("Activity Id not found!");
		}
		return activity;
	}

	@Override
	public void deleteActivityById(long acid) {
		this.activityRepository.deleteById(acid);
	}

	@Override
	public Page<Activity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
			Sort.by(sortField).descending();
		Pageable pageable =PageRequest.of(pageNo - 1, pageSize,sort);
		return this.activityRepository.findAll(pageable);
	}

}
