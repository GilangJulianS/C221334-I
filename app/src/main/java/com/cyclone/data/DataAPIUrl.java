package com.cyclone.data;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.fragment.PlaylistFragment;
import com.cyclone.fragment.RecyclerFragment;
import com.cyclone.fragment.RequestFragment;
import com.cyclone.interfaces.getData;
import com.cyclone.loopback.model.AccountStats;
import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.loopback.model.comment;
import com.cyclone.loopback.model.radioProfile;
import com.cyclone.loopback.model.radioProgram;
import com.cyclone.loopback.repository.AccountStatsRepository;
import com.cyclone.loopback.repository.CommentRepository;
import com.cyclone.loopback.repository.FavotireRepository;
import com.cyclone.loopback.repository.FeedTimelineRepository;
import com.cyclone.loopback.repository.MusicRepository;
import com.cyclone.loopback.repository.PlaylistDataRepository;
import com.cyclone.loopback.repository.ProfileRepository;
import com.cyclone.loopback.repository.RadioContentRepository;
import com.cyclone.loopback.repository.radioProfileRepository;
import com.cyclone.loopback.repository.radioProgramRepository;
import com.cyclone.model.Content;
import com.cyclone.service.ServiceGetData;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.adapters.Adapter;

import java.util.List;

/**
 * Created by solusi247 on 15/02/16.
 */
public class DataAPIUrl {
    Activity activity;
    Context getContext;
    Context recyclerContext;
    ApiCallback callback;
    ProgressBar progressBar;
    String DataId;
    protected getData mGetData;
    public int scc = 0;
    RestAdapter restAdapter;

    public DataAPIUrl(Activity activity, Context context, String DataId, ApiCallback callback, Context recyclerContext, getData mGetData, ProgressBar progressBar) {
        this.callback = callback;
        this.activity = activity;
        this.recyclerContext = recyclerContext;
        this.mGetData = mGetData;
        this.progressBar = progressBar;
        this.getContext = context;
        this.DataId = DataId;

        restAdapter = new RestAdapter(getContext, ServerUrl.API_URL);
    }

    public interface ApiCallback {
        void showData();

        void refresh();
    }

    public void getDataHome() {
        System.out.println("on getdata home");
        //final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
        final RadioContentRepository radioContentRepository = restAdapter.createRepository(RadioContentRepository.class);
        final MusicRepository musicRepository = restAdapter.createRepository(MusicRepository.class);
        scc = 0;
        radioContentRepository.get(new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                scc++;
                System.out.println("scc : " + scc + " | " + response);
                if (scc == 2) callback.showData();
            }

            @Override
            public void onError(Throwable t) {
                callback.refresh();
            }
        });

        musicRepository.get(new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                scc++;
                System.out.println("scc : " + scc + " | " + response);
                if (scc == 2) callback.showData();
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    public void getDataLive() {
        try {
            ServiceGetData serviceGetData = new ServiceGetData();
            serviceGetData.getDataStream(recyclerContext, mGetData);
        } catch (Exception e) {
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
            //onRefresh();
        }
    }

    public void getRequest() {
        System.out.println("on Requesttttttttttttt");
        try {
            ServiceGetData serviceGetData = new ServiceGetData();
            serviceGetData.getDataRequest(RequestFragment.getmContext(), RequestFragment.getmGetData());
        } catch (Exception e) {
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
            //onRefresh();
        }
    }

    public void getDataClub() {
        //final RestAdapter restAdapter = new RestAdapter(getContext, ServerUrl.API_URL);
        final FeedTimelineRepository feedTimelineRepository = restAdapter.createRepository(FeedTimelineRepository.class);

        feedTimelineRepository.createContract();
        feedTimelineRepository.get("0", "10", new ListCallback<FeedTimeline>() {
            @Override
            public void onSuccess(List<FeedTimeline> objects) {
                System.out.println("here here here");
                callback.showData();

            }

            @Override
            public void onError(Throwable t) {

                //showData();
            }
        });
    }

    public void getDataProfile() {
        //final RestAdapter restAdapter = new RestAdapter(getContext, ServerUrl.API_URL);
        final ProfileRepository profileRepository = restAdapter.createRepository(ProfileRepository.class);

        profileRepository.get(DataId, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                PersonProfileFragment.getInstance().setProfile();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error : " + t);
            }
        });
    }

    public void getDataStatsProfile() {
        //final RestAdapter restAdapter = new RestAdapter(getContext, ServerUrl.API_URL);
        final AccountStatsRepository accountStatsRepository = restAdapter.createRepository(AccountStatsRepository.class);

        accountStatsRepository.get(DataId, new ObjectCallback<AccountStats>() {
            @Override
            public void onSuccess(AccountStats object) {
                PersonProfileFragment.getInstance().setStats(object);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    public void getDataRadioProgram() {
        //final RestAdapter restAdapter = new RestAdapter(getContext, ServerUrl.API_URL);
        final radioProgramRepository programRepository = restAdapter.createRepository(radioProgramRepository.class);

        programRepository.get(ServerUrl.RADIO_ID, new ListCallback<radioProgram>() {
            @Override
            public void onSuccess(List<radioProgram> objects) {
                System.out.println("on Successssss LLLLL :::: " + objects.get(0).getName());
                callback.showData();
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    public void getDataComment() {
        //final RestAdapter restAdapter = new RestAdapter(getContext, ServerUrl.API_URL);
        final CommentRepository commentRepository = restAdapter.createRepository(CommentRepository.class);

        commentRepository.get(DataId, 0, 10, new ListCallback<comment>() {
            @Override
            public void onSuccess(List<comment> objects) {
                System.out.println("comment data recived");
                callback.showData();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("comment data error : " + t);
            }
        });
    }

    public void getRadioProfile() {
        //final RestAdapter restAdapter = new RestAdapter(getContext, ServerUrl.API_URL);
        final radioProfileRepository profileRepository = restAdapter.createRepository(radioProfileRepository.class);

        profileRepository.get(ServerUrl.RADIO_ID, new ObjectCallback<radioProfile>() {
            @Override
            public void onSuccess(radioProfile object) {
                UtilArrayData.radioProfile = object;
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    public void getDataPlaylist() {
        System.out.println("on getdata home");
        //final RestAdapter restAdapter = new RestAdapter(getContext, ServerUrl.API_URL);
        final PlaylistDataRepository playlistDataRepository = restAdapter.createRepository(PlaylistDataRepository.class);
        scc = 0;
        System.out.println("id Sendded : " + PlaylistFragment.getIdPlaylist());
        playlistDataRepository.get(PlaylistFragment.getIdPlaylist(), "0", "10", new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                callback.showData();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error anehh : " + t);
            }
        });
    }

    public void getDataFavorite() {
        FavotireRepository favotireRepository;
        FavotireRepository.newInstance(FavotireRepository.ACCOUNT_CLASS);
        favotireRepository = restAdapter.createRepository(FavotireRepository.class);
        UtilArrayData.favorites.clear();
        scc = 0;
        //get music favorite
        favotireRepository.getMusic("0", "3", new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("berhasill music : " + response);
                scc++;
                if (scc >= 3) callback.showData();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gagal music : " + t);
            }
        });

        //get playlist favorite
        favotireRepository.getPlaylist("0", "3", new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("berhasill playlist : " + response);
                scc++;
                if (scc >= 3) callback.showData();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gagal playlist : " + t);
            }
        });

        //get radio content favorite
        favotireRepository.getRadiocontent("0", "3", new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("berhasill radiocontent : " + response);
                scc++;
                if (scc >= 3) callback.showData();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gagal radio content : " + t);
            }
        });
    }

    public void getDataFavorite(int typeFavorite) {
        FavotireRepository favotireRepository;

        FavotireRepository.newInstance(FavotireRepository.ACCOUNT_CLASS);
        favotireRepository = restAdapter.createRepository(FavotireRepository.class);
        UtilArrayData.favorites.clear();
        scc = 0;

        if (typeFavorite == Content.TYPE_TRACKS) {
            //get music favorite
            favotireRepository.getMusic("" + RecyclerFragment.getOffset(), "" + RecyclerFragment.getTake(), new Adapter.Callback() {
                @Override
                public void onSuccess(String response) {
                    System.out.println("berhasill music : " + response);
                    callback.showData();
                    RecyclerFragment.setOffset(RecyclerFragment.getOffset() + RecyclerFragment.getTake());
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println("gagal music : " + t);
                }
            });
        } else if (typeFavorite == Content.TYPE_PLAYLIST) {
            //get playlist favorite
            favotireRepository.getPlaylist("" + RecyclerFragment.getOffset(), "" + RecyclerFragment.getTake(), new Adapter.Callback() {
                @Override
                public void onSuccess(String response) {
                    System.out.println("berhasill playlist : " + response);
                    callback.showData();
                    RecyclerFragment.setOffset(RecyclerFragment.getOffset() + RecyclerFragment.getTake());
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println("gagal playlist : " + t);
                }
            });
        } else if (typeFavorite == Content.TYPE_RADIO_CONTENT) {
            //get radio content favorite
            favotireRepository.getRadiocontent("" + RecyclerFragment.getOffset(), "" + RecyclerFragment.getTake(), new Adapter.Callback() {
                @Override
                public void onSuccess(String response) {
                    System.out.println("berhasill radiocontent : " + response);
                    callback.showData();
                    RecyclerFragment.setOffset(RecyclerFragment.getOffset() + RecyclerFragment.getTake());
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println("gagal radio content : " + t);
                }
            });
        }


    }
}
