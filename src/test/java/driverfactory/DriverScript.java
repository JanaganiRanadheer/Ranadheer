package driverfactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonfunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	String inputpath = "./FileInput/DataEngine.xlsx";
	String outputpath = "./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String Testcases = "MasterTestCases";

	@Test
	public void starttest() throws Throwable {
		String Module_Status = "";
		String Module_New = "";
		// create object for Excelfileutil class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		// iterate all test cases in Testcases
		for (int i = 1; i <= xl.rowcount(Testcases); i++) {
			if (xl.getcelldata(Testcases, i, 2).equalsIgnoreCase("Y")) {
				// read corresponding sheet or testcases
				String TCModule = xl.getcelldata(Testcases, i, 1);
				// define path of html
				report = new ExtentReports(
						"./target/ExtentReports/" + TCModule + FunctionLibrary.generateDate() + ".html");
				logger = report.startTest(TCModule);
				logger.assignAuthor("Rana");
				// iterate all rows in TCmodule sheet
				for (int j = 1; j <= xl.rowcount(TCModule); j++) {
					// read all cell from TCmodule sheet
					String Description = xl.getcelldata(TCModule, j, 0);
					String Objecttype = xl.getcelldata(TCModule, j, 1);
					String Lname = xl.getcelldata(TCModule, j, 2);
					String Lvalue = xl.getcelldata(TCModule, j, 3);
					String Testdata = xl.getcelldata(TCModule, j, 4);
					try {
						if (Objecttype.equalsIgnoreCase("startBrowser")) {
							driver = FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if (Objecttype.equalsIgnoreCase("openurl")) {
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);

						}
						if (Objecttype.equalsIgnoreCase("waitforelement")) {
							FunctionLibrary.waitForElement(Lname, Lvalue, Testdata);
							logger.log(LogStatus.INFO, Description);

						}
						if (Objecttype.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(Lname, Lvalue, Testdata);
							logger.log(LogStatus.INFO, Description);

						}
						if (Objecttype.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(Lname, Lvalue);
							logger.log(LogStatus.INFO, Description);

						}
						if (Objecttype.equalsIgnoreCase("validateTitle")) {
							FunctionLibrary.validateTitle(Testdata);
							logger.log(LogStatus.INFO, Description);

						}
						if (Objecttype.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);

						}
						if(Objecttype.equalsIgnoreCase("mouseClick"))
						{
							FunctionLibrary.mouseClick();
							logger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("categoryTable"))
						{
							FunctionLibrary.categoryTable(Testdata);
							logger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Lname, Lvalue, Testdata);
							logger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("captureStock"))
						{
							FunctionLibrary.captureStock(Lname, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("capturesupplier")) {
							FunctionLibrary.capturesupplier(Lname, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("captureCustomer"))
						{
							FunctionLibrary.captureCustomer(Lname, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("customerTable"))
						{
								FunctionLibrary.customerTable();
								logger.log(LogStatus.INFO, Description);
						}
						// write as pass in TCmodule sheet in status cell
						xl.setcelldata(TCModule, j, 5, "PASS", outputpath);
						logger.log(LogStatus.PASS, Description);
						Module_Status = "True";
					} catch (Exception e) {
						System.out.println(e.getMessage());
						// write as fail in TCmodule sheet in status cell
						xl.setcelldata(TCModule, j, 5, "FAIL", outputpath);
						logger.log(LogStatus.FAIL, Description);
						Module_New = "False";
					}
					if (Module_Status.equalsIgnoreCase("True")) {
						// write as pass in Testcases sheet in status cell
						xl.setcelldata(Testcases, i, 3, "PASS", outputpath);
					} 
					if (Module_New.equalsIgnoreCase("FALSE")) {
						xl.setcelldata(Testcases, i, 3, "FAIL", outputpath);

					}
					
					report.endTest(logger);
					report.flush();
				}
			} else {
				// write as blocked testcases flag to N in Testcases sheet
				xl.setcelldata(Testcases, i, 3, "Blocked", outputpath);
			}
		}

	}
}
