package com.shelby.android.healthviz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by John on 11/29/2014.
 */
public class AlertsFragment extends Fragment {
    private Patient mPatient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPatient = new Patient();
        UUID patientId = (UUID)getArguments().getSerializable(PatientFragment.EXTRA_PATIENT_ID);
        mPatient = PatientLab.get(getActivity()).getPatient(patientId);
        mPatient.parseCCD(PatientFragment.CCD_ALERTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alerts, parent, false);

        ArrayList<PatientAlerts> mPatientAlerts = mPatient.getPatientAlerts();

        if (mPatientAlerts == null) {

        }
        else {
            for (PatientAlerts p : mPatientAlerts) {

            }
        }

        return v;
    }
}

