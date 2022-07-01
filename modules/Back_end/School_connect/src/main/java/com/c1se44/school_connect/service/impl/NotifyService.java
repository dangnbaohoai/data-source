package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.notifyEntity;
import com.c1se44.school_connect.repository.INotifyRepository;
import com.c1se44.school_connect.service.INotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyService implements INotifyService {
	@Autowired
	INotifyRepository notifyRepository;
	
	@Override
	public notifyEntity save(notifyEntity notifyEntity) {
		return notifyRepository.save(notifyEntity);
	}
}
