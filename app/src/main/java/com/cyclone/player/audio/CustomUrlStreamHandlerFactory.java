package com.cyclone.player.audio;

/**
 * Created by solusi247 on 30/11/15.
 */
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class CustomUrlStreamHandlerFactory implements URLStreamHandlerFactory {

    public final static String URL_PROTOCOL_RTMP   = "rtmp";
    public final static String URL_PROTOCOL_RTMPT  = "rtmpt";
    public final static String URL_PROTOCOL_RTMPS  = "rtmps";
    public final static String URL_PROTOCOL_RTMPE  = "rtmpe";
    public final static String URL_PROTOCOL_RTMPTE = "rtmpte";
    public final static String URL_PROTOCOL_RTMFP  = "rtmfp";

    public URLStreamHandler createURLStreamHandler(String protocol) {
        System.out.println("RtmpUrlStreamHandlerFactory # protocol: " + protocol);
        if (protocol != null && protocol.startsWith("rtmp")) return new RtmpUrlStreamHandler();
        return null;
    }
}
