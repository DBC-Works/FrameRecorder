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
     * Bind image files and merge sound file to video
     * 
     * @param videoFileName Video file name
     * @param soundFilePath Sound file path
     * @param frameRate     Frame rate
     */
     void bindTo(String videoFileName, String soundFilePath, int frameRate);

    /**
     * Finish recording
     */
    void finish();
}
