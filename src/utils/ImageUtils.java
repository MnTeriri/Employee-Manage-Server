package utils;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static InputStream decodeImageString(String imageString) throws IOException {
        byte[] bytes = new BASE64Decoder().decodeBuffer(imageString);
        return new ByteArrayInputStream(bytes);
    }
}
