// package backend;

// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.*;

// public class WordLadderSolver {

//     private static Set<String> dictionary = new HashSet<>();

//     static {
//         // Load dictionary from WordList.txt
//         loadDictionary("../src/backend/WordList.txt");
//     }

//     private static void loadDictionary(String filename) {
//         try {
//             BufferedReader reader = new BufferedReader(new FileReader(filename));
//             String word;
//             while ((word = reader.readLine()) != null) {
//                 dictionary.add(word.trim().toLowerCase());
//             }
//             reader.close();
//         } catch (IOException e) {
//             System.err.println("Error loading dictionary: " + e.getMessage());
//         }
//     }

//     public static void main(String[] args) {
//         // Main method to call different algorithms
//         Scanner scanner = new Scanner(System.in);

//         try {
//             System.out.println("Enter the start word:");
//             String startWord = scanner.nextLine().trim().toLowerCase();
//             while (!dictionary.contains(startWord)) {
//                 System.out.println("Start word not found in the dictionary. Please enter a valid start word:");
//                 startWord = scanner.nextLine().trim().toLowerCase();
//             }

//             System.out.println("Enter the end word:");
//             String endWord = scanner.nextLine().trim().toLowerCase();
//             while (!dictionary.contains(endWord)) {
//                 System.out.println("End word not found in the dictionary. Please enter a valid end word:");
//                 endWord = scanner.nextLine().trim().toLowerCase();
//             }

//             System.out.println("Choose an algorithm:");
//             System.out.println("1. GBFS (Breadth-First Search)");
//             System.out.println("2. Uniform Cost Search (UCS)");
//             System.out.println("3. A*");

//             int choice = scanner.nextInt();
//             scanner.nextLine();

//             List<String> wordList = new ArrayList<>(dictionary);
//             switch (choice) {
//                 case 1:
//                     GGBFS GBFS = new GGBFS();
//                     long startTime = System.currentTimeMillis();
//                     List<String> pathGBFS = GBFS.findLadder(startWord, endWord, wordList);
//                     long endTime = System.currentTimeMillis();
//                     if (!pathGBFS.isEmpty()) {
//                         System.out.println("Number of paths using GBFS: " + pathGBFS.size());
//                         System.out.println("Path using GBFS:");
//                         for (String word : pathGBFS) {
//                             System.out.println(word);
//                         }
//                     } else {
//                         System.out.println("No path found using GBFS.");
//                     }
//                     long duration = endTime - startTime;
//                     System.out.println("Algorithm execution time: " + duration + " milliseconds");
//                     break;
//                 case 2:
//                     UCS ucs = new UCS();
//                     long startTimeUCS = System.currentTimeMillis();
//                     List<String> pathUCS = ucs.findLadder(startWord, endWord, dictionary);
//                     long endTimeUCS = System.currentTimeMillis();
//                     if (!pathUCS.isEmpty()) {
//                         System.out.println("Number of paths using GBFS: " + pathUCS.size());
//                         System.out.println("Path using UCS:");
//                         for (String word : pathUCS) {
//                             System.out.println(word);
//                         }
//                     } else {
//                         System.out.println("No path found using UCS.");
//                     }
//                     long durationUCS = endTimeUCS - startTimeUCS;
//                     System.out.println("Algorithm execution time: " + durationUCS + " milliseconds");
//                     break;
//                 case 3:
//                     long startTimeA = System.currentTimeMillis();
//                     AStar.SearchResult result = AStar.findLadder(startWord, endWord, dictionary);
//                     long endTimeA = System.currentTimeMillis();
//                     List<String> pathAStar = result.getPath();
//                     int numberOfPaths = result.getNumberOfPaths();
//                     if (!pathAStar.isEmpty()) {
//                         System.out.println("Number of paths using A*: " + numberOfPaths);
//                         System.out.println("Path using A*:");
//                         for (String word : pathAStar) {
//                             System.out.println(word);
//                         }
//                     } else {
//                         System.out.println("No path found using A*.");
//                     }
//                     long durationA = endTimeA - startTimeA;
//                     System.out.println("Algorithm execution time: " + durationA + " milliseconds");
//                     break;
//                 default:
//                     System.out.println("Invalid choice");
//             }
//         } finally {
//             scanner.close();
//         }
//     }
// }

package backend;

import javax.swing.*;
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
        setSize(600, 400); // Adjusted size
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel startLabel = new JLabel("Start Word:");
        startWordField = new JTextField(15);

        JLabel endLabel = new JLabel("End Word:");
        endWordField = new JTextField(15);

        JLabel algorithmLabel = new JLabel("Algorithm:");
        String[] algorithms = { "GBFS", "UCS", "A*" };
        algorithmComboBox = new JComboBox<>(algorithms);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(this);

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

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

        switch (algorithm) {
            case "GBFS":
                System.out.println("GBFS");
                GBFS GBFS = new GBFS();
                path = GBFS.findLadder(startWord, endWord, wordList);
                System.out.println("Number of paths using GBFS: " + path.size());
                System.out.println("Paths using GBFS: " + path);
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
