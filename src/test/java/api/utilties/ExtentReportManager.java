package api.utilties;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;
	
	 public void onStart(ITestContext testContext) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";
		// Set the location where the report will be generated
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // Replace with the path you want

		sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");
		// Configure the SparkReporter (optional, e.g., adding a report name)
		sparkReporter.config().setReportName("Automated Test Report");
		sparkReporter.config().setTheme(Theme.DARK);

		// Initialize the ExtentReports object
		extent = new ExtentReports();
		// Attach the reporter to the extent object
		extent.attachReporter(sparkReporter);
	}

	// Your other methods like onTestSuccess, onTestFailure, etc.
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS, "Test Passed");

	}
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.FAIL, "Test Failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());

	}
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.SKIP, "Test Skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());

	}
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
}
