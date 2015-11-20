// IAudioService.aidl
package com.cyclone;



// Declare any non-default types here with import statements
import com.cyclone.IAudioServiceCallback;

interface IAudioService {
    void play();
    void pause();
    void stop();
    void next();
    void previous();
    void shuffle();
    void setTime(long time);
    void load(in List<String> mediaPathList, int position, boolean noVideo);
    void append(in List<String> mediaPathList);
    void moveItem(int positionStart, int positionEnd);
    void remove(int position);
    void removeLocation(String location);
    List<String> getMediaLocations();
    String getCurrentMediaLocation();
    boolean isPlaying();
    boolean isShuffling();
    int getRepeatType();
    void setRepeatType(int t);
    boolean hasMedia();
    boolean hasNext();
    boolean hasPrevious();
    String getTitle();
    String getTitlePrev();
    String getTitleNext();
    String getArtist();
    String getArtistPrev();
    String getArtistNext();
    String getAlbum();
    int getTime();
    int getLength();
    Bitmap getCover();
    Bitmap getCoverPrev();
    Bitmap getCoverNext();
    void addAudioCallback(IAudioServiceCallback cb);
    void removeAudioCallback(IAudioServiceCallback cb);
    void detectHeadset(boolean enable);
    void showWithoutParse(int index);
    void playIndex(int index);
    float getRate();

    boolean reloadQueue();
}
