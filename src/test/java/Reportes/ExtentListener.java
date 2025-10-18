package Reportes;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * ITestListener آمن ضد NullPointer:
 * حتى لو الفشل حصل قبل onTestStart (مثلاً في @BeforeMethod)،
 * نستخدم ensureTest() علشان ننشئ TestNode قبل ما نعمل fail/skip.
 */
public class ExtentListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> TL = new ThreadLocal<>();
    // NOTE: بنعتمد على ExtentManager.getInstance() اللي تحت في الملف رقم (2)
    private static final ExtentReports extent = ExtentManager.getInstance();

    /** بيرجع TestNode الحالي أو ينشئ واحد مؤقت لو مش موجود */
    private ExtentTest ensureTest(ITestResult r, String fallbackLabel) {
        ExtentTest t = TL.get();
        if (t == null) {
            String cls = (r.getTestClass() != null && r.getTestClass().getRealClass() != null)
                    ? r.getTestClass().getRealClass().getSimpleName()
                    : "UnknownClass";
            String method = (r.getMethod() != null && r.getMethod().getMethodName() != null)
                    ? r.getMethod().getMethodName()
                    : fallbackLabel; // "setup" عادة
            t = extent.createTest(cls + " :: " + method);
            TL.set(t);
        }
        return t;
    }

    @Override
    public void onStart(ITestContext context) {
        // ممكن تضيف لوج لو حابب
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult r) {
        String cls = r.getTestClass().getRealClass().getSimpleName();
        String method = r.getMethod().getMethodName();
        TL.set(extent.createTest(cls + " :: " + method));
    }

    @Override
    public void onTestSuccess(ITestResult r) {
        ensureTest(r, "setup").pass("Passed");
    }

    @Override
    public void onTestFailure(ITestResult r) {
        ensureTest(r, "setup").fail(r.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult r) {
        Throwable th = (r.getThrowable() != null) ? r.getThrowable() : new RuntimeException("Skipped");
        ensureTest(r, "setup").skip(th);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult r) { }

    @Override
    public void onTestFailedWithTimeout(ITestResult r) {
        onTestFailure(r);
    }

    public static void log(String message) {
        ExtentTest t = TL.get();
        if (t != null) t.info(message);
    }

}
