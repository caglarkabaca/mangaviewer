import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

public class ChapterList extends JFrame {
    private JPanel panel;
    private JTable table;
    private JButton button;
    private JScrollPane jp;
    private JLabel info;

    ChapterList(Chapter[] chapters) {
        add(panel);
        setSize(720, 720);
        setVisible(true);
        setTitle("mangaViewer");

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                Main.openedFrames.get("mangaList").setVisible(true);
                Main.openedFrames.remove("chapterList");
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

        info.setText(String.format("Found %d chapters", chapters.length));
        String[][] data = new String[chapters.length][6];

        for (int i = 0; i < chapters.length; i++) {
            Chapter chapter = chapters[i];
            data[i] = new String[] {
                    Integer.toString(i + 1),
                    chapter.attributes.title,
                    chapter.attributes.volume,
                    chapter.attributes.chapter,
                    chapter.attributes.translatedLanguage,
                    Integer.toString(chapter.attributes.pages)
            };
        }

        table.setModel(new DefaultTableModel(
                data,
                new String[] {"", "Title", "Volume", "Chapter", "Language", "Page Count"}
        ));

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMinWidth(25);
        columnModel.getColumn(1).setMinWidth(150);
        columnModel.getColumn(2).setMinWidth(25);
        columnModel.getColumn(3).setMinWidth(25);
        columnModel.getColumn(4).setMinWidth(25);
        columnModel.getColumn(5).setMinWidth(25);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = table.getSelectedRow();
                    String[] urls = Api.getChapterImageUrls(chapters[index]);
                    Main.openedFrames.put("chapterView", new ChapterView(urls));
                    setVisible(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "ERROR: " + ex.getMessage());
                }

            }
        });
    }
}
