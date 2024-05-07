import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
    private Map<String, List<String>> pathCache; // Cache for storing paths

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordLadderSolverGUI gui = new WordLadderSolverGUI();
            gui.setVisible(true);
        });
    }

    public WordLadderSolverGUI() {
        setTitle("Word Ladder Solver");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.decode("#088add"));

        ImageIcon headerIcon = new ImageIcon("../src/home.png");
        Image image = headerIcon.getImage();
        Image scaledImage = image.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        headerIcon = new ImageIcon(scaledImage);
        JLabel headerLabel = new JLabel(headerIcon);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JLabel startLabel = new JLabel("Start Word:");
        startLabel.setAlignmentX(CENTER_ALIGNMENT);
        startLabel.setVerticalAlignment(SwingConstants.CENTER);
        startWordField = new JTextField(15);
        startWordField.setHorizontalAlignment(JTextField.CENTER);

        JLabel endLabel = new JLabel("End Word:");
        endLabel.setAlignmentX(CENTER_ALIGNMENT);
        endLabel.setVerticalAlignment(SwingConstants.CENTER);
        endWordField = new JTextField(15);
        endWordField.setHorizontalAlignment(JTextField.CENTER);
       

        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setAlignmentX(CENTER_ALIGNMENT);
        String[] algorithms = { "GBFS", "UCS", "A*" };
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(this);
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.setPreferredSize(new Dimension(200, 25));

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(this);
        solveButton.setAlignmentX(CENTER_ALIGNMENT);
        solveButton.setPreferredSize(new Dimension(200, 25));

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
        mainPanel.add(generateButton);
        mainPanel.add(solveButton);
        mainPanel.add(scrollPane);

        add(mainPanel);

        loadDictionary("WordList.txt");
        pathCache = new HashMap<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Solve")) {
            String startWord = startWordField.getText().trim().toLowerCase();
            String endWord = endWordField.getText().trim().toLowerCase();
            String algorithm = (String) algorithmComboBox.getSelectedItem();
            solveWordLadder(startWord, endWord, algorithm);
        } else if (e.getActionCommand().equals("Generate")) {
            generateRandomWords();
        }
    }

    private void solveWordLadder(String startWord, String endWord, String algorithm) {
        // Clear previous result
        outputArea.setText("");

        // Check if the path is already cached
        String cacheKey = startWord + "-" + endWord + "-" + algorithm;
        if (pathCache.containsKey(cacheKey)) {
            displayResult(pathCache.get(cacheKey), 0);
            return;
        }

        List<String> path;

        boolean startValid = dictionary.contains(startWord);
        boolean endValid = dictionary.contains(endWord);

        if (!startValid || !endValid) {
            if (!startValid) {
                outputArea.append(startWord + " is not a valid word.\n");
            }
            if (!endValid) {
                outputArea.append(endWord + " is not a valid word.\n");
            }
            return;
        }

        long startTime = System.currentTimeMillis();

        switch (algorithm) {
            case "GBFS":
                GBFS GBFS = new GBFS();
                path = GBFS.findLadder(startWord, endWord, wordList);
                break;
            case "UCS":
                UCS ucs = new UCS();
                path = ucs.findLadder(startWord, endWord, dictionary);
                break;
            case "A*":
                AStar.SearchResult result = AStar.findLadder(startWord, endWord, dictionary);
                path = result.getPath();
                break;
            default:
                path = Collections.emptyList();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Cache the path
        pathCache.put(cacheKey, path);

        displayResult(path, duration);
    }

    private void displayResult(List<String> path, long duration) {
        if (!path.isEmpty()) {
            for (String word : path) {
                outputArea.append(word + "\n");
            }
            outputArea.append("\nExecution Time: " + duration + " milliseconds\n");
            outputArea.append("Path found (" + (path.size() - 1) + " words):\n");
        } else {
            outputArea.append("No path found.\n");
        }
    }

    private void generateRandomWords() {
        Random random = new Random();
        int wordLength = random.nextInt(4) + 2;
        StringBuilder sb = new StringBuilder();

        List<String> wordsWithSameLength = new ArrayList<>();
        for (String word : wordList) {
            if (word.length() == wordLength) {
                wordsWithSameLength.add(word);
            }
        }

        if (wordsWithSameLength.isEmpty()) {
            outputArea.append("No words of length " + wordLength + " found in the dictionary.");
            return;
        }

        String randomStartWord = wordsWithSameLength.get(random.nextInt(wordsWithSameLength.size()));
        startWordField.setText(randomStartWord);

        sb.setLength(0);

        String randomEndWord = randomStartWord;
        while (randomEndWord.equals(randomStartWord)) {
            randomEndWord = wordsWithSameLength.get(random.nextInt(wordsWithSameLength.size()));
        }
        endWordField.setText(randomEndWord);
    }
}
