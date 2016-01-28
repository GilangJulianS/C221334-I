package com.cyclone.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.interfaces.getData;
import com.cyclone.interfaces.onLoadMediaWrapper;
import com.cyclone.model.Content;
import com.cyclone.model.Program;
import com.cyclone.model.ProgramContent;
import com.cyclone.model.ProgramPager;
import com.cyclone.model.RunningProgram;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String ACTION_GET_DATA_STREAM = "com.cyclone.service.action.GET_DATA_STREAM";
    private static final String ACTION_PLAY_ON_HOME = "com.cyclone.service.action.ACTION_PLAY_ON_HOME";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.cyclone.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.cyclone.service.extra.PARAM2";
    private static final String MEDIA_WARPER = "com.cyclone.service.extra.MEDIA_WARPER";

    private static final String COVER_PATH = "com.cyclone.service.extra.COVERPAth";
    private static final String CACHE_PATH = "com.cyclone.service.extra.CHAHE_PATH";
    private static final String COVER_URL = "com.cyclone.service.extra.COVER_URL";

    //static private test mCallbacks ;
    public static ArrayList<onLoadMediaWrapper> mCallbacks = new ArrayList<onLoadMediaWrapper>();
    public static ArrayList<getData> mCallbacksGetDataHome = new ArrayList<getData>();
    public static ArrayList<IgetCoverUrl> callbackCover = new ArrayList<IgetCoverUrl>();

    public static getData getData;


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
    public static void startActionGetTestDataForPlay(Context context, onLoadMediaWrapper client) {
        if (!mCallbacks.contains(client))
            mCallbacks.add(client);

        System.out.println("dalam callback : " + mCallbacks.size());
        Intent intent = new Intent(context, ServiceGetData.class);
        intent.setAction(ACTION_FOO);
        context.startService(intent);
    }

    public static void getDataHome(Context context, getData client) {
        if (!mCallbacksGetDataHome.contains(client))
            mCallbacksGetDataHome.add(client);

        System.out.println("dalam callback : " + mCallbacksGetDataHome.size());
        Intent intent = new Intent(context, ServiceGetData.class);
        intent.setAction(ACTION_GET_DATA_HOME);
        context.startService(intent);
    }

    public static void getDataStream(Context context, getData client) {
        if (!mCallbacksGetDataHome.contains(client))
            mCallbacksGetDataHome.add(client);

        getData = client;

        System.out.println("dalam callback : " + mCallbacksGetDataHome.size());
        Intent intent = new Intent(context, ServiceGetData.class);
        intent.setAction(ACTION_GET_DATA_STREAM);
        context.startService(intent);
    }

    public static void startPlayOnHome(Context context, MediaWrapper media, onLoadMediaWrapper client) {
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
        System.out.println("onHandleIntent start");
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
                handleActionGetCoveUrl(url, coverPath, cachePath, width);
            }
            else if(ACTION_GET_DATA_HOME.equals(action)){
                getDataHome();
            }
            else if(ACTION_GET_DATA_STREAM.equals(action)){
                getDataStream();
            }

           /* else if (ACTION_PLAY_ON_HOME.equals(action)) {
                MediaWrapper media = intent.getParcelableExtra(MEDIA_WARPER);
                handleActionPlayOnHome(media);
            }*/

        }
        System.out.println("onHandleIntent finish");
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

    private void getDataStream(){
        new getDataStream().execute();
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
            for(onLoadMediaWrapper myTest : mCallbacks)
                myTest.OnLoadComplite(mw);
        }
    }

    private class getDataHome extends AsyncTask<Void, Void, Void> {
        List<List> list = new ArrayList<>();

        Map<String, List> mapList = new HashMap<String, List>();
        ServiceQueueJson sh = new ServiceQueueJson();
        String url_news = "http://www.1071klitefm.com/apis/data/lists/berita";
        String url_talk = "http://www.1071klitefm.com/apis/data/lists/variety";
       /* String url_news = "http://www.sonorasemarang.com/apis/data/lists/berita";
        String url_talk = "http://www.sonorasemarang.com/apis/data/lists/variety";*/

        String jsonStrNews ;
        String jsonStrTalk ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("onPreExecute");

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            // Making a request to url and getting response
            System.out.println("doInBackground");

            jsonStrNews = sh.makeServiceCall(url_news, ServiceQueueJson.GET);
            // Creating service handler class instance

            Log.d("Response News: ", "> " + jsonStrNews);

            List<Content> news = new ArrayList<>();
            if (jsonStrNews != null) {
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStrNews);
                    // looping through All Contacts
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        System.out.println("added from JSON  news item");
                            JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                            String id = mJsonObject.getString("id");
                            String title = mJsonObject.getString("title");
                            String date = mJsonObject.getString("post_date");
                            String file = mJsonObject.getString("file");
                            String gambar = mJsonObject.getString("attachment");
                            String radio = mJsonObject.getString("radio");
                            news.add(new Content(gambar, UtilArrayData.CATEGORY_NEWS,Content.FAVORITABLE, title, UtilArrayData.NAMA_RADIO, date, false, Content.TYPE_RADIO_CONTENT, file, i, id));
                    }

                    list.add(news);
                    mapList.put("news", news);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }


            jsonStrTalk = sh.makeServiceCall(url_talk, ServiceQueueJson.GET);
            Log.d("Response Talk: ", "> " + jsonStrTalk);
            List<Content> talk = new ArrayList<>();
            if (jsonStrTalk != null) {
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStrTalk);
                    // looping through All Contacts
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        System.out.println("added from JSON  news item");
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                        String id = mJsonObject.getString("id");
                        String title = mJsonObject.getString("title");
                        String date = mJsonObject.getString("post_date");
                        String file = mJsonObject.getString("file");
                        String gambar = mJsonObject.getString("attachment");
                        String radio = mJsonObject.getString("radio");
                        talk.add(new Content(gambar, UtilArrayData.CATEGORY_TALK,Content.FAVORITABLE, title, UtilArrayData.NAMA_RADIO, date, false, Content.TYPE_RADIO_CONTENT, file, i, id));
                    }
                    list.add(talk);
                    mapList.put("talk", talk);


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

            UtilArrayData.ContentTalk.clear();
            UtilArrayData.ContentTalk = mapList.get("talk");
            System.out.println("content talk size: "+ UtilArrayData.ContentTalk.size());
            UtilArrayData.ContentNews.clear();
            UtilArrayData.ContentNews = mapList.get("news");
            System.out.println("content news size : " + UtilArrayData.ContentNews.size());

            for(getData dataCallback : mCallbacksGetDataHome)
                dataCallback.onDataLoadedHome(mapList);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            for(getData dataCallback : mCallbacksGetDataHome)
                 dataCallback.onDataLoadedHomeCancel();
                //dataCallback.onDataLoadedHome(mapList);

        }

    }

    private class getDataStream extends AsyncTask<Void, Void, Void> {

        ServiceQueueJson sh = new ServiceQueueJson();
        String url_rundown = "http://www.1071klitefm.com/apis/data/rundown";
        String url_runing = "http://www.1071klitefm.com/apis/data/programme";

        String jsonStrRunDown ;
        String jsonStrRuning ;

        List<Object> datas = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("onPreExecute");

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            // Making a request to url and getting response
            System.out.println("doInBackground");

            jsonStrRuning = sh.makeServiceCall(url_runing, ServiceQueueJson.GET);
            jsonStrRunDown = sh.makeServiceCall(url_rundown, ServiceQueueJson.GET);
            // Creating service handler class instance

            Log.d("Response Running: ", "> " + jsonStrRuning);
            Log.d("Response Run Down: ", "> " + jsonStrRunDown);

            List<Program> programs = new ArrayList<>();
            programs.add(new Program("", "The Dandees", "9am-12am", 5f));
            programs.add(new Program("", "Desta and Gina in The Morning", "7am-9am", 4.5f));
            programs.add(new Program("", "Popular Musics", "1pm-2pm", 4.2f));
            datas.add(new ProgramPager(programs, 1));

            if (jsonStrRuning != null) {
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStrRuning);

                    for (int i = 0; i < mJsonArray.length(); i++) {
                        System.out.println("added from JSON  running item");
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                        String nama = mJsonObject.getString("nama");
                        String foto = mJsonObject.getString("foto");
                        String deskripsi = mJsonObject.getString("deskripsi");
                        datas.add(new RunningProgram(nama, deskripsi, foto));
                        UtilArrayData.program = new RunningProgram(nama, deskripsi, foto);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            if (jsonStrRunDown != null) {
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStrRunDown);
                    // looping through All Contacts
                    int pjg = 5;
                    if(mJsonArray.length() < 5)
                        pjg = mJsonArray.length();
                    for (int i = 0; i < pjg; i++) {
                        System.out.println("added from JSON  news item");
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                        String type = mJsonObject.getString("log_group");
                        String waktu = mJsonObject.getString("waktu");
                        String remark = mJsonObject.getString("remark");
                        int setType = ProgramContent.TYPE_SOUND;
                        if(type.equalsIgnoreCase("SONG"))
                            setType = ProgramContent.TYPE_MUSIC;
                        else if (type.equalsIgnoreCase("ADVS_AUDIO"))
                                setType = ProgramContent.TYPE_COMMERCIAL;
                        else if(type .equalsIgnoreCase("SOUND"))
                                setType = ProgramContent.TYPE_SOUND;

                        datas.add(new ProgramContent(setType, waktu, remark));
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
            System.out.println("jumlah list : " + datas.size());

            if(datas.size() > 0){
                UtilArrayData.ContentLiveStreaming.clear();
                UtilArrayData.ContentLiveStreaming = datas;
            }
            getData.onDataLoadedLiveStreaming(datas);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
                getData.onDataLoadedHomeCancel();
            //dataCallback.onDataLoadedHome(mapList);

        }

    }
}
