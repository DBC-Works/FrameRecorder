package org.dbc_works.processing.lib.framerecorder;

import processing.core.PApplet;

/**
 * FrameRecorderFactory
 */
public final class FrameRecorderFactory {
    //
    // Static methods
    //

    /**
     * Create frame recorder
     * 
     * @param type Frame recorder type
     * @param app  Application
     * @return Frame recorder instance
     */
    public static Recorder createFrameRecorderInstanceOf(RecorderType type, PApplet app) {
        assert (app != null);

        switch (type) {
            case SyncTgaRecorder:
                return new SyncRecorder(app, "tga");
            case SyncPngRecorder:
                return new SyncRecorder(app, "png");
            case AsyncRecorder:
                return new AsyncRecorder(app);
            default:
                throw new RuntimeException();
        }
    }

    //
    // Methods
    //

    private FrameRecorderFactory() {
    }
}
