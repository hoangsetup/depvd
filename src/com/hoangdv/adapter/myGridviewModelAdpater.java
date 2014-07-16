package com.hoangdv.adapter;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hoangdv.mybabygirl.R;
import com.hoangdv.variable.Lady;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class myGridviewModelAdpater extends ArrayAdapter<Lady> {
	private Activity ctx;
	private int IdLayout;
	private Vector<Lady> arr = new Vector<Lady>();

	public myGridviewModelAdpater(Activity context, int resource,
			Vector<Lady> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.ctx = context;
		this.IdLayout = resource;
		this.arr = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = ctx.getLayoutInflater().inflate(IdLayout, null);
		}
		final ProgressBar bar = (ProgressBar) convertView
				.findViewById(R.id.progressBar_imgLoadingHome);
		
		TextView tv_tite = (TextView) convertView
				.findViewById(R.id.text);
		ImageView img = (ImageView) convertView.findViewById(R.id.picture);
		
		Picasso.with((Context) ctx).load(arr.elementAt(position).getLINK_IMG())
				.into(img, new Callback() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						bar.setVisibility(View.GONE);
					}

					@Override
					public void onError() {
						// TODO Auto-generated method stub
						
					}
				});
		tv_tite.setText(arr.elementAt(position).getTITLE());
		return convertView;
	}

}
