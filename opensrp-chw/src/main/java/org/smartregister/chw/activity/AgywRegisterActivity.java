package org.smartregister.chw.activity;

import android.app.Activity;
import android.content.Intent;

import com.vijay.jsonwizard.domain.Form;

import org.smartregister.chw.R;
import org.smartregister.chw.agyw.util.Constants;
import org.smartregister.chw.core.activity.CoreAgywRegisterActivity;
import org.smartregister.chw.fragment.AgywRegisterFragment;
import org.smartregister.view.fragment.BaseRegisterFragment;

public class AgywRegisterActivity extends CoreAgywRegisterActivity {

    public static void startRegistration(Activity activity, String baseEntityId, int age) {
        Intent intent = new Intent(activity, AgywRegisterActivity.class);
        intent.putExtra(Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID, baseEntityId);
        intent.putExtra(Constants.ACTIVITY_PAYLOAD.ACTION, Constants.ACTIVITY_PAYLOAD_TYPE.REGISTRATION);
        intent.putExtra(Constants.ACTIVITY_PAYLOAD.AGE, age);
        intent.putExtra(Constants.ACTIVITY_PAYLOAD.AGYW_FORM_NAME, Constants.FORMS.AGYW_REGISTRATION);

        activity.startActivity(intent);
    }

    @Override
    public Form getFormConfig() {
        Form form = new Form();
        form.setActionBarBackground(org.smartregister.chw.core.R.color.family_actionbar);
        form.setWizard(true);
        form.setName(getString(R.string.agyw_screening));
        form.setNavigationBackground(org.smartregister.chw.core.R.color.family_navigation);
        form.setNextLabel(this.getResources().getString(org.smartregister.chw.core.R.string.next));
        form.setPreviousLabel(this.getResources().getString(org.smartregister.chw.core.R.string.back));
        form.setSaveLabel(this.getResources().getString(org.smartregister.chw.core.R.string.save));
        return form;
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new AgywRegisterFragment();
    }
}
