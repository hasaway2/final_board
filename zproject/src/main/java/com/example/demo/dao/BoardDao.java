package com.example.demo.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.entity.*;

@Mapper
public interface BoardDao {
	public Long save(Board board);

	@Select("select * from board where bno=#{bno} and rownum=1")
	public Board findByBno(Long bno);

	@Update("update board set title=#{title}, content=#{content} where bno=#{bno} and rownum=1")
	public Long update(Board board);

	@Select("select count(bno) from board")
	public Long count();

	public List<Board> findAll(Long startRownum, Long endRownum);

	@Update("update board set read_cnt=read_cnt+1 where bno=#{bno} and rownum=1")
	public void increaseReadcnt(Long bno);

	@Delete("delete from board where bno=#{bno} and rownum=1")
	public Long deleteByBno(Long bno);

	@Update("update board set good_cnt=good_cnt+1 where bno=#{bno} and rownum=1")
	public Long increaseGoodCnt(Long bno);

	@Update("update board set bad_cnt=bad_cnt+1 where bno=#{bno} and rownum=1")
	public Long increaseBadCnt(Long bno);
}
