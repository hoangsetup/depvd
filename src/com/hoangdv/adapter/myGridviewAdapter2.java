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

public class myGridviewAdapter2 extends ArrayAdapter<Lady> {
	private Activity context;
	private int layoutID;
	private Vector<Lady> lst = new Vector<Lady>();

	public myGridviewAdapter2(Activity context, int resource,
			Vector<Lady> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutID = resource;
		this.lst = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = context.getLayoutInflater().inflate(layoutID, null);
		}

		TextView tv_tite = (TextView) convertView
				.findViewById(R.id.text);

		ImageView img = (ImageView) convertView
				.findViewById(R.id.picture);

		final ProgressBar bar_loading = (ProgressBar) convertView
				.findViewById(R.id.progressBar_imgLoadingHome);

		Lady temp = new Lady();
		temp = lst.elementAt(position);

		tv_tite.setText(temp.getTITLE());

		Picasso.with((Context) context).load(temp.getLINK_IMG())
				.into(img, new Callback() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						bar_loading.setVisibility(View.GONE);
					}

					@Override
					public void onError() {
						// TODO Auto-generated method stub

					}
				});

		return convertView;
	}

}
