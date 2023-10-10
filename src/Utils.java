import javax.swing.*;
import java.awt.*;

public class Utils {
    public static ImageIcon upscaleImage(String source, int width, int height) {
        return new ImageIcon(new ImageIcon(source).getImage().getScaledInstance(
                width, height, Image.SCALE_DEFAULT));
    }
}
