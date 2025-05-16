import view.MainView;

public class App {
    public static void main(String[] args) {
        try {
            // Set look and feel
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show main window
        java.awt.EventQueue.invokeLater(() -> {
            new MainView().setVisible(true);
        });
    }
}
