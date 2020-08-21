package utilClasses;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RequestDataUtils extends ExcelUtils {
	String requestClassesDir="pojoRequest";
	String responseClassesDir="pojoResponse";
	public RequestDataUtils(String exceLocation) {
		super(exceLocation);
		// TODO Auto-generated constructor stub
	}
	
	public Object[][] getResourceData(String resourceName) {
		List<Integer>rows=new ArrayList<>();
		for(int i=1;i<=getSheet().getLastRowNum();i++) {
			if(getSheet().getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(resourceName)) {
					rows.add(getSheet().getRow(i).getCell(0).getRowIndex());
				}
			}
		int totalRows=rows.size();
		int totalColumns=this.getDataSet(rows.get(0)).length;
		Object[][]dataArray=new Object[totalRows][totalColumns];
		for(int i=0;i<rows.size();i++) {
			for(int j=0;j<totalColumns;j++) {
				dataArray[i][j]=this.getDataSet(rows.get(i))[j];
			}
			}
		
		return dataArray;
		}
		 
	
	
	private Object[] getDataSet(int rowNumber) {
		List<Object>data=new ArrayList<>();
		if(getQueryParameters(rowNumber)!=null) {
			data.add(this.getQueryParameters(rowNumber));
		}
	if(getHeaders(rowNumber)!=null) {
		data.add(this.getHeaders(rowNumber));
		}
	if(this.getPathParameters(rowNumber)!=null) {
		data.add(this.getPathParameters(rowNumber));
		}
	if(this.getFile(rowNumber)!=null) {
		data.add(this.getFile(rowNumber));
		}
	if(this.getBody(rowNumber)!=null) {
		data.add(this.getBody(rowNumber));
		}
	return data.toArray();
	}
	
	private Map<String, Object> returnHashMapObject(String keys) {
		Map<String, Object> bodyMap = new HashMap<>();
		String[] keysPair = keys.split(",");
		for (int i = 0; i < keysPair.length; i++) {
			bodyMap.put(keysPair[i].split("=")[0], keysPair[i].split("=")[1]);
		}
		return bodyMap;
	}

	
	private Map<String,Object> getPathParameters(int resourceRow){
		int column=-1;
		Map<String,Object>map=null;
		for(int i=0;i<getSheet().getRow(0).getLastCellNum();i++) {
			if(getSheet().getRow(0).getCell(i).getStringCellValue().contains("Path")) {
				column=getSheet().getRow(0).getCell(i).getColumnIndex();
			}
		}
		
		map=returnHashMapObject(getSheet().getRow(resourceRow).getCell(column).getStringCellValue());	
		
	
		return map;
	}
	private Map<String,Object> getQueryParameters(int resourceRow){
		int column=-1;
		Map<String,Object>map=null;
		for(int i=0;i<getSheet().getRow(0).getLastCellNum();i++) {
			if(getSheet().getRow(0).getCell(i).getStringCellValue().contains("query")) {
				column=getSheet().getRow(0).getCell(i).getColumnIndex();
			}
		}
		
		map=returnHashMapObject(getSheet().getRow(resourceRow).getCell(column).getStringCellValue());	
		
	
		return map;
	}
	
	private Map<String, Object> returnBodyMap(String keys) {
		Map<String, Object> bodyMap = null;
		String[] keysPair = keys.split(",");
		if( keysPair.length>1) {
			bodyMap=new HashMap<>();
		for (int i = 0; i < keysPair.length; i++) {
			bodyMap.put(keysPair[i].split("=")[0], keysPair[i].split("=")[1]);
		}
		}
		else
		{
			bodyMap=new HashMap<>();
			bodyMap.put(keysPair[0].split("=")[0], keysPair[0].split("=")[1]);

		}
		return bodyMap;
	}
	
	private Map<String,Object> getHeaders(int resourceRow){
		int column=-1;
		Map<String,Object>map=new HashMap<>();
		for(int i=0;i<getSheet().getRow(0).getLastCellNum();i++) {
			if(getSheet().getRow(0).getCell(i).getStringCellValue().contains("headers")) {
				column=getSheet().getRow(0).getCell(i).getColumnIndex();
			}
		}
			map=returnHashMapObject(getSheet().getRow(resourceRow).getCell(column).getStringCellValue());	
		
	
		return map;
	}
	private File getFile(int resourceRow){
		int column=-1;
		for(int i=0;i<getSheet().getRow(0).getLastCellNum();i++) {
			if(getSheet().getRow(0).getCell(i).getStringCellValue().contains("query")) {
				column=getSheet().getRow(0).getCell(i).getColumnIndex();
			}
		}
		File file=new File(getSheet().getRow(resourceRow).getCell(column).getStringCellValue());
	
		return file;
	}

	private Object getBody(int resourceRow){
		Object object=null;
		for(int i=0;i<getSheet().getRow(0).getLastCellNum();i++) {
			if(getSheet().getRow(0).getCell(i).getStringCellValue().contains("body")) {
				
				String className=requestClassesDir+"."+getSheet().getRow(resourceRow).getCell(i).getStringCellValue().trim();
			
				try {
					object=Class.forName(className).newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Field[] fields=object.getClass().getDeclaredFields();
				int totalFields=fields.length;
			
					Map<String,Object>keys=this.returnBodyMap((String)returnCellValue(resourceRow,i));
					Iterator<String>keysValue=keys.keySet().iterator();			
					while(keysValue.hasNext()) {
						for(int j=0;i<totalFields;i++) {
							String nextKey=keysValue.next();
							if(fields[j].getName().equalsIgnoreCase(nextKey)) {
								try {
									fields[j].set(object,keys.get(nextKey));
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
					}
	
			
			}
		}
		return object;	
	}
	}
