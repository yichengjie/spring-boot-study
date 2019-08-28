package com.yicj.mybatis.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CalculateCodeUtil {
	private static long classcount = 0; // Java类的数量
	private static long normalLines = 0; // 空行
	private static long commentLines = 0; // 注释行
	private static long writeLines = 0; // 代码行
	private static long allLines = 0; // 代码行
	private static String workspace = "D:\\code\\java\\step1\\" ;

	public static void main(String[] args) throws Exception {
		//demo
		String AirtisWeb = workspace +  "demo" ;
		CalculateCodeUtil.execute(AirtisWeb,"demo");
		
		
		
	}
	
	
	
	public static void execute(String sourcPath,String systemName) throws Exception {
		init();
		File f = new File(sourcPath); // 目录
		String type = ".java";// 查找什么类型的代码，如".java"就是查找以java开发的代码量，".php"就是查找以PHP开发的代码量
		CalculateCodeUtil.treeFile(f, type);
		System.out.println("系统名称：         " +systemName);
		//System.out.println("路径：" + f.getPath());
		System.out.println("Java类数量：   " + classcount);
		System.out.println("代码数量：          " + writeLines);
		System.out.println("注释数量：         " + commentLines);
		System.out.println("空行数量：          " + normalLines);
		if (classcount == 0) {
			System.out.println("代码平均数量: " + 0);
		} else {
			System.out.println("代码平均数量: " + writeLines / classcount);
		}
		System.out.println("总 行数量：          " + allLines);
		System.out.println("-------------------------");
	}
	
	
	private static void init() {
		classcount = 0; // Java类的数量
		normalLines = 0; // 空行
		commentLines = 0; // 注释行
		writeLines = 0; // 代码行
		allLines = 0; // 代码行
	}
	
	
	

	public static void treeFile(File f, String type) throws Exception {
		File[] childs = f.listFiles();
		for (int i = 0; i < childs.length; i++) {
			File file = childs[i];
			if (!file.isDirectory()) {
				if (file.getName().endsWith(type)) {
					classcount++;
					BufferedReader br = null;
					boolean comment = false;
					br = new BufferedReader(new FileReader(file));
					String line = "";
					while ((line = br.readLine()) != null) {
						allLines++;
						line = line.trim();
						if (line.matches("^[//s&&[^//n]]*$")) {// 这一行匹配以空格开头，但不是以回车符开头，但以回车符结尾
							normalLines++;
						} else if (line.startsWith("/*") && !line.endsWith("*/")) {// 匹配以/*......*/括住的多行注释
							commentLines++;
							comment = true;
						} else if (true == comment) {
							commentLines++;
							if (line.endsWith("*/")) {
								comment = false;
							} // 匹配以//开头的单行注释，及以/*......*/括住的单行注释
						} else if (line.startsWith("//") || (line.startsWith("/*") && line.endsWith("*/"))) {
							commentLines++;
						} else {// 其他的就是代码行
							writeLines++;
						}
					}
					if (br != null) {
						br.close();
						br = null;
					}
				}
			} else {
				treeFile(childs[i], type);
			}
		}
	}
}
