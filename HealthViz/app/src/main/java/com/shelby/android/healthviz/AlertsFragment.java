package com.shelby.android.healthviz;

import android.graphics.Color;
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

                if (i % 2 == 0) {
                    row.setBackgroundColor(Color.LTGRAY);
                }

                row.setId(100 + i);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                // Make TextView to hold the substance
                TextView substanceField = new TextView(getActivity());
                substanceField.setId(200 + i);
                substanceField.setText(p.getSubstance());

                row.addView(substanceField);

                // Make TextView to hold the reaction
                TextView reactionField = new TextView(getActivity());
                reactionField.setId(300 + i);
                reactionField.setText(p.getReaction());
                row.addView(reactionField);

                // Make TextView to hold the severity
                TextView severityField = new TextView(getActivity());
                severityField.setId(400 + i);
                severityField.setText(p.getSeverity());
                row.addView(severityField);

                tl.addView(row, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                i++;
            }
        }

        return v;
    }
}

