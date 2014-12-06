package com.shelby.android.healthviz;

import java.util.Date;

/**
 * Created by John on 12/4/2014.
 */
public class PatientAlerts {
    private String mType;
    private String mSubstance;
    private String mReaction;
    private String mActions;
    private String mSeverity;
    private Date mFromDate;
    private String mToDate;

    public PatientAlerts(String substance) {
        mSubstance = substance;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getSubstance() {
        return mSubstance;
    }

    public void setSubstance(String substance) {
        mSubstance = substance;
    }

    public String getReaction() {
        return mReaction;
    }

    public void setReaction(String reaction) {
        mReaction = reaction;
    }

    public String getActions() {
        return mActions;
    }

    public void setActions(String actions) {
        mActions = actions;
    }

    public String getSeverity() {
        return mSeverity;
    }

    public void setSeverity(String severity) {
        mSeverity = severity;
    }

    public Date getFromDate() {
        return mFromDate;
    }

    public void setFromDate(Date fromDate) {
        mFromDate = fromDate;
    }

    public String getToDate() {
        return mToDate;
    }

    public void setToDate(String toDate) {
        mToDate = toDate;
    }


}
