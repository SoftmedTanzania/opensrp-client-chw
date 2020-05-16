package org.smartregister.chw.application;

public abstract class DefaultChwApplicationFlv implements ChwApplication.Flavor {
    @Override
    public boolean hasP2P() {
        return true;
    }

    @Override
    public boolean hasReferrals() {
        return false;
    }

    @Override
    public boolean hasANC() {
        return true;
    }

    @Override
    public boolean hasPNC() {
        return true;
    }

    @Override
    public boolean hasChildSickForm() {
        return false;
    }

    @Override
    public boolean hasFamilyPlanning() {
        return false;
    }

    @Override
    public boolean hasMalaria() {
        return false;
    }

    @Override
    public boolean hasWashCheck() {
        return true;
    }

    @Override
    public boolean hasRoutineVisit() {
        return false;
    }

    @Override
    public boolean hasServiceReport() {
        return false;
    }

    @Override
    public boolean hasStockUsageReport() {
        return false;
    }

    @Override
    public boolean hasPinLogin() {
        return false;
    }

    @Override
    public boolean hasReports() {
        return false;
    }

    @Override
    public boolean hasJobAids() {
        return true;
    }

    @Override
    public boolean hasQR() {
        return false;
    }

    @Override
    public boolean hasTasks() {
        return false;
    }

    @Override
    public boolean hasHIV() {
        return false;
    }

    @Override
    public boolean hasTB() {
        return false;
    }
}
