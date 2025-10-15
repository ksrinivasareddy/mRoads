package elevator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BuildingPanel extends JPanel {
    private final int totalFloors;
    private final List<Elevator> elevators;
    private final float[] animatedFloors;

    public BuildingPanel(int totalFloors, List<Elevator> elevators) {
        this.totalFloors = totalFloors;
        this.elevators = elevators;
        this.animatedFloors = new float[elevators.size()];
        for (int i = 0; i < elevators.size(); i++) animatedFloors[i] = elevators.get(i).getCurrentFloor();

        setPreferredSize(new Dimension(300, totalFloors * 60));
        setBackground(Color.WHITE);

        Timer timer = new Timer(50, e -> {
            updateAnimation();
            repaint();
        });
        timer.start();
    }

    private void updateAnimation() {
        for (int i = 0; i < elevators.size(); i++) {
            Elevator e = elevators.get(i);
            float target = e.getCurrentFloor();
            float current = animatedFloors[i];

            if (Math.abs(target - current) > 0.01) animatedFloors[i] += 0.1 * (target - current);
            else animatedFloors[i] = target;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int floorHeight = 50, padding = 20;

        // Draw floors
        for (int i = 0; i < totalFloors; i++) {
            int y = getHeight() - (i + 1) * floorHeight;
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(0, y, getWidth(), y);
            g.setColor(Color.BLACK);
            g.drawString("Floor " + i, 5, y + 15);
        }

        // Draw elevators
        int elevatorWidth = 40, elevatorHeight = 40, gap = 60;
        for (int i = 0; i < elevators.size(); i++) {
            Elevator e = elevators.get(i);
            int x = padding + i * gap;
            int y = (int)(getHeight() - (animatedFloors[i] + 1) * floorHeight + 5);
            g.setColor(Color.BLUE);
            g.fillRect(x, y, elevatorWidth, elevatorHeight);
            g.setColor(Color.WHITE);
            g.drawString("E" + e.getId(), x + 10, y + 20);
        }
    }
}
