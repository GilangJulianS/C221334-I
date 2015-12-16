package com.cyclone.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.cyclone.interfaces.getData;
import com.cyclone.interfaces.test;
import com.cyclone.model.Content;
import com.cyclone.player.helpers.GetCoverUrl;
import com.cyclone.player.helpers.ServiceQueueJson;
import com.cyclone.player.interfaces.IgetCoverUrl;
import com.cyclone.player.media.MediaCustom;
import com.cyclone.player.media.MediaDatabase;
import com.cyclone.player.media.MediaWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.videolan.libvlc.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ServiceGetData extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.cyclone.service.action.FOO";
    private static final String ACTION_BAZ = "com.cyclone.service.action.BAZ";
    private static final String ACTION_GET_COVER_URL = "com.cyclone.service.action.GET_COVER_URL";
    private static final String ACTION_GET_DATA_HOME = "com.cyclone.service.action.GET_DATA_HOME";
    private static final String ACTION_PLAY_ON_HOME = "com.cyclone.service.action.ACTION_PLAY_ON_HOME";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.cyclone.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.cyclone.service.extra.PARAM2";
    private static final String MEDIA_WARPER = "com.cyclone.service.extra.MEDIA_WARPER";

    private static final String COVER_PATH = "com.cyclone.service.extra.COVERPAth";
    private static final String CACHE_PATH = "com.cyclone.service.extra.CHAHE_PATH";
    private static final String COVER_URL = "com.cyclone.service.extra.COVER_URL";

    //static private test mCallbacks ;
    public static ArrayList<test> mCallbacks = new ArrayList<test>();
    public static ArrayList<getData> mCallbacksGetDataHome = new ArrayList<getData>();
    public static ArrayList<IgetCoverUrl> callbackCover = new ArrayList<IgetCoverUrl>();


    public static Context CoverContext;

    public ServiceGetData() {
        super("ServiceGetData");

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("start nihhhhhhhhhh servisvya");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetTestDataForPlay(Context context, test client) {
        if (!mCallbacks.contains(client))
            mCallbacks.add(client);

        System.out.println("dalam callback : " + mCallbacks.size());
        Intent intent = new Intent(context, ServiceGetData.class);
        intent.setAction(ACTION_FOO);
        context.startService(intent);
    }

    public static void getDataHome(Context context, getData client, String param2) {
        if (!mCallbacksGetDataHome.contains(client))
            mCallbacksGetDataHome.add(client);

        System.out.println("dalam callback : " + mCallbacks.size());
        Intent intent = new Intent(context, ServiceGetData.class);
        intent.setAction(ACTION_GET_DATA_HOME);
        //mCallbacks = param1;
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startPlayOnHome(Context context, MediaWrapper media, test client) {
        if(mCallbacks != null)
            mCallbacks.clear();
        mCallbacks.add(client);

        System.out.println("si start on play home : "+ mCallbacks.size());
        Intent intent = new Intent(context, ServiceGetData.class);
        intent.setAction(ACTION_PLAY_ON_HOME);
        intent.putExtra(MEDIA_WARPER, media);
        context.startService(intent);
    }

    public static void getCoverUrl(IgetCoverUrl client,Context context, String url, String coverPath, String cachePath, int width) {
        if (!callbackCover.contains(client))
            callbackCover.clear();
        callbackCover.add(client);

        System.out.println("dalam callback : " + callbackCover.size());
        Intent intent = new Intent(context, ServiceGetData.class);
        intent.setAction(ACTION_GET_COVER_URL);
        intent.putExtra(COVER_PATH, coverPath);
        intent.putExtra(CACHE_PATH, cachePath);
        intent.putExtra(COVER_URL, url);
        intent.putExtra("int", width);

        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ServiceGetData.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo();
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
            else if(ACTION_GET_COVER_URL.equals(action)){
                final String url = intent.getStringExtra(COVER_URL);
                final String coverPath = intent.getStringExtra(COVER_PATH);
                final String cachePath = intent.getStringExtra(CACHE_PATH);
                final int width = intent.getIntExtra("int", 0);
                handleActionGetCoveUrl(url, coverPath, cachePath,width);
            }
            else if(ACTION_GET_DATA_HOME.equals(action)){
                getDataHome();
            }
           /* else if (ACTION_PLAY_ON_HOME.equals(action)) {
                MediaWrapper media = intent.getParcelableExtra(MEDIA_WARPER);
                handleActionPlayOnHome(media);
            }*/
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo() {
        System.out.println("ahaaa conect");
        new getQueueArray().execute();
    }

    private void handleActionGetCoveUrl(String url, String coverPath, String cachePath, int width){
        System.out.println("Url = "+url);
        new GetCoverUrl(url, coverPath, cachePath, width).execute();
    }

    private void getDataHome(){
        new getDataHome().execute();
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class getQueueArray extends AsyncTask<Void, Void, Void> {


        List<Media> alMedia = new ArrayList<Media>();
        Media m;
        MediaDatabase mDB = MediaDatabase.getInstance();
        ServiceQueueJson sh = new ServiceQueueJson();
        String url = "http://www.diradio.net/apis/data/lists/live-song";

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceQueueJson.GET);

        List<MediaWrapper> mw =new ArrayList<MediaWrapper>();
        Uri myUri ;
        MediaWrapper mMedia;
        MediaCustom MC;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
           /* pDialogA = new ProgressDialog(mContextA);
            pDialogA.setMessage("Please wait...");
            pDialogA.setCancelable(false);
            pDialogA.show();*/

            System.out.println("onPreExecute");

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            System.out.println("doInBackground");

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray mJsonArray = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);

                        myUri = Uri.parse(mJsonObject.getString("file"));

                        MC = new MediaCustom();
                        MC.setUri(myUri);
                        MC.setTitle(mJsonObject.getString("title"));
                        MC.setArtist(mJsonObject.getString("title"));
                        MC.setAlbum(mJsonObject.getString("title"));
                        MC.setAlbumArtist(mJsonObject.getString("title"));
                        MC.setArtworkURL(mJsonObject.getString("attachment"));

                        mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                                MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                                MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                                MC.getDiscNumber(), MC.getLastModified());

                        mDB.addMedia(mMedia);

                        mw.add(mMedia);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            System.out.println("onPostExecute");
            System.out.println("jumlah list :" + mw.size());
            for(test myTest : mCallbacks)
                myTest.OnLoadComplite(mw);

           /* if (pDialogA.isShowing())
                pDialogA.dismiss();*/
            /**
             * Updating parsed JSON data into ListView
             * */



        }

    }


    private class getDataHome extends AsyncTask<Void, Void, Void> {


        List<List> list = new ArrayList<>();
        ServiceQueueJson sh = new ServiceQueueJson();
        String url_news = "http://www.1071klitefm.com/apis/data/lists/berita";

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url_news, ServiceQueueJson.GET);



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            System.out.println("onPreExecute");

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            System.out.println("doInBackground");

            Log.d("Response: ", "> " + jsonStr);

            List<Content> news = new ArrayList<>();
            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray mJsonArray = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < 3; i++) {

                            JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                            String id = mJsonObject.getString("id");
                            String title = mJsonObject.getString("title");
                            String date = mJsonObject.getString("post_date");
                            String file = mJsonObject.getString("file");
                            String gambar = mJsonObject.getString("attachment");
                            String radio = mJsonObject.getString("radio");
                            // myUri = Uri.parse(mJsonObject.getString("file"));

                            //news.add(new Content(id,title, date, file, gambar, radio));
                        news.add(new Content(gambar, "Latest News", title, "Nama Radio", date, file));

                    }

                    list.add(news);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            System.out.println("onPostExecute");
            System.out.println("jumlah list : " + list.size());

            for(getData dataCallback : mCallbacksGetDataHome)
                dataCallback.onDataLoadedHome(list);



           /* if (pDialogA.isShowing())
                pDialogA.dismiss();*/
            /**
             * Updating parsed JSON data into ListView
             * */



        }

    }

    /*private void handleActionPlayOnHome(MediaWrapper media) {
        System.out.println("handleActionPlayOnHome");
        for(test myTest : mCallbacks)
            myTest.onLoadedPlayOnHolder(media);
    }*/
}
