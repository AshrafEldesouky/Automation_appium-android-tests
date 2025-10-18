// src/test/java/Reportes/VideoListener.java
package Reportes;

import Core.ConfigLoader;
import Utilites.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class VideoListener implements ITestListener {

    /** نتحقق هل السيرفر BrowserStack عشان ما نسجّلش فيديو محلي */
    private boolean isCloud() {
        String url = System.getProperty("serverUrl", null);
        if (url == null) {
            try { url = ConfigLoader.get("serverUrl"); } catch (Exception ignored) {}
        }
        return url != null && url.contains("browserstack.com");
    }

    @Override
    public void onTestStart(ITestResult r) {
        if (isCloud()) return; // على BS الفيديو بيتسجل تلقائياً
        String cls = r.getTestClass().getRealClass().getSimpleName();
        String method = r.getMethod().getMethodName();
        try {
            VideoRecorder.start(cls, method);
        } catch (Exception ignored) { /* لا تفشل الرن بسبب التسجيل */ }
    }

    @Override
    public void onTestFailure(ITestResult r) {
        if (isCloud()) return;
        try {
            String p = VideoRecorder.stopAndSave();
            if (p != null) r.setAttribute("video", p);
        } catch (Exception ignored) { }
    }

    @Override
    public void onTestSuccess(ITestResult r) {
        if (isCloud()) return;
        try {
            String p = VideoRecorder.stopAndSave();
            if (p != null) r.setAttribute("video", p);
        } catch (Exception ignored) { }
    }

    @Override
    public void onTestSkipped(ITestResult r) {
        if (isCloud()) return;
        try {
            String p = VideoRecorder.stopAndSave();
            if (p != null) r.setAttribute("video", p);
        } catch (Exception ignored) { }
    }

    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult r) {}
    @Override public void onStart(ITestContext c) {}
    @Override public void onFinish(ITestContext c) {}
}
