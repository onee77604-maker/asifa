# GyroLock Motion Photo — GCam-style capture test 3.0.2

This build targets Android 11 (API 30) and asks CameraX for the highest available 4:3 still-image resolution. The actual output size depends on camera hardware and the concurrently bound Preview + VideoCapture + ImageCapture stream combination; verify the saved JPEG dimensions on-device. This experiment aims for 12 MP where the camera exposes it.

## Motion Photo changes
- Keeps the result as one `.jpg` file with the JPEG still, embedded MP4 motion segment, and XMP metadata. It does not publish a separate MP4 companion.
- Fixes invalid XMP caused by writing duplicate `MotionPhoto` and `MotionPhotoVersion` attributes through two prefixes bound to the same namespace URI.
- Retains the modern `Camera:MotionPhoto` and `GContainer:Directory` fields plus legacy `GCamera:MicroVideo*` fields for reader compatibility.
- Increments versionCode to 36 and versionName to 3.0.0.

## Important compatibility note
This is a standards-oriented compatibility fix based on the available Infinix/Transsion APK and the GyroLock source. It is not a complete reproduction of proprietary Infinix camera processing. TikTok and WhatsApp may still treat a Motion Photo JPEG as a still image because app import support varies by version and media picker. Build and test the generated JPEG on the target device; inspect its actual dimensions and whether its embedded motion is recognized.

## Build
Open GitHub Actions, run the included workflow, and download the debug APK artifact. The workflow compiles the Vulkan shaders and builds the Android project.


## 3.0.1 — GCam-style metadata experiment

This test build keeps the photo and video in one `*MP.jpg` file. It writes both modern Android Motion Photo Container metadata and legacy `GCamera:MicroVideo*` fields, while omitting a synthetic `Item:Length=0` on the primary JPEG item. The filename ends in `MP.jpg` as required by the Android Motion Photo 1.0 filename pattern. This is an interoperability experiment, not a guarantee that TikTok will accept the file; test both TikTok Live Photo Comments and the post composer. No standalone MP4 is published.


## 3.0.2 — GCam-style capture experiment

The still-image selector now prioritizes the highest available resolution for a 4:3 aspect ratio instead of requesting only 4000x3000. Gyro stabilization classes and the post-recording stabilization path are retained. Motion Photo remains a single JPEG with embedded MP4 bytes and XMP metadata; no standalone MP4 is published by the Motion Photo writer. This is an experiment: higher still resolution may not be available while the rolling video use case is active, and TikTok acceptance is not guaranteed without device testing.
