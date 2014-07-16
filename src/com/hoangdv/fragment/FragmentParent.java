package com.hoangdv.fragment;

import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hoangdv.adapter.myGridviewAdapter2;
import com.hoangdv.mybabygirl.AlbumActivity;
import com.hoangdv.mybabygirl.R;
import com.hoangdv.variable.Lady;

public class FragmentParent extends Fragment {
	private String url;
	//private GridView GridMainView;
	private GridView GridMainView;
	private myGridviewAdapter2 adp = null;
	private Vector<Lady> arrLady = new Vector<Lady>();
	private ProgressBar bar_firsttime, bar_loading;
	private Button btn_xemthem;
	private int page = 2;
	private ParserURL myTask = null;

	public FragmentParent() {
		super();
		this.url = "http://www.depvd.com";
	}

	public FragmentParent(String url) {
		this.url = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_parent2, container,
				false);
		// Get widget item
		
		// GridMainView = (GridView) rootView.findViewById(R.id.gridView_home);
		GridMainView = (GridView) rootView.findViewById(R.id.gridView_home);
		bar_firsttime = (ProgressBar) rootView
				.findViewById(R.id.progressBar_firstTime);
		bar_loading = (ProgressBar) rootView
				.findViewById(R.id.progressBar_loading);
		btn_xemthem = (Button) rootView.findViewById(R.id.button_xemthem);

		// Setdata null for gridview
		adp = new myGridviewAdapter2(getActivity(), R.layout.item_gridview2,
				arrLady);
		GridMainView.setAdapter(adp);

		myTask = new ParserURL();
		myTask.execute(url);

		btn_xemthem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});
		myEvent();
		return rootView;
	}

	private class ParserURL extends AsyncTask<String, Lady, Vector<Lady>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			btn_xemthem.setVisibility(View.GONE);
			bar_loading.setVisibility(View.GONE);
			super.onPreExecute();
		}

		@Override
		protected Vector<Lady> doInBackground(String... params) {
			// TODO Auto-generated method stub
			Vector<Lady> vttemp = new Vector<Lady>();
			try {
				Document doc = Jsoup.connect(params[0]).get();
				// html = doc.text();
				Elements imgEle = doc.select("img.lazy"); // get link img
				Elements aEle = doc.select("a.group"); // get Link to album
				Elements countEle = doc.select("div.vd-topic-count ul");// get
																		// count
																		// view
				int i = 0;
				for (Element ele : imgEle) {
					// Lady temp = new Lady();
					Lady temp = new Lady();
					temp.setTITLE(ele.attr("alt"));
					temp.setLINK_IMG(ele.attr("src"));
					temp.setLINK(aEle.eq(i).attr("href"));
					temp.setVIEW(countEle.eq(i).select("li").eq(0).text());
					temp.setLIKE(countEle.eq(i).select("li").eq(1).text());
					temp.setCMT(countEle.eq(i).select("li").eq(2).text());
					i++;
					publishProgress(temp);
				}
				return vttemp;
			} catch (Exception ex) {
				Log.d("MyTagError", ex.toString());
				Toast.makeText(getActivity(),
						"Gặp sự cố kết nối, hãy kiểm tra lại!",
						Toast.LENGTH_LONG).show();
				return null;
			}
		}

		@Override
		protected void onProgressUpdate(Lady... values) {
			// TODO Auto-generated method stub

			arrLady.add(values[0]);
			adp.notifyDataSetChanged();
			bar_firsttime.setVisibility(View.GONE);
			bar_loading.setVisibility(View.VISIBLE);

			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Vector<Lady> result) {
			// TODO Auto-generated method stub
			// btn_xemthem.setVisibility(View.VISIBLE);
			bar_loading.setVisibility(View.GONE);
			// adp.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}

	public void myEvent() {
		GridMainView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						Intent myIntent = new Intent(getActivity(),
								AlbumActivity.class);
						myIntent.putExtra("link", arrLady.elementAt(arg2)
								.getLINK());
						myIntent.putExtra("title", arrLady.elementAt(arg2)
								.getTITLE());
						startActivity(myIntent);
					}
				});

		btn_xemthem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String link = url + "/p" + page;
				new ParserURL().execute(link);
				page++;
				bar_loading.setVisibility(View.VISIBLE);
			}
		});

		GridMainView.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				int index = GridMainView.getLastVisiblePosition();
				if (index == (arrLady.size() - 1)) {
					btn_xemthem.setVisibility(View.VISIBLE);
				} else {
					btn_xemthem.setVisibility(View.GONE);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void toCancelTask() {
		if (!myTask.isCancelled()) {
			myTask.cancel(true);
		}
	}

}
