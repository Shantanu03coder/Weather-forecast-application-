import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIChatWindow extends JFrame {

    private JTextArea chatArea;
    private JTextField userInput;

    public AIChatWindow() {
        setTitle("AI Weather Assistant");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel background = new JLabel();
        background.setLayout(new BorderLayout());
        setContentPane(background);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(false);
        userInput = new JTextField();
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputPanel.add(userInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        background.add(scrollPane, BorderLayout.CENTER);
        background.add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sendMessage() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        chatArea.append("You: " + input + "\n");
        userInput.setText("");

        String response = getAIResponse(input);
        chatArea.append("AI: " + response + "\n\n");
    }

    private String getAIResponse(String input) {
        input = input.toLowerCase();

        if (input.contains("weather") && input.contains("paris")) {
            return "It’s usually cool and sometimes rainy in Paris. You can check current details from the dashboard!";
        } else if (input.contains("suggest") && input.contains("cold")) {
            return "Try Leh, Manali, or Shimla – they’re often chilly!";
        } else if (input.contains("humidity")) {
            return "Humidity is the amount of moisture in the air. High humidity can make it feel warmer.";
        } else if (input.contains("hot")) {
            return "Delhi and Rajasthan are known for their hot summers.";
        } else if (input.contains("clear")) {
            return "Clear skies usually mean no clouds, good visibility, and lots of sun!";
        } else {
            return "I’m still learning! Try asking about weather, city suggestions, or climate terms.";
        }
    }

    public static void main(String[] args) {
        new AIChatWindow();
    }
}
