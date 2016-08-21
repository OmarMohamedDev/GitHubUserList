package com.omarmohamed.githubuserlist.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to check the internet connection
 * Created by omarmohamed on 21/08/2016.
 */
public class ConnectionDetector {

    static boolean status;
    private static Handler messageHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            status = msg.getData().getBoolean("status");

        }
    };
    List<String> connections;
    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
        connections = new ArrayList<String>();
    }

    public boolean isConnectingToInternet() {
        boolean internetState = false;
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        connections.add(info[i].getTypeName());
                        internetState = true;

                    }

        }
        return internetState;
    }

    List<String> getConnections() {
        return connections;
    }

    public boolean hasActiveInternetConnection() {
        new Thread() {
            public void run() {
                Message msg = Message.obtain();

                try {
                    HttpURLConnection urlc = (HttpURLConnection) (new URL(
                            "http://www.google.com").openConnection());
                    urlc.setRequestProperty("User-Agent", "Test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    boolean status = urlc.getResponseCode() == 200;
                    Bundle b = new Bundle();
                    b.putBoolean("status", status);
                    msg.setData(b);
                } catch (IOException e) {
                    //Connection problem
                }
                messageHandler.sendMessage(msg);
            }

        }.start();

        return status;
    }
}
