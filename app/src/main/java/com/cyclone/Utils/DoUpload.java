package com.cyclone.Utils;

import android.net.Uri;
import android.os.StrictMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by solusi247 on 10/02/16.
 */
public class DoUpload {
    String name;
    String caption;
    String cover;
    String Private;
    File file;
    static DoUpload doUpload;

    public static DoUpload newInsane(String name, String caption, String cover, String Private, File file) {
        doUpload = new DoUpload();
        doUpload.name = name;
        doUpload.caption = caption;
        doUpload.cover = cover;
        doUpload.Private = Private;
        doUpload.file = file;
        return doUpload;
    }

    public void upload() throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        System.out.println("token >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>." + UtilUser.currentToken.getId().toString());
        System.out.println("File path : " + file.getPath());
        final String IMGUR_CLIENT_ID = UtilUser.currentToken.getId().toString();
        final MediaType MEDIA_TYPE_AUDIO = MediaType.parse("audio/mpeg");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", name, RequestBody.create(MEDIA_TYPE_AUDIO, file))
                .addFormDataPart("name", name)
                .addFormDataPart("caption", caption)
                .addFormDataPart("coverArt", cover)
                .addFormDataPart("private", Private)
                .build();

        Request request = new Request.Builder()
                .header("authorization", IMGUR_CLIENT_ID)
                .addHeader("content-type", "multipart/form-data")
                .url(ServerUrl.API_URL + "/uploads")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());

        /*MediaType mediaType = MediaType.parse("audio/mpeg");
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name",name)
                .addFormDataPart("caption", caption)
                .addFormDataPart("coverArt", cover)
                .addFormDataPart("private", Private)
                .addFormDataPart("file", name, RequestBody.create(mediaType, file))
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.12:3000/api/upload")
                .post(body)
                .addHeader("content-type", "multipart/form-data")
                .addHeader("authorization", UtilUser.currentToken.getId().toString())
                .build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());*/


    }

    byte[] UriToByte(Uri data) {
        //Uri data = result.getData();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(data.getPath()));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

   /* OkHttpClient client = new OkHttpClient();

    MediaType mediaType = MediaType.parse("multipart/form-data; boundary=---011000010111000001101001");
    RequestBody body = RequestBody.create(mediaType, "-----011000010111000001101001\r\nContent-Disposition: form-data;" +
            " name=\"name\"\r\n\r\nakjshfdkjadshf\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
            " name=\"caption\"\r\n\r\nhehehe\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
            " name=\"coverArt\"\r\n\r\ntest\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; " +
            "name=\"genre\"\r\n\r\naklsjdflas\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; " +
            "name=\"lyric\"\r\n\r\naskdjf\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; " +
            "name=\"info\"\r\n\r\nasdf\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; " +
            "name=\"private\"\r\n\r\nfalse\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; " +
            "name=\"url\"\r\n\r\n/api/blablabla/blablabla/blablabla\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
            " name=\"file\"; filename=\"bad-things.mp3\"\r\nContent-Type: audio/mpeg\r\n\r\n\r\n-----011000010111000001101001--");
    Request request = new Request.Builder()
            .url("http://192.168.1.12:3000/api/uploads")
            .post(body)
            .addHeader("content-type", "multipart/form-data; boundary=---011000010111000001101001")
            .addHeader("authorization", "d4iLAVIvYsFQt7JIT48bmU0LAabFuoEjxXVwVdWsJlz8Bw8MDMIqStEskjzJQqsz")
            .addHeader("cache-control", "no-cache")
            .addHeader("postman-token", "dd8ab186-8887-98d4-dfd9-60e511f76261")
            .build();

    Response response = client.newCall(request).execute();*/
}
