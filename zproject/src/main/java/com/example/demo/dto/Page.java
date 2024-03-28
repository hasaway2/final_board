package com.example.demo.dto;

import java.util.*;

import com.example.demo.entity.*;

import lombok.*;

@Data
@AllArgsConstructor
public class Page {
	private long prev;
	private long start;
	private long end;
	private long next;
	private long pageno;
	private List<Board> list;
}
