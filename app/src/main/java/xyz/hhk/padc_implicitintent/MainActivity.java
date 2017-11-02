package xyz.hhk.padc_implicitintent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.hhk.padc_implicitintent.utils.MAConstants;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
    private String mNumberToCall="09796622680";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_implicit);
        ButterKnife.bind(this,this);
    }

    @OnClick(R.id.btn_map)
    public void onTapLocateAttraction(View view) {
        String uriToOpen = MAConstants.URI_TO_OPEN_IN_MAP;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uriToOpen));
        startActivity(intent);
    }

    @OnClick(R.id.btn_share)
    public void onTapShareCompact(View view) {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(MainActivity.this)
                .setType("text/plain")
                .setText("Let'go to facebook.")
                .getIntent(), getString(R.string.action_share)));
    }

    @OnClick(R.id.btn_phone_call)
    public void onTapPhoneCall(View view) {
        makeCall(MAConstants.CUSTOMER_SUPPORT_PHONE);
    }

    @OnClick(R.id.btn_send_email)
    public void onTapSendEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "aungzawhtike1092@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
    }

    @OnClick(R.id.btn_take_picture_camera)
    public void onTapTakePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @OnClick(R.id.btn_take_picture_from_device_storage)
    public void onTapTakePictureFromDevice(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),REQUEST_IMAGE_CAPTURE);
    }

    @OnClick(R.id.btn_calendar)
    public void onTapCalendar(View view) {
        addEvent("Barcamp","yangon",12,12);
    }

    private void makeCall(String numberToCall) {
        numberToCall = numberToCall.replaceAll(" ", "");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numberToCall));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            mNumberToCall = numberToCall;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            return;
        }
        startActivity(intent);
    }

    public void addEvent(String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



}
