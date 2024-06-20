import view.MenuForm;

import javax.swing.*;

import static javax.swing.SwingUtilities.*;

public class Main {
    public static void main(String[] args) {
        invokeLater(() -> {
            var form = new MenuForm();
            form.setVisible(true);
        });
    }
}
