package com.shelby.android.healthviz;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class PatientLab {
    private ArrayList<Patient> mPatients;

    private static PatientLab sPatientLab;
    private Context mAppContext;

    private PatientLab(Context appContext) {
        mAppContext = appContext;
        mPatients = new ArrayList<Patient>();

        Patient p1 = new Patient(mAppContext);
        p1.setTitle("Marla M Dixon");
        p1.setXmlFileName("marla_update");
        mPatients.add(p1);

        Patient p2 = new Patient(mAppContext);
        p2.setTitle("Marla Dixon");
        p2.setXmlFileName("marla_original");
        mPatients.add(p2);

        Patient p3 = new Patient(mAppContext);
        p3.setTitle("Adam Everyman");
        p3.setXmlFileName("adameveryman_referralsummary");
        mPatients.add(p3);

        Patient p4 = new Patient(mAppContext);
        p4.setTitle("Isabella Jones");
        p4.setXmlFileName("isabellajones_referralsummary");
        mPatients.add(p4);

        Patient p5 = new Patient(mAppContext);
        p5.setTitle("Mary Grant");
        p5.setXmlFileName("marygrant_clinicalsummary");
        mPatients.add(p5);
    }

    public static PatientLab get(Context c) {
        if (sPatientLab == null) {
            sPatientLab = new PatientLab(c.getApplicationContext());
        }
        return sPatientLab;
    }

    public Patient getPatient(UUID id) {
        for (Patient p : mPatients) {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }
    
    public void addPatient(Patient c) {
        mPatients.add(c);
    }

    public ArrayList<Patient> getPatients() {
        return mPatients;
    }

    public void deletePatient(Patient p) {
        mPatients.remove(p);
    }
}

