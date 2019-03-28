package com.yicj.study.util;

import org.junit.Test;

import com.yicj.study.tuple.ThreeTuple;
import com.yicj.study.tuple.TwoTuple;
import com.yicj.study.utils.SSH2Util;

import ch.ethz.ssh2.Connection;

public class SSH2UtilTest {
	
	@Test
	public void testSearchByTicketNo() {
		String ip = "192.168.187.129" ;
		String username = "yicj" ;
		String password = "123" ;
		//String cmd = "sh /home/yicj/code/shell/demo1.sh " + ticketNo +" " + time ;
		String cmd = "/home/yicj/code/shell/demo1.sh yicj hello" ;
		ThreeTuple<Boolean, Connection, String> retLog = SSH2Util.login(ip, username, password);
		if(retLog.first) {
			Connection conn = retLog.second ;
			TwoTuple<Boolean, String> retCmd = SSH2Util.execmd(conn, cmd, true);
			System.out.println(retCmd.second);
		}else {
			System.out.println(retLog.third);
		}
	}
	
}
