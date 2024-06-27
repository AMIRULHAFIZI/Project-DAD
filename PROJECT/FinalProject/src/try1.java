import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class try1 {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField nameTextField;
    private JTextField borrowTextField;
    private JTextField returnDateTextField;
    private JTextField equipNameFormTextField;
    private JTextField equipTypeFormTextField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                try1 window = new try1();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public try1() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 725, 464);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel equipAvalaibleLable = new JLabel("Available Equipment :");
        equipAvalaibleLable.setBounds(21, 66, 202, 13);
        frame.getContentPane().add(equipAvalaibleLable);

        JButton checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchAvailableEquipment();
            }
        });
        checkAvailabilityButton.setBounds(217, 35, 195, 21);
        frame.getContentPane().add(checkAvailabilityButton);

        JLabel formLabel = new JLabel("EQUIPMENT BOOKING FORM");
        formLabel.setBounds(261, 185, 217, 13);
        frame.getContentPane().add(formLabel);

        JLabel userNameLabel = new JLabel("User Name : ");
        userNameLabel.setBounds(21, 293, 121, 13);
        frame.getContentPane().add(userNameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(173, 290, 171, 19);
        frame.getContentPane().add(nameTextField);
        nameTextField.setColumns(10);

        JLabel borrowDateLabel = new JLabel("Borrow Date :");
        borrowDateLabel.setBounds(21, 316, 121, 13);
        frame.getContentPane().add(borrowDateLabel);

        borrowTextField = new JTextField();
        borrowTextField.setBounds(173, 313, 96, 19);
        frame.getContentPane().add(borrowTextField);
        borrowTextField.setColumns(10);

        JLabel exampleLabel = new JLabel("(ex: 2024-06-01)");
        exampleLabel.setBounds(291, 319, 98, 13);
        frame.getContentPane().add(exampleLabel);

        JLabel returnDateLabel = new JLabel("Return Date : ");
        returnDateLabel.setBounds(21, 339, 121, 13);
        frame.getContentPane().add(returnDateLabel);

        returnDateTextField = new JTextField();
        returnDateTextField.setBounds(173, 336, 96, 19);
        frame.getContentPane().add(returnDateTextField);
        returnDateTextField.setColumns(10);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitBooking();
            }
        });
        submitButton.setBounds(161, 365, 108, 21);
        frame.getContentPane().add(submitButton);

        JLabel equipNameFormLabel = new JLabel("Equipment Name : ");
        equipNameFormLabel.setBounds(21, 234, 160, 13);
        frame.getContentPane().add(equipNameFormLabel);

        equipNameFormTextField = new JTextField();
        equipNameFormTextField.setBounds(173, 231, 96, 19);
        frame.getContentPane().add(equipNameFormTextField);
        equipNameFormTextField.setColumns(10);

        textArea = new JTextArea();
        textArea.setBounds(191, 66, 260, 109);
        frame.getContentPane().add(textArea);

        JLabel equipTypeFormLabel = new JLabel("Equipment Type :");
        equipTypeFormLabel.setBounds(21, 270, 142, 13);
        frame.getContentPane().add(equipTypeFormLabel);

        equipTypeFormTextField = new JTextField();
        equipTypeFormTextField.setBounds(173, 267, 96, 19);
        frame.getContentPane().add(equipTypeFormTextField);
        equipTypeFormTextField.setColumns(10);

        JButton searchTypeButton = new JButton("Search Button");
        searchTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchEquipment();
            }
        });
        searchTypeButton.setBounds(294, 230, 130, 21);
        frame.getContentPane().add(searchTypeButton);
    }

    private void fetchAvailableEquipment() {
        CompletableFuture.supplyAsync(() -> {
            String availabilityData = fetchEquipmentAvailability();
            return parseAvailableEquipment(availabilityData);
        }).thenAccept(equipmentList -> {
            EventQueue.invokeLater(() -> {
                displayAvailableEquipment(equipmentList);
            });
        });
    }

    private String fetchEquipmentAvailability() {
        try {
            String url = "http://localhost/equip_sport_db/fetch_available_equipment.php";
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching data: " + e.getMessage();
        }
    }

    private String[] parseAvailableEquipment(String data) {
        return data.split(","); // Modify based on actual response format
    }

    private void displayAvailableEquipment(String[] equipmentList) {
        textArea.setText(""); // Clear previous content
        for (String equipment : equipmentList) {
            textArea.append(equipment + "\n"); // Append each equipment name to the text area
        }
    }

    private void searchEquipment() {
        String equipName = equipNameFormTextField.getText().trim();
        if (equipName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter an equipment name.");
            return;
        }

        CompletableFuture.supplyAsync(() -> {
            String typeData = fetchEquipmentType(equipName);
            return parseEquipmentType(typeData);
        }).thenAccept(equipType -> {
            EventQueue.invokeLater(() -> {
                equipTypeFormTextField.setText(equipType);
            });
        });
    }

    private String fetchEquipmentType(String equipName) {
        try {
            String url = "http://localhost/equip_sport_db/fetch_equipment_type.php"; // Replace with your endpoint
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            String requestBody = "equipName=" + equipName;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching data: " + e.getMessage();
        }
    }

    private String parseEquipmentType(String data) {
        return data; // Modify based on actual response format
    }

    private void submitBooking() {
        String userName = nameTextField.getText().trim();
        String equipName = equipNameFormTextField.getText().trim();
        String borrowDate = borrowTextField.getText().trim();
        String returnDate = returnDateTextField.getText().trim();

        if (userName.isEmpty() || equipName.isEmpty() || borrowDate.isEmpty() || returnDate.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }

        // Perform booking submission
        CompletableFuture.runAsync(() -> {
            String url = "http://localhost/equip_sport_db/insert_booking.php";
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            // Prepare data for POST request
            String requestBody = String.format("userName=%s&equipName=%s&borrowDate=%s&returnDate=%s",
                    userName, equipName, borrowDate, returnDate);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                EventQueue.invokeLater(() -> {
                    if (responseBody.contains("success")) {
                        JOptionPane.showMessageDialog(frame, "Booking submitted successfully.");
                        // Reset form fields after successful submission
                        nameTextField.setText("");
                        equipNameFormTextField.setText("");
                        borrowTextField.setText("");
                        returnDateTextField.setText("");
                        textArea.setText(""); // Clear text area
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to submit booking: " + responseBody);
                    }
                });

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                EventQueue.invokeLater(() -> {
                    JOptionPane.showMessageDialog(frame, "Error submitting booking: " + ex.getMessage());
                });
            }
        });
    }

}
