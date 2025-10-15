package elevator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainGUI {
    public static void main(String[] args) {
        int totalFloors = 10;
        int numElevators = 3;
        List<Elevator> elevators = new ArrayList<>();

        // Create elevators
        for (int i = 1; i <= numElevators; i++) {
            Elevator e = new Elevator(i, 6, totalFloors - 1);
            elevators.add(e);
            new Thread(e).start();
        }

        // GUI
        JFrame frame = new JFrame("🏢 Elevator Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);
        frame.setLayout(new BorderLayout());

        BuildingPanel buildingPanel = new BuildingPanel(totalFloors, elevators);
        frame.add(buildingPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        JTextField sourceField = new JTextField(3);
        JTextField destField = new JTextField(3);
        JButton requestBtn = new JButton("Request Elevator");

        inputPanel.add(new JLabel("Source:"));
        inputPanel.add(sourceField);
        inputPanel.add(new JLabel("Destination:"));
        inputPanel.add(destField);
        inputPanel.add(requestBtn);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Button action
        requestBtn.addActionListener(ae -> {
            try {
                int source = Integer.parseInt(sourceField.getText());
                int dest = Integer.parseInt(destField.getText());
                if (source < 0 || source >= totalFloors || dest < 0 || dest >= totalFloors) {
                    JOptionPane.showMessageDialog(frame, "Invalid floor!");
                    return;
                }
                ElevatorController.assignRequest(elevators, new Request(source, dest));
                sourceField.setText("");
                destField.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Enter valid numbers!");
            }
        });

        frame.setVisible(true);
    }
}
