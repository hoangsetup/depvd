package com.hoangdv.mybabygirl;

import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.hoangdv.adapter.myGridviewModelAdpater;
import com.hoangdv.variable.Lady;

public class ModelActivity extends Activity {
	private GridView grv;

	private Vector<Lady> arrLady = new Vector<Lady>();

	private ArrayAdapter<Lady> adp = null;
	private String subTitle = "";
	private String LINK = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.activity_model);
		grv = (GridView) findViewById(R.id.gridView_model);
		adp = new myGridviewModelAdpater(ModelActivity.this,
				R.layout.item_gridview2, arrLady);
		grv.setAdapter(adp);

		Intent myIntent = getIntent();
		subTitle = myIntent.getStringExtra("subtitle");
		LINK = myIntent.getStringExtra("link");

		// First time
		setTitle("Các chủ đề có liên quan");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00b4ff")));
		// Xóa icon app trên Actionbar
		getActionBar().setIcon(android.R.color.transparent);
		getActionBar().setSubtitle(subTitle);
		// Color for subtitle
		int actionBarTitleId = Resources.getSystem().getIdentifier(
				"action_bar_subtitle", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE);
			}
		}
		super.onCreate(savedInstanceState);

		new ParserURL().execute(LINK);

		// Event
		grv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(ModelActivity.this,
						AlbumActivity.class);
				myIntent.putExtra("link", arrLady.elementAt(arg2).getLINK());
				myIntent.putExtra("title", arrLady.elementAt(arg2).getTITLE());
				startActivity(myIntent);
			}

		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private class ParserURL extends AsyncTask<String, Lady, Void> {
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog = ProgressDialog.show(ModelActivity.this, "", "Loading ...");
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect(params[0]).get();
				Elements imgEle1 = doc.select("div.thumbnail.vd-xitin");
				for (Element ele : imgEle1) {
					Lady temp = new Lady();
					String link = "";
					String linkImg = "";
					String linkTitle = "";
					link = ele.select("a").attr("href");
					linkImg = ele.select("img.vd-photo-grid").attr("src");
					linkTitle = ele.select("img.vd-photo-grid").attr("alt");

					temp.setLINK_IMG(linkImg);
					temp.setLINK(link);
					temp.setTITLE(linkTitle);
					publishProgress(temp);
				}
				return null;
			} catch (Exception ex) {
				return null;
			}
		}

		@Override
		protected void onProgressUpdate(Lady... values) {
			// TODO Auto-generated method stub
			arrLady.add(values[0]);
			adp.notifyDataSetChanged();
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (dialog != null) {
				dialog.dismiss();
			}
			super.onPostExecute(result);
		}

	}
}
