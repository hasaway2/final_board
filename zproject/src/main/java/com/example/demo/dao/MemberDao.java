package com.example.demo.dao;

import org.apache.ibatis.annotations.*;

import com.example.demo.entity.*;

@Mapper
public interface MemberDao {
	@Select("select count(*) from member where username=#{username} and rownum=1")
	public Boolean existsByUsername(String username);
	
	public Long save(Member member);
	
	@Select("select username from member where email=#{email} and rownum=1")
	public String findUsernameByEmail(String email);
	
	@Update("update member set password=#{password} where username=#{username} and rownum=1")
	public Long changePassword(String password, String username);
	
	@Select("select * from member where username=#{username} and rownum=1")
	public Member findByUsername(String username);
	
	@Update("update member set email=#{email} where username=#{username} and rownum=1")
	public Long changeEmail(String email, String username);
	
	@Delete("delete from member where username=#{username} and rownum=1")
	public Long deleteByUsername(String username);
}
