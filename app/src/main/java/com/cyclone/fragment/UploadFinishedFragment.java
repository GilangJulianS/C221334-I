package com.cyclone.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.strongloop.android.loopback.Container;
import com.strongloop.android.loopback.ContainerRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.VirtualObject;

import java.io.File;

/**
 * Created by gilang on 10/12/2015.
 */
public class UploadFinishedFragment extends Fragment {

	public UploadFinishedFragment(){}

	ImageView img;
	Button btnPublish;
	Context mContext;

	public static UploadFinishedFragment newInstance(){
		UploadFinishedFragment fragment = new UploadFinishedFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_upload_finished, parent, false);
		img = (ImageView) v.findViewById(R.id.img);
		btnPublish = (Button) v.findViewById(R.id.btn_publish);

		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);

				try {
					startActivityForResult(
							Intent.createChooser(intent, "Complete action using"), 1);
				} catch (android.content.ActivityNotFoundException ex) {
					// Potentially direct the user to the Market with a Dialog
					Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		btnPublish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
				final ContainerRepository containerRepo = restAdapter.createRepository(ContainerRepository.class);
				if (UtilUser.currentUser.getUsername() != null) {
					System.out.println("current user id : " + UtilUser.currentUser.getId());
					System.out.println("token user id : " + UtilUser.currentToken.getUserId());
					containerRepo.get(UtilUser.currentUser.getId(), new ObjectCallback<Container>() {
						@Override
						public void onSuccess(Container container) {
							// container was found
							System.out.println("container GET name : " + container.getName());
							goUpload(container);
						}

						@Override
						public void onError(Throwable error) {
							// request failed
							containerRepo.create(UtilUser.currentUser.getId(), new ObjectCallback<Container>() {
								@Override
								public void onSuccess(Container container) {
									// container was created
									System.out.println("name " + container.getName());
									goUpload(container);
								}

								@Override
								public void onError(Throwable error) {
									// request failed
								}
							});
						}
					});

				} else {
					Snackbar.make(v.getRootView(), "ID is Null", Snackbar.LENGTH_SHORT).show();
				}
			}
		});

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 1:
				if (resultCode == Activity.RESULT_OK) {
					Uri uri = data.getData();
					image = uri;
					img.setImageURI(uri);
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private Uri image = null;

	void goUpload(Container container) {
		if (image != null) {
			File localFile = new File(image.getPath());
			System.out.println("path : " + image.toString());

			container.upload(localFile, new ObjectCallback() {
				@Override
				public void onSuccess(VirtualObject object) {
					System.out.println("susscess : Upload");
				}

				@Override
				public void onError(Throwable t) {
					System.out.println("error upload");
				}
			});
		}
	}
}
