package com.yicj.study.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.yicj.study.dto.UserDTO;

public class ExcelExportUtilTest {
	
	
	@Test
	public void testExportExcel() throws Exception {
		String name = "D:\\opt\\applog\\excel\\" + System.currentTimeMillis()+".xls";
        File file = new File(name);
        OutputStream outputStream = new FileOutputStream(file);
        try {
        	List<UserDTO> list = new ArrayList<UserDTO>();
            list.add(new UserDTO(1,"王尼玛", 1, "C语言", 22));
            list.add(new UserDTO(1,"王尼玛", 1, "C++", 33));
            list.add(new UserDTO(2,"葫芦娃", 1, "Python", 50));
            list.add(new UserDTO(2,"葫芦娃", 1, "java", 44));
            list.add(new UserDTO(2,"葫芦娃", 1, "PHP", 66));
            list.add(new UserDTO(3,"佩奇", 2, "Python", 77));
            list.add(new UserDTO(3,"佩奇", 2, "java", 54));
            list.add(new UserDTO(3,"佩奇", 2, "PHP", 82));
            list.add(new UserDTO(4,"乔治", 1, "Python", 63));
            list.add(new UserDTO(4,"乔治", 1, "java", 77));
            list.add(new UserDTO(4,"乔治", 1, "PHP", 72));
            list.add(new UserDTO(5,"熊大", 1, "Python", 88));
            list.add(new UserDTO(5,"熊大", 1, "java", 91));
            list.add(new UserDTO(5,"熊大", 1, "PHP", 12));
            ExcelExportUtil.exportExcel("测试", list, outputStream);
		} finally {
			if(outputStream!=null) {
				outputStream.close();
			}
		}
        
	}

}
