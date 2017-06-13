package com.util;

import java.util.Hashtable;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class TestUtil {

	// Read the all test cases from excel file testcases -> login or leads xls
	// -> Excel file object
	
	
	public static boolean isTestcaseExecutable(String testcases, Xls_Reader xls) {
		for (int rNum = 2; rNum <= xls.getRowCount("Test Cases"); rNum++) {
			if (testcases.equals(xls.getCellData("Test Cases", "TCID", rNum))) {
				//System.out.println(xls.getCellData("Test Cases", "TCID",rNum));
				if (xls.getCellData("Test Cases", "Runmode", rNum).equals("Y")) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	// Read the test data based on the test cases form need
	public static Object[][] getData(String testcases, Xls_Reader xls) {
		int testStartsRowNum = 0;

		for (int rNum = 1; rNum <= xls.getRowCount("Test Data"); rNum++) {
			if (testcases.equals(xls.getCellData("Test Data", 0, rNum))) {
				testStartsRowNum = rNum;
				break;
			}
		}

		System.out.println("Test cases started row number :" + testStartsRowNum);

		int columnStartNum = testStartsRowNum + 1;
		int cols = 0;

		while (!xls.getCellData("Test Data", cols, columnStartNum).equals("")) {
			cols++;
		}

		System.out.println("Test cases total column count are :" + cols);

		int rowStartNum = testStartsRowNum + 2;
		int rows = 0;

		while (!xls.getCellData("Test Data", 0, (rowStartNum + rows)).equals("")) {
			rows++;
		}

		System.out.println("Test cases total rows count are :" + rows);

		Object[][] data = new Object[rows][1];
		// System.out.println(data.length);
		Hashtable<String, String> table = null;

		for (int rNum = rowStartNum; rNum < (rowStartNum + rows); rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < cols; cNum++) {
				table.put(xls.getCellData("Test Data", cNum, columnStartNum), xls.getCellData("Test Data", cNum, rNum));

				System.out.print(xls.getCellData("Test Data", cNum, columnStartNum) + "--"
						+ xls.getCellData("Test Data", cNum, rNum) + " --");
			}
			System.out.println();
			data[rNum - rowStartNum][0] = table;
		}
		return data;

	}

	public static int getNum(String testcases, Xls_Reader xls) {
		int testStartsRowNum = 0;

		for (int rNum = 1; rNum <= xls.getRowCount("Test Data"); rNum++) {
			if (testcases.equals(xls.getCellData("Test Data", 0, rNum))) {
				testStartsRowNum = rNum;
				break;
			}
		}
		System.out.println("Test cases started row number :" + testStartsRowNum);

		int columnStartNum = testStartsRowNum + 1;
		int cols = 0;

		while (!xls.getCellData("Test Data", cols, columnStartNum).equals("")) {
			cols++;
		}

		System.out.println("Test cases total column count are :" + cols);

		int rowStartNum = testStartsRowNum + 2;
		int rows = 0;

		while (!xls.getCellData("Test Data", 0, (rowStartNum + rows)).equals("")) {
			rows++;
		}

		System.out.println("Test cases total rows count are :" + rows);

		Object[][] data = new Object[rows][1];
		System.out.println(data.length);
		Hashtable<String, String> table = new Hashtable<String, String>();

		for (int rNum = rowStartNum; rNum < (rowStartNum + rows); rNum++) {
			for (int cNum = 0; cNum < cols; cNum++) {
				table.put(xls.getCellData("Test Data", cNum, columnStartNum), xls.getCellData("Test Data", cNum, rNum));

				System.out.print(xls.getCellData("Test Data", cNum, columnStartNum) + "--"
						+ xls.getCellData("Test Data", cNum, rNum) + " --");
			}
			System.out.println();
			data[rNum - rowStartNum][0] = table;

		}

		return rows;
	}

}
