package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Mix;

/**
 * Created by gilang on 23/11/2015.
 */
public class MixHolder extends UniversalHolder {

	public ImageView imgCover;
	public TextView txtTitle;
	public ImageView imgHeart;

	public MixHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		imgHeart = (ImageView) v.findViewById(R.id.img_heart);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Mix) object);
	}

	/*@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Mix) object);
	}*/

	public void bind(final Mix mix){
		imgCover.setImageResource(R.drawable.wallpaper);
		imgCover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!mix.isFavorite) {
					imgHeart.setVisibility(View.VISIBLE);
					int oldPosition = adapter.datas.indexOf(mix);
					adapter.datas.remove(mix);
					adapter.add(mix, getLastLikedEntry());
					adapter.notifyItemMoved(oldPosition, getLastLikedEntry());
					mix.isFavorite = true;
					addRecommend(oldPosition);
				}else{
					imgHeart.setVisibility(View.GONE);
					int oldPosition = adapter.datas.indexOf(mix);
					int newPosition = getLastLikedEntry() + 1;
					adapter.datas.remove(mix);
					adapter.add(mix, newPosition);
					adapter.notifyItemMoved(oldPosition, newPosition);
					mix.isFavorite = false;
				}
			}
		});
		txtTitle.setText(mix.text);
		if(mix.isFavorite){
			imgHeart.setVisibility(View.VISIBLE);
		}else{
			imgHeart.setVisibility(View.GONE);
		}
	}

	public void addRecommend(int curPosition){
		adapter.datas.add(curPosition + 1, new Mix("", "Test", ""));
		adapter.datas.add(curPosition + 2, new Mix("", "Test2", ""));
		adapter.datas.add(curPosition + 3, new Mix("", "Test3", ""));
		adapter.notifyItemInserted(curPosition + 1);
		adapter.notifyItemInserted(curPosition + 2);
		adapter.notifyItemInserted(curPosition + 3);
	}

	public int getLastLikedEntry(){
		int idx = 0;
		for(int i=0; i<adapter.datas.size(); i++){
			if(((Mix)adapter.datas.get(i)).isFavorite)
				idx = i;
		}
		return idx;
	}
}
