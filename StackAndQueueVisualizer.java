import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.LinkedList;

public class StackAndQueueVisualizer extends JFrame {
    private Stack<String> stack = new Stack<>();
    private LinkedList<String> queue = new LinkedList<>();
    private Stack<String> deletedElements = new Stack<>();
    private JPanel stackPanel;
    private JButton pushButton;
    private JButton queueButton;
    private JButton popButton;
    private JButton dequeueButton;

    public StackAndQueueVisualizer() {
        setTitle(" Stack and Queue Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        stackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawStack(g);
            }
        };

        pushButton = createStyledButton("Push", new Color(255, 123, 123));
        queueButton = createStyledButton("Enqueue", new Color(123, 200, 255));
        popButton = createStyledButton("Pop", new Color(255, 123, 123));
        dequeueButton = createStyledButton("Dequeue", new Color(123, 200, 255));

        JTextField inputDataField = new JTextField(10);
        inputDataField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        pushButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputData = inputDataField.getText();
                if (!inputData.isEmpty()) {
                    push(inputData);
                    stackPanel.repaint();
                    inputDataField.setText("");
                }
            }
        });

        queueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputData = inputDataField.getText();
                if (!inputData.isEmpty()) {
                    enqueue(inputData);
                    stackPanel.repaint();
                    inputDataField.setText("");
                }
            }
        });

        popButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stack.isEmpty()) {
                    String deletedElement = stack.pop();
                    deletedElements.push(deletedElement);
                    stackPanel.repaint();
                }
            }
        });

        dequeueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!queue.isEmpty()) {
                    String deletedElement = queue.poll();
                    deletedElements.push(deletedElement);
                    stackPanel.repaint();
                }
            }
        });

        JButton clearButton = createStyledButton("Clear", new Color(0, 153, 0));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stack.clear();
                queue.clear();
                deletedElements.clear();
                stackPanel.repaint();
                enableAllButtons();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Data: "));
        inputPanel.add(inputDataField);
        inputPanel.add(pushButton);
        inputPanel.add(queueButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(popButton);
        buttonPanel.add(dequeueButton);
        buttonPanel.add(clearButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(stackPanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    private void push(String data) {
        stack.push(data);
        queueButton.setEnabled(false);
        dequeueButton.setEnabled(false);
        pushButton.setEnabled(true);
        popButton.setEnabled(true);
    }

    private void enqueue(String data) {
        queue.add(data);
        pushButton.setEnabled(false);
        popButton.setEnabled(false);
        queueButton.setEnabled(true);
        dequeueButton.setEnabled(true);
    }    
    private void drawStack(Graphics g) {
        int stackX = 50;
        int stackY = stackPanel.getHeight() - 30;
    
        for (String data : stack) {
            g.setColor(Color.RED);
            g.fillRect(stackX, stackY - 10, 60, 20);
            g.setColor(Color.BLACK);
            g.drawString(data, stackX + 5, stackY + 5);
    
            stackY -= 30; // Adjust the Y-coordinate for the next element
        }
    
        int queueX = 200;
        int queueY = stackPanel.getHeight() - 30;
    
        for (int i = 0; i < queue.size(); i++) {
            String data = queue.get(i);
            g.setColor(Color.GREEN); // Queue is green for all elements
            g.fillRect(queueX, queueY - 10, 60, 20);
            g.setColor(Color.BLACK);
            g.drawString(data, queueX + 5, queueY + 5);
    
            queueX += 80;
        }
    
        int deletedX = 350;
        int deletedY = stackPanel.getHeight() - 30;
    
        for (String data : deletedElements) {
            g.setColor(Color.GRAY);
            g.fillRect(deletedX, deletedY - 10, 60, 20);
            g.setColor(Color.BLACK);
            g.drawString(data, deletedX + 5, deletedY + 5);
    
            deletedY -= 30;
        }
    }
    

    private void enableAllButtons() {
        pushButton.setEnabled(true);
        queueButton.setEnabled(true);
        popButton.setEnabled(true);
        dequeueButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new StackAndQueueVisualizer().setVisible(true);
        });
    }
}