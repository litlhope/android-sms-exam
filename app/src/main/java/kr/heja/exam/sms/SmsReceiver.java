package kr.heja.exam.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * 문자 수신처리용 Receiver
 * Created by litlhope on 15. 4. 30..
 */
public class SmsReceiver extends BroadcastReceiver {
	private static final String TAG = "SmsReceiver";

	private Handler mSmsHandler;

	public SmsReceiver(Handler h) {
		mSmsHandler = h;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
			SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
			for (SmsMessage smsMessage : smsMessages) {
				Log.d(TAG, "DATE: " + smsMessage.getTimestampMillis());
				Log.d(TAG, "SENDER: " + smsMessage.getOriginatingAddress());
				Log.d(TAG, "MSG: " + smsMessage.getMessageBody());
				Log.d(TAG, "D_MSG: " + smsMessage.getDisplayMessageBody());

				Message msg = new Message();
				Bundle data = new Bundle();
				data.putLong(Const.KEY_SMS_DATE, smsMessage.getTimestampMillis());
				data.putString(Const.KEY_SMS_SENDER, smsMessage.getOriginatingAddress());
				data.putString(Const.KEY_SMS_MESSAGE, smsMessage.getMessageBody());

				msg.setData(data);
				msg.what = Const.WHAT_SMS_HANDLER;

				mSmsHandler.sendMessage(msg);
			}
		}
	}
}
