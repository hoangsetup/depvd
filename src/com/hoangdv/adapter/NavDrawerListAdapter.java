package com.hoangdv.adapter;

import java.util.Calendar;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoangdv.mybabygirl.R;
import com.hoangdv.variable.Variable;

public class NavDrawerListAdapter extends BaseAdapter {
	private Activity context;
	private String[] arrString;

	public NavDrawerListAdapter(Activity context, String[] arrString) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arrString = arrString;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrString.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arrString[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = context.getLayoutInflater().inflate(
					R.layout.item_slidermenu, null);
		}
		
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
		//
		imgIcon.setImageResource(Variable.ICON_NAV[arg0]);
		txtTitle.setText(arrString[arg0]);
		if (Variable.COUNT_NAV[arg0].equalsIgnoreCase("0")) {
			//txtCount.setVisibility(View.GONE);
			Calendar calendar = Calendar.getInstance();
			int week = calendar.get(Calendar.WEEK_OF_YEAR);
			int year = calendar.get(Calendar.YEAR);
			txtCount.setText("Tuần "+(week-1)+"-"+year);
		} else {
			txtCount.setText(Variable.COUNT_NAV[arg0]);
		}
		return convertView;
	}
}
