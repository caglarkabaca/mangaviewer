import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChapterView extends JFrame {
    private JPanel panel;
    private JLabel image;
    private JButton right;
    private JButton left;

    String[] files;
    int index = 0;

    ChapterView(String[] _files) {

        add(panel);
        setSize(720, 1000);
        setVisible(true);
        setTitle("mangaViewer");
        getContentPane().addComponentListener(listener);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                Main.openedFrames.get("chapterList").setVisible(true);
                Main.openedFrames.remove("chapterView");
                Api.clearDownloadedImages();
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        files = _files;
        setImage();

        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index - 1 > 0) {
                    index--;
                    setImage();
                }
            }
        });

        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index + 1 < files.length) {
                    index++;
                    setImage();
                }
            }
        });
    }

    void setImage() {
        try {
            int h = getHeight();
            Image icon = new ImageIcon(Api.downloadChapter(files[index])).getImage();
            icon = icon.getScaledInstance(-1, h - 200, Image.SCALE_DEFAULT);
            image.setIcon(new ImageIcon(icon));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "ERROR: " + ex.getMessage());
        }
    }

    ComponentListener listener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            int h = getHeight();
            Image icon = ((ImageIcon) image.getIcon())
                    .getImage()
                    .getScaledInstance(-1, h - 200, Image.SCALE_DEFAULT);
            image.setIcon(new ImageIcon(icon));
        }

        @Override
        public void componentMoved(ComponentEvent e) {}

        @Override
        public void componentShown(ComponentEvent e) {}

        @Override
        public void componentHidden(ComponentEvent e) {}
    };

}
