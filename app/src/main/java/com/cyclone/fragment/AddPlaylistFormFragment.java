package com.cyclone.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cyclone.R;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.model.Data;

/**
 * Created by gilang on 07/12/2015.
 */
public class AddPlaylistFormFragment extends Fragment {

	private Button btnNext;
	private EditText formTitle, formDescription;

	public AddPlaylistFormFragment(){}

	public static AddPlaylistFormFragment newInstance(){
		AddPlaylistFormFragment fragment = new AddPlaylistFormFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_add_mix_playlist, parent, false);

		btnNext = (Button) v.findViewById(R.id.btn_next);
		formTitle = (EditText) v.findViewById(R.id.form_title);
		formDescription = (EditText) v.findViewById(R.id.form_description);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(formTitle.getText().length() < 1 || formTitle.getText().equals(" ")){
					Snackbar.make(getView(), "Title is Empty", Snackbar.LENGTH_SHORT).show();
					return;
				}
				else{
					Data.reset();
//				Intent i = new Intent(getActivity(), DrawerActivity.class);
//				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_ADD_PLAYLIST);
//				i.putExtra("title", "Add Playlist");
//				startActivity(i);
					UtilArrayData.TitleAdd = formTitle.getText().toString();
					FragmentManager manager = getActivity().getSupportFragmentManager();
					manager.beginTransaction().replace(R.id.container, AddPlaylistFragment.newInstance("")).commit();
				}

			}
		});

		return v;
	}
}
