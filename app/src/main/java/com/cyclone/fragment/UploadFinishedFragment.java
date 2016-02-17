package com.cyclone.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.Utils.DoUpload;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.player.gui.PlaybackServiceFragment;
import com.strongloop.android.loopback.Container;
import com.strongloop.android.loopback.ContainerRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.VirtualObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by gilang on 10/12/2015.
 */
public class UploadFinishedFragment extends PlaybackServiceFragment {

	public UploadFinishedFragment(){}

	ImageView img;
	Button btnPublish;
	Context mContext;
	byte[] imgByte;
	private String coverName;
	RestAdapter restAdapter;
	TextView formTitle, formDesc;
	String path;

	//static Uri audioUri;
	static UploadFinishedFragment fragment;

	public static UploadFinishedFragment newInstance(String path) {
		fragment = new UploadFinishedFragment();
		fragment.path = path;
		System.out.println("... " + path);
		System.out.println("sudio Uri : " + path);
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
		formTitle = (EditText) v.findViewById(R.id.txt_title);
		formDesc = (EditText) v.findViewById(R.id.txt_description);

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

				restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
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
					try {
						Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
						imgByte = stream.toByteArray();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("gagal to byte");
					}

				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private Uri image = null;
	private Uri imageRenamed = null;

	void goUpload(Container container) {
		Uri imgUri = rename();
		if (imgUri != null) {
			File localFile = new File(imgUri.getPath());
			System.out.println("path : " + imgUri.toString());

			container.upload(localFile, new ObjectCallback() {
				@Override
				public void onSuccess(VirtualObject object) {
					System.out.println("susscess : Upload");
					delete();
					goUploadAudio();
				}

				@Override
				public void onError(Throwable t) {
					System.out.println("error upload");
				}
			});
		}
	}

	void goUploadAudio() {
		String coverUrl = ServerUrl.API_URL + "/containers/" + UtilUser.currentUser.getId() + "/download/" + coverName + ".jpg";
		File audio = new File(path);
		//byte[] audioByte = UriToByte(audioUri);

		DoUpload doUpload = DoUpload.newInsane(formTitle.getText().toString(), formDesc.getText().toString(), coverUrl, "false", audio);
		try {
			doUpload.upload();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("errr");
		}
	}

	Uri rename() {
		File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Cyclone");
		if (!directory.exists()) {
			directory.mkdir();
		}
		OutputStream fOut;
		coverName = "" + System.currentTimeMillis();
		File file = new File(directory, coverName + ".jpg");
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			fOut = new FileOutputStream(file);
			fOut.write(imgByte);
			fOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Uri uri = Uri.parse(file.getPath());
		System.out.println(uri);
		return uri;
	}

	void delete() {
		File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Cyclone");
		OutputStream fOut;
		File file = new File(directory, coverName + ".jpg");
		if (file.exists()) {
			file.delete();
		}
	}

	byte[] UriToByte(Uri data) {
		//Uri data = result.getData();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(data.getPath()));
			byte[] buf = new byte[1024];
			int n;
			while (-1 != (n = fis.read(buf)))
				baos.write(buf, 0, n);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
}
//http://192.168.1.12:3000/api/containers/567246f8e14b45fb245b8e9c/download/1455006227523.jpg
