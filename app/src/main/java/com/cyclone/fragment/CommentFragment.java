package com.cyclone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.Utils.UtilUser;
import com.cyclone.loopback.model.comment;
import com.cyclone.loopback.repository.CommentRepository;
import com.cyclone.model.Comment;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class CommentFragment extends RecyclerFragment {

	public static final int MODE_READ = 100;
	public static final int MODE_READ_WRITE = 101;
	private EditText formComment;
	private ImageButton btnSubmit;
	private int mode;

	public CommentFragment(){}

	public static CommentFragment newInstance(String json){
		CommentFragment fragment = new CommentFragment();
		fragment.json = json;
		fragment.mode = MODE_READ_WRITE;
		return fragment;
	}

	public static CommentFragment newInstance(String json, int mode, String id){
		CommentFragment fragment = new CommentFragment();
		fragment.json = json;
		fragment.mode = mode;
		fragment.DataId = id;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		if(mode == MODE_READ_WRITE) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			View commentView = inflater.inflate(R.layout.part_comment, parent, false);
			((ViewGroup) v).addView(commentView);

			formComment = (EditText) commentView.findViewById(R.id.form_comment);
			btnSubmit = (ImageButton) commentView.findViewById(R.id.btn_submit);

			btnSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (formComment.getText().length()>0 && !formComment.getText()
							.toString().equalsIgnoreCase("") && !formComment.getText()
							.toString().equalsIgnoreCase(" ") && !formComment.getText()
							.toString().equalsIgnoreCase("  ") ){
						final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
						final CommentRepository commentRepository = restAdapter.createRepository(CommentRepository.class);
						commentRepository.post(DataId, formComment.getText().toString(), new Adapter.Callback() {
							@Override
							public void onSuccess(String response) {

							}

							@Override
							public void onError(Throwable t) {

							}
						});
						Date date = new Date();
						Calendar calendar = GregorianCalendar.getInstance();
						calendar.setTime(date);
						String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
						List<Object> datas = new ArrayList<>();
						datas.add(new Comment("", UtilUser.currentUser.getUsername(), formComment.getText().toString(), time));
						adapter.datas.add(0, datas.get(0));
						formComment.setText(null);
						adapter.notifyItemInserted(0);
						recyclerView.scrollToPosition(0);
						//adapter.notifyDataSetChanged();
					}

					/*Date date = new Date();
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(date);
					String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
					adapter.add(new Comment("", "Dimas Danang", formComment.getText().toString(), time));
					adapter.notifyItemInserted(adapter.getItemCount() - 1);
					recyclerView.scrollToPosition(adapter.getItemCount() - 1);
					formComment.setText("");*/
				}
			});
		}
	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return true;
	}

	@Override
	public int getHeaderLayoutId() {
		return 0;
	}

	@Override
	public void prepareHeader(View v) {

	}

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		if(UtilArrayData.commentList.size()>0){
			List<comment> cmtL = UtilArrayData.commentList;
			for(int i = 0; i < cmtL.size(); i++){
				comment cmt = cmtL.get(i);
				datas.add(new Comment("", cmt.getUsername(), cmt.getContent(), cmt.getUpdatedAt().substring(11,16)));
			}

		}
		/*datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));*/
		return datas;
	}
}
