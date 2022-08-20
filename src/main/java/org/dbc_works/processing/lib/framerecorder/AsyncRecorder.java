package org.dbc_works.processing.lib.framerecorder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

//import org.dbc_works.processing.lib.framerecorder.FrameBinder;
//import org.dbc_works.processing.lib.framerecorder.Recorder;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Asynchronous frame recorder
 */
final class AsyncRecorder extends AbstractFrameRecorder implements Recorder {
  private final PApplet parent;
  private final ExecutorService executor = Executors.newCachedThreadPool();
  private final List<Future<?>> futures = new ArrayList<Future<?>>();

  /**
   * Constructor
   * 
   * @param app Application instance
   */
  AsyncRecorder(PApplet app) {
    assert (app != null);

    parent = app;
  }

  /**
   * Record frame
   */
  public void recordFrame() {
    if (executor.isShutdown()) {
      return;
    }

    parent.loadPixels();

    final int[] savePixels = Arrays.copyOf(parent.pixels, parent.pixels.length);
    final long saveFrameCount = parent.frameCount;
    Runnable saveTask = new Runnable() {
      public void run() {
        final PImage frameImage = parent.createImage(parent.width, parent.height, parent.HSB);
        frameImage.pixels = savePixels;
        frameImage.save(String.format("%s/%010d.jpg", DIR_NAME, saveFrameCount));
      }
    };

    Iterator<Future<?>> it = futures.iterator();
    while (it.hasNext()) {
      final Future<?> f = it.next();
      if (f.isDone()) {
        it.remove();
      }
    }
    futures.add(executor.submit(saveTask));
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
    new FrameBinder(videoFileName, targetPath, "jpg", null, frameRate).bind();
  }

  /**
   * Bind image files to movie
   * 
   * @param videoFileName Video file name
   * @param soundFileName Sound file name
   * @param frameRate     Frame rate
   */
  public void bindTo(String videoFileName, String soundFileName, int frameRate) {
    assert (videoFileName != null && 0 < videoFileName.length());
    assert (0 < frameRate);

    final String targetPath = makeTargetPath(parent.sketchPath());
    new FrameBinder(videoFileName, targetPath, "jpg", soundFileName, frameRate).bind();
  }

  /**
   * Finish recording
   */
  public void finish() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    for (Future<?> f : futures) {
      if (f.isDone() == false && f.isCancelled() == false) {
        try {
          f.get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
      }
    }
    if (executor.isShutdown() == false) {
      executor.shutdown();
      try {
        if (executor.awaitTermination(5, TimeUnit.SECONDS) == false) {
          executor.shutdownNow();
          executor.awaitTermination(5, TimeUnit.SECONDS);
        }
      } catch (InterruptedException e) {
        executor.shutdownNow();
      }
    }
  }
}
