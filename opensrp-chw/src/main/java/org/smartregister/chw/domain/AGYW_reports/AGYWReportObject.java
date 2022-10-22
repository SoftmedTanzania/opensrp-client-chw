package org.smartregister.chw.domain.AGYW_reports;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.dao.ReportDao;
import org.smartregister.chw.domain.ReportObject;

import java.util.Date;

public class AGYWReportObject extends ReportObject {


    private final String[] noOfQuestions = new String[]
            {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
    private final String[] questionsGroups = new String[]{
            "1-10-14","1-15-19","1-20-24",
            "2-10-14","2-15-19","2-20-24",
            "3-10-14","3-15-19","3-20-24",
            "4-10-14","4-15-19","4-20-24",
            "5-10-14","5-15-19","5-20-24",
            "6-10-14","6-15-19","6-20-24",
            "7-10-14","7-15-19","7-20-24",
            "8-10-14","8-15-19","8-20-24",
            "9-10-14","9-15-19","9-20-24",
            "10-10-14","10-15-19","10-20-24",
            "11-10-14","11-15-19","11-20-24",
            "12-10-14","12-15-19","12-20-24",
            "13-10-14","13-15-19","13-20-24",
    };



    private final Date reportDate;
    private JSONObject jsonObject ;

    public AGYWReportObject(Date reportDate) {
        super(reportDate);
        this.reportDate = reportDate;
    }

    @Override
    public JSONObject getIndicatorData() throws JSONException {



        jsonObject = new JSONObject();
        for (String questionGroup : questionsGroups) {
                        jsonObject.put("agyw"+"-"+questionGroup,
                                ReportDao.getReportPerIndicatorCode("agyw"+"-"+questionGroup, reportDate));
        }
        // get total of all
        getTotalPerEachIndicator();

        return jsonObject;
    }

    private int getTotalPerEachIndicator() throws JSONException {
        int finalTotal = 0;
        for (String question: noOfQuestions){
                finalTotal += ReportDao.getReportPerIndicatorCode("agyw"+"-"+question+"-JUMLA", reportDate);
            jsonObject.put("agyw"+"-"+question+"-JUMLA",finalTotal);  //display the total for specified gender
        }
        return finalTotal;
    }


}
