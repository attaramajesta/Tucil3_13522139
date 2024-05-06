package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordLadderSolver {

    private static Set<String> dictionary = new HashSet<>();

    static {
        // Load dictionary from WordList.txt
        loadDictionary("../src/backend/WordList.txt");
    }

    private static void loadDictionary(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String word;
            while ((word = reader.readLine()) != null) {
                dictionary.add(word.trim().toLowerCase());
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Main method to call different algorithms
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the start word:");
            String startWord = scanner.nextLine().trim().toLowerCase();
            while (!dictionary.contains(startWord)) {
                System.out.println("Start word not found in the dictionary. Please enter a valid start word:");
                startWord = scanner.nextLine().trim().toLowerCase();
            }

            System.out.println("Enter the end word:");
            String endWord = scanner.nextLine().trim().toLowerCase();
            while (!dictionary.contains(endWord)) {
                System.out.println("End word not found in the dictionary. Please enter a valid end word:");
                endWord = scanner.nextLine().trim().toLowerCase();
            }

            System.out.println("Choose an algorithm:");
            System.out.println("1. BFS (Breadth-First Search)");
            System.out.println("2. Uniform Cost Search (UCS)");
            System.out.println("3. A*");

            int choice = scanner.nextInt();
            scanner.nextLine();

            List<String> wordList = new ArrayList<>(dictionary);
            switch (choice) {
                case 1:
                    BFS bfs = new BFS();
                    long startTime = System.currentTimeMillis();
                    List<String> pathBFS = bfs.findLadder(startWord, endWord, wordList);
                    long endTime = System.currentTimeMillis();
                    if (!pathBFS.isEmpty()) {
                        System.out.println("Number of paths using BFS: " + pathBFS.size());
                        System.out.println("Path using BFS:");
                        for (String word : pathBFS) {
                            System.out.println(word);
                        }
                    } else {
                        System.out.println("No path found using BFS.");
                    }
                    long duration = endTime - startTime;
                    System.out.println("Algorithm execution time: " + duration + " milliseconds");
                    break;
                case 2:
                    UCS ucs = new UCS();
                    long startTimeUCS = System.currentTimeMillis();
                    List<String> pathUCS = ucs.findLadder(startWord, endWord, dictionary);
                    long endTimeUCS = System.currentTimeMillis();
                    if (!pathUCS.isEmpty()) {
                        System.out.println("Number of paths using BFS: " + pathUCS.size());
                        System.out.println("Path using UCS:");
                        for (String word : pathUCS) {
                            System.out.println(word);
                        }
                    } else {
                        System.out.println("No path found using UCS.");
                    }
                    long durationUCS = endTimeUCS - startTimeUCS;
                    System.out.println("Algorithm execution time: " + durationUCS + " milliseconds");
                    break;
                case 3:
                    long startTimeA = System.currentTimeMillis();
                    AStar.SearchResult result = AStar.findLadder(startWord, endWord, dictionary);
                    long endTimeA = System.currentTimeMillis();
                    List<String> pathAStar = result.getPath();
                    int numberOfPaths = result.getNumberOfPaths();
                    if (!pathAStar.isEmpty()) {
                        System.out.println("Number of paths using A*: " + numberOfPaths);
                        System.out.println("Path using A*:");
                        for (String word : pathAStar) {
                            System.out.println(word);
                        }
                    } else {
                        System.out.println("No path found using A*.");
                    }
                    long durationA = endTimeA - startTimeA;
                    System.out.println("Algorithm execution time: " + durationA + " milliseconds");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } finally {
            scanner.close();
        }
    }
}
