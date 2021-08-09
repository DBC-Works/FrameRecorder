package org.dbc_works.processing.lib.framerecorder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

/**
 * Abstract Frame Recorder
 */
abstract class AbstractFrameRecorder {
    //
    // Constants
    //

    /**
     * Directory name to save frames
     */
    protected static final String DIR_NAME = "frames";

    //
    // Methods
    //

    /**
     * Make target path
     * 
     * @param sketchPath Sketch path
     * @return Target path
     */
    static String makeTargetPath(String sketchPath) {
        assert (sketchPath != null && Files.exists(Paths.get(sketchPath)) != false);

        return MessageFormat.format("{0}{1}frames", sketchPath, File.separator);
    }
}
