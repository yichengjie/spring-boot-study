package com.yicj.study.busi;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XPathDemo {
	
	
	 public static void selectNodes(String basepath,String filename) throws Exception {
		 Document doc = new SAXReader().read(new File(basepath +filename));
	        /**
	         * @param xpath 表示xpath语法变量
	         */
	    	String xpath="";
	        /**
	         * 1.      /      绝对路径      表示从xml的根位置开始或子元素（一个层次结构）
	         */
	        xpath = "/contactList";
	        /**
	         * 2. //     相对路径       表示不分任何层次结构的选择元素。
	         */
	        xpath = "//contact/name";
	        xpath = "//name";
	        
	        /**
	         * 3. *      通配符         表示匹配所有元素
	         */
	        xpath = "/contactList/*"; //根标签contactList下的所有子标签
	        xpath = "/contactList//*";//根标签contactList下的所有标签（不分层次结构）
	        /**
	         * 4. []      条件           表示选择什么条件下的元素
	         */
	        //带有id属性的contact标签
	        xpath = "//contact[@id]";
	        //第二个的contact标签
	        xpath = "//contact[2]";
	        //选择最后一个contact标签
	        xpath = "//contact[last()]";
	        /**
	         * 5. @     属性            表示选择属性节点
	         */
	        xpath = "//@id"; //选择id属性节点对象，返回的是Attribute对象
	        xpath = "//contact[not(@id)]";//选择不包含id属性的contact标签节点
	        //xpath = "//contact[@id='002']";//选择id属性值为002的contact标签
	        //xpath = "//contact[@id='001' and @name='eric']";//选择id属性值为001，且name属性为eric的contact标签
	        /**
	         *6.  text()   表示选择文本内容
	         */
	        //选择name标签下的文本内容，返回Text对象
	        //xpath = "//name/text()";
	        //xpath = "//contact/name[text()='张三']";//选择姓名为张三的name标签
	        List<Node> list = doc.selectNodes(xpath);
	        for (Node node : list) {
	            System.out.println(node);
	        }
	        //写出xml文件
	        //输出位置
//	        FileOutputStream out = new FileOutputStream(basepath + "contact.xml");
//	        //指定格式
//	        OutputFormat format = OutputFormat.createPrettyPrint();
//	        format.setEncoding("utf-8");
//	        XMLWriter writer = new XMLWriter(out,format);
//	        //写出内容
//	        writer.write(doc);
//	        //关闭资源
//	        writer.close();
	 }
	 
	 public static void selectSingleNode(String basepath,String filename) throws DocumentException {
		 //读取XML文件，获得document对象
	        SAXReader saxReader = new SAXReader();    
	        Document doc = saxReader.read(new File(basepath+filename));
	        //使用xpath获取某个节点
	        String xpath = "";
	        //对contact元素 id="001"的节点，操作
	        xpath = "//contact[@id = '001']";
	        Element contactElem =  (Element)doc.selectSingleNode(xpath);
	        //设置这个节点的属性值
	        contactElem.addAttribute("name", "001");
	        //输出这个节点的所有属性值
	        for(Iterator it = contactElem.attributeIterator();it.hasNext();){
		        Attribute conAttr = (Attribute)it.next();
		        String conTxt = conAttr.getValue();
		        String conAttrName = conAttr.getName();
		        System.out.println(conAttrName+" = "+conTxt);
	        }
	 }
	
	 public static void main(String[] args) throws Exception {
		    String basepath = "/Users/yicj/Desktop/test/" ;
		    String filename = "xpath-demo.xml" ;
		    //selectNodes(basepath,filename);
		    selectSingleNode(basepath, filename);
	 }

}
