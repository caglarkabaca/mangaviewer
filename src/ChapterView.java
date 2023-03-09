import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChapterView extends JFrame {
    private JPanel panel;
    private JLabel image;
    private JButton right;
    private JButton left;

    String[] files;
    int index = 0;

    ChapterView(String[] _files) {

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

        add(panel);
        setSize(720, 1000);
        setVisible(true);
        setTitle("mangaViewer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void setImage() {
        try {
            image.setIcon(new ImageIcon(Api.downloadChapter(files, index)));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "ERROR: " + ex.getMessage());
        }
    }

}
