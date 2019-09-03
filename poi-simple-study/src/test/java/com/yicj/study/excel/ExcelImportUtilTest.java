package com.yicj.study.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.yicj.study.dto.UserDTO;
import com.yicj.study.excel.imp.ExcelImportUtil;

public class ExcelImportUtilTest {
	
	@Test
	public void testExportExcel() throws Exception {
		String name = "D:\\opt\\applog\\excel\\1567471512354.xls";
        File file = new File(name);
        InputStream inputStream = null ;
        try {
        	inputStream = new FileInputStream(file);
            List<?> list = ExcelImportUtil.importExcel(inputStream, UserDTO.class, ExcelType.xls);
            list.forEach(System.out::println);
		} finally {
			if(inputStream!=null) {
				inputStream.close();
			}
		}
        
       
	}

}
