package api.utilties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private static String filePath = "C:\\Users\\Akash\\Documents\\Auromation 2\\RestAPIYouTube\\"
			+ "RestAssuredFrameWorkSwagger\\TestData\\TestDataExcelSheet.xlsx";

    // Constructor to initialize file path
    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }
    // Method to get row count from a sheet
    public int getRowCount(String sheetName) throws IOException {
        FileInputStream fi = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fi);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows(); // Get the number of rows
        workbook.close();
        fi.close();
        return rowCount;
    }

    // Method to get cell count in a specific row from a sheet
    public int getCellCount(String sheetName, int rownum) throws IOException {
        FileInputStream fi = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fi);
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rownum);
        int cellCount = row.getLastCellNum(); // Get the last cell number (number of cells in a row)
        workbook.close();
        fi.close();
        return cellCount;
    }

    // Method to get cell data from a specific row and column
    public String getCellData(String sheetName, int rownum, int cellnum) throws IOException {
        FileInputStream fi = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fi);
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rownum);
        Cell cell = row.getCell(cellnum);

        String cellData = "";

        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    cellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    cellData = String.valueOf(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    cellData = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    cellData = "Unsupported Cell Type";
                    break;
            }
        }
        workbook.close();
        fi.close();
        return cellData;
    }
    
//    public static void main(String[] args) throws IOException {
//    	 ExcelReader reader = new ExcelReader(filePath);
//
//    	 int rowCount = reader.getRowCount("Sheet1");
//         System.out.println("Row count in Sheet1: " + rowCount);
//         
//         
//         int cellCount = reader.getCellCount("Sheet1", 1);
//         System.out.println("Cell count in row 1 of Sheet1: " + cellCount);
//
//         // Example usage of getCellData
//         String cellData = reader.getCellData("Sheet1", 1, 1);
//         System.out.println("Data in row 1, column 1 of Sheet1: " + cellData);
//	}	
}
