package com.cyclone.player.audio;

/**
 * Created by solusi247 on 30/11/15.
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class RtmpUrlStreamHandler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new RtmpUrlConnection(u);
    }

    @Override
    protected void parseURL(URL u, String spec, int start, int limit) {
        super.parseURL(u, spec, start, limit);
    }

    @Override
    protected synchronized InetAddress getHostAddress(URL u) {
        return super.getHostAddress(u);
    }

}