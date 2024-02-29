package com.maru.chaekmaru;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@Log4j2
public class MainController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	String nextPage = "";

	@GetMapping({ "/", "" })
	public String index(Model model) {
		log.info("index");

		
		model.addAttribute("name", "가나다");
		
		nextPage = "layout";

		return nextPage;
	}

	
	@GetMapping( "/list" )
	public String list(Model model) {
		log.info("list");
		
		nextPage = "list";
		
		String sql = "select * from TBL_BOOK";
		
		List<TestDto> testDtos = new ArrayList<>();
		
		try {
			
			RowMapper<TestDto> rowMapper = BeanPropertyRowMapper.newInstance(TestDto.class);
			testDtos = jdbcTemplate.query(sql, rowMapper);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(testDtos);
		
		model.addAttribute("test", testDtos);
		
		return nextPage;
	}


}
