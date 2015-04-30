package kr.heja.exam.sms;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * 동적 SMS리시버 설정 예제.
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private SmsReceiver mSmsReceiver;

	private TextView tvReceiveDate;
	private TextView tvSenderNumber;
	private TextView tvReceiveMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		// Receiver 등록
		IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
		Handler smsHandler = new SmsHandler();
		mSmsReceiver = new SmsReceiver(smsHandler);

		registerReceiver(mSmsReceiver, intentFilter);
		Log.d(TAG, "SMS Receiver 등록");
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mSmsReceiver);
		Log.d(TAG, "SMS Receiver 해제");
		super.onDestroy();
	}

	private void init() {
		tvReceiveDate = (TextView) findViewById(R.id.tvReceiveDate);
		tvSenderNumber = (TextView) findViewById(R.id.tvSenderNumber);
		tvReceiveMessage = (TextView) findViewById(R.id.tvReceiveMessage);
	}

	private class SmsHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (Const.WHAT_SMS_HANDLER == msg.what) {
				Bundle data = msg.getData();
				long datetime = data.getLong(Const.KEY_SMS_DATE);
				String senderNumber = data.getString(Const.KEY_SMS_SENDER);
				String messageBody = data.getString(Const.KEY_SMS_MESSAGE);

				tvReceiveDate.setText(datetime + "");
				tvSenderNumber.setText(senderNumber);
				tvReceiveMessage.setText(messageBody);
			}
		}
	}
}
