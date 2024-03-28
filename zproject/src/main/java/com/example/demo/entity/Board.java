package com.example.demo.entity;

import java.time.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime writeTime;
	private Long readCnt;
	private Long goodCnt;
	private Long badCnt;
	
	public void increaseReadcnt() {
		this.readCnt++;
	}
}
