package org.dbc_works.processing.lib.framerecorder;

/**
 * Frame recorder type
 */
public enum RecorderType {
  /**
   * Synchronous recorder(image format: TGA)
   */
  SyncTgaRecorder,

  /**
   * Synchronous recorder(image format: PNG)
   */
  SyncPngRecorder,

  /**
   * Synchronous recorder(image format: JPG)
   */
  AsyncRecorder
}
