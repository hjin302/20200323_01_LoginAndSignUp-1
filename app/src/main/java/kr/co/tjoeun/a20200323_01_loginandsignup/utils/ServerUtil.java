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
import okhttp3.HttpUrl;
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

    //    파라미터 기초 구조 : 어떤화면 context / 무슨일 handler
//    가운데 추가 고려 : 화면에서 어떤 데이터를 받아서 => 서버로 전달?
    public static void putRequestSignUp(Context context, String id, String pw, String name, String phoneNum, final JsonResponseHandler handler) {

        OkHttpClient client = new OkHttpClient();

//        어느주소로? 기능 주소만 변경.
        String urlStr = String.format("%s/auth", BASE_URL);

//        어떤 데이터를 담아야하는지? API 명세 참조.
        FormBody formData = new FormBody.Builder()
                .add("login_id", id)
                .add("password", pw)
                .add("name", name)
                .add("phone", phoneNum)
                .build();

//        어떤 메쏘드를 쓰는지?
        Request request = new Request.Builder()
                .url(urlStr)
                .put(formData)
                .build();


//        건드릴 필요가 없는 부분.
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

    public static void getRequestMyInfo(Context context, final JsonResponseHandler handler) {

        OkHttpClient client = new OkHttpClient();

//        GET - 파라미터를 query에 담는다. => URL에 노출된다.
//         => URL을 가공하면? 파라미터가 첨부된다.

//        뼈대가 되는 주소 가공 변수 : 호스트주소/기능주소 연결해서 생성.
        HttpUrl.Builder urlBuilder = HttpUrl.parse(String.format("%s/my_info", BASE_URL)).newBuilder();
//        urlBuilder.addEncodedQueryParameter("파라미터이름", "값");
//        GET에서 query파라미터를 요구하면 윗 줄처럼 담아주자.

//        파라미터들이 첨부된 urlBuilder를 이용해 => String으로 변환
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build(); // GET의 경우에는 메쏘드 지정 필요 없다. (제일 기본이라)


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

    public static void getRequestBlackList(Context context, final JsonResponseHandler handler) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(String.format("%s/black_list", BASE_URL)).newBuilder();
//        urlBuilder.addEncodedQueryParameter("파라미터이름", "값");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build(); // GET의 경우에는 메쏘드 지정 필요 없다. (제일 기본이라)


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


    public static void getRequestUserList(Context context, String active, final JsonResponseHandler handler) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(String.format("%s/admin/user", BASE_URL)).newBuilder();
        urlBuilder.addEncodedQueryParameter("active", active);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
//                .header("X-Http-Token", ContextUtil.getUserToken(context))
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