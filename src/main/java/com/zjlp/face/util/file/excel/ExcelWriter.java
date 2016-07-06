package com.zjlp.face.util.file.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.Assert;

import com.zjlp.face.util.date.DateUtil;

/**
 * 1.一个sheet的数据结构需保持一致
 * 
 * 2.可以有多个sheet同时存在于一个工作薄
 * 
 * 3.支持列宽的定义
 * 
 * 4.暂时不支持各种样式的写入
 * 
 * 5.特定的数据结构需实现其toString方法
 * 
 * @ClassName: ExcelWriter 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2014年12月23日 下午3:22:59
 */
public class ExcelWriter {

	private Logger log = Logger.getLogger(getClass());
	//默认列宽
	private static final Integer DEFAULT_COLUMN_WIDTH = 15;
	//声明一个工作薄
	private HSSFWorkbook workbook = null;
	//sheet集合
	private List<MySheet<?>> sheets = null;
	
	public ExcelWriter() {
		this.workbook = new HSSFWorkbook();
		sheets = new ArrayList<MySheet<?>>();
	}
	
	/**
	 * 新增sheet
	 * @Title: addSheet 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param title
	 * @param clazz
	 * @return
	 * @date 2014年12月23日 下午3:22:41  
	 * @author Administrator
	 */
	public <E> MySheet<E> addSheet(String title, Class<E> clazz) {
		MySheet<E> sheet = new MySheet<E>(title);
		sheets.add(sheet);
		return sheet;
	}
	
	/**
	 * 工作薄对象生成
	 * @Title: build 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @throws Exception
	 * @date 2014年12月23日 下午3:22:15  
	 * @author Administrator
	 */
	public ExcelWriter build() throws Exception {
		if (null == sheets || sheets.isEmpty()) {
			throw new Exception("no sheet in this book.");
		}
		for (MySheet<?> sheet : sheets) {
			sheet.build();
		}
		return this;
	}
	
	/**
	 * 写文件
	 * @Title: write 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param stream
	 * @throws IOException
	 * @date 2014年12月23日 下午3:22:04  
	 * @author Administrator
	 */
	public void write(OutputStream stream) throws IOException {
		this.workbook.write(stream);
	}
	
	class MySheet<T> {

		//表格
		private HSSFSheet sheet = null;
		//标题样式
		private HSSFCellStyle headerStyle = null;
		//标题字体
		private HSSFFont headerFont = null;
		//数据样式
		private HSSFCellStyle bodyStyle = null;
		//数据字体
		private HSSFFont bodyFont = null;
		//标题
		private Collection<String> header = null;
		//数据
		private Collection<T> datas = null;
		//反射类
		private Class<?> clazz;
		//字段名
		private List<String> fildsList;
		
		
		MySheet(String title){
			sheet = workbook.createSheet(title);
			sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
			headerStyle = workbook.createCellStyle();
			headerFont = workbook.createFont();
			bodyStyle = workbook.createCellStyle();
			bodyFont = workbook.createFont();
			datas = new ArrayList<T>();
			fildsList = new ArrayList<String>();
		}
		
		/**
		 * 标题数据输入(可选)
		 * @Title: setHeader 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @param header
		 * @return
		 * @date 2014年12月23日 下午3:21:00  
		 * @author Administrator
		 */
		public MySheet<T> setHeader(Collection<String> header) {
			this.header = header;
			return this;
		}
		
		/**
		 * 设置列宽(可选)
		 * @Title: setColumnWidth 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @param w
		 * @return
		 * @date 2014年12月23日 下午3:20:46  
		 * @author Administrator
		 */
		public MySheet<T> setColumnWidth(int w) {
			sheet.setDefaultColumnWidth(w);
			return this;
		}
		
		/**
		 * 压入数据
		 * @Title: addData 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @param data
		 * @return
		 * @date 2014年12月23日 下午3:20:41  
		 * @author Administrator
		 */
		public MySheet<T> addData(T data) {
			datas.add(data);
			return this;
		}
		
		/**
		 * 压入数据
		 * @Title: addDatas 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @param data
		 * @return
		 * @date 2014年12月23日 下午3:20:31  
		 * @author Administrator
		 */
		public MySheet<T> addDatas(Collection<T> data) {
			datas.addAll(data);
			return this;
		}
		
		
		/**
		 * 生成
		 * @Title: build 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @throws Exception
		 * @date 2014年12月23日 下午3:19:12  
		 * @author Administrator
		 */
		public void build() throws Exception {
			if (null == datas && datas.isEmpty()) {
				throw new Exception("no data in this sheet.");
			}
			initReflectInfo();
			initHeader();
			//样式
			headerStyle.setFont(headerFont);
			bodyStyle.setFont(bodyFont);
			//值设置
			int index = 0;
			this.setHeader(sheet.createRow(index++), headerStyle, header);
			for (T data : datas) {
				this.setRow(sheet.createRow(index++), bodyStyle, data);
			}
		}
		
		/**
		 * 标题数据处理
		 * @Title: initHeader 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @throws Exception
		 * @date 2014年12月23日 下午3:20:11  
		 * @author Administrator
		 */
		private void initHeader() throws Exception {
			if (null != header && !header.isEmpty()) {
				return;
			}
			header = new ArrayList<String>();
			header.addAll(fildsList);
		}

		/**
		 * 头数据设定
		 * @Title: setHeader 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @param row
		 * @param style
		 * @param header
		 * @date 2014年12月23日 下午3:19:02  
		 * @author Administrator
		 */
		private void setHeader(HSSFRow row, HSSFCellStyle style, Collection<?> header) {
			Assert.notEmpty(header, "title's value is empty.");
			Iterator<?> iterator = header.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				HSSFCell cell = row.createCell(i++);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(iterator.next().toString());
				cell.setCellValue(text);
			}
		}
		
		/**
		 * 行数据设定
		 * @Title: setRow 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @param row
		 * @param style
		 * @param data
		 * @throws Exception
		 * @date 2014年12月23日 下午3:18:48  
		 * @author Administrator
		 */
		private void setRow(HSSFRow row, HSSFCellStyle style, T data) throws Exception {
			Assert.notNull(data, "data can not be null.");
			StringBuilder sb = new StringBuilder();
			String fieldName = null;
			for (int i = 0; i < fildsList.size(); i++) {
				fieldName = fildsList.get(i);
				log.info(fieldName);
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				String getMethodName = sb.append("get").append(fieldName.substring(0, 1).toUpperCase())
						                 .append(fieldName.substring(1)).toString();
				Method getMethod = clazz.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(data, new Object[] {});
				cell.setCellValue(coverObjectValue(value));
				sb.delete(0, sb.length());
			}
		}
		
		/**
		 * 数据信息处理
		 * @Title: initReflectInfo 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @date 2014年12月23日 下午3:19:38  
		 * @author Administrator
		 */
		private void initReflectInfo() {
			this.clazz = datas.iterator().next().getClass();
			Field[] fields = clazz.getDeclaredFields();
			Assert.notEmpty(fields, "no fild in this sheet.");
			for (Field field : fields) {
				fildsList.add(field.getName());
			}
		}
		
		/**
		 * 值转换
		 * @Title: coverObjectValue 
		 * @Description: (这里用一句话描述这个方法的作用) 
		 * @param obj
		 * @return
		 * @date 2014年12月23日 下午3:18:32  
		 * @author Administrator
		 */
		private String coverObjectValue(Object obj) {
			String value = null;
			if (obj instanceof Date) {
				value = DateUtil.DateToString((Date)obj, "yyyyMMdd");
			} else {
				value = String.valueOf(obj);
			}
			return value;
		}
	}
	
	public static void main(String[] args) {
		OutputStream out = null;
		try {
			// 测试学生
			String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
			List<Student> dataset = new ArrayList<Student>();
			dataset.add(new Student(10000001, "张三", 20, true, new Date()));
			dataset.add(new Student(20000002, "李四", 24, false, new Date()));
			dataset.add(new Student(30000003, "王五", 22, true, new Date()));
			// 测试图书
//			String[] headers2 = { "图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN",
//					"图书出版社", "封面图片" };
			List<Book> dataset2 = new ArrayList<Book>();
			dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567", "清华出版社"));
			dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567", "阳光出版社"));
			dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567", "清华出版社"));
			dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567", "清华出版社"));
			dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567", "汤春秀出版社"));

			out = new FileOutputStream("D://a.xls");
			ExcelWriter book = new ExcelWriter();
			book.addSheet("sheet 1", Student.class).setHeader(Arrays.asList(headers)).addDatas(dataset);
//			book.addSheet("sheet 2", Book.class).setHeader(Arrays.asList(headers2)).addDatas(dataset2);
//			book.addSheet("sheet 1", Student.class).addDatas(dataset);
			book.addSheet("sheet 2", Book.class).addDatas(dataset2);
			book.build().write(out);
			JOptionPane.showMessageDialog(null, "导出成功!");
			System.out.println("excel导出成功！");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "导出失败!");
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		
	}
	
}
