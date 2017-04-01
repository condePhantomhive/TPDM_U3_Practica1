package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jessica on 27/02/17.
 */

public class IncomingSms extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try {

            final Object[] pdusObj = (Object[]) bundle.get("pdus");

            for (int i = 0; i < pdusObj.length; i++) {

                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                String message = currentMessage.getDisplayMessageBody();
                //sms.sendTextMessage(phoneNumber,null,"Orden recibida. Su número de orden es: " + Cuenta.orderID,null,null);
                //Log.i("IncomingSms", "senderNum: "+ senderNum + "; message: " + message);

                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

            }

        } catch (Exception e) {
            Log.e("IncomingSms", "Exception smsReceiver" +e);

        }
    }
}
