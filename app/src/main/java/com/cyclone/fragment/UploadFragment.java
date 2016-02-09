package com.cyclone.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;

import java.net.URISyntaxException;

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

					try {
						String path = getPath(getContext(), uri);
						Toast.makeText(getContext(), "File path : " + uri.toString(), Toast.LENGTH_SHORT).show();
						Intent i = new Intent(getContext(), DrawerActivity.class);
						i.putExtra("fragmentType", MasterActivity.FRAGMENT_UPLOAD_FINISHED);
						i.putExtra("title", "Content Uploaded");
						i.putExtra("path", uri.toString());
						startActivity(i);
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public String getPath(Context context, Uri uri) throws URISyntaxException {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri, projection, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it
			}
		}
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}
}
