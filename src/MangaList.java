import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MangaList extends JFrame {
    private JTable table;
    private JScrollPane jp;
    private JPanel panel;
    private JButton button;
    private JLabel info;

    MangaList(Manga[] mangas) {
        add(panel);
        setSize(720, 720);
        setVisible(true);
        setTitle("mangaViewer");

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                Main.openedFrames.get("enterForm").setVisible(true);
                Main.openedFrames.remove("mangaList");
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

        info.setText(String.format("Found %d match", mangas.length));
        String[][] data = new String[mangas.length][3];

        for (int i = 0; i < mangas.length; i++) {
            Manga manga = mangas[i];
            StringBuilder tags = new StringBuilder();
            for (Tag tag : manga.attributes.tags) {
                tags.append(tag.attributes.name.en).append(" | ");
            }
            data[i] = new String[]{Integer.toString(i + 1), manga.attributes.title.en, tags.toString()};
        }

        table.setModel(new DefaultTableModel(
                data,
                new String[] {"", "Title", "Tags"}
        ));
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMinWidth(50);
        columnModel.getColumn(1).setMinWidth(350);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = table.getSelectedRow();
                    Chapter[] chapters = Api.getChapterList(mangas[index]);
                    Main.openedFrames.put("chapterList", new ChapterList(chapters));
                    setVisible(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "ERROR: " + ex.getMessage());
                }

            }
        });

    }
}