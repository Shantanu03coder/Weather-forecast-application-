import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuWindow extends JFrame {

    public MainMenuWindow() {
        setTitle("Weather Forecast Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //background
        ImageIcon backgroundIcon = new ImageIcon("resources/bg_shant_main.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);

        // Title
        JLabel title = new JLabel("Weather Forecast Application", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(Color.BLUE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        backgroundLabel.add(title, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(5, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        String[] buttons = {
                "Check Current Weather",
                "View 5-Day Forecast",
                "View Search History",
                "Talk to AI Assistant",
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("SansSerif", Font.BOLD, 18));
            button.setBackground(new Color(0, 123, 255));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            button.addActionListener(new ButtonHandler());

            buttonPanel.add(button);
        }

        backgroundLabel.add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "Check Current Weather" -> new CurrentWeatherWindow();
                case "View 5-Day Forecast" -> new ForecastWindow();
                case "View Search History" -> new SearchHistoryWindow();
                case "Talk to AI Assistant" -> new AIChatWindow();
            }
        }
    }

    public static void main(String[] args) {
        new MainMenuWindow();
    }
}
