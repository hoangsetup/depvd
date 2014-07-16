package com.hoangdv.adapter;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hoangdv.mybabygirl.R;
import com.hoangdv.variable.Lady;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class myListTopChudeAdapter extends ArrayAdapter<Lady> {
	private Context context;
	private int IdLayout;
	private Vector<Lady> arr = new Vector<Lady>();

	public myListTopChudeAdapter(Context context, int resource,
			Vector<Lady> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.IdLayout = resource;
		this.arr = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(IdLayout, null);
		}

		Button btn_toprank = (Button) convertView
				.findViewById(R.id.button_toprank_chude);
		if (position == 0) {
			btn_toprank.setBackgroundResource(R.drawable.pie);
		} else {
			btn_toprank.setBackgroundResource(R.drawable.pie2);
		}
		btn_toprank.setText((position + 1) + "");
		TextView tv_title = (TextView) convertView
				.findViewById(R.id.textView_title_topchude);

		TextView tv_view = (TextView) convertView
				.findViewById(R.id.textView_viewTopchude);
		TextView tv_like = (TextView) convertView
				.findViewById(R.id.textView_liketopchude);
		TextView tv_cmt = (TextView) convertView
				.findViewById(R.id.textView_cmttopchude);

		ImageView img_thumb = (ImageView) convertView
				.findViewById(R.id.imageView_thumb_topchude);
		final ProgressBar bar = (ProgressBar) convertView
				.findViewById(R.id.progressBar_loading_avata_topchude);

		Lady t = arr.elementAt(position);

		tv_title.setText(t.getTITLE());
		tv_view.setText(t.getVIEW());
		tv_like.setText(t.getLIKE());
		tv_cmt.setText(t.getCMT());
		Picasso.with((Context) context).load(t.getLINK_IMG())
				.into(img_thumb, new Callback() {

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

		return convertView;
	}

}
