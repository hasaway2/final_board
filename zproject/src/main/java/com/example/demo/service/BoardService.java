package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.*;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	@Value("10")
	private long PAGE_SIZE;
	@Value("5")
	private long BLOCK_SIZE;
	
	public Page list(long pageno) {
		long countOfProduct = boardDao.count();
		long countOfPage = (countOfProduct-1)/PAGE_SIZE + 1;

		if(pageno>countOfPage)
			pageno = countOfPage;
		
		long startRownum = (pageno-1)*PAGE_SIZE + 1;
		long endRownum = startRownum + PAGE_SIZE - 1;
		List<Board> list = boardDao.findAll(startRownum, endRownum);
		
		long prev = (pageno-1)/BLOCK_SIZE * BLOCK_SIZE;
		long start = prev+1;
		long end = prev + BLOCK_SIZE;
		long next = end+1;
		if(end>=countOfPage) {
			end = countOfPage;
			next = 0;
		}
		return new Page(prev, start, end, next, pageno, list);
	}
}
