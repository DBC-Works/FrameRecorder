package org.dbc_works.processing.lib.framerecorder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Frame binder
 */
final class FrameBinder {
  private final String movieFileName;
  private final String path;
  private final String imgExt;
  private final String soundFilePath;
  private final int rate;

  /**
   * constructor
   * 
   * @param fileName   Movie file name to bind
   * @param targetPath Path where image files exist
   * @param ext        Extension of target image file name
   * @param soundFile  Sound file path
   * @param frameRate  Movie file frame rate(per second)
   */
  public FrameBinder(
    String fileName,
    String targetPath,
    String ext,
    String soundFile,
    int frameRate
  ) {
    assert (fileName != null && 0 < fileName.length());
    assert (targetPath != null && Files.exists(Paths.get(targetPath)) != false);
    assert (ext != null && 0 < ext.length() && ext.startsWith(".") == false);
    assert (soundFile == null || Files.exists(Paths.get(soundFile)) != false);
    assert (0 < frameRate);
    
    movieFileName = fileName;
    path = targetPath;
    imgExt = ext;
    soundFilePath = soundFile;
    rate = frameRate;
  }

  /**
   * Bind image files to a movie file
   */
  void bind() {
    try {
      final File imgDir = new File(path);

      final File movie = new File(imgDir, movieFileName);
      if (movie.exists()) {
        movie.delete();
      }

      execFfmpeg(imgDir);
      deleteFrames(imgDir);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private String[] createCommandLineParam() {
    final ArrayList<String> command = new ArrayList<String>(Arrays.asList(
      "ffmpeg",
      "-framerate",
      Integer.toString(rate),
      "-i",
      String.format("%%010d.%s", imgExt)
    ));
    if (soundFilePath != null) {
      command.addAll(Arrays.asList("-i", soundFilePath));
      final String[] compressOptions = {
        "-codec:a",
        "aac",
        "-b:a",
        "320k"
      };
      final String[] copyOptions =  {
        "-codec:a",
        "copy"
      };
      command.addAll(Arrays.asList(soundFilePath.toLowerCase().endsWith(".wav")
        ? compressOptions
        : copyOptions));
    }
    command.addAll(Arrays.asList(
      "-pix_fmt",
      "yuv420p",
      "-r",
      Integer.toString(rate),
      movieFileName
    ));

    return command.toArray(new String[0]);
  }

  private void execFfmpeg(File imgDir) throws IOException, InterruptedException {
    final ProcessBuilder processBuilder = new ProcessBuilder(createCommandLineParam());
    processBuilder.redirectErrorStream(true);
    processBuilder.inheritIO();
    processBuilder.directory(imgDir);
    final Process process = processBuilder.start();
    process.waitFor();
    process.destroy();
  }

  private void deleteFrames(File imgDir) {
    final File[] files = imgDir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return pathname.getName().endsWith(imgExt);
      }
    });
    for (File file : files) {
      file.delete();
    }
  }
}
