package com.hoangdv.mybabygirl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hoangdv.adapter.myListTopChudeAdapter;
import com.hoangdv.adapter.myListTopModelAdapter;
import com.hoangdv.variable.Lady;

public class TopActivity extends Activity {
	private String TITLE = "";
	private ListView lstv1, lstv2, lstv3;
	private Vector<Lady> arrTopModel = new Vector<Lady>();
	private ArrayAdapter<Lady> adparr1 = null;

	private Vector<Lady> arrTopChude = new Vector<Lady>();
	private ArrayAdapter<Lady> adparr2 = null;

	private Vector<Lady> arrTopUser = new Vector<Lady>();
	private ArrayAdapter<Lady> adparr3 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.top20activity);
		super.onCreate(savedInstanceState);

		// First time
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00b4ff")));
		// Xóa icon app trên Actionbar
		//getActionBar().setIcon(android.R.color.transparent);
		getActionBar().setSubtitle("Top 20");
		// Color for subtitle
		int actionBarTitleId = Resources.getSystem().getIdentifier(
				"action_bar_subtitle", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE);
			}
		}

		configTab();
		doTab1();
		doTab2();
		doTab3();
		myEvent();
	}

	public void configTab() {
		final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
		tab.setup();
		TabHost.TabSpec spec;
		// Config tab1
		spec = tab.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Model");
		tab.addTab(spec);
		// Config tab2
		spec = tab.newTabSpec("tab2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Chủ đề");
		tab.addTab(spec);
		// Config tab3
		spec = tab.newTabSpec("tab3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("User");
		tab.addTab(spec);
	}

	public void doTab1() {
		lstv1 = (ListView) findViewById(R.id.listView1);

		adparr1 = new myListTopModelAdapter(TopActivity.this,
				R.layout.item_topmodel, arrTopModel);
		lstv1.setAdapter(adparr1);

		new ParserURL().execute("http://www.depvd.com/top20");

	}

	public void doTab2() {
		lstv2 = (ListView) findViewById(R.id.listView2);
		adparr2 = new myListTopChudeAdapter(TopActivity.this,
				R.layout.item_topchude, arrTopChude);
		lstv2.setAdapter(adparr2);
	}

	public void doTab3() {
		lstv3 = (ListView) findViewById(R.id.listView3);
		adparr3 = new myListTopModelAdapter(TopActivity.this,
				R.layout.item_topmodel, arrTopUser);
		lstv3.setAdapter(adparr3);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	public void myEvent() {
		lstv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(TopActivity.this,
						ModelActivity.class);
				myIntent.putExtra("subtitle", arrTopModel.elementAt(arg2)
						.getTITLE());
				myIntent.putExtra("link", arrTopModel.elementAt(arg2).getLINK());
				startActivity(myIntent);
			}
		});

		lstv2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(TopActivity.this,
						AlbumActivity.class);
				myIntent.putExtra("link", arrTopChude.elementAt(arg2).getLINK());
				myIntent.putExtra("title", arrTopChude.elementAt(arg2)
						.getTITLE());
				startActivity(myIntent);

			}
		});

		lstv3.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(TopActivity.this,
						ModelActivity.class);
				myIntent.putExtra("subtitle", arrTopUser.elementAt(arg2)
						.getTITLE());
				myIntent.putExtra("link", arrTopUser.elementAt(arg2).getLINK());
				startActivity(myIntent);
			}
		});
	}

	private class ParserURL extends AsyncTask<String, Lady, Vector<Lady>> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog = ProgressDialog.show(TopActivity.this, "", "Loading...");
			dialog.setCancelable(true);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Vector<Lady> doInBackground(String... params) {
			// TODO Auto-generated method stub
			// Vector<Lady> t = new Vector<Lady>();
			try {
				Document doc = Jsoup.connect(params[0]).get();
				// html = doc.text();
				TITLE = "Tuần "
						+ doc.select("div.row.top20-row").attr("data-week")
						+ " - "
						+ doc.select("div.row.top20-row").attr("data-year");
				Elements Ele = doc.select("li.media.vd-media"); // get element
				int i = 0;

				// Get top 20 model
				for (; i < 20; i++) {
					Lady temp = new Lady();
					temp.setLINK_IMG(Ele.eq(i).select("img.media-object")
							.attr("src"));
					temp.setTITLE(Ele.eq(i).select("a.media-heading").text());
					temp.setLINK(Ele.eq(i).select("a.media-heading")
							.attr("href"));
					temp.setLIKE(Ele.eq(i).select("span.hint").text());
					// temp.setIMG(downloadImage(temp.getLINK_IMG()));
					publishProgress(temp);
					// t.add(temp);
				}

				// Get top chu de
				for (; i < 40; i++) {
					Lady temp = new Lady();
					temp.setLINK_IMG(Ele.eq(i).select("img.media-object")
							.attr("src"));
					temp.setTITLE(Ele.eq(i).select("a.media-heading").text());
					temp.setVIEW(Ele.eq(i).select("ul.topic-info").eq(0)
							.select("li").eq(0).text());

					temp.setLINK(Ele.eq(i).select("a.media-heading")
							.attr("href"));

					temp.setLIKE(Ele.eq(i).select("ul.topic-info").eq(1)
							.select("li").eq(0).text());
					temp.setCMT(Ele.eq(i).select("ul.topic-info").eq(1)
							.select("li").eq(1).text());

					arrTopChude.add(temp);
				}
				// Get top user
				for (; i < 60; i++) {
					Lady temp = new Lady();
					temp.setLINK_IMG(Ele.eq(i).select("img.media-object")
							.attr("src"));
					temp.setTITLE(Ele.eq(i).select("a.media-heading").text());
					temp.setLINK(Ele.eq(i).select("a.media-heading")
							.attr("href"));
					temp.setLIKE(Ele.eq(i).select("span.hint").text());

					// temp.IMG = downloadImage(temp.getLINK_IMG());
					arrTopUser.add(temp);

				}
				return null;
			} catch (Exception ex) {
				Log.d("MyTag", ex.toString());
				return null;
			}
		}

		@Override
		protected void onProgressUpdate(Lady... values) {
			// TODO Auto-generated method stub
			arrTopModel.add(values[0]);
			adparr1.notifyDataSetChanged();
			if (arrTopModel.size() == 20) {
				if (dialog != null) {
					dialog.dismiss();
				}
			}
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Vector<Lady> result) {
			setTitle(TITLE);
			for (int i = 0; i < 20; i++) {
				if (arrTopUser.elementAt(i).getLINK_IMG().indexOf("facebook") != -1) {
					try {
						arrTopUser.elementAt(i).setLINK_IMG(
								new redirectLink().execute(
										arrTopUser.elementAt(i).getLINK_IMG())
										.get());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * 
	 * @author Hoàng
	 * Lấy ra link ảnh gốc, của directlink, link img Facebook
	 *
	 */
	public class redirectLink extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... p) {
			try {
				URL url = new URL(p[0]);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(5000);
				conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				conn.addRequestProperty("User-Agent", "Mozilla");
				conn.addRequestProperty("Referer", "google.com");

				String newUrl = conn.getHeaderField("Location");

				conn = (HttpURLConnection) new URL(newUrl).openConnection();
				conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				conn.addRequestProperty("User-Agent", "Mozilla");
				conn.addRequestProperty("Referer", "google.com");
				publishProgress(newUrl);
				return newUrl;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
}
