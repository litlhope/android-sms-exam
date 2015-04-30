package kr.heja.exam.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * 문자 수신처리용 Receiver
 * Created by litlhope on 15. 4. 30..
 */
public class SMSReceiver extends BroadcastReceiver {
	private static final String TAG = "SMSReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
			SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
			for (SmsMessage smsMessage : smsMessages) {
				Log.d(TAG, "DATE: " + smsMessage.getTimestampMillis());
				Log.d(TAG, "SENDER: " + smsMessage.getOriginatingAddress());
				Log.d(TAG, "MSG: " + smsMessage.getMessageBody());
				Log.d(TAG, "D_MSG: " + smsMessage.getDisplayMessageBody());
			}
		}
	}
}
