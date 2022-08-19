![workflow stats](https://github.com/DBC-Works/FrameRecorder/actions/workflows/gradle.yml/badge.svg)

# FrameRecorder(Processing library)

A simple video creation support library for [Processing](https://processing.org/).

## Requirements

### Runtime

FrameRecorder uses [FFmpeg](https://www.ffmpeg.org/) to create a video file. Before use, please install FFmpeg(using the package management system like [Homebrew](https://brew.sh/), [Chocolatey](https://chocolatey.org/), and so on).

### Development

If you want to build the library from source codes, please install [Gradle](https://gradle.org/)(using the package management system like [Homebrew](https://brew.sh/), [Chocolatey](https://chocolatey.org/), and so on).

## Installation

Copy `FrameRecorder` directory in the distribution file into `processing/libraries`.

## Usage

### 1. Create recorder instance

Call `FrameRecorderFactory#createFrameRecorderInstanceOf(RecorderType type, PApplet app)` to create frame recorder instance. Recorder types are:

- AsyncRecorder(Recommended): Fast but better quality
- SyncTgaRecorder: Fast, best quality but requires very large free disk space
- SyncPngRecorder: Best quality but very slow

### 2. Record frame

Call `Recorder#recordFrame()` method at the end of `draw` method to save.

FrameRecorder creates `frames` directory under the sketch directory and save the drawing result to a image file.

### 3. Bind frames to video file

When all the frames have been drawn, call `Recorder#finish()` to complete the save and `Recorder#bindTo(String videoFileName, int frameRate)` to bind image files to the video file(`Recorder#bindTo` method deletes image files).

## CHANGELOG

[CHANGELOG](CHANGELOG.md)

## Rules

### Versioning

[Semantic versioning](https://semver.org/)

### Branching model

[GitLab Flow](https://docs.gitlab.com/ee/topics/gitlab_flow.html)

### Commit message format

[Conventional Commits](https://www.conventionalcommits.org/)

## License

MIT
