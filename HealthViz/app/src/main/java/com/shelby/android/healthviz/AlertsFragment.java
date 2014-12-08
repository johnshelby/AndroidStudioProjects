package com.shelby.android.healthviz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
        //mPatient = new Patient();
        UUID patientId = (UUID)getArguments().getSerializable(PatientFragment.EXTRA_PATIENT_ID);
        mPatient = PatientLab.get(getActivity()).getPatient(patientId);
        mPatient.parseCCD(PatientFragment.CCD_ALERTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alerts, parent, false);

        ArrayList<PatientAlerts> mPatientAlerts = mPatient.getPatientAlerts();

        if (mPatientAlerts == null) {
            // no patient alerts
            Toast.makeText(getActivity(), "No patient alerts", Toast.LENGTH_SHORT).show();
        }
        else {
            TableLayout tl = (TableLayout)v.findViewById(R.id.patient_alerts_table);
            int i = 0;
            for (PatientAlerts p : mPatientAlerts) {
                TableRow row = new TableRow(getActivity());

                row.setId(100 + i);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                // Make TV to hold the details
                TextView detailstv = new TextView(getActivity());
                detailstv.setId(200 + i);
                detailstv.setText(p.getSubstance());
                row.addView(detailstv);

                tl.addView(row, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                i++;
            }
        }

        return v;
    }
}

