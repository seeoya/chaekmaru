package com.maru.chaekmaru.admin.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminBoardService {

	@Autowired
	IAdminBoardDaoForMybatis adminBoardDao;
	
}
