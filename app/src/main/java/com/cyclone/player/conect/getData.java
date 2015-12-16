package com.cyclone.player.conect;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.cyclone.player.helpers.ServiceQueueJson;
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
 * Created by solusi247 on 13/12/15.
 */
public class getData  {
    List<MediaWrapper> mAudioListA;
    private ProgressDialog pDialogA;
    private Context mContextA;

    ArrayList<Callback> mCallback = new ArrayList<Callback>();

    public void getNeeded(List<MediaWrapper> lmw,ProgressDialog pd, Context context) {
        this.mAudioListA = lmw;
        this.pDialogA = pd;
        this.mContextA = context;
    }




    public interface Callback {
        void updateShow(String text);
    }



    public void startConet(){

       for(Callback callback : mCallback){
            callback.updateShow("iniiiiiiiiiiiiiiiiiiiiiiiiiiiii disiniiiiiiiiiiiii");
        }
       // new getQueueArray().execute();

    }
    private class getQueueArray extends AsyncTask<Void, Void, Void> {


        List<Media> alMedia = new ArrayList<Media>();
        Media m;
        MediaDatabase mDB = MediaDatabase.getInstance();
        ServiceQueueJson sh = new ServiceQueueJson();
        String url = "http://www.diradio.net/apis/data/lists/live-song";

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceQueueJson.GET);

        List<MediaWrapper> mw =new ArrayList<>();
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

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance


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
                        //mAudioListA.add(mMedia);
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
            for(Callback callback : mCallback){
                callback.updateShow("iniiiiiiiiiiiiiiiiiiiiiiiiiiiii disiniiiiiiiiiiiii");
            }
           /* if (pDialogA.isShowing())
                pDialogA.dismiss();*/
            /**
             * Updating parsed JSON data into ListView
             * */



        }

    }
}
