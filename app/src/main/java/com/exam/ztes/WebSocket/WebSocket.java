package com.exam.ztes.WebSocket;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exam.ztes.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocket extends AppCompatActivity {
    private WebSocketClient mWebSocketClient;

    private static final int SERVERPORT = 9000;
//    private static final String SERVER_IP = "ws://sismaf.co.id:9000/MultindoMobile/tester/ChatWebSocket/server.php";
    private static final String SERVER_IP = "ws://sismaf.co.id:9000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket_client);

        connectWebSocket();

        Button btn_ = findViewById(R.id.btn_);
        btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soc();
            }
        });

    }

    private void soc() {

            JSONObject json_column = new JSONObject();
            try {
                json_column.put("idclient", "satu");
                json_column.put("message", "qwertyuiop");
                json_column.put("name", "tes");
                json_column.put("color", "FF7000");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        mWebSocketClient.send(String.valueOf(json_column));
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI(SERVER_IP);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv_ = (TextView)findViewById(R.id.tv_);
                        tv_.setText(tv_.getText() + "\n" + message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}