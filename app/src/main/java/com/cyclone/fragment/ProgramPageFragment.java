package com.cyclone.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.custom.SnapGestureListener;
import com.cyclone.model.Comment;
import com.cyclone.model.Playlist;
import com.cyclone.model.PlaylistData;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by gilang on 11/10/2015.
 */
public class ProgramPageFragment extends Fragment {

	private NestedScrollView nestedScrollView;
	private DrawerActivity activity;
	private GestureDetectorCompat gd;
	private TextView btnAllComment;
	private RatingBar ratingBar, ratingBar1, ratingBar2, ratingBar3;
	private TextView txtName1, txtName2, txtName3, txtComment1, txtComment2, txtComment3;
	private Comment[] comments;


	public ProgramPageFragment(){}

	public static ProgramPageFragment newInstance(){
		ProgramPageFragment fragment = new ProgramPageFragment();
		fragment.comments = new Comment[3];
		fragment.comments[0] = new Comment("", "Subi", "Mantap pisan lah", "", 4.5f);
		fragment.comments[1] = new Comment("", "Alif", "Agak garing euy", "", 3f);
		fragment.comments[2] = new Comment("", "Toro", "Jadwalnya kurang pas", "", 4f);
		return fragment;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_program_page, parent, false);
		nestedScrollView = (NestedScrollView) v.findViewById(R.id.nested_scroll_view);

//		if(activity != null){
//			gd = new GestureDetectorCompat(activity, new SnapGestureListener(activity));
//			nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					System.out.println("touch recycler");
//					if(nestedScrollView.computeVerticalScrollOffset() == 0)
//						return gd.onTouchEvent(event);
//					return false;
//				}
//			});
//		}
		btnAllComment = (TextView) v.findViewById(R.id.btn_all_comment);
		ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
		txtName1 = (TextView) v.findViewById(R.id.txt_name1);
		txtName2 = (TextView) v.findViewById(R.id.txt_name2);
		txtName3 = (TextView) v.findViewById(R.id.txt_name3);
		txtComment1 = (TextView) v.findViewById(R.id.txt_comment1);
		txtComment2 = (TextView) v.findViewById(R.id.txt_comment2);
		txtComment3 = (TextView) v.findViewById(R.id.txt_comment3);
		ratingBar1 = (RatingBar) v.findViewById(R.id.ratingbar1);
		ratingBar2 = (RatingBar) v.findViewById(R.id.ratingbar2);
		ratingBar3 = (RatingBar) v.findViewById(R.id.ratingbar3);

		btnAllComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, DrawerActivity.class);
				intent.putExtra("title", "Comments");
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_COMMENT);
				intent.putExtra("mode", CommentFragment.MODE_READ);
				activity.startActivity(intent);
			}
		});

		ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(final RatingBar ratingBar, final float rating, boolean fromUser) {
				View v = activity.getLayoutInflater().inflate(R.layout.dialog_single_form, null);
				final EditText formName = (EditText) v.findViewById(R.id.form);
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage("Write your review")
						.setTitle("")
						.setView(v)
						.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(activity, "Comment Added", Toast.LENGTH_SHORT).show();
								comments[2] = comments[1];
								comments[1] = comments[0];
								comments[0] = new Comment("", "Dimas Danang", formName.getText().toString(), "", ratingBar.getRating());
								refreshComment();
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						});
				builder.create().show();
			}
		});

		refreshComment();

		return v;
	}

	public void refreshComment(){
		txtName1.setText(comments[0].username);
		txtName2.setText(comments[1].username);
		txtName3.setText(comments[2].username);
		ratingBar1.setRating(comments[0].rating);
		ratingBar2.setRating(comments[1].rating);
		ratingBar3.setRating(comments[2].rating);
		txtComment1.setText(comments[0].comment);
		txtComment2.setText(comments[1].comment);
		txtComment3.setText(comments[2].comment);
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		if(context instanceof DrawerActivity) {
			activity = (DrawerActivity)context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(R.layout.part_header_program_page, parallaxHeader,
					false);
			parallaxHeader.removeAllViews();
			parallaxHeader.addView(header);
		}
	}
}
