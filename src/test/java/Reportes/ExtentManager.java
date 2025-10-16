package Reportes;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports get() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("artifacts/reports/ExtentReport.html");
            spark.config().setReportName("Android Automation Report");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}
