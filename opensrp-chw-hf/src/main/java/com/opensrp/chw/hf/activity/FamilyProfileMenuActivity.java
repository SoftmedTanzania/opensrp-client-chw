package com.opensrp.chw.hf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.opensrp.chw.core.activity.CoreFamilyProfileMenuActivity;
import com.opensrp.chw.core.utils.CoreConstants;
import com.opensrp.chw.hf.fragement.FamilyProfileChangeHead;
import com.opensrp.chw.hf.fragement.FamilyProfileChangePrimaryCG;

import org.smartregister.family.util.Constants;

public class FamilyProfileMenuActivity extends CoreFamilyProfileMenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        menuOption = intent.getStringExtra(CoreFamilyProfileMenuActivity.MENU);
        familyBaseEntityId = getIntent().getStringExtra(Constants.INTENT_KEY.BASE_ENTITY_ID);

        Fragment fragment;
        switch (menuOption) {
            case CoreConstants.MenuType.ChangeHead:
                fragment = FamilyProfileChangeHead.newInstance(familyBaseEntityId);
                break;
            case CoreConstants.MenuType.ChangePrimaryCare:
                fragment = FamilyProfileChangePrimaryCG.newInstance(familyBaseEntityId);
                break;
            default:
                fragment = FamilyProfileChangeHead.newInstance(familyBaseEntityId);
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(com.opensrp.chw.core.R.id.frameLayout, fragment);
        ft.commit();
    }
}
