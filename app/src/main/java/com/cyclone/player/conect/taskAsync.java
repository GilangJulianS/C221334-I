package com.cyclone.player.conect;

import android.os.AsyncTask;
import android.util.Log;

import com.cyclone.player.helpers.ServiceQueueJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by solusi247 on 13/12/15.
 */
public class taskAsync extends AsyncTask<Void, Void, Void> {
    ServiceQueueJson sh = new ServiceQueueJson();
    String url = "http://www.diradio.net/apis/data/lists/live-song";
    String jsonStr = sh.makeServiceCall(url, ServiceQueueJson.GET);

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("do in background");
        if (jsonStr != null) {
            try {
                //JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray mJsonArray = new JSONArray(jsonStr);

                // looping through All Contacts
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject mJsonObject = mJsonArray.getJSONObject(i);

                    /*myUri = Uri.parse(mJsonObject.getString("file"));

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

                    mDB.addMedia(mMedia);*/
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
    protected void onPreExecute() {
        super.onPreExecute();

        System.out.println("on pre execute");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        System.out.println("on post execute");
    }

}
