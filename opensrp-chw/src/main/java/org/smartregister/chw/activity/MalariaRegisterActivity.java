package org.smartregister.chw.activity;

import static org.smartregister.chw.core.utils.CoreConstants.JSON_FORM.getIccmEnrollment;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;

import org.json.JSONObject;
import org.smartregister.chw.core.activity.CoreMalariaRegisterActivity;
import org.smartregister.chw.core.utils.FormUtils;
import org.smartregister.chw.fragment.MalariaRegisterFragment;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.family.util.DBConstants;
import org.smartregister.family.util.JsonFormUtils;
import org.smartregister.family.util.Utils;
import org.smartregister.helper.BottomNavigationHelper;
import org.smartregister.view.fragment.BaseRegisterFragment;

import timber.log.Timber;

public class MalariaRegisterActivity extends CoreMalariaRegisterActivity {

    public static void startMalariaRegistrationActivity(Activity activity, String baseEntityID, @Nullable String familyBaseEntityID, String formName) {
        Intent intent = new Intent(activity, MalariaRegisterActivity.class);
        intent.putExtra(org.smartregister.chw.malaria.util.Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID, baseEntityID);
        intent.putExtra(org.smartregister.chw.malaria.util.Constants.ACTIVITY_PAYLOAD.FAMILY_BASE_ENTITY_ID, familyBaseEntityID);
        intent.putExtra(org.smartregister.chw.malaria.util.Constants.ACTIVITY_PAYLOAD.MALARIA_FORM_NAME, formName);
        intent.putExtra(org.smartregister.chw.malaria.util.Constants.ACTIVITY_PAYLOAD.ACTION, org.smartregister.chw.anc.util.Constants.ACTIVITY_PAYLOAD_TYPE.REGISTRATION);
        activity.startActivity(intent);
    }

    @Override
    protected void registerBottomNavigation() {
        bottomNavigationHelper = new BottomNavigationHelper();
        bottomNavigationView = findViewById(org.smartregister.R.id.bottom_navigation);
        FamilyRegisterActivity.registerBottomNavigation(bottomNavigationHelper, bottomNavigationView, this);
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new MalariaRegisterFragment();
    }

    @Override
    public void startFormActivity(JSONObject jsonForm) {
        if (FORM_NAME.equalsIgnoreCase(getIccmEnrollment())) {
            try {
                final CommonPersonObject personObject = Utils.context().commonrepository(Utils.metadata().familyMemberRegister.tableName).findByBaseEntityId(BASE_ENTITY_ID);
                String dobString = org.smartregister.util.Utils.getValue(personObject.getColumnmaps(), DBConstants.KEY.DOB, false);
                int age = org.smartregister.chw.util.Utils.getAgeFromDate(dobString);
                jsonForm.getJSONObject("global").put("age", age);
                startActivityForResult(FormUtils.getStartFormActivity(jsonForm, this.getString(org.smartregister.chw.core.R.string.iccm_enrollment), this), JsonFormUtils.REQUEST_CODE_GET_JSON);
            } catch (Exception e) {
                Timber.e(e);
            }
        } else {
            startActivityForResult(FormUtils.getStartFormActivity(jsonForm, this.getString(org.smartregister.chw.core.R.string.malaria_registration), this), JsonFormUtils.REQUEST_CODE_GET_JSON);
        }
    }

}