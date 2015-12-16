package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Data;
import com.cyclone.model.SubcategoryItem;

/**
 * Created by gilang on 21/11/2015.
 */
public class SubcategoryHolder extends UniversalHolder {

	public ImageView imgCover;
	public TextView txtPrimary;
	public TextView txtSecondary;
	public ImageButton btnMenu;
	public ImageButton btnDelete;
	public CheckBox checkbox;

	public SubcategoryHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
		txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
		btnDelete = (ImageButton) v.findViewById(R.id.btn_delete);
		checkbox = (CheckBox) v.findViewById(R.id.checkbox);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((SubcategoryItem) object);
	}

	/*@Override
	public void bind(Object object, Activity activity, int position) {
		bind((SubcategoryItem) object);
	}*/

	public void bind(final SubcategoryItem subcategoryItem){
		imgCover.setImageResource(R.drawable.wallpaper);
		txtPrimary.setText(subcategoryItem.txtPrimary);
		txtSecondary.setText(subcategoryItem.txtSecondary);
		if(subcategoryItem.type == SubcategoryItem.TYPE_NORMAL) {
			btnMenu.setVisibility(View.VISIBLE);
			btnMenu.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					PopupMenu menu = new PopupMenu(activity, btnMenu);
					menu.inflate(R.menu.popup_default);
					menu.setOnMenuItemClickListener(new PopupMenuListener(activity));
					menu.show();
				}
			});
		}else if(subcategoryItem.type == SubcategoryItem.TYPE_DELETABLE){
			btnDelete.setVisibility(View.VISIBLE);
			btnDelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int itemPosition = adapter.datas.indexOf(subcategoryItem);
					Data.remove(subcategoryItem);
					adapter.datas.remove(subcategoryItem);
					adapter.notifyItemRemoved(itemPosition);
				}
			});
		}else if(subcategoryItem.type == SubcategoryItem.TYPE_SELECTABLE){
			checkbox.setVisibility(View.VISIBLE);
		}
	}
}
