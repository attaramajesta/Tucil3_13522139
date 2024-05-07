package backend;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.util.*;

public class WordLadderSolverGUI extends JFrame implements ActionListener {
    private JTextField startWordField;
    private JTextField endWordField;
    private JTextArea outputArea;
    private JComboBox<String> algorithmComboBox;

    private Set<String> dictionary;
    private List<String> wordList;

    private void loadDictionary(String filename) {
        dictionary = new HashSet<>();
        wordList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(getClass().getResourceAsStream("/" + filename));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim().toLowerCase();
                dictionary.add(word);
                wordList.add(word);
            }
            scanner.close();
        } catch (Exception e) {
            outputArea.setText("Error loading dictionary: " + e.getMessage());
        }
        System.out.println("Dictionary loaded with " + dictionary.size() + " words.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordLadderSolverGUI gui = new WordLadderSolverGUI();
            gui.setVisible(true);
        });
    }

    public WordLadderSolverGUI() {
        setTitle("Word Ladder Solver");
        setSize(400, 600); // Adjusted size
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.decode("#088add"));

        ImageIcon headerIcon = new ImageIcon("../src/home.png");
        Image image = headerIcon.getImage();
        Image scaledImage = image.getScaledInstance(400, -1, Image.SCALE_SMOOTH);
        headerIcon = new ImageIcon(scaledImage);
        JLabel headerLabel = new JLabel(headerIcon);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JLabel startLabel = new JLabel("Start Word:");
        startLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startLabel.setVerticalAlignment(SwingConstants.CENTER);
        startWordField = new JTextField(15);
        startWordField.setHorizontalAlignment(JTextField.CENTER);

        JLabel endLabel = new JLabel("End Word:");
        endLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endLabel.setVerticalAlignment(SwingConstants.CENTER);
        endWordField = new JTextField(15);
        endWordField.setHorizontalAlignment(JTextField.CENTER);

        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String[] algorithms = { "GBFS", "UCS", "A*" };
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(this);

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        startLabel.setForeground(Color.WHITE);
        startWordField.setForeground(Color.BLACK);
        endLabel.setForeground(Color.WHITE);
        endWordField.setForeground(Color.BLACK);
        algorithmLabel.setForeground(Color.WHITE);
        algorithmComboBox.setForeground(Color.BLACK);
        solveButton.setForeground(Color.BLACK);
        outputArea.setForeground(Color.BLACK);

        mainPanel.add(startLabel);
        mainPanel.add(startWordField);
        mainPanel.add(endLabel);
        mainPanel.add(endWordField);
        mainPanel.add(algorithmLabel);
        mainPanel.add(algorithmComboBox);
        mainPanel.add(solveButton);
        mainPanel.add(scrollPane);

        add(mainPanel);

        loadDictionary("backend/WordList.txt");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Solve")) {
            String startWord = startWordField.getText().trim().toLowerCase();
            String endWord = endWordField.getText().trim().toLowerCase();
            String algorithm = (String) algorithmComboBox.getSelectedItem();
            solveWordLadder(startWord, endWord, algorithm);
        }
    }

    private void solveWordLadder(String startWord, String endWord, String algorithm) {
        // Clear previous result
        outputArea.setText("");
    
        List<String> path;
        long startTime = System.currentTimeMillis();
    
        System.out.println("Start Word: " + startWord);
        System.out.println("End Word: " + endWord);
        System.out.println("Algorithm: " + algorithm);
    
        // Check if start and end words are in the dictionary
        boolean startValid = dictionary.contains(startWord);
        boolean endValid = dictionary.contains(endWord);
    
        if (!startValid || !endValid) {
            if (!startValid) {
                outputArea.append(startWord + " is not a valid word.\n");
            }
            if (!endValid) {
                outputArea.append(endWord + " is not a valid word.\n");
            }
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            outputArea.append("\nExecution Time: " + duration + " milliseconds\n");
            return; // Exit method if start or end word is invalid
        }
    
        switch (algorithm) {
            case "GBFS":
                System.out.println("GBFS");
                GBFS GBFS = new GBFS();
                path = GBFS.findLadder(startWord, endWord, wordList);
                break;
            case "UCS":
                System.out.println("UCS");
                UCS ucs = new UCS();
                path = ucs.findLadder(startWord, endWord, dictionary);
                break;
            case "A*":
                System.out.println("A*");
                AStar.SearchResult result = AStar.findLadder(startWord, endWord, dictionary);
                path = result.getPath();
                break;
            default:
                path = Collections.emptyList();
        }
    
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
    
        displayResult(path, duration);
    }
    
    
    private void displayResult(List<String> path, long duration) {
        if (!path.isEmpty()) {
            outputArea.append("Path found (" + (path.size() - 1) + " words):\n");
            for (String word : path) {
                outputArea.append(word + "\n");
            }
            outputArea.append("\nExecution Time: " + duration + " milliseconds\n");
        } else {
            outputArea.append("No path found.\n");
        }
    }

}


