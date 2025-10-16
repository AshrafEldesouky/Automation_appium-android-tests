package Reportes;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Deque;

public class ExtentListener implements ITestListener {
    private static final ThreadLocal<ExtentTest> TL = new ThreadLocal<>();
    private ExtentReports extent;

    @Override public void onStart(ITestContext ctx) { extent = ExtentManager.get(); }
    @Override public void onFinish(ITestContext ctx) { if (extent != null) extent.flush(); }

    @Override public void onTestStart(ITestResult r) {
        String cls = r.getTestClass().getRealClass().getSimpleName();
        String method = r.getMethod().getMethodName();
        TL.set(extent.createTest(cls + " :: " + method));
    }

    @Override public void onTestSuccess(ITestResult r) {
        TL.get().pass("Passed");
        attachArtifacts(r, /*attachLogTail=*/false); // عادة ما نرفق اللوج عند النجاح
    }

    @Override public void onTestFailure(ITestResult r) {
        TL.get().fail(r.getThrowable());
        attachArtifacts(r, /*attachLogTail=*/true);  // ← هنا هنرفق Tail من اللوج
    }

    @Override public void onTestSkipped(ITestResult r) {
        TL.get().skip("Skipped");
        attachArtifacts(r, /*attachLogTail=*/true);
    }

    private void attachArtifacts(ITestResult r, boolean attachLogTail) {
        Object shot = r.getAttribute("screenshot");
        if (shot != null) {
            try { TL.get().addScreenCaptureFromPath(shot.toString()); } catch (Exception ignore) {}
        }

        Object vid = r.getAttribute("video");
        if (vid != null) {
            TL.get().info("<b>Video:</b> <a href='" + vid + "' target='_blank'>Watch</a>");
        }

        if (attachLogTail) {
            String logPath = "artifacts/logs/test.log";
            String tail = safeTail(logPath, 200); // آخر 200 سطر
            if (!tail.isEmpty()) {
                TL.get().info("<b>Log tail (last 200 lines)</b><br/><pre>" + html(tail) + "</pre>");
            } else {
                TL.get().info("Log file not found or empty: " + logPath);
            }
        }
    }

    // اقرأ آخر N سطر من ملف اللوج
    private static String safeTail(String filePath, int n) {
        try (BufferedReader br = Files.newBufferedReader(new File(filePath).toPath(), StandardCharsets.UTF_8)) {
            Deque<String> dq = new ArrayDeque<>(n);
            String line;
            while ((line = br.readLine()) != null) {
                if (dq.size() == n) dq.removeFirst();
                dq.addLast(line);
            }
            return String.join("\n", dq);
        } catch (Exception e) {
            return "";
        }
    }

    // هروب HTML بسيط
    private static String html(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /** تقدر تستخدمها من التيست لو حاب تكتب خطوة داخل التقرير */
    public static void log(String msg) { if (TL.get()!=null) TL.get().info(msg); }
}
