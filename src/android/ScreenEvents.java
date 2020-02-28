package com.wrobins.cordova.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

public class ScreenEvents extends CordovaPlugin {
    private Activity activity;
    private Context context;
    private CallbackContext callbackContext;

    private static final String LISTENER_INIT = "listenerInit";
    private static final String SCREEN_TURNED_ON = "SCREEN_TURNED_ON";
    private static final String SCREEN_TURNED_OFF = "SCREEN_TURNED_OFF";

    private class ScreenStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                ScreenEvents.this.callbackContext.success(SCREEN_TURNED_ON);
                activity.unregisterReceiver(this);
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                ScreenEvents.this.callbackContext.success(SCREEN_TURNED_OFF);
                activity.unregisterReceiver(this);
            }
        }
    }

    private ScreenStateReceiver mReceiver;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        activity = cordova.getActivity();
        context = webView.getContext();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;

        try {
            if (LISTENER_INIT.equals(action)) {
                this.listenerInit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error(e.getMessage());
            return false;
        }

        return true;
    }

    private void listenerInit() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new ScreenStateReceiver();
        activity.registerReceiver(mReceiver, intentFilter);
    }
}