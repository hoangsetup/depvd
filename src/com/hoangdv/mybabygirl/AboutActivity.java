package com.hoangdv.mybabygirl;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends Activity {
	private TextView tv_fb, tv_bq;
	private Button btn_send;
	private EditText edit_body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.activity_about);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00b4ff")));
		// Xóa icon app trên Actionbar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setSubtitle("Đây là bài thực hành  HTLM parser");
		// Color for subtitle
		int actionBarTitleId = Resources.getSystem().getIdentifier(
				"action_bar_subtitle", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE);
			}
		}

		tv_fb = (TextView) findViewById(R.id.textView_facebook);
		tv_bq = (TextView) findViewById(R.id.textView_banquyen);
		tv_fb.setText(Html
				.fromHtml("<a href=\"http://www.facebook.com/hoang.setup\">Hoàng Đinh</a> "));
		tv_fb.setMovementMethod(LinkMovementMethod.getInstance());
		tv_bq.setText(Html.fromHtml("<a href=\"http://www.depvd.com\">Bản quyền ảnh thuộc depvd.com</a> "));
		tv_bq.setMovementMethod(LinkMovementMethod.getInstance());

		btn_send = (Button) findViewById(R.id.button_send);
		edit_body = (EditText) findViewById(R.id.editText_bodyemail);

		btn_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String to = "hoang.dv@outlook.com";
				String body = " " + edit_body.getText();

				String[] TO = { to };
				// String[] CC = { "mcmohd@gmail.com" };
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setData(Uri.parse("mailto:"));
				emailIntent.setType("text/plain");

				emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
				// emailIntent.putExtra(Intent.EXTRA_CC, CC);
				emailIntent.putExtra(Intent.EXTRA_SUBJECT,
						"FeedBack MyBabyGirls");
				emailIntent.putExtra(Intent.EXTRA_TEXT, body);

				try {
					startActivity(Intent.createChooser(emailIntent,
							"Send mail..."));
					finish();
					Log.i("Finished sending email...", "");
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(AboutActivity.this,
							"There is no email client installed.",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
