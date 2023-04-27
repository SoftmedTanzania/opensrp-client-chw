package org.smartregister.chw.actionhelper;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.R;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.chw.ld.util.AppExecutors;
import org.smartregister.chw.malaria.contract.BaseIccmVisitContract;
import org.smartregister.chw.malaria.domain.VisitDetail;
import org.smartregister.chw.malaria.model.BaseIccmVisitAction;
import org.smartregister.chw.util.Constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class IccmPhysicalExaminationActionHelper implements BaseIccmVisitAction.IccmVisitActionHelper {
    private String jsonPayload;
    private final String baseEntityId;
    private final Context context;
    private final LinkedHashMap<String, BaseIccmVisitAction> actionList;
    private final BaseIccmVisitContract.InteractorCallBack callBack;

    private String physicalExamination;

    private final boolean isEdit;
    private final Map<String, List<VisitDetail>> details;

    public IccmPhysicalExaminationActionHelper(Context context, String baseEntityId, LinkedHashMap<String, BaseIccmVisitAction> actionList,  Map<String, List<VisitDetail>> details,BaseIccmVisitContract.InteractorCallBack callBack, boolean isEdit) {
        this.context = context;
        this.baseEntityId = baseEntityId;
        this.actionList = actionList;
        this.isEdit = isEdit;
        this.callBack = callBack;
        this.details = details;
    }

    @Override
    public void onJsonFormLoaded(String jsonPayload, Context context, Map<String, List<VisitDetail>> map) {
        this.jsonPayload = jsonPayload;
    }

    @Override
    public String getPreProcessed() {
        try {
            JSONObject jsonObject = new JSONObject(jsonPayload);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onPayloadReceived(String jsonPayload) {
        try {
            JSONObject jsonObject = new JSONObject(jsonPayload);
            physicalExamination = CoreJsonFormUtils.getValue(jsonObject, "physical_examination");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BaseIccmVisitAction.ScheduleStatus getPreProcessedStatus() {
        return null;
    }

    @Override
    public String getPreProcessedSubTitle() {
        return null;
    }

    @Override
    public String postProcess(String jsonPayload) {
        JSONObject jsonObject = null;
        String isMalariaSuspect = "false";
        try {
            jsonObject = new JSONObject(jsonPayload);
            isMalariaSuspect = CoreJsonFormUtils.getValue(jsonObject, "is_malaria_suspect");
        } catch (JSONException e) {
            Timber.e(e);
        }

        if (isMalariaSuspect.equalsIgnoreCase("true")) {
            try {
                String title = context.getString(R.string.iccm_malaria);
                IccmMalariaActionHelper actionHelper = new IccmMalariaActionHelper(context, baseEntityId, actionList, isEdit);
                BaseIccmVisitAction action = new BaseIccmVisitAction.Builder(context, title).withOptional(false).withHelper(actionHelper).withDetails(details).withBaseEntityID(baseEntityId).withFormName(Constants.JsonForm.getIccmMalaria()).build();
                actionList.put(title, action);
            } catch (Exception e) {
                Timber.e(e);
            }
        } else {
            //Removing the malaria actions  the client is not a malaria suspect.
            if (actionList.containsKey(context.getString(R.string.iccm_malaria))) {
                actionList.remove(context.getString(R.string.iccm_malaria));
            }
        }

        //Calling the callback method to preload the actions in the actionns list.
        new AppExecutors().mainThread().execute(() -> callBack.preloadActions(actionList));

        return null;
    }

    @Override
    public String evaluateSubTitle() {
        return null;
    }

    @Override
    public BaseIccmVisitAction.Status evaluateStatusOnPayload() {
        if (StringUtils.isBlank(physicalExamination))
            return BaseIccmVisitAction.Status.PENDING;
        else {
            return BaseIccmVisitAction.Status.COMPLETED;
        }
    }

    @Override
    public void onPayloadReceived(BaseIccmVisitAction baseIccmVisitAction) {
        //overridden
    }
}
