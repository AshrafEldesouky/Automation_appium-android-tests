package Reportes;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Files;
import java.nio.file.Path;

public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            try {
                Path reportsDir = Path.of("artifacts", "reports");
                Files.createDirectories(reportsDir);
                String reportPath = reportsDir.resolve("ExtentReport.html").toString();

                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
                spark.config().setDocumentTitle("Automation Report");
                spark.config().setReportName("Test Results");
                spark.config().setTheme(Theme.STANDARD);

                extent = new ExtentReports();
                extent.attachReporter(spark);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize ExtentReports", e);
            }
        }
        return extent;
    }
}
