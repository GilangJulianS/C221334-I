package com.cyclone.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.loopback.repository.PlaylistContentRepository;
import com.cyclone.loopback.repository.PlaylistRepository;
import com.cyclone.model.Content;
import com.cyclone.model.Song;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by macair on 1/14/16.
 */
public class PopupPlaylistListener implements PopupMenu.OnMenuItemClickListener {

    private Activity activity;
    private Content content;
    private View anchorView;
    String contentId;

    public PopupPlaylistListener(Activity activity, Content content, View anchorView, String contentID){
        this.activity = activity;
        this.content = content;
        this.anchorView = anchorView;
        this.contentId = contentID;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String title = item.getTitle().toString();
        if(title.equals("New Playlist")){
            View v = activity.getLayoutInflater().inflate(R.layout.dialog_single_form, null);
            final EditText formName = (EditText) v.findViewById(R.id.form);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("")
                    .setTitle("Playlist Name")
                    .setView(v)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(UtilUser.currentUser != null) {
                                Map<String, String> parm = new HashMap<String, String>();
                                //parm.put("id", UtilUser.currentUser.getId().toString());
                                parm.put("name", formName.getText().toString());
                                parm.put("caption", "created new");
                                parm.put("private", "false");
                                final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
                                PlaylistRepository playlistRepository = restAdapter.createRepository(PlaylistRepository.class);

                                playlistRepository.createContract();
                                playlistRepository.creat(parm, new Adapter.Callback() {
                                    @Override
                                    public void onSuccess(String response) {
                                        System.out.println("saved success : " + response);
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            PlaylistData.playlists.add(new Playlist(jsonObject.getString("name"), jsonObject.getString("id")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onError(Throwable t) {
                                       // activity.onBackPressed();
                                        System.out.println(t);
                                    }
                                });
                            }

                            Toast.makeText(activity, "Item added to ZZZZZZZZZ " + formName.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        }else{
            for(Playlist p : PlaylistData.playlists){
                if(title.equals(p.name)){
                    Song song = new Song(content.txtPrimary, content.txtSecondary, p.id);
                    p.add(song);
                    final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
                    final PlaylistContentRepository playlistContentRepository = restAdapter.createRepository(PlaylistContentRepository.class);
                    System.out.println("ID DID DID DI: "+p.id+" : "+contentId);
                    playlistContentRepository.PostRadioContent(p.id, "56988c8233b5b2bc69665537", new Adapter.Callback() {
                        @Override
                        public void onSuccess(String response) {

                        }

                        @Override
                        public void onError(Throwable t) {

                        }
                    });
                    Toast.makeText(activity, "Itemid "+contentId+" added to " + p.name , Toast.LENGTH_SHORT).show();
                }
            }
        }
        return true;
    }
}
