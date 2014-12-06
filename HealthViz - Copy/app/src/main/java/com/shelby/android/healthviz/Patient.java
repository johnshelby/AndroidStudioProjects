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
import java.util.Date;
import java.util.Locale;
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
    private String mGender = "";
    private Date mBirthDate = new Date();

    private String mStreetAddress = "";
    private String mCity = "";
    private String mState = "";
    private String mPostalCode = "";
    private String mCountry = "";

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

    public String getBirthDateDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("cccc, LLLL d, y", Locale.US);
        return sdf.format(mBirthDate);
    }

    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    public Patient(Context appContext) {
        mId = UUID.randomUUID();
        mDate = new Date();
        mContext = appContext;
    }

    public Patient() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    @Override
    public String toString() {
        return mGiven + " " + mFamily;
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

    public String getGender() {
        return mGender;
    }

    public String getGenderDisplay() {
        if (mGender.equals("F")) {
            return "Female";
        }
        else if (mGender.equals("M")) {
            return "Male";
        }
        else {
            return "Unknown";
        }
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getXmlFileName() {
        return mXmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        mXmlFileName = xmlFileName;
    }

    public void parseCCD() {
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
            else {
                resId = R.raw.marla_update;
            }

            InputStream in = mContext.getResources().openRawResource(resId);
            ccd.parse(in);
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

        // We don't use namespaces

        //public CCDXmlParser() {
//
        //}

        public void parse(java.io.InputStream in) throws XmlPullParserException, IOException {
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
                if (name.equals("recordTarget")) {
                    readRecordTarget(parser);
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
                    setGender(readGender(parser));
                } else if (name.equals("birthTime")) {
                    setBirthDate(readBirthTime(parser));
                } else {
                    skip(parser);
                }
            }
        }

        private void readPatientName(XmlPullParser parser) throws XmlPullParserException, IOException {
            final String TAG = "CCDXMLParser read name node";
            String link = "";

            parser.require(XmlPullParser.START_TAG, ns, "name");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("given")) {
                    setGiven(readGiven(parser));
                } else if (name.equals("family")) {
                    setFamily(readFamily(parser));
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
            Log.d(TAG, "Entering readGender, attribute count is " + parser.getAttributeCount());
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

        private Date readBirthTime(XmlPullParser parser) throws IOException, XmlPullParserException {
            final String TAG = "CCDXMLParser read birthTime node";
            Date t = new Date();
            String d = "";
            Log.d(TAG, "Entering readBirthTime, attribute count is " + parser.getAttributeCount());
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
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                Log.d(TAG, name);
                // Look for nested tags in patient
                if (name.equals("streetAddressLine")) {
                    setStreetAddress(readPatientStreetAddress(parser));
                } else if (name.equals("city")) {
                    setCity(readPatientCity(parser));
                } else if (name.equals("state")) {
                    setState(readPatientState(parser));
                } else if (name.equals("postalCode")) {
                    setPostalCode(readPatientPostalCode(parser));
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
