package kr.co.tjoeun.a20200323_01_loginandsignup.utils;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServerUtil {

//     이론
//     서버통신 주체? ServerUtil
//     응답처리? 액티비티가 함. => 인터페이스로 연결.

    public interface JsonResponseHandler {
        void onResponse(JSONObject json);
    }

//    서버 호스트 주소를 편하게 가져다 쓰려고 변수로 저장.
    private static final String BASE_URL = "http://192.168.0.236:5000";

    public static void postRequestLogin(Context context, String id, String pw, final JsonResponseHandler handler) {

        OkHttpClient client = new OkHttpClient();

        String urlStr = String.format("%s/auth", BASE_URL);

        FormBody formData = new FormBody.Builder()
                .add("login_id", id)
                .add("password", pw)
                .build();

        Request request = new Request.Builder()
                .url(urlStr)
                .post(formData)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("서버연결실패", "연결안됨!");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String body = response.body().string();

                try {
                    JSONObject json = new JSONObject(body);

                    if (handler != null) {
                        handler.onResponse(json);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }




}
