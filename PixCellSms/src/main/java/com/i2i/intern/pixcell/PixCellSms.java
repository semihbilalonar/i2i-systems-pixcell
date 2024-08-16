package com.i2i.intern.pixcell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class PixCellSms {

    public static void main(String[] args) {
        JFrame frame = new JFrame("PixCell SMS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#1c1e23"));
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\tanem\\OneDrive\\Masaüstü\\PixCellSms\\picture\\pixlogo.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(10, 10, 360, 100);
        panel.add(imageLabel);

        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setForeground(Color.decode("#fefffe"));
        messageLabel.setBounds(10, 120, 80, 25);
        panel.add(messageLabel);

        JTextField messageText = new JTextField(20);
        messageText.setBounds(100, 120, 165, 25);
        panel.add(messageText);

        JLabel pixcellnoLabel = new JLabel("PixCell No:");
        pixcellnoLabel.setForeground(Color.decode("#fefffe"));
        pixcellnoLabel.setBounds(10, 150, 80, 25);
        panel.add(pixcellnoLabel);

        JTextField pixcellnoText = new JTextField(20);
        pixcellnoText.setBounds(100, 150, 165, 25);
        panel.add(pixcellnoText);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(10, 180, 150, 25);
        panel.add(submitButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setForeground(Color.decode("#fefffe"));
        resultLabel.setBounds(10, 210, 350, 50);
        panel.add(resultLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageText.getText();
                String pixcellno = pixcellnoText.getText();
                String result = processMessage(message, pixcellno);
                resultLabel.setText("<html>" + result.replaceAll("\n", "<br>") + "</html>");
            }
        });
    }

    public static String processMessage(String message, String pixcellno) {
        if ("KALAN".equalsIgnoreCase(message)) {
            return getAllBalancesFromAPI(pixcellno);
        } else if (message.startsWith("KALAN ")) {
            String[] parts = message.split(" ");
            if (parts.length == 2) {
                String type = parts[1].toUpperCase();
                switch (type) {
                    case "SMS":
                        return getBalanceFromAPI(pixcellno, "balanceSms").replace("Kalan Sms:", "KALAN SMS:");
                    case "DAKIKA":
                        return getBalanceFromAPI(pixcellno, "balanceMinutes").replace("Kalan Dakika:", "KALAN DAKIKA:");
                    case "INTERNET":
                        return getBalanceFromAPI(pixcellno, "balanceData").replace("Kalan Internet:", "KALAN INTERNET:");
                    default:
                        return "Unknown type!";
                }
            }
        }
        return "Invalid message!";
    }

    private static String getAllBalancesFromAPI(String msisdn) {
        String apiUrl = "http://34.172.128.173/api/balance/getRemainingCustomerBalanceByMsisdn/" + msisdn;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            // Balance verilerinin olup olmadığını kontrol et
            if (jsonResponse.has("balanceSms") && jsonResponse.has("balanceMinutes") && jsonResponse.has("balanceData")) {
                int balanceSms = jsonResponse.getInt("balanceSms");
                int balanceMinutes = jsonResponse.getInt("balanceMinutes");
                int balanceData = jsonResponse.getInt("balanceData");

                // Geçersiz değerleri kontrol et
                if (balanceSms == -1 || balanceMinutes == -1 || balanceData == -1) {
                    return "Invalid Number!";
                }

                balanceSms = Math.max(0, balanceSms);
                balanceMinutes = Math.max(0, balanceMinutes);
                balanceData = Math.max(0, balanceData);

                return String.format("KALAN SMS: %d\nKALAN DAKIKA: %d\nKALAN INTERNET: %d",
                        balanceSms, balanceMinutes, balanceData);
            } else {
                return "Invalid Number!";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving balance from API.";
        }
    }

    private static String getBalanceFromAPI(String msisdn, String balanceType) {
        String apiUrl = "http://34.172.128.173/api/balance/getRemainingCustomerBalanceByMsisdn/" + msisdn;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            // İstenen bakiyeyi al ve negatif değerler için kontrol yap
            if (jsonResponse.has(balanceType)) {
                int balance = jsonResponse.getInt(balanceType);

                if (balance == -1) {
                    return "Invalid Number!";
                }

                balance = Math.max(0, balance);
                return String.format("KALAN %s: %d", balanceTypeToLabel(balanceType), balance);
            } else {
                return "Invalid Number!";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving balance from API.";
        }
    }

    private static String balanceTypeToLabel(String balanceType) {
        switch (balanceType) {
            case "balanceSms":
                return "SMS";
            case "balanceMinutes":
                return "DAKIKA";
            case "balanceData":
                return "INTERNET";
            default:
                return "UNKNOWN";
        }
    }
}
