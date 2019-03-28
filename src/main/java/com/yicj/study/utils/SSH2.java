package com.yicj.study.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.yicj.study.tuple.ThreeTuple;
import com.yicj.study.tuple.Tuples;
import com.yicj.study.tuple.TwoTuple;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSH2 {
	
    public static ThreeTuple<Boolean,Connection,String> login(String hostname,String username,String password) {
    	Connection conn = new Connection(hostname);
    	boolean flag = true;
    	String msg = "" ;
        try {
			conn.connect();
			boolean isconn = conn.authenticateWithPassword(username, password);//使用用户名和密码校验
	        if(!isconn){
	        	flag = false;
	        	msg = "用户名称或者是密码不正确" ;
	        }
		} catch (IOException e) {
			flag = false;
			msg = e.getMessage();
		}
        return Tuples.tuple(flag, conn, msg) ;
    }
	
    /**
     * 执行命令
     * @param conn 服务器连接
     * @param cmd  待执行的命令
     * @param closeConn 是否关闭连接
     * @return
     */
    public static TwoTuple<Boolean, String> execmd(Connection conn, String cmd,boolean closeConn) {
        String result="";
        boolean flag = true;
        String msg = "" ;
        Session ssh = null ;
        try {
        	ssh = conn.openSession();
            //使用多个命令用分号隔开
            ssh.execCommand(cmd);
            //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
            result=processStdout(ssh.getStdout(),"utf-8");
            //如果为得到标准输出为空，说明脚本执行出错了
            if(isBlank(result)){
            	flag = false;
                result=processStdout(ssh.getStderr(),"utf-8");
            }
		} catch (IOException e) {
			flag = false;
			result = e.getMessage();
		}finally {
			if(ssh!=null) {
				ssh.close();
			}
			if(closeConn && conn!=null) {
				conn.close();
			}
		}
        return Tuples.tuple(flag, msg) ;
    }
    

	private static String processStdout(InputStream in, String charset) throws IOException{
        InputStream  stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();;
        BufferedReader br = null ;
        try {
            br = new BufferedReader(new InputStreamReader(stdout,charset));
            String line=null;
            while((line=br.readLine()) != null){
                buffer.append(line+"\n");
            }
        } finally {
        	if(br !=null) {
        		br.close();
        	}
        }
        return buffer.toString();
    }
    
    public static boolean isBlank(String result) {
    	if(result==null || result.trim().length() ==0) {
    		return true ;
    	}
    	return false;
    }
    
    public static void main(String[] args) throws IOException {
    	String  hostname = "" ;
    	String username = "" ;
    	String password = "";
    	String cmd = "" ;
    	ThreeTuple<Boolean, Connection, String> ret = SSH2.login(hostname, username, password);
    	Connection conn = ret.second ;
    	TwoTuple<Boolean, String> execmd = SSH2.execmd(conn, cmd, true);
    	System.out.printf("%s | %s %n",execmd.first,execmd.second);
	}
}
