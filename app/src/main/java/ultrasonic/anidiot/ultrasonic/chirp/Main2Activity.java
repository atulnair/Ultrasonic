package ultrasonic.anidiot.ultrasonic.chirp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.models.ChirpConnectState;
import io.chirp.connect.models.ChirpError;
import kotlin.text.Charsets;
import ultrasonic.anidiot.ultrasonic.R;

public class Main2Activity extends AppCompatActivity {
    private ChirpConnect chirpConnect;
    private Context context;

    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;

    TextView status;
    TextView lastChirp;

    EditText edit;

    Button startStopSdkBtn;
    Button startStopSendingBtn;

    Boolean startStopSdkBtnPressed = false;


    String CHIRP_APP_KEY = "272BFd0A6dC1f5659ba2eBB11";
    String CHIRP_APP_SECRET = "Eecd9B42E4daDAdABeFdE5EFE3fD9EEDc391AacBEB1A9f9ffa";
    String CHIRP_APP_CONFIG = "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";


    String TAG = "ConnectDemoApp";

    View parentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        parentLayout = findViewById(android.R.id.content);

        status = (TextView) findViewById(R.id.stateValue);
        lastChirp = (TextView) findViewById(R.id.lastChirp);
        startStopSdkBtn = (Button) findViewById(R.id.startStopSdkBtn);
        startStopSendingBtn = (Button) findViewById(R.id.startStopSengingBtn);

        edit = findViewById(R.id.edit);

        startStopSendingBtn.setAlpha(.4f);
        startStopSendingBtn.setClickable(false);
        startStopSdkBtn.setAlpha(.4f);
        startStopSdkBtn.setClickable(false);

        context = this;


        chirpConnect = new ChirpConnect(this, CHIRP_APP_KEY, CHIRP_APP_SECRET);




        ChirpError setConfigError = chirpConnect.setConfig(CHIRP_APP_CONFIG);
        if (setConfigError.getCode() > 0) {
            Log.e(TAG, setConfigError.getMessage());
        } else {
            startStopSdkBtn.setAlpha(1f);
            startStopSdkBtn.setClickable(true);
        }

        chirpConnect.setListener(connectEventListener);
    }


    ConnectEventListener connectEventListener = new ConnectEventListener() {

        @Override
        public void onSending(byte[] data, int channel) {

            String dataToSend="null";
            try {
                dataToSend = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            updateLastPayload(dataToSend);
        }

        @Override
        public void onSent(byte[] data, int channel) {

            String dataToSend="null";
            try {
                dataToSend = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            updateLastPayload(dataToSend);
        }

        @Override
        public void onReceiving(int channel) {

            Log.v(TAG, "ConnectCallback: onReceiving on channel: " + channel);
        }

        @Override
        public void onReceived(byte[] data, int channel) {
            /**
             * onReceived is called when a receive event has completed.
             * If the payload was decoded successfully, it is passed in data.
             * Otherwise, data is null.
             */
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            String dataRecieved="null";
            try {
                dataRecieved = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Toast.makeText(Main2Activity.this,dataRecieved,Toast.LENGTH_LONG).show();
            Log.e(TAG, "ConnectCallback: onReceived: " + hexData + " on channel: " + channel);
            updateLastPayload(dataRecieved);
        }

        @Override
        public void onStateChanged(int oldState, int newState) {
            /**
             * onStateChanged is called when the SDK changes state.
             */
            Log.v(TAG, "ConnectCallback: onStateChanged " + oldState + " -> " + newState);
            if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_NOT_CREATED.getCode()) {
                updateStatus("NotCreated");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_STOPPED.getCode()) {
                updateStatus("Stopped");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_PAUSED.getCode()) {
                updateStatus("Paused");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_RUNNING.getCode()) {
                updateStatus("Running");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_SENDING.getCode()) {
                updateStatus("Sending");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_RECEIVING.getCode()) {
                updateStatus("Receiving");
            } else {
                updateStatus(newState + "");
            }

        }

        @Override
        public void onSystemVolumeChanged(float oldVolume, float newVolume) {
            /**
             * onSystemVolumeChanged is called when the system volume is changed.
             */
//
        }

    };

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            if (startStopSdkBtnPressed) startSdk();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (startStopSdkBtnPressed) stopSdk();
                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpConnect.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            chirpConnect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        stopSdk();
    }

    public void updateStatus(final String newStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                status.setText(newStatus);
            }
        });
    }
    public void updateLastPayload(final String newPayload) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lastChirp.setText(newPayload);
            }
        });
    }

    public void stopSdk() {
        ChirpError error = chirpConnect.stop();
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
            return;
        }
        startStopSendingBtn.setAlpha(.4f);
        startStopSendingBtn.setClickable(false);
        startStopSdkBtn.setText("Start Sdk");
    }

    public void startSdk() {
        ChirpError error = chirpConnect.start();
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
            return;
        }
        startStopSendingBtn.setAlpha(1f);
        startStopSendingBtn.setClickable(true);
        startStopSdkBtn.setText("Stop Sdk");
    }

    public void startStopSdk(View view) {

        startStopSdkBtnPressed = true;
        if (chirpConnect.getState() == ChirpConnectState.CHIRP_CONNECT_STATE_STOPPED) {
            startSdk();
        } else {
            stopSdk();
        }
    }

    public void sendPayload(View view) {
        /**
         * A payload is a byte array dynamic size with a maximum size defined by the config string.
         *
         * Generate a random payload, and send it.
         */
        long maxPayloadLength = chirpConnect.maxPayloadLength();
        long size = (long) new Random().nextInt((int) maxPayloadLength) + 1;
//        byte[] payload = chirpConnect.randomPayload((byte) size);

        String payloadString = edit.getText().toString();
        byte[] bytes = new byte[0];
        try {
            bytes = payloadString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        long maxSize = chirpConnect.maxPayloadLength();
        if (maxSize < bytes.length) {
            Log.e(TAG, "Invalid Payload");
            return;
        }
        ChirpError error = chirpConnect.send(bytes);
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
        }
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
