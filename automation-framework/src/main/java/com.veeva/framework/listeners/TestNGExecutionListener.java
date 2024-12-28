package com.veeva.framework.listeners;

import com.veeva.framework.utils.ExtentReportManager;
import org.testng.IExecutionListener;

public class TestNGExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionFinish() {
        ExtentReportManager.flushAllReports();
    }
}
