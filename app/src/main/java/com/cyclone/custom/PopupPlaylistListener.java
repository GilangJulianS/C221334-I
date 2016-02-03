package com.cyclone.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.PlaylistAccount;
import com.cyclone.loopback.repository.PlaylistRepository;
import com.cyclone.model.Content;
import com.cyclone.model.Playlist;
import com.cyclone.model.PlaylistData;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by macair on 1/14/16.
 */
public class PopupPlaylistListener implements PopupMenu.OnMenuItemClickListener {

    private Activity activity;
    private Content content;
    private View anchorView;

    public PopupPlaylistListener(Activity activity, Content content, View anchorView){
        this.activity = activity;
        this.content = content;
        this.anchorView = anchorView;
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
                            PlaylistData.playlists.add(new Playlist(formName.getText().toString()));
                            String name = formName.getText().toString();
                            System.out.println("nama : " + name);
                            Toast.makeText(activity, "Item added to " + formName.getText().toString(), Toast.LENGTH_SHORT).show();
                            Map<String, String> parm = new HashMap<String, String>();
                            parm.put("name", "" + name + " ");
                            parm.put("caption", " ");
                            parm.put("private", "false");

                            final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
                            final PlaylistRepository playlistRepository = restAdapter.createRepository(PlaylistRepository.class);

                            final ProgressDialog Pdialog = ProgressDialog.show(activity, "",
                                    "Loading...", true);
                            Pdialog.show();

                            playlistRepository.creat(parm, new Adapter.Callback() {
                                @Override
                                public void onSuccess(String response) {
                                    System.out.println("saved success : " + response);
                                    try {
                                        final PlaylistAccount playlistAccount = new PlaylistAccount();
                                        JSONObject jsonObject = new JSONObject(response);

                                        playlistAccount.setName(jsonObject.getString("name"));
                                        playlistAccount.setCaption(jsonObject.getString("caption"));
                                        playlistAccount.setId(jsonObject.getString("id"));
                                        playlistAccount.setCreatedAt(jsonObject.getString("createdAt"));
                                        playlistAccount.setAccountId(jsonObject.getString("accountId"));
                                        playlistAccount.setPrivate(jsonObject.getString("private"));
                                        UtilArrayData.playlistAccount.add(playlistAccount);
                                        final RestAdapter restAdapter2 = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
                                        final PlaylistRepository playlistRepository2 = restAdapter2.createRepository(PlaylistRepository.class);
                                        List<String> list = new ArrayList<>();
                                        list.add(content.id);
                                        playlistRepository2.addToPlaylist(playlistAccount.getId(), list, new Adapter.Callback() {
                                            @Override
                                            public void onSuccess(String response) {
                                                Toast.makeText(activity, "Item added to " + playlistAccount.getName(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onError(Throwable t) {

                                            }
                                        });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(activity, "Failed to creat Playlist Catch", Toast.LENGTH_SHORT).show();
                                    }
                                    Pdialog.dismiss();
                                    // activity.onBackPressed();
                                }

                                @Override
                                public void onError(Throwable t) {
                                    //activity.onBackPressed();
                                    Pdialog.dismiss();
                                    Toast.makeText(activity, "Failed to creat Playlist error", Toast.LENGTH_SHORT).show();
                                    System.out.println(t);
                                }
                            });
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        }else{
            for (final PlaylistAccount p : UtilArrayData.playlistAccount) {
                if (title.equals(p.getName())) {
                    System.out.println("Name : " + p.getName());
                    System.out.println("IdPlaylist : " + p.getId());
                    System.out.println("IdContent : " + content.id);

                    final RestAdapter restAdapter = new RestAdapter(activity, ServerUrl.API_URL);
                    final PlaylistRepository playlistRepository = restAdapter.createRepository(PlaylistRepository.class);
                    List<String> list = new ArrayList<>();
                    list.add(content.id);
                    playlistRepository.addToPlaylist(p.getId(), list, new Adapter.Callback() {
                        @Override
                        public void onSuccess(String response) {
                            Toast.makeText(activity, "Item added to " + p.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable t) {

                        }
                    });
                }
            }
        }
        return true;
    }
}
