package com.hoangdv.adapter;

import java.util.Vector;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.hoangdv.mybabygirl.R;
import com.squareup.picasso.Picasso;

public class ViewPageAdapter extends PagerAdapter {
	Context context;
	Vector<String> arrBm = new Vector<String>();

	public ViewPageAdapter(Context context, Vector<String> arrBm) {
		this.context = context;
		this.arrBm = arrBm;
	}

	public int getCount() {
		return this.arrBm.size();
	}

	public Object instantiateItem(View collection, int position) {
		// ImageView view = new ImageView(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v1 = inflater.inflate(R.layout.imageview_pageview, null);
		ImageView view = (ImageView) v1.findViewById(R.id.img_pageview);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//view.setScaleType(ScaleType.FIT_XY);
		Picasso.with((Context) context).load(arrBm.elementAt(position))
				.into(view);
		((ViewPager) collection).addView(view, 0);
		return view;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (View) arg1;
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}
}
