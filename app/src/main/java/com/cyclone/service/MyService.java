package com.cyclone.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.util.Log;

public class MyService extends Service {
    public static final String GET_LAGU = "get_lagu";

    private final IBinder mBinder = new LocalBinder();
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("my service create");
    }

    private class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
    public static MyService getService(IBinder iBinder) {
        LocalBinder binder = (LocalBinder) iBinder;
        return binder.getService();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action == GET_LAGU){
                System.out.println("my service aktif");
            }
        }
    };

    public static class Client {
        public static final String TAG = "PlaybackService.Client";

        @MainThread
        public interface Callback {
            void onConnectedMyService(MyService service);
            void onDisconnectedMyService();
        }

        private boolean mBound = false;
        private final Callback mCallback;
        private final Context mContext;

        private final ServiceConnection mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                Log.d(TAG, "Service Connected ======================= nnnniiiiiiiihhhhhhhhhhh");
                if (!mBound)
                    return;

                final MyService service = MyService.getService(iBinder);
                if (service != null)
                    mCallback.onConnectedMyService(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "Service Disconnected");
                mCallback.onDisconnectedMyService();
            }
        };

        private static Intent getServiceIntent(Context context) {
            return new Intent(context, MyService.class);
        }

        private static void startService(Context context) {
            context.startService(getServiceIntent(context));
        }

        private static void stopService(Context context) {
            context.stopService(getServiceIntent(context));
        }

        public Client(Context context, Callback callback) {
            if (context == null || callback == null)
                throw new IllegalArgumentException("Context and callback can't be null");
            mContext = context;
            mCallback = callback;
        }

        @MainThread
        public void connect() {
            if (mBound)
                throw new IllegalStateException("already connected");
            startService(mContext);
            mBound = mContext.bindService(getServiceIntent(mContext), mServiceConnection, BIND_AUTO_CREATE);
        }

        @MainThread
        public void disconnect() {
            if (mBound) {
                mBound = false;
                mContext.unbindService(mServiceConnection);
            }
        }

        public static void restartService(Context context) {
            stopService(context);
            startService(context);
        }
    }
}
