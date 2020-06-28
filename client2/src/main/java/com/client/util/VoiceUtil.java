package com.client.util;

import javax.sound.sampled.AudioFormat;
import java.io.ByteArrayOutputStream;

/**
 * @version 1.0
 * @ClassName VoiceUtil
 * @Author Tung
 * @Date 2020/6/26 13:28
 * @Description This is description of class
 * Website www.tunglee.ink
 * Github www.github.com/realLiamTurner
 */
public class VoiceUtil {
    public static void setRecording(boolean flag){
        isRecording = flag;
    }

    public static boolean isRecording() {
        return isRecording;
    }

    protected static boolean isRecording = false;
    static ByteArrayOutputStream out;
    /**
     * Defines an audio format
     */
    static AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }
}
