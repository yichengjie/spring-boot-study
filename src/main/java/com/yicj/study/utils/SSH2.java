package com.yicj.study.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSH2 {
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
	// String cmd="shutdownUpdateServer.sh";
    public String execmd(String cmd) throws IOException {
        String hostname = "机器ip";
        String username = "用户名";
        String password = "密码";
        String result="";
        //指明连接主机的IP地址
        Connection conn = new Connection(hostname);
        Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if(!isconn){
                return "用户名称或者是密码不正确";
            }else{
                ssh = conn.openSession();
                //使用多个命令用分号隔开
                ssh.execCommand(cmd);
                //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
                result=processStdout(ssh.getStdout(),"utf-8");
                //如果为得到标准输出为空，说明脚本执行出错了
                if(isBlank(result)){
                    result=processStdout(ssh.getStderr(),"utf-8");
                    System.out.print(result);
                }
                return "已经连接OK" ;
            }
        } finally {
        	//连接的Session和Connection对象都需要关闭
        	ssh.close();
            conn.close();
        }
    }
    
    public boolean isBlank(String result) {
    	if(result==null || result.trim().length() ==0) {
    		return true ;
    	}
    	return false;
    }
    
    
    public static void startup() throws IOException {
   	    String cmd="sh /startUpdateServer.sh";
   	    String execmd = new SSH2().execmd(cmd);
   	    System.out.println(execmd);
   } 
    
    public static void main(String[] args) throws IOException {
    	//shutdown(); 
    	//startup();
	}
}
