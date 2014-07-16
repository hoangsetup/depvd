package com.hoangdv.mybabygirl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DownloadActivity extends Activity {
	private ImageView img;
	private TextView tv_path;
	private EditText edit_name;
	private Button btn_download;
	private String link = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_download);
		super.onCreate(savedInstanceState);
		img = (ImageView) findViewById(R.id.imageView_imgdown);
		tv_path = (TextView) findViewById(R.id.textView_path);
		edit_name = (EditText) findViewById(R.id.editText_namefile);
		btn_download = (Button) findViewById(R.id.button_down);

		Intent myIntent = this.getIntent();
		link = myIntent.getStringExtra("img");
		tv_path.setText("Save to: SDCard/Download/");

		Picasso.with((Context) DownloadActivity.this).load(link).into(img);

		btn_download.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Picasso.with((Context) DownloadActivity.this).load(link)
						.into(target);
				Toast.makeText(DownloadActivity.this, "Đã tải xong!",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

	private Target target = new Target() {
		@Override
		public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String getName = "";
					File file;
					if (edit_name.getText().toString().equalsIgnoreCase("")) {
						Calendar cal = Calendar.getInstance();
						getName = "Baby" + cal.get(Calendar.DATE)
								+ cal.get(Calendar.MINUTE)
								+ cal.get(Calendar.SECOND)
								+ cal.get(Calendar.MILLISECOND) + "";
						file = new File(Environment
								.getExternalStorageDirectory().getPath()
								+ "/Download/" + getName + ".png");
					} else {
						file = new File(Environment
								.getExternalStorageDirectory().getPath()
								+ "/Download/" + edit_name.getText() + ".jpg");
					}

					try {
						file.createNewFile();
						FileOutputStream ostream = new FileOutputStream(file);
						bitmap.compress(CompressFormat.PNG, 75, ostream);
						ostream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();
		}

		@Override
		public void onBitmapFailed(Drawable errorDrawable) {
		}

		@Override
		public void onPrepareLoad(Drawable placeHolderDrawable) {
			if (placeHolderDrawable != null) {
				Toast.makeText(DownloadActivity.this, "HHHHH",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

}
