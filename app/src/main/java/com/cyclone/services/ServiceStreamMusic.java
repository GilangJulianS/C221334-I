package com.cyclone.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.cyclone.CollapseActivity;
import com.cyclone.fragment.PlayerFragment;

import java.io.IOException;

//import io.vov.vitamio.MediaPlayer;

//import com.cyclone.utils.Utils;


/**
 * Created by solusi247 on 22/10/15.
 */
public class ServiceStreamMusic extends Service implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener, MediaPlayer.OnSeekCompleteListener{

    public static String URL_STREAM = "http://stream.suararadio.com:8000/bandung_klitefm_mp3";
    public static final String BROADCAST_BUFFER = "com.cyclone.broadcastbuffer";
    public static final String mBroadcastStringActionSeek = "com.cyclone.broadcast.stringseek";
    private static final int NOTIFICATION_ID = 1;

    public static final int BRODCAST_FIRST_PLAY = 0;
    public static final int BRODCAST_BUFFER_UPDATE = 1;
    public static final int BRODCAST_PAUSE = 2;

    public int FINISH_PLAYING = 0;

    TelephonyManager telephonyManager;
    PhoneStateListener phoneStateListener;
    MediaPlayer mediaPlayer;
    private boolean isPausedInCall = false;
    private NotificationCompat.Builder builder;
    private Intent bufferIntent;
    private IntentFilter mIntentFilter;

    int statBuffer=0;


    private BroadcastReceiver broadcastSeekReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent bufferIntent) {
            changePossSeek(Long.valueOf(bufferIntent.getStringExtra("posSeek")));
            System.out.println("posssssss : "+bufferIntent.getStringExtra("posSeek"));
        }
    };

    private final Handler handler = new Handler();



    @Override
    public void onCreate() {
        super.onCreate();


        Log.d("create", "service created");

        bufferIntent = new Intent(BROADCAST_BUFFER);
        mediaPlayer = new MediaPlayer();

        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);


        mediaPlayer.reset();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastStringActionSeek);

        registerReceiver(broadcastSeekReceiver, mIntentFilter);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("play", "Play streaming");

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                switch (state) {
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        if (mediaPlayer != null) {
                            pauseMedia();
                            isPausedInCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        if (mediaPlayer != null) {
                            if (isPausedInCall) {
                                playMedia();
                            }
                        }
                }
            }
        };

        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        initNotification();

        mediaPlayer.reset();

        if (!mediaPlayer.isPlaying()) {
            try {
                Log.d("Stream", "" + URL_STREAM);
                mediaPlayer.setDataSource(URL_STREAM);

                mediaPlayer.prepareAsync();
                setupHandler();
            } catch (IllegalArgumentException e) {
                Log.d("error", e.getMessage());
            } catch (IllegalStateException e) {
                Log.d("Error", e.getMessage());
            } catch (IOException e) {
                Log.d("error", e.getMessage());
            }
        }



        return START_STICKY;
    }

    private void changePossSeek(long pos){
        if (mediaPlayer.isPlaying()) {
           // handler.removeCallbacks(sendToUi);
            mediaPlayer.stop();
          //  mediaPlayer.seekTo(pos);
            mediaPlayer.start();



          //  setupHandler();
        }
    }

    /*Show Notification*/

    private void initNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, CollapseActivity.class), 0);
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_media_play);
        builder.setContentTitle("Stream Music");
        builder.setContentText("Prambos Radio");
        builder.setContentIntent(intent);
        builder.setOngoing(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void cancelNotification() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        builder.setOngoing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tag", "remove notivication");
        //Utils.setDataBooleanToSP(this, Utils.IS_STREAM, false);

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.release();
        }

        if (phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        handler.removeCallbacks(sendToUi);
        cancelNotification();
        try {
            unregisterReceiver(broadcastSeekReceiver);
        }
        catch (Exception e){

        }


    }

    private void pauseMedia() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    private void playMedia() {
        if (!mediaPlayer.isPlaying()) {
           // mediaPlayer.setUseCache(true);
           // mediaPlayer.setAdaptiveStream(true);
            //mediaPlayer.setDeinterlace(true);
            mediaPlayer.start();
            FINISH_PLAYING = 0;
        }
    }

    private void stopMedia() {
        if (mediaPlayer.isPlaying())
            handler.removeCallbacks(sendToUi);
            mediaPlayer.stop();
        try {
            unregisterReceiver(broadcastSeekReceiver);
        }
        catch (Exception e){

        }


    }



    private void setupHandler() {
        handler.removeCallbacks(sendToUi);
        handler.postDelayed(sendToUi, 1000); // 1 second
    }


    private Runnable sendToUi = new Runnable() {
        public void run() {
            // // Log.d(TAG, "entered sendUpdatesToUI");

           LogMediaPosition();

            System.out.println("Runnable run");
           // mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());

            handler.postDelayed(this, 1000); // 2 seconds

        }
    };

    private void LogMediaPosition() {
        // // Log.d(TAG, "entered LogMediaPosition");
        if (mediaPlayer.isPlaying()) {


            bufferIntent.setAction(PlayerFragment.mBroadcastStringAction);
            bufferIntent.putExtra("modeSendBuffer", BRODCAST_FIRST_PLAY);
            bufferIntent.putExtra("finishPlaying", FINISH_PLAYING);
            bufferIntent.putExtra("buffering", "0");
            bufferIntent.putExtra("durasi", "" + mediaPlayer.getDuration());
            bufferIntent.putExtra("curPosisi", "" + mediaPlayer.getCurrentPosition());
            bufferIntent.putExtra("buferProgres", "" + statBuffer);
            sendBroadcast(bufferIntent);

            System.out.println("Send Brodcast ttt" + mediaPlayer.getCurrentPosition() + "/" + mediaPlayer.getDuration() + "  |" + statBuffer);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

          statBuffer = percent;
    }



    @Override
    public void onCompletion(MediaPlayer mp) {
        FINISH_PLAYING = 1;
        stopMedia();
        stopSelf();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this, "Error not valid playback", Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                Toast.makeText(this, "Error server died", Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "Error unknown", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();

    }




    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }


    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }


}
