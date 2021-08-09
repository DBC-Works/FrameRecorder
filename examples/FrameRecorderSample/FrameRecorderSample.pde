import java.text.MessageFormat;
import org.dbc_works.processing.lib.framerecorder.FrameRecorderFactory;
import org.dbc_works.processing.lib.framerecorder.RecorderType;
import org.dbc_works.processing.lib.framerecorder.Recorder;

final int FRAME_RATE = 60;
Recorder recorder;
float t = 0;

void setup() {
  size(720, 720);
  frameRate(FRAME_RATE);
  noStroke();

  recorder = FrameRecorderFactory.createFrameRecorderInstanceOf(RecorderType.AsyncRecorder, this);
}
void draw() {
  background(0);
  float H = 360;
  translate(H, H);
  float i, j;
  for (i = 0; (i += 0.1) < PI;) {
    for (j = 0; (j += 0.2) < PI;) {
      float x = cos((i + j + t)) * H * sin(i);
      float y = sin(((i * j) + t)) * H * sin(j);
      float r = H / 200 * i * j;
      circle(x, y, r);
      circle(-x * sin(j), -y * sin(j), r);

      t+= 0.00001;
    }
  }

  recorder.recordFrame();
  if (60 * 10 < frameCount) {
    recorder.finish();
    recorder.bindTo("movie.mp4", FRAME_RATE);
    exit();
  }
}
