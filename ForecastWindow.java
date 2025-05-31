import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import org.json.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ForecastWindow extends JFrame {

    private JTextField cityField;
    private JPanel forecastPanel;
    private final String API_KEY = "6d284285726f487e84a82e896ca1bfed";

    public ForecastWindow() {
        setTitle("5-Day Forecast");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //background
        JLabel background = new JLabel();
        background.setLayout(new BorderLayout());
        setContentPane(background);


        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(false);
        cityField = new JTextField(20);
        JButton fetchButton = new JButton("Get Forecast");
        fetchButton.addActionListener(e -> fetchForecast());
        JLabel enterCity = new JLabel("Enter City:");
        enterCity.setForeground(Color.BLACK);
        inputPanel.add(enterCity);
        inputPanel.add(cityField);
        inputPanel.add(fetchButton);
        background.add(inputPanel, BorderLayout.NORTH);


        forecastPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        forecastPanel.setOpaque(false);
        forecastPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        background.add(forecastPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void fetchForecast() {
        forecastPanel.removeAll();

        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a city.");
            return;
        }

        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" +
                    URLEncoder.encode(city, "UTF-8") + "&appid=" + API_KEY + "&units=metric";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) json.append(line);
            reader.close();

            JSONObject obj = new JSONObject(json.toString());
            JSONArray list = obj.getJSONArray("list");

            LinkedHashMap<String, JSONObject> dailyForecast = new LinkedHashMap<>();
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMM dd");

            for (int i = 0; i < list.length(); i++) {
                JSONObject forecastObj = list.getJSONObject(i);
                String dateTxt = forecastObj.getString("dt_txt");
                Date date = inputFormat.parse(dateTxt);
                String day = outputFormat.format(date);

                if (!dailyForecast.containsKey(day)) {
                    dailyForecast.put(day, forecastObj);
                }

                if (dailyForecast.size() == 5) break;
            }

            for (Map.Entry<String, JSONObject> entry : dailyForecast.entrySet()) {
                JSONObject forecastObj = entry.getValue();
                JSONObject main = forecastObj.getJSONObject("main");
                JSONObject weather = forecastObj.getJSONArray("weather").getJSONObject(0);
                String iconId = weather.getString("icon");


                URL iconUrl = new URL("https://openweathermap.org/img/wn/" + iconId + "@2x.png");
                ImageIcon icon = new ImageIcon(iconUrl);
                Image scaledImg = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImg);
//

                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(255, 255, 255, 180));
                card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel dateLabel = new JLabel(entry.getKey());
                dateLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

                JLabel detailsLabel = new JLabel("Temp: " + main.getDouble("temp") + "°C | Condition: " + weather.getString("main"));
                detailsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

                JLabel iconLabel = new JLabel(icon);
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel textPanel = new JPanel(new GridLayout(2, 1));
                textPanel.setOpaque(false);
                textPanel.add(dateLabel);
                textPanel.add(detailsLabel);
//
                card.add(iconLabel, BorderLayout.WEST);
                card.add(textPanel, BorderLayout.CENTER);

                forecastPanel.add(card);
            }

            forecastPanel.revalidate();
            forecastPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching forecast. Check city name or internet.");
        }
    }


    public static void main(String[] args) {
        new ForecastWindow();
    }
}
