/*
 * Copyright (c) 2023.
 */

package Utils;

import com.shaft.tools.internal.support.JavaHelper;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.tools.io.ReportManager;
import com.shaft.tools.io.internal.ReportManagerHelper;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.*;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtraExcelFun extends ExcelFileManager {

    /**
     * Creates a new instance of the test data Excel reader using the target Excel
     * file path
     */
    private void initializeVariables() {
        fis = null;
        workbook = null;
        sheet = null;
        row = null;
        cell = null;
        excelFilePath = "";
        testDataColumnNamePrefix = System.getProperty("testDataColumnNamePrefix");
    }


    /**
     * Creates a new instance of the test data Excel reader using the target Excel
     * file path
     *
     * @param excelFilePath target test data Excel file path
     */
    public ExtraExcelFun(String excelFilePath) {
        super(excelFilePath);
        excelFilePath = JavaHelper.appendTestDataToRelativePath(excelFilePath);
        initializeVariables();
        this.excelFilePath = excelFilePath;
        try {
            fis = new FileInputStream(excelFilePath);
            workbook = new XSSFWorkbook(fis);
            XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
      //      XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

            workbook.setForceFormulaRecalculation(true);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            evaluator.evaluateAll();
       //     evaluator.
            evaluator.evaluateAll();
            evaluator.evaluateAll();
           // evaluator.evaluateAll();

            // FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
           // CellValue cellValue = evaluator.evaluate(cell);
            fis.close();
//            ReportManager.logDiscrete("Reading test data from the following file [" + excelFilePath + "].");
        } catch (IOException e) {
            ReportManagerHelper.log(e);
            ReportManager.log("Couldn't find the desired file. [" + excelFilePath + "].");
            Assert.fail("Couldn't find the desired file. [" + excelFilePath + "].");
        } catch (OutOfMemoryError e) {
//	    ReportManagerHelper.log(e); override function to be able to log errors
            ReportManager.log("Couldn't open the desired file. [" + excelFilePath + "].");
            Assert.fail("Couldn't open the desired file. [" + excelFilePath + "].");
        } catch (EmptyFileException e) {
            ReportManagerHelper.log(e);
            ReportManager.log("Please check the target file, as it may be corrupted. [" + excelFilePath + "].");
            Assert.fail("Please check the target file, as it may be corrupted. [" + excelFilePath + "].");
        }

        List<List<Object>> attachments = new ArrayList<>();
        List<Object> testDataFileAttachment = null;
        try {
            testDataFileAttachment = Arrays.asList("Test Data", "Excel",
                    new FileInputStream(excelFilePath));
        } catch (FileNotFoundException e) {
            //unreachable code because if the file was not found then the reader would have failed at a previous step
        }
        attachments.add(testDataFileAttachment);
        ReportManagerHelper.log("Loaded Test Data: \"" + excelFilePath + "\".", attachments);
    }


    private FileInputStream fis;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;
    private String excelFilePath;
     String testDataColumnNamePrefix;

    public int CountRowsHasSpecificText(String sheetName, String rowName) {
        //     try {
        // get the row number that corresponds to the desired rowName within the first
        // column [0]
        sheet = workbook.getSheet(sheetName);
        int count = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            // get the first cell of each row, and compare it to rowName
            // if they match then that's the row we want

            if (row != null && row.getCell(0).getStringCellValue().contains(rowName)) {
                count++;
            }
            // in certain cases if the row is empty, its value is set to null, and hence a
            // null pointer exception is thrown when
            // you try to get the cell from it.
            // we can skip this exception by checking if row != null.
        }
        return count;


    }
    private int getRowNumberFromRowName(String sheetName, String rowName) {
        try {
            // get the row number that corresponds to the desired rowName within the first
            // column [0]
            sheet = workbook.getSheet(sheetName);

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                // get the first cell of each row, and compare it to rowName
                // if they match then that's the row we want

                if (row != null && row.getCell(0).getStringCellValue().equals(rowName)) {
                    return i;
                }
                // in certain cases if the row is empty, its value is set to null, and hence a
                // null pointer exception is thrown when
                // you try to get the cell from it.
                // we can skip this exception by checking if row != null.
            }

            // in case you provided valid data type, no exceptions were thrown, and yet the
            // rowName you mentioned was not present in this sheet
            ReportManager.log(
                    "Failed to get the row number that coresponds to rowName [" + rowName + "] in the Test Data Sheet ["
                            + sheetName + "], under the following path [" + excelFilePath + "].");
            Assert.fail(
                    "Failed to get the row number that coresponds to rowName [" + rowName + "] in the Test Data Sheet ["
                            + sheetName + "], under the following path [" + excelFilePath + "].");
            return -1; // in case of failure this line is unreachable
        } catch (Exception e) {
            ReportManagerHelper.log(e);
            ReportManager.log(
                    "Failed to get the row number that coresponds to rowName [" + rowName + "] in the Test Data Sheet ["
                            + sheetName + "], under the following path [" + excelFilePath + "].");
            Assert.fail(
                    "Failed to get the row number that coresponds to rowName [" + rowName + "] in the Test Data Sheet ["
                            + sheetName + "], under the following path [" + excelFilePath + "].");
            return -1; // in case of failure this line is unreachable
        }
    }
    private int getColumnNumberFromColumnName(String sheetName, String columnName) {
        try {
            // get the column number that corresponds to the desired columnName within the
            // target row [row_Num]
            // if no column name is provided, retrieves data from the 2nd
            // column (1st Value in the test data file)
            if (!columnName.equals("")) {
                row = sheet.getRow(0);
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    // get the first cell of each column, and compare it to columnName
                    // if they match then that's the column we want
                    if (row.getCell(i).getStringCellValue().equals(columnName)) {
                        return i;
                    }
                }
            } else {
                return 1;
            }

            // in case you provided valid data type, no exceptions were thrown, and yet the
            // columnName you mentioned was not present in this sheet
            ReportManager.log("Failed to get the column number that coresponds to columnName [" + columnName
                    + "] in the Test Data Sheet [" + sheetName + "], under the following path [" + excelFilePath
                    + "].");
            Assert.fail("Failed to get the column number that coresponds to columnName [" + columnName
                    + "] in the Test Data Sheet [" + sheetName + "], under the following path [" + excelFilePath
                    + "].");
            return -1; // in case of failure this line is unreachable
        } catch (Exception e) {
            ReportManagerHelper.log(e);
            ReportManager.log("Failed to get the column number that coresponds to columnName [" + columnName
                    + "] in the Test Data Sheet [" + sheetName + "], under the following path [" + excelFilePath
                    + "].");
            Assert.fail("Failed to get the column number that coresponds to columnName [" + columnName
                    + "] in the Test Data Sheet [" + sheetName + "], under the following path [" + excelFilePath
                    + "].");
            return -1; // in case of failure this line is unreachable
        }
    }

    public void AddExpectedResult(String sheetName, String rowName,String columnName,String ExpectedResult) throws IOException {
        //     try {
        // get the row number that corresponds to the desired rowName within the first
        // column [0]
        excelFilePath = JavaHelper.appendTestDataToRelativePath(excelFilePath);

        FileInputStream ExcelFile = new FileInputStream(excelFilePath);
        workbook = new XSSFWorkbook(ExcelFile);
        sheet = workbook.getSheet(sheetName);
        int rowNum = getRowNumberFromRowName(sheetName, rowName);
        int columnNum = getColumnNumberFromColumnName(sheetName, columnName);

        row = sheet.getRow(rowNum);
        cell=row.getCell(columnNum);
            // get the first cell of each row, and compare it to rowName
            // if they match then that's the row we want

            if (cell == null ) {
                 cell = row.createCell(columnNum);
            
                cell.setCellValue(ExpectedResult);
            } else {
                cell.setCellValue(ExpectedResult);
            }
            
      //  initializeVariables();
       // this.excelFilePath = excelFilePath;
       // workbook.setForceFormulaRecalculation();

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
        workbook.setForceFormulaRecalculation(true);
        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
        workbook.setForceFormulaRecalculation(true);
        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
        workbook.setForceFormulaRecalculation(true);
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.evaluateAll();
        //     evaluator.
        evaluator.evaluateAll();
        evaluator.evaluateAll();

        try {
            ExcelFile.close();
            FileOutputStream fos  = new FileOutputStream(excelFilePath);
            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //  }
    }



    }