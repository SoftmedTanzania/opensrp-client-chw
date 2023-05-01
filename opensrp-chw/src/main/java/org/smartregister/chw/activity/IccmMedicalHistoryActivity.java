package org.smartregister.chw.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.smartregister.chw.R;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.presenter.BaseAncMedicalHistoryPresenter;
import org.smartregister.chw.core.activity.CoreAncMedicalHistoryActivity;
import org.smartregister.chw.interactor.IccmMedicalHistoryInteractor;
import org.smartregister.chw.malaria.domain.IccmMemberObject;

import java.util.List;

public class IccmMedicalHistoryActivity extends CoreAncMedicalHistoryActivity {
    private static IccmMemberObject iccmMemberObject;
    private final Flavor flavor = new IccmMedicalHistoryActivityFlv();
    private ProgressBar progressBar;

    public static void startMe(Activity activity, IccmMemberObject memberObject) {
        Intent intent = new Intent(activity, IccmMedicalHistoryActivity.class);
        iccmMemberObject = memberObject;
        activity.startActivity(intent);
    }

    @Override
    public void initializePresenter() {
        presenter = new BaseAncMedicalHistoryPresenter(new IccmMedicalHistoryInteractor(), this, iccmMemberObject.getBaseEntityId());
    }

    @Override
    public void setUpView() {
        linearLayout = findViewById(org.smartregister.chw.opensrp_chw_anc.R.id.linearLayoutMedicalHistory);
        progressBar = findViewById(org.smartregister.chw.opensrp_chw_anc.R.id.progressBarMedicalHistory);

        TextView tvTitle = findViewById(org.smartregister.chw.opensrp_chw_anc.R.id.tvTitle);
        tvTitle.setText(getString(org.smartregister.chw.opensrp_chw_anc.R.string.back_to, iccmMemberObject.getFullName()));

        ((TextView) findViewById(R.id.medical_history)).setText(getString(R.string.visits_history));
    }

    @Override
    public View renderView(List<Visit> visits) {
        super.renderView(visits);
        View view = flavor.bindViews(this);
        displayLoadingState(true);
        flavor.processViewData(visits, this);
        displayLoadingState(false);
        TextView agywVisitTitle = view.findViewById(org.smartregister.chw.core.R.id.customFontTextViewHealthFacilityVisitTitle);
        agywVisitTitle.setText(R.string.iccm_visit);
        return view;
    }

    @Override
    public void displayLoadingState(boolean state) {
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }
}
