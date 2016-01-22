package com.cyclone.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.Content;
import com.cyclone.model.Playlist;
import com.cyclone.model.PlaylistData;
import com.cyclone.model.Song;

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
                            Toast.makeText(activity, "Item added to " + formName.getText().toString(), Toast.LENGTH_SHORT).show();
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
                    Song song = new Song(content.txtPrimary, content.txtSecondary);
                    p.add(song);
                    Toast.makeText(activity, "Item added to " + p.name, Toast.LENGTH_SHORT).show();
                }
            }
        }
        return true;
    }
}
