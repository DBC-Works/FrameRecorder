package org.dbc_works.processing.lib.framerecorder;

import java.nio.file.Files;
import java.nio.file.Paths;

//import org.dbc_works.processing.lib.framerecorder.FrameBinder;
//import org.dbc_works.processing.lib.framerecorder.Recorder;

import processing.core.PApplet;

/**
 * Synchronous frame recorder
 */
final class SyncRecorder extends AbstractFrameRecorder implements Recorder {
  private final PApplet parent;
  private final String imgExt;
  private final String frameFormat;

  /**
   * Constructor
   * 
   * @param app Application instance
   * @param ext Image file extension
   */
  SyncRecorder(PApplet app, String ext) {
    assert (app != null);
    assert (ext != null && 0 < ext.length() && ext.startsWith(".") == false);

    parent = app;
    imgExt = ext;
    frameFormat = String.format("%s/##########.%s", DIR_NAME, ext);
  }

  /**
   * Record frame
   */
  public void recordFrame() {
    parent.saveFrame(frameFormat);
  }

  /**
   * Bind image files to movie
   * 
   * @param videoFileName Video file name
   * @param frameRate     Frame rate
   */
  public void bindTo(String videoFileName, int frameRate) {
    assert (videoFileName != null && 0 < videoFileName.length());
    assert (0 < frameRate);

    final String targetPath = makeTargetPath(parent.sketchPath());
    new FrameBinder(videoFileName, targetPath, imgExt, frameRate).bind();
  }

  /**
   * Finish recording
   */
  public void finish() {
    // Do nothing
  }
}
