package backend;

import java.util.*;

public class AStar {
    public static class SearchResult {
        private List<String> path;
        private int numberOfPaths;

        public SearchResult(List<String> path, int numberOfPaths) {
            this.path = path;
            this.numberOfPaths = numberOfPaths;
        }

        public List<String> getPath() {
            return path;
        }

        public int getNumberOfPaths() {
            return numberOfPaths;
        }
    }

    public static SearchResult findLadder(String startWord, String endWord, Set<String> dictionary) {
        Map<String, String> cameFrom = new HashMap<>();
        Map<String, Integer> gScore = new HashMap<>();
        Map<String, Integer> fScore = new HashMap<>();
        PriorityQueue<String> openSet = new PriorityQueue<>(Comparator.comparingInt(fScore::get));

        gScore.put(startWord, 0);
        fScore.put(startWord, heuristicCostEstimate(startWord, endWord));
        openSet.add(startWord);

        while (!openSet.isEmpty()) {
            String current = openSet.poll();
            if (current.equals(endWord)) {
                return new SearchResult(reconstructPath(cameFrom, current), gScore.get(endWord));
            }

            for (String neighbor : getNeighbors(current, dictionary)) {
                int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1;
                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristicCostEstimate(neighbor, endWord));
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return new SearchResult(Collections.emptyList(), 0);
    }

    private static List<String> reconstructPath(Map<String, String> cameFrom, String current) {
        List<String> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(current);
        Collections.reverse(path);
        return path;
    }

    private static int heuristicCostEstimate(String current, String goal) {
        Set<Character> currentChars = new HashSet<>();
        Set<Character> goalChars = new HashSet<>();
        for (char c : current.toCharArray()) {
            currentChars.add(c);
        }
        for (char c : goal.toCharArray()) {
            goalChars.add(c);
        }
        Set<Character> diff = new HashSet<>(currentChars);
        diff.removeAll(goalChars);
        return diff.size();
    }

    private static List<String> getNeighbors(String word, Set<String> dictionary) {
        List<String> neighbors = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String neighbor = word.substring(0, i) + c + word.substring(i + 1);
                if (dictionary.contains(neighbor)) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }
}
