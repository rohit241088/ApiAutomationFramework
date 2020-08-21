package utilClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private Workbook wbook = null;
	private Sheet sheet = null;
	private FileInputStream in = null;

	public ExcelUtils(String exceLocation) {

		try {
			in = new FileInputStream(exceLocation);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String excelExtension = exceLocation.split("\\.")[1];
		try {
			switch (excelExtension) {
			case "xlsx":
				wbook = new XSSFWorkbook(in);
				break;
			case "xls":
				wbook = new HSSFWorkbook(in);
				break;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(String sheetName) {
sheet=wbook.getSheet(sheetName);

	}
	
	
	
	
	
	
	
	public Object[] getRowData(int RowIndex) {
		int totalcolumns=sheet.getRow(RowIndex).getLastCellNum();
		
		Object[]column=new Object[totalcolumns];
		for(int i=0;i<sheet.getRow(RowIndex).getLastCellNum();i++) {
			column[i]=returnCellValue(RowIndex, i);
		}
		return column;
		}
	
	public Object[] getColumnData(int columnIndex) {
		Object[]data=new Object[sheet.getLastRowNum()+1];
		for(int i=0;i<=sheet.getLastRowNum();i++) {
			data[i]=returnCellValue(i, columnIndex);
		}
		return data;
		
	}
	
	
	public Map<String,Integer>getColumnMap(int RowIndex){
		Map<String,Integer>data=new HashMap<>();
		for(int i=0;i<sheet.getRow(RowIndex).getLastCellNum();i++) {
			data.put((String)returnCellValue(RowIndex, i),(Integer)sheet.getRow(RowIndex).getCell(i).getColumnIndex());
		}
		return data;
		
		}
	
	public Map<String,Integer>getRowMap(int columnIndex){
		Map<String,Integer>data=new HashMap<>();
		for(int i=0;i<=sheet.getLastRowNum();i++) {
			data.put((String)returnCellValue(columnIndex, i),(Integer)sheet.getRow(columnIndex).getCell(i).getRowIndex());
		}
		return data;
		
		}
	public Object returnCellValue(int rowNum, int cellNum) {
		Object cellValue=null;
	
			if(sheet.getRow(rowNum)==null) {
			return null;
		}
			CellType cellType=sheet.getRow(rowNum).getCell(cellNum).getCellType();
			switch(cellType) {
			case BLANK:
				cellValue=null;
				break;
			case BOOLEAN:
				cellValue=sheet.getRow(rowNum).getCell(cellNum).getBooleanCellValue();
				break;
			case ERROR:
				cellValue=sheet.getRow(rowNum).getCell(cellNum).getErrorCellValue();
				break;
			case FORMULA:
				cellValue=sheet.getRow(rowNum).getCell(cellNum).getCellFormula();
				break;
			case NUMERIC:
				cellValue=sheet.getRow(rowNum).getCell(cellNum).getNumericCellValue();
				break;
			case STRING:
				cellValue=sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
				break;
			case _NONE:
				cellValue=null;
				break;
		
				
			}
			
			if(DateUtil.isCellDateFormatted(sheet.getRow(rowNum).getCell(cellNum))) {
				cellValue=sheet.getRow(rowNum).getCell(cellNum).getDateCellValue();
			}
			
	
	return cellValue;
	}
	
	public Object[][] getDataRange(int start,int endRow, int startColumn, int endColumn){
		int TotalRows=endRow-start;
		int totalColumns=endColumn-startColumn;
		Object[][]data=new Object[TotalRows][totalColumns];
			for(int i=0;i<=endRow;i++) {
			for(int j=0;j<=totalColumns;j++) {
				
				data[i][j]=returnCellValue(start, startColumn);
				
				startColumn++;
			}
			start++;
		}
			return data;
		
	}
	
	
	
	
}
