package org.smartregister.chw.util;

import android.content.Context;
import android.webkit.JavascriptInterface;

import static org.smartregister.util.Utils.getAllSharedPreferences;

public class ChwWebAppInterface {
    Context mContext;

    String reportType;


    public ChwWebAppInterface(Context c, String reportType) {
        mContext = c;
        this.reportType = reportType;
    }

    @JavascriptInterface
    public String getDataForReport() {
        if (reportType.equalsIgnoreCase(Constants.ReportConstants.ReportTypes.CBHS_REPORT)) {
            ReportUtils.setPrintJobName("cbhs_monthly_summary-" + ReportUtils.getReportPeriod() + ".pdf");
            return ReportUtils.CBHSReport.computeReport(ReportUtils.getReportDate(), mContext);
        }
        if(reportType.equalsIgnoreCase(Constants.ReportConstants.ReportTypes.MOTHER_CHAMPION_REPORT)){
            ReportUtils.setPrintJobName("mother_champion_report-" + ReportUtils.getReportPeriod() + ".pdf");
            return ReportUtils.MotherChampionReport.computeReport(ReportUtils.getReportDate());
        }


        return "";
    }

    @JavascriptInterface
    public String getData(String key) {
        if (reportType.equalsIgnoreCase(Constants.ReportConstants.ReportTypes.AGYW_REPORT)){
            ReportUtils.setPrintJobName("AGYW_report_ya_mwezi-" + ReportUtils.getReportPeriod() + ".pdf");
            return ReportUtils.AGYWReport.computeReport(ReportUtils.getReportDate());
        }
        if (reportType.equalsIgnoreCase(Constants.ReportConstants.ReportTypes.CONDOM_DISTRIBUTION_REPORT)){
            switch (key) {
                case Constants.ReportConstants.CDPReportKeys.ISSUING_REPORTS:
                    ReportUtils.setPrintJobName("CDP_issuing_report_ya_mwezi-" + ReportUtils.getReportPeriod() + ".pdf");
                    return ReportUtils.CDPReports.computeIssuingReports(ReportUtils.getReportDate());
                case Constants.ReportConstants.CDPReportKeys.RECEIVING_REPORTS:
                    ReportUtils.setPrintJobName("CDP_receiving_report_ya_mwezi-" + ReportUtils.getReportPeriod() + ".pdf");
                    return ReportUtils.CDPReports.computeReceivingReports(ReportUtils.getReportDate());
                default:
                    return "";
            }
        }
        return "";
    }


    @JavascriptInterface
    public String getDataPeriod() {
        return ReportUtils.getReportPeriod();
    }

    @JavascriptInterface
    public String getDataPeriod(String reportKey) {
        return ReportUtils.getReportPeriod();
    }

    @JavascriptInterface
    public String getReportingFacility() {
        return getAllSharedPreferences().fetchCurrentLocality();
    }
}
