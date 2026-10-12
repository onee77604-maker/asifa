package com.ridiitols.gyrolock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

final class MotionPhotoWriter {
    private static final String XMP_HEADER =
            "http://ns.adobe.com/xap/1.0/\0";

    private MotionPhotoWriter() {}

<<<<<<< HEAD
    static void write(File jpeg, File mp4, File output,
                      long presentationTimestampUs) throws IOException {
        if (!jpeg.isFile() || jpeg.length() < 4) {
            throw new IOException("Foto JPEG tidak valid");
        }
=======
    static void write(
            File jpeg,
            File mp4,
            File output,
            long presentationTimestampUs
    ) throws IOException {

        if (!jpeg.isFile() || jpeg.length() < 4) {
            throw new IOException("Foto JPEG tidak valid");
        }

>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
        if (!mp4.isFile() || mp4.length() < 1024) {
            throw new IOException("Video Motion Photo tidak valid");
        }

        long videoLength = mp4.length();
        long marker = Math.max(0L, presentationTimestampUs);

<<<<<<< HEAD
        byte[] xmpBytes = (XMP_HEADER
                + buildXmp(videoLength, marker))
                .getBytes(StandardCharsets.UTF_8);

        int segmentLength = xmpBytes.length + 2;
=======
        String xmp = buildXmp(videoLength, marker);
        byte[] xmpBytes = (
                XMP_HEADER + xmp
        ).getBytes(StandardCharsets.UTF_8);

        int segmentLength = xmpBytes.length + 2;

>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
        if (segmentLength > 65535) {
            throw new IOException("Metadata XMP terlalu besar");
        }

        File parent = output.getParentFile();
<<<<<<< HEAD
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("Tidak dapat membuat folder output");
        }

        if (output.getCanonicalFile().equals(jpeg.getCanonicalFile())
                || output.getCanonicalFile().equals(mp4.getCanonicalFile())) {
            throw new IOException("File output tidak boleh menimpa sumber");
        }

=======
        if (parent != null && !parent.exists()
                && !parent.mkdirs()) {
            throw new IOException(
                    "Tidak dapat membuat folder output"
            );
        }

        if (jpeg.getCanonicalFile().equals(
                output.getCanonicalFile())) {
            throw new IOException(
                    "File output tidak boleh menimpa foto sumber"
            );
        }

        byte[] buffer = new byte[64 * 1024];

>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
        try (FileInputStream in = new FileInputStream(jpeg);
             FileOutputStream out = new FileOutputStream(output)) {

            int a = in.read();
            int b = in.read();

            if (a != 0xFF || b != 0xD8) {
                throw new IOException("Header JPEG tidak valid");
            }

<<<<<<< HEAD
            out.write(a);
            out.write(b);
=======
            // JPEG Start Of Image
            out.write(a);
            out.write(b);

            // XMP APP1 segment
>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
            out.write(0xFF);
            out.write(0xE1);
            out.write((segmentLength >> 8) & 0xFF);
            out.write(segmentLength & 0xFF);
            out.write(xmpBytes);

<<<<<<< HEAD
            byte[] buffer = new byte[64 * 1024];
            int n;

=======
            int n;
>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }

<<<<<<< HEAD
            try (FileInputStream videoIn = new FileInputStream(mp4)) {
=======
            // Append MP4 after the original JPEG bytes.
            try (FileInputStream videoIn =
                         new FileInputStream(mp4)) {
>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
                while ((n = videoIn.read(buffer)) != -1) {
                    out.write(buffer, 0, n);
                }
            }

            out.flush();
            out.getFD().sync();
        }
    }

<<<<<<< HEAD
    private static String buildXmp(long videoLength, long marker) {
        return "<?xpacket begin='\uFEFF' id='W5M0MpCehiHzreSzNTczkc9d'?>"
                + "<x:xmpmeta xmlns:x='adobe:ns:meta/'>"
                + "<rdf:RDF xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'>"
=======
    private static String buildXmp(
            long videoLength,
            long presentationTimestampUs
    ) {
        return "<?xpacket begin='\uFEFF' "
                + "id='W5M0MpCehiHzreSzNTczkc9d'?>"
                + "<x:xmpmeta xmlns:x='adobe:ns:meta/'>"
                + "<rdf:RDF "
                + "xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'>"
>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
                + "<rdf:Description rdf:about=''"
                + " xmlns:GCamera='http://ns.google.com/photos/1.0/camera/'"
                + " xmlns:TCamera='http://ns.transsion.com/photos/1.0/camera/'"
                + " xmlns:Container='http://ns.google.com/photos/1.0/container/'"
                + " xmlns:Item='http://ns.google.com/photos/1.0/container/item/'"
                + " GCamera:MotionPhoto='1'"
                + " GCamera:MotionPhotoVersion='1'"
<<<<<<< HEAD
                + " GCamera:MotionPhotoPresentationTimestampUs='" + marker + "'"
                + " TCamera:LivePhoto='1'"
                + " GCamera:MicroVideo='1'"
                + " GCamera:MicroVideoVersion='1'"
                + " GCamera:MicroVideoOffset='" + videoLength + "'"
                + " GCamera:MicroVideoPresentationTimestampUs='" + marker + "'>"
                + "<Container:Directory><rdf:Seq>"
                + "<rdf:li rdf:parseType='Resource'>"
                + "<Container:Item Item:Mime='image/jpeg'"
                + " Item:Semantic='Primary'"
                + " Item:Length='0' Item:Padding='0'/>"
                + "</rdf:li>"
                + "<rdf:li rdf:parseType='Resource'>"
                + "<Container:Item Item:Mime='video/mp4'"
=======
                + " GCamera:MotionPhotoPresentationTimestampUs='"
                + presentationTimestampUs + "'"
                + " TCamera:LivePhoto='1'>"
                + "<Container:Directory><rdf:Seq>"

                // Primary JPEG item
                + "<rdf:li rdf:parseType='Resource'>"
                + "<Container:Item"
                + " Item:Mime='image/jpeg'"
                + " Item:Semantic='Primary'"
                + " Item:Length='0'"
                + " Item:Padding='0'/>"
                + "</rdf:li>"

                // Embedded MP4 item
                + "<rdf:li rdf:parseType='Resource'>"
                + "<Container:Item"
                + " Item:Mime='video/mp4'"
>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
                + " Item:Semantic='MotionPhoto'"
                + " Item:Length='" + videoLength + "'"
                + " Item:Padding='0'/>"
                + "</rdf:li>"
<<<<<<< HEAD
                + "</rdf:Seq></Container:Directory>"
                + "</rdf:Description></rdf:RDF></x:xmpmeta>"
                + "<?xpacket end='w'?>";
=======

                + "</rdf:Seq></Container:Directory>"
                + "</rdf:Description></rdf:RDF>"
                + "</x:xmpmeta><?xpacket end='w'?>";
>>>>>>> 7fff5c9 (Fix Motion Photo XMP compatibility)
    }
}
