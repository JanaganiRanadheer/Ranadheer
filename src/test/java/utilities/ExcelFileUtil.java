package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelFileUtil {
Workbook wb;
// constructor for reading excelpath
public  ExcelFileUtil(String Excelpath) throws Throwable{
	FileInputStream fi = new FileInputStream(Excelpath);
wb = WorkbookFactory.create(fi);
}
// count number of rows in a sheet
public int rowcount(String sheetname) {
	return wb.getSheet(sheetname).getLastRowNum();
}
// method for reading cell data
public String getcelldata(String Sheetname,int row, int column) {
	String data;
	if (wb.getSheet(Sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC) {
		int celldata = (int)wb.getSheet(Sheetname).getRow(row).getCell(column).getNumericCellValue();
		data = String.valueOf(celldata);
	}
	else {
		data = wb.getSheet(Sheetname).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
}
// method for cell data
public void setcelldata(String Sheetname,int row, int columns,String Status, String writeecxelpath)throws Throwable{
	// get sheet from wb
	Sheet ws = wb.getSheet(Sheetname);
	// get row from sheet
	Row rownum = ws.getRow(row);
	//create cell in row
	Cell cell = rownum.createCell(columns);
	// write status
	cell.setCellValue(Status);
	if (Status.equalsIgnoreCase("PASS")) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//Color with green
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		style.setFont(font);
		ws.getRow(row).getCell(columns).setCellStyle(style);
	}
	if (Status.equalsIgnoreCase("FAIL")) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//Color with green
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
		style.setFont(font);
		ws.getRow(row).getCell(columns).setCellStyle(style);
	}
	if (Status.equalsIgnoreCase("BLOCKED")) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//Color with green
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setBold(true);
		style.setFont(font);
		ws.getRow(row).getCell(columns).setCellStyle(style);
	}
	
	FileOutputStream fo = new FileOutputStream(writeecxelpath);
	wb.write(fo);
}
public static void main(String[] args) throws Throwable {
	// create object for class
	ExcelFileUtil xl = new ExcelFileUtil("D:/Sample.xlsx");
	// count no.of rows in Employee sheet
	int rc = xl.rowcount("Employee");
	System.out.println("Number of rows are::"+rc);
	for(int i = 1;i<=rc;i++) {
		// read all rows each cell data
		String Fname = xl.getcelldata("Employee", i, 0);
		String Mname = xl.getcelldata("Employee", i, 1);
		String Lname = xl.getcelldata("Employee", i, 2);
		String id =  xl.getcelldata("Employee", i, 3);
		System.out.println(Fname+" "+Mname+" "+Lname+" "+id);
		// write as 'pass' into status cell
		xl.setcelldata("Employee", 1, 4, "PASS", "D:/Sample.xlsx");
		xl.setcelldata("Employee", 2, 4, "PASS", "D:/Sample.xlsx");
		xl.setcelldata("Employee",3 , 4, "FAIL", "D:/Sample.xlsx");
		xl.setcelldata("Employee", 4, 4, "BLOCKED", "D:/Sample.xlsx");
	}
	
	
}


}
