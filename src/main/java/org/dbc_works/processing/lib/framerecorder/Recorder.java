package org.dbc_works.processing.lib.framerecorder;

/**
 * Recorder interface
 */
public interface Recorder {
    /**
     * Record frame
     */
    void recordFrame();

    /**
     * Bind image files to video
     * 
     * @param videoFileName Video file name
     * @param frameRate     Frame rate
     */
    void bindTo(String videoFileName, int frameRate);

    /**
     * Finish recording
     */
    void finish();
}
