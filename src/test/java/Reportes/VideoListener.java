// src/test/java/Reportes/VideoListener.java
package Reportes;

import Utilites.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class VideoListener implements ITestListener {
    @Override public void onTestStart(ITestResult r) {
        String cls = r.getTestClass().getRealClass().getSimpleName();
        String method = r.getMethod().getMethodName();
        VideoRecorder.start(cls, method);
    }
    @Override public void onTestFailure(ITestResult r) {
        String p = VideoRecorder.stopAndSave();
        if (p != null) r.setAttribute("video", p);
    }
    @Override public void onTestSuccess(ITestResult r) {
        String p = VideoRecorder.stopAndSave();
        if (p != null) r.setAttribute("video", p);
    }
    @Override public void onTestSkipped(ITestResult r) { String p = VideoRecorder.stopAndSave(); if (p!=null) r.setAttribute("video", p); }
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult r) {}
    @Override public void onStart(ITestContext c) {}
    @Override public void onFinish(ITestContext c) {}
}
