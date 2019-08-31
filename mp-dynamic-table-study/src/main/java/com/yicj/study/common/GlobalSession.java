package com.yicj.study.common;

import com.yicj.study.vo.UserVo;

public class GlobalSession {
	
	private static ThreadLocal<UserVo> loginSession = new ThreadLocal<>() ;
	
	public static void login(UserVo vo) {
		loginSession.set(vo);
	}
	
	public static UserVo getUserVo() {
		return loginSession.get() ;
	}

}
