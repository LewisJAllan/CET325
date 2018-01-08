package com.example.bg50xx.assignment;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by Lewis on 08/01/2018.
 */

public class ActivityInstrumentationTest extends ActivityInstrumentationTestCase2<Ticket> {

    //activity tests to be ran
    private Activity mTicketActivity;
    private EditText mEditText, mEditText1, mEditText2;

    //use all methods etc available from Ticket.class
    public ActivityInstrumentationTest() {
        super(Ticket.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        mTicketActivity = getActivity();
        // Get instance of the editText box
        mEditText = (EditText)mTicketActivity.findViewById(R.id.editAdult);
        mEditText1 = (EditText)mTicketActivity.findViewById(R.id.editChild);
        mEditText2 = (EditText)mTicketActivity.findViewById(R.id.editDiscount);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPersistentData(){
        mEditText = (EditText) mTicketActivity.findViewById(R.id.editAdult);
        //give value to test
        final String p = "50";

        // To access UI via an instrumentation test you must use
        // runOnUiThread() and override the run() method
        mTicketActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEditText.setText(p);
            }
        });

        // Close the activity and see if the text we sent to mEditText persists

            mTicketActivity.finish();
            setActivity(null);

            // Re-open the activity
            mTicketActivity = getActivity();
        String q = mEditText.getText().toString();

        // Check the value in editText after re-opening matches our expected value
        assertEquals(p, q);
    }

    public void testPersistentData1(){
        mEditText1 = (EditText) mTicketActivity.findViewById(R.id.editChild);
        //give value to test
        final String p = "30";

        // To access UI via an instrumentation test you must use
        // runOnUiThread() and override the run() method
        mTicketActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEditText1.setText(p);
            }
        });

        // Close the activity and see if the text we sent to mEditText persists

        mTicketActivity.finish();
        setActivity(null);

        // Re-open the activity
        mTicketActivity = getActivity();
        String q = mEditText1.getText().toString();

        // Check the value in editText after re-opening matches our expected value
        assertEquals(p, q);
    }

    public void testPersistentData2(){
        mEditText2 = (EditText) mTicketActivity.findViewById(R.id.editDiscount);
        //give value to test
        final String p = "15";

        // To access UI via an instrumentation test you must use
        // runOnUiThread() and override the run() method
        mTicketActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEditText2.setText(p);
            }
        });

        // Close the activity and see if the text we sent to mEditText persists

        mTicketActivity.finish();
        setActivity(null);

        // Re-open the activity
        mTicketActivity = getActivity();
        String q = mEditText2.getText().toString();

        // Check the value in editText after re-opening matches our expected value
        assertEquals(p, q);
    }
}
