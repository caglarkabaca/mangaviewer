import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static HashMap<String, JFrame> openedFrames = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main.openedFrames.put("enterForm", new EnterForm());
            }
        });
    }
}