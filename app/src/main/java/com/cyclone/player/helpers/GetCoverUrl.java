package com.cyclone.player.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.cyclone.player.interfaces.IgetCoverUrl;
import com.cyclone.player.util.Util;
import com.cyclone.service.ServiceGetData;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.videolan.libvlc.util.AndroidUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by solusi247 on 14/12/15.
 */
public class GetCoverUrl extends AsyncTask<Void, Void, Bitmap> {
    private String url;
    private Bitmap cover;
    String coverPath;
    String cachePath;
    int width;
    BitmapCache cache = BitmapCache.getInstance();

    public GetCoverUrl(String url, String coverPath, String cachePath, int width) {
        this.url = url;
        this.coverPath = coverPath;
        this.width = width;
        this.cachePath = cachePath;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            System.out.println("Url = "+url);
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);


            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        BitmapCache cache = BitmapCache.getInstance();
        cover = bitmap;
        // read (and scale?) the bitmap
        // cover = readCoverBitmap(context, coverPath, width);

        // store cover into both cache

       /* try {
            writeBitmap(cover, cachePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            cache.addBitmapToMemCache(cachePath, cover);
            System.out.println("addBitmapToMemCache berhasil <<<<<<");
        }catch (Exception e){
            System.out.println("addBitmapToMemCache GAGAL  <<<<<<");
        }*/



        // store cover into both cache
        if (cachePath != null) {
            try {
                writeBitmap(cover, cachePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cache.addBitmapToMemCache(cachePath, cover);
            cover = readCoverBitmap(cachePath, width);
        }

        /*for(IgetCoverUrl getCover : ServiceGetData.callbackCover)
            getCover.OnCoverLoaded(bitmap);*/





    }

    @Override
    protected void onCancelled(Bitmap bitmap) {
        super.onCancelled(bitmap);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private static void writeBitmap(Bitmap bitmap, String path) throws IOException {
        OutputStream out = null;
        try {
            System.out.println("writeBitmap start");
            File file = new File(path);
            if (file.exists() && file.length() > 0)
                return;
            out = new BufferedOutputStream(new FileOutputStream(file), 4096);
            if (bitmap != null) {
                System.out.println("writeBitmap not null");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                for(IgetCoverUrl getCover : ServiceGetData.callbackCover)
                    getCover.OnCoverLoaded(bitmap);
            }
            else{
                System.out.println("writeBitmap NULL");
            }
        } catch (Exception e) {
            Log.e("GetCoverUrl", "writeBitmap failed : " + e.getMessage());
        } finally {
            Util.close(out);
        }
    }

    private static Bitmap readCoverBitmap(String path, int dipWidth) {
        Bitmap cover = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        int width = UiTools.convertDpToPx(dipWidth);

        /* Get the resolution of the bitmap without allocating the memory */
        options.inJustDecodeBounds = true;
        if (AndroidUtil.isHoneycombOrLater())
            options.inMutable = true;
        BitmapUtil.setInBitmap(options);
        BitmapFactory.decodeFile(path, options);

        if (options.outWidth > 0 && options.outHeight > 0) {
            options.inJustDecodeBounds = false;
            options.inSampleSize = 1;

            // Find the best decoding scale for the bitmap
            while( options.outWidth / options.inSampleSize > width)
                options.inSampleSize = options.inSampleSize * 2;

            // Decode the file (with memory allocation this time)
            BitmapUtil.setInBitmap(options);
            cover = BitmapFactory.decodeFile(path, options);
        }

        return cover;
    }
}
