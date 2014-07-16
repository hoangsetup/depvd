package com.hoangdv.mybabygirl;

import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.hoangdv.adapter.ViewPageAdapter;

public class AlbumActivity extends Activity {
	private ViewPager myViewPager;
	private TextView tv_page;
	private ViewPageAdapter adp = null;

	private String url = "";

	private Vector<String> LinkIMGs = new Vector<String>();

	private int count = 0;
	private int curent = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.albumactivity);
		myViewPager = (ViewPager) findViewById(R.id.myfivepanelpager);
		tv_page = (TextView) findViewById(R.id.textView_count);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		adp = new ViewPageAdapter(AlbumActivity.this, LinkIMGs);
		myViewPager.setAdapter(adp);

		Intent myIntent = this.getIntent();
		url = myIntent.getStringExtra("link");
		this.setTitle(myIntent.getStringExtra("title"));
		new ParserURL().execute(url);
		myEnvent();

		// First time
//		getActionBar().setBackgroundDrawable(
//				new ColorDrawable(Color.parseColor("#00b4ff")));
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#BCC1B8")));
		// Xóa icon app trên Actionbar
		getActionBar().setIcon(android.R.color.transparent);
		getActionBar().setSubtitle("SexyGirl");
		// Color for subtitle
		int actionBarTitleId = Resources.getSystem().getIdentifier(
				"action_bar_subtitle", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE);
			}
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		} else if (item.getItemId() == R.id.action_download) {
			Intent myIntent = new Intent(AlbumActivity.this,
					DownloadActivity.class);
			myIntent.putExtra("img", LinkIMGs.elementAt(curent));
			startActivity(myIntent);
		}
		return super.onOptionsItemSelected(item);
	}

	public void myEnvent() {
		myViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						// TODO Auto-generated method stub
						curent = arg0;
						tv_page.setText((curent + 1) + "/" + count);

					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	private class ParserURL extends AsyncTask<String, String, Vector<String>> {
		// private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Vector<String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			Vector<String> vttemp = new Vector<String>();
			try {
				Document doc = Jsoup.connect(params[0]).get();
				Elements imgEle1 = doc.select("img.img");
				for (Element ele : imgEle1) {

					String link_temp = ele.attr("data-original");
					if (link_temp != "") {
						publishProgress(link_temp);
					}
				}
				return vttemp;
			} catch (Exception ex) {
				return null;
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			LinkIMGs.add(values[0]);
			adp.notifyDataSetChanged();
			count = LinkIMGs.size();
			tv_page.setText((curent + 1) + "/" + count);
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Vector<String> result) {
			// TODO Auto-generated method stub
			if (result == null) {
				return;
			}
			ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar_albumactivity);
			bar.setVisibility(View.GONE);
			super.onPostExecute(result);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_download, menu);

		MenuItem item = menu.findItem(R.id.menu_item_share);
		ShareActionProvider myShareActionProvider = (ShareActionProvider) item
				.getActionProvider();
		Intent myIntent = new Intent();
		myIntent.setAction(Intent.ACTION_SEND);
		myIntent.putExtra(Intent.EXTRA_TEXT, url);
		myIntent.setType("text/plain");
		myShareActionProvider.setShareIntent(myIntent);

		return true;
	}

}
