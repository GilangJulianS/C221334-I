package com.cyclone.data;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by solusi247 on 16/02/16.
 */
public class randomImage {
    final String Url = "http://stream.suararadio.com/cyclone/public/dummy/kr-artwork/";
    private ArrayList<Integer> list = new ArrayList<>();
    static randomImage ranImage;
    static int curPos = 0;

    public static randomImage getInstance() {
        if (ranImage == null) {
            ranImage = new randomImage();
            ranImage.generate();
            curPos = 0;
        }
        return ranImage;
    }

    private void generate() {
        Random randomGenerator = new Random();
        for (int i = 1; i <= 10; i++) {
            int randomInt = randomGenerator.nextInt(10);
            list.add(randomInt);
        }
    }

    public String getImage() {
        curPos++;
        if (curPos > 10) curPos = 1;
        String img = Url + list.get(curPos) + ".png";
        return img;

    }
}
