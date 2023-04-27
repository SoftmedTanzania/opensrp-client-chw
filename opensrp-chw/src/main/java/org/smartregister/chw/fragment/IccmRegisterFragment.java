package org.smartregister.chw.fragment;

import android.view.View;

import org.smartregister.chw.R;
import org.smartregister.chw.activity.IccmProfileActivity;
import org.smartregister.chw.activity.MalariaFollowUpVisitActivity;
import org.smartregister.chw.activity.MalariaProfileActivity;
import org.smartregister.chw.core.fragment.CoreMalariaRegisterFragment;
import org.smartregister.chw.model.IccmRegisterFragmentModel;
import org.smartregister.chw.model.MalariaRegisterFragmentModel;
import org.smartregister.chw.presenter.IccmRegisterFragmentPresenter;
import org.smartregister.chw.presenter.MalariaRegisterFragmentPresenter;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;
import org.smartregister.view.customcontrols.FontVariant;

public class IccmRegisterFragment extends CoreMalariaRegisterFragment {

    @Override
    public void setupViews(android.view.View view) {
        super.setupViews(view);
        CustomFontTextView titleView = view.findViewById(org.smartregister.malaria.R.id.txt_title_label);
        if (titleView != null) {
            titleView.setVisibility(android.view.View.VISIBLE);
            titleView.setText(getString(R.string.iccm));
            titleView.setFontVariant(FontVariant.REGULAR);
        }
    }

    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }
        String viewConfigurationIdentifier = ((BaseRegisterActivity) getActivity()).getViewIdentifiers().get(0);
        presenter = new IccmRegisterFragmentPresenter(this, new IccmRegisterFragmentModel(), viewConfigurationIdentifier);
    }

    @Override
    protected void openProfile(String  baseEntityId) {
        IccmProfileActivity.startMalariaActivity(getActivity(), baseEntityId);
    }

    @Override
    protected void openFollowUpVisit(String  baseEntityId) {
    }

    @Override
    protected void toggleFilterSelection(View dueOnlyLayout) {
        super.toggleFilterSelection(dueOnlyLayout);
    }

    @Override
    protected String searchText() {
        return super.searchText();
    }

    @Override
    protected void dueFilter(View dueOnlyLayout) {
        super.dueFilter(dueOnlyLayout);
    }

    @Override
    protected void normalFilter(View dueOnlyLayout) {
        super.normalFilter(dueOnlyLayout);
    }
}


