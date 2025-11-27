public class Main {
    public static void main(String[] args) {
        // Run UI in Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            new ui.MainJFrame().setVisible(true);
        });
    }
}
