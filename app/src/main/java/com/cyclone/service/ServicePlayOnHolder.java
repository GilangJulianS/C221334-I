package com.cyclone.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.player.media.MediaWrapper;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ServicePlayOnHolder extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.cyclone.service.action.FOO";
    private static final String ACTION_BAZ = "com.cyclone.service.action.BAZ";
    private static final String ACTION_PLAY_ON_HOME = "com.cyclone.service.action.ACTION_PLAY_ON_HOME";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.cyclone.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.cyclone.service.extra.PARAM2";
    private static final String MEDIA_WARPER = "com.cyclone.service.extra.MEDIA_WARPER";
    public static ArrayList<PlayOnHolder> mCallBackPlay = new ArrayList<PlayOnHolder>();

    public ServicePlayOnHolder() {
        super("ServicePlayOnHolder");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ServicePlayOnHolder.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ServicePlayOnHolder.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startPlayOnHome(Context context, MediaWrapper media, PlayOnHolder client) {
        if(mCallBackPlay != null)
            mCallBackPlay.clear();
        mCallBackPlay.add(client);

        System.out.println("si start on play home : " + mCallBackPlay.size());
        Intent intent = new Intent(context, ServicePlayOnHolder.class);
        intent.setAction(ACTION_PLAY_ON_HOME);
        intent.putExtra(MEDIA_WARPER, media);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PLAY_ON_HOME.equals(action)) {
                MediaWrapper media = intent.getParcelableExtra(MEDIA_WARPER);
                handleActionPlayOnHome(media);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    private void handleActionPlayOnHome(MediaWrapper media) {
        for (PlayOnHolder poh : mCallBackPlay)
            poh.onLoadedPlayOnHolder(media);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
