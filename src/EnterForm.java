import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterForm extends JFrame {
    private JPanel enterPanel;
    private JTextField textField1;
    private JButton findButton;

    EnterForm() {
        add(enterPanel);
        setSize(500, 500);
        setVisible(true);
        setTitle("mangaViewer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Manga[] mangas = Api.getMangaList(textField1.getText());
                    new MangaList(mangas);
                    setVisible(false);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(enterPanel, "ERROR: " + exception.getMessage());
                }
            }
        });

    }

}
