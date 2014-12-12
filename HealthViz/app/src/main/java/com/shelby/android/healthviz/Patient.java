package com.shelby.android.healthviz;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Patient {
    private Context mContext;
    private UUID mId;
    private String mXmlFileName;
    private Date mDate;
    private String mTitle;
    private boolean mSolved;
    private static final String TAG = "Patient";

    private String mGiven = "";
    private String mFamily = "";
    private String mGenderCode = "";
    private Date mBirthDate = new Date(0);
    private String mMaritalStatusCode = "";
    private String mReligiousAffiliationCode = "";
    private String mRaceCode = "";
    private String mEthnicGroupCode = "";

    private String mAddrUse = "";
    private String mStreetAddress = "";
    private String mCity = "";
    private String mState = "";
    private String mPostalCode = "";
    private String mCountry = "";

    private String mTelephoneUse = "";
    private String mTelephone = "";

    private String mEmailUse = "";
    private String mEmail = "";

    private String mPatientId = "";
    private String mPatientSsn = "";

    private PatientInfo mPatientInfo;
    private ArrayList<PatientAlerts> mPatientAlerts;

    public Patient() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mPatientAlerts = new ArrayList<PatientAlerts>();
    }

    public Patient(Context appContext) {
        mId = UUID.randomUUID();
        mDate = new Date();
        mContext = appContext;

        mPatientAlerts = new ArrayList<PatientAlerts>();
        Log.d(TAG, "Patient alerts size is: " + mPatientAlerts.size());
        // create some bogus alerts
        PatientAlerts a1 = new PatientAlerts("Penicillin");
        a1.setReaction("Hives");
        a1.setSeverity("Moderate to severe");
        mPatientAlerts.add(a1);
        PatientAlerts a2 = new PatientAlerts("Aspirin");
        a2.setReaction("Wheezing");
        a2.setSeverity("Moderate to severe");
        mPatientAlerts.add(a2);
        PatientAlerts a3 = new PatientAlerts("Codeine");
        a3.setReaction("Nausea");
        a3.setSeverity("Severe");
        mPatientAlerts.add(a3);
        Log.d(TAG, "Patient alerts size is: " + mPatientAlerts.size());
    }

    @Override
    public String toString() {
        return mGiven + " " + mFamily;
    }

    public ArrayList<PatientAlerts> getPatientAlerts() {
        return mPatientAlerts;
    }

    public Context getContext() {
        return mContext;
    }

    public String getAddrUse() {
        return mAddrUse;
    }

    public void setAddrUse(String addrUse) {
        mAddrUse = addrUse;
    }

    public String getMaritalStatusCode() {
        return mMaritalStatusCode;
    }

    public void setMaritalStatusCode(String maritalStatusCode) {
        mMaritalStatusCode = maritalStatusCode;
    }

    public String getReligiousAffiliationCode() {
        return mReligiousAffiliationCode;
    }

    public void setReligiousAffiliationCode(String religiousAffiliationCode) {
        mReligiousAffiliationCode = religiousAffiliationCode;
    }

    public String getRaceCode() {
        return mRaceCode;
    }

    public void setRaceCode(String raceCode) {
        mRaceCode = raceCode;
    }

    public String getEthnicGroupCode() {
        return mEthnicGroupCode;
    }

    public void setEthnicGroupCode(String ethnicGroupCode) {
        mEthnicGroupCode = ethnicGroupCode;
    }

    public String getStreetAddress() {
        return mStreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        mStreetAddress = streetAddress;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getGiven() {
        return mGiven;
    }

    public void setGiven(String given) {
        mGiven = given;
    }

    public String getFamily() {
        return mFamily;
    }

    public void setFamily(String family) {
        mFamily = family;
    }

    public String getGenderCode() {
        return mGenderCode;
    }

    public String getGender() {
        if (mGenderCode.toUpperCase().equals("F")) {
            return "Female";
        }
        else if (mGenderCode.toUpperCase().equals("M")) {
            return "Male";
        }
        else {
            return "Undifferentiated";
        }
    }

    public void setGenderCode(String genderCode) {
        mGenderCode = genderCode;
    }

    public String getXmlFileName() {
        return mXmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        mXmlFileName = xmlFileName;
    }

    public void parseCCD(String parseSection) {
        CCDXmlParser ccd = new CCDXmlParser();
        Log.d(TAG, "Entering parseCCD");
        BufferedReader reader = null;
        try {
            int resId;
            if (mXmlFileName.equals("marla_update")) {
                resId = R.raw.marla_update;
            }
            else if (mXmlFileName.equals("marla_original")) {
                resId = R.raw.marla_original;
            }
            else if (mXmlFileName.equals("johnsccd")) {
                resId = R.raw.johnsccd;
            }
            else if (mXmlFileName.equals("adameveryman_referralsummary")) {
                resId = R.raw.adameveryman_referralsummary;
            }
            else if (mXmlFileName.equals("isabellajones_referralsummary")) {
                resId = R.raw.isabellajones_referralsummary;
            }
            else if (mXmlFileName.equals("marygrant_clinicalsummary")) {
                resId = R.raw.marygrant_clinicalsummary;
            }
            else if (mXmlFileName.equals("ccd_sample")) {
                resId = R.raw.ccd_sample;
            }
            else {
                resId = R.raw.marla_update;
            }

            InputStream in = mContext.getResources().openRawResource(resId);
            ccd.parse(in, parseSection);
        }
        catch(FileNotFoundException e) {
            Toast.makeText(mContext, R.string.ccd_not_found + ": " + mXmlFileName, Toast.LENGTH_SHORT).show();
        }
        catch(IOException e) {

        }
        catch(XmlPullParserException e) {

        }

        Log.d(TAG, "Exiting parseCCD");
    }

    /**
     * Given an InputStream representation of a feed, it returns a List of entries,
     * where each list element represents a single entry (post) in the XML feed.
     */
    private class CCDXmlParser {
        private final String ns = null;
        private final String TAG = "CCDXmlParser";
        private String mParseSection;

        public void parse(java.io.InputStream in, String parseSection) throws XmlPullParserException, IOException {
            mParseSection = parseSection;
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                readClinicalDocument(parser);
            } finally {
                in.close();
            }
        }

        private void readClinicalDocument(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read ClinicalDocument node";
            Log.d(TAG, "Entering readClinicalDocument");

            parser.require(XmlPullParser.START_TAG, ns, "ClinicalDocument");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in ClinicalDocument
                if (name.equals("recordTarget") && mParseSection.equals(PatientFragment.CCD_PERSONAL)) {
                    readRecordTarget(parser);
                }
                else if (name.equals("component") && (mParseSection.equals(PatientFragment.CCD_ALERTS))) {
                    readComponent(parser);
                }
                else {
                    skip(parser);
                }
            }
        }

        // --------------------------------------------------------------------
        // read outer component section
        // --------------------------------------------------------------------
        private void readComponent(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read outer component node";
            Log.d(TAG, "Entering readComponent");

            parser.require(XmlPullParser.START_TAG, ns, "component");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in recordTarget
                if (name.equals("structuredBody")) {
                    readStructuredBody(parser);
                } else {
                    skip(parser);
                }
            }
        }

        // --------------------------------------------------------------------
        // read structuredBody section
        // --------------------------------------------------------------------
        private void readStructuredBody(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read structuredBody node";
            Log.d(TAG, "Entering readStructuredBody");

            parser.require(XmlPullParser.START_TAG, ns, "structuredBody");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in recordTarget
                if (name.equals("component")) {
                    readInnerComponent(parser);
                } else {
                    skip(parser);
                }
            }
        }

        // --------------------------------------------------------------------
        // read inner component section
        // --------------------------------------------------------------------
        private void readInnerComponent(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read inner component node";
            Log.d(TAG, "Entering readInnerComponent");

            parser.require(XmlPullParser.START_TAG, ns, "component");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in recordTarget
                if (name.equals("section")) {
                    readSection(parser);
                } else {
                    skip(parser);
                }
            }
        }

        // --------------------------------------------------------------------
        // read section section
        // --------------------------------------------------------------------
        private void readSection(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser section node";
            Log.d(TAG, "Entering readSection");

            parser.require(XmlPullParser.START_TAG, ns, "section");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in recordTarget
                if (name.equals("code")) {
                    String code = parser.getAttributeValue(null, "code");
                    Log.d(TAG, "found code attribute " + code);
                    parser.nextTag();
                    if (code.equals("48765-2")) {
                        Log.d(TAG, "found LOINC tag for Allergies section");
                    }
                    else if (code.equals("10160-0")) {
                        Log.d(TAG, "found LOINC tag for Medications section");
                    }
                    else if (code.equals("11450-4")) {
                        Log.d(TAG, "found LOINC tag for Problems section");
                    }
                    else if (code.equals("47519-4")) {
                        Log.d(TAG, "found LOINC tag for Procedures section");
                    }
                    else if (code.equals("30954-2")) {
                        Log.d(TAG, "found LOINC tag for Results section");
                    }
                    else if (code.equals("46240-8")) {
                        Log.d(TAG, "found LOINC tag for Encounters section");
                    }
                    else if (code.equals("10157-6")) {
                        Log.d(TAG, "found LOINC tag for Family History section");
                    }
                    else if (code.equals("11369-6")) {
                        Log.d(TAG, "found LOINC tag for Immunizations section");
                    }
                    else if (code.equals("46264-8")) {
                        Log.d(TAG, "found LOINC tag for Medical Equipment section");
                    }
                    else if (code.equals("48768-6")) {
                        Log.d(TAG, "found LOINC tag for Payers section");
                    }
                    else if (code.equals("18776-5")) {
                        Log.d(TAG, "found LOINC tag for Plan of Care");
                    }
                    else if (code.equals("29762-2")) {
                        Log.d(TAG, "found LOINC tag for Social History section");
                    }
                    else if (code.equals("8716-3")) {
                        Log.d(TAG, "found LOINC tag for Vital Signs section");
                    }
                } else {
                    skip(parser);
                }
            }
        }

        // --------------------------------------------------------------------
        // personal information in recordTarget node
        // --------------------------------------------------------------------
        private void readRecordTarget(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read recordTarget node";
            Log.d(TAG, "Entering readRecordTarget");

            parser.require(XmlPullParser.START_TAG, ns, "recordTarget");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in recordTarget
                if (name.equals("patientRole")) {
                    readPatientRole(parser);
                } else {
                    skip(parser);
                }
            }
        }

        private void readPatientRole(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read patientRole node";
            Log.d(TAG, "Entering readPatientRole");

            parser.require(XmlPullParser.START_TAG, ns, "patientRole");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in patientRole
                if (name.equals("patient")) {
                    readPatient(parser);
                }
                else if (name.equals("addr")) {
                    readPatientAddress(parser);
                }
                else {
                    skip(parser);
                }
            }
        }

        private void readPatient(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read patient node";
            Log.d(TAG, "Entering readPatient");

            parser.require(XmlPullParser.START_TAG, ns, "patient");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in patient
                if (name.equals("name")) {
                    readPatientName(parser);
                } else if (name.equals("administrativeGenderCode")) {
                    mGenderCode = readGender(parser);
                } else if (name.equals("maritalStatusCode")) {
                    mMaritalStatusCode = readMarital(parser);
                } else if (name.equals("raceCode")) {
                    mRaceCode = readRaceCode(parser);
                } else if (name.equals("ethnicGroupCode")) {
                    mEthnicGroupCode = readEthnicGroupCode(parser);
                } else if (name.equals("religiousAffiliationCode")) {
                    mReligiousAffiliationCode = readReligiousAffiliationCode(parser);
                } else if (name.equals("birthTime")) {
                    mBirthDate = readBirthTime(parser);
                } else {
                    skip(parser);
                }
            }
        }

        private void readPatientName(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read name node";

            parser.require(XmlPullParser.START_TAG, ns, "name");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("given")) {
                    mGiven = readGiven(parser);
                } else if (name.equals("family")) {
                    mFamily = readFamily(parser);
                } else {
                    skip(parser);
                }
            }
        }

        // Processes given (name) tags in the feed.
        private String readGiven(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "given");
            String given = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "given");
            return given;
        }

        // Processes gender tags in the feed.
        private String readGender(XmlPullParser parser) throws IOException, XmlPullParserException {
            final String TAG = "CCDXMLParser read gender node";
            String gender = "";
            parser.require(XmlPullParser.START_TAG, ns, "administrativeGenderCode");
            String tag = parser.getName();
            if (tag.equals("administrativeGenderCode")) {
                gender = parser.getAttributeValue(null, "code");
                parser.nextTag();
            }
            Log.d(TAG, "Gender code is " + gender);
            parser.require(XmlPullParser.END_TAG, ns, "administrativeGenderCode");
            return gender;
        }

        // Processes marital status code tags in the feed.
        private String readMarital(XmlPullParser parser) throws IOException, XmlPullParserException {
            final String TAG = "CCDXMLParser read maritalStatus node";
            String marital = "";
            parser.require(XmlPullParser.START_TAG, ns, "maritalStatusCode");
            String tag = parser.getName();
            if (tag.equals("maritalStatusCode")) {
                marital = parser.getAttributeValue(null, "code");
                parser.nextTag();
            }
            Log.d(TAG, "Marital Status code is " + marital);
            parser.require(XmlPullParser.END_TAG, ns, "maritalStatusCode");
            return marital;
        }

        // Processes race code tags in the feed.
        private String readRaceCode(XmlPullParser parser) throws IOException, XmlPullParserException {
            final String TAG = "CCDXMLParser read raceCode node";
            String race = "";
            parser.require(XmlPullParser.START_TAG, ns, "raceCode");
            String tag = parser.getName();
            if (tag.equals("raceCode")) {
                race = parser.getAttributeValue(null, "code");
                parser.nextTag();
            }
            Log.d(TAG, "Race code is " + race);
            parser.require(XmlPullParser.END_TAG, ns, "raceCode");
            return race;
        }

        // Processes ethnic group code tags in the feed.
        private String readEthnicGroupCode(XmlPullParser parser) throws IOException, XmlPullParserException {
            final String TAG = "CCDXMLParser read ethnicGroupCode node";
            String ethnic = "";
            parser.require(XmlPullParser.START_TAG, ns, "ethnicGroupCode");
            String tag = parser.getName();
            if (tag.equals("ethnicGroupCode")) {
                ethnic = parser.getAttributeValue(null, "code");
                parser.nextTag();
            }
            Log.d(TAG, "Ethnic group code is " + ethnic);
            parser.require(XmlPullParser.END_TAG, ns, "ethnicGroupCode");
            return ethnic;
        }

        // Processes religious affiliation tags in the feed.
        private String readReligiousAffiliationCode(XmlPullParser parser) throws IOException, XmlPullParserException {
            final String TAG = "CCDXMLParser read religiousAffiliationCode node";
            String r = "";
            parser.require(XmlPullParser.START_TAG, ns, "religiousAffiliationCode");
            String tag = parser.getName();
            if (tag.equals("religiousAffiliationCode")) {
                r = parser.getAttributeValue(null, "code");
                parser.nextTag();
            }
            Log.d(TAG, "Religious affiliation code is " + r);
            parser.require(XmlPullParser.END_TAG, ns, "religiousAffiliationCode");
            return r;
        }

        private Date readBirthTime(XmlPullParser parser) throws IOException, XmlPullParserException {
            final String TAG = "CCDXMLParser read birthTime node";
            Date t = new Date();
            String d = "";
            parser.require(XmlPullParser.START_TAG, ns, "birthTime");
            String tag = parser.getName();
            if (tag.equals("birthTime")) {
                d = parser.getAttributeValue(null, "value");
                parser.nextTag();
            }
            parser.require(XmlPullParser.END_TAG, ns, "birthTime");

            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
            try {
                t = ft.parse(d);
                Log.d(TAG, "Parsed " + d + " as " + t.toString());
            }
            catch (ParseException e) {
                Log.d(TAG, "Could not parse birth date " + d);
            }
            Log.d(TAG, "Birth text is " + d + ", parsed date is " + t.toString());
            return t;

        }

        // Processes family (name) tags in the feed.
        private String readFamily(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "family");
            String summary = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "family");
            return summary;
        }

        private void readPatientAddress(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read patient address";
            Log.d(TAG, "Entering readPatientAddress");

            parser.require(XmlPullParser.START_TAG, ns, "addr");
            String tag = parser.getName();
            if (tag.equals("addr")) {
                String u = parser.getAttributeValue(null, "use");
                Log.d(TAG, "Address use: " + u);
                mAddrUse = u;
            }
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in patient
                if (name.equals("streetAddressLine")) {
                    mStreetAddress = readPatientStreetAddress(parser);
                } else if (name.equals("city")) {
                    mCity = readPatientCity(parser);
                } else if (name.equals("state")) {
                    mState = readPatientState(parser);
                } else if (name.equals("postalCode")) {
                    mPostalCode = readPatientPostalCode(parser);
                } else if (name.equals("country")) {
                    mCountry = readPatientCountry(parser);
                } else {
                    skip(parser);
                }
            }
        }

        private String readPatientStreetAddress(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "streetAddressLine");
            String s = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "streetAddressLine");
            return s;
        }

        private String readPatientCity(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "city");
            String s = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "city");
            return s;
        }

        private String readPatientState(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "state");
            String s = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "state");
            return s;
        }

        private String readPatientCountry(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "country");
            String s = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "country");
            return s;
        }

        private String readPatientPostalCode(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "postalCode");
            String s = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "postalCode");
            return s;
        }

        // For text tags, extract string values
        private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
            String result = "";
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
            return result;
        }

        // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
        // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
        // finds the matching END_TAG (as indicated by the value of "depth" being 0).
        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }
    }
}
