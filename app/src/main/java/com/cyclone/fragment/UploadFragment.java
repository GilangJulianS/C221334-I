package com.cyclone.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;

/**
 * Created by gilang on 10/12/2015.
 */
public class UploadFragment extends Fragment{

	private static final int REQUEST_CODE = 0;
	private ImageView btnUpload;

	public UploadFragment(){}

	public static UploadFragment newInstance(){
		UploadFragment fragment = new UploadFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_upload, parent, false);

		btnUpload = (ImageView) v.findViewById(R.id.btn_upload);
		btnUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showFileChooser();
			}
		});

		return v;
	}

	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("audio/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(
					Intent.createChooser(intent, "Select a File to Upload"), REQUEST_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CODE:
				if (resultCode == Activity.RESULT_OK) {
					Uri uri = data.getData();

					String path = getRealPathFromURI(getContext(), uri);
					System.out.println("pppaaaattthh <<<<<<<<<<<<<<<<<<<<<<<, : " + path);
					Toast.makeText(getContext(), "File path : " + uri.toString(), Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getContext(), DrawerActivity.class);
					i.putExtra("fragmentType", MasterActivity.FRAGMENT_UPLOAD_FINISHED);
					i.putExtra("title", "Content Uploaded");
					i.putExtra("path", path);

					startActivity(i);
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {
			String[] proj = {MediaStore.Audio.Media.DATA};
			cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}


}
