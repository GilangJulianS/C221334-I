package com.cyclone.custom;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.cyclone.DrawerActivity;

/**
 * Created by gilang on 07/11/2015.
 */
public class SnapGestureListener implements GestureDetector.OnGestureListener{

	DrawerActivity activity;

	public SnapGestureListener(DrawerActivity activity){
		this.activity = activity;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//		System.out.println("fling recycler: " + velocityX + " " + velocityY);
//		AppBarLayout appBarLayout = activity.appBarLayout;
//		if(activity.changing)
//			return true;
//		if(Math.abs(velocityY) > 10){
//			activity.changing = true;
//			if(velocityY > 0){
//				appBarLayout.setExpanded(true);
//				System.out.println("expanded");
//			}else if(velocityY < 0){
//				appBarLayout.setExpanded(false);
//				System.out.println("collapsed");
//
//			}
//		}
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
//		if(activity.changing && activity.lastPercent > 50) {
//			activity.appBarLayout.setExpanded(false);
//			return true;
//		}else if(activity.changing && activity.lastPercent < 50){
//			activity.appBarLayout.setExpanded(true);
//			return true;
//		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
//		if(activity.changing)
//			return true;
		return false;
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if(distanceY > 0){
			activity.appBarLayout.setExpanded(false);
			if(activity.isExpanded)
				return true;
			else
				return false;
		}else{
			activity.appBarLayout.setExpanded(true);
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}
}
