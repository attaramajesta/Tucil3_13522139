package backend;
import java.util.*;

public class UCS {
    public List<String> findLadder(String startWord, String endWord, Set<String> dictionary) {
        Map<String, Integer> cost = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(cost::get));

        cost.put(startWord, 0);
        queue.add(startWord);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(endWord)) {
                return reconstructPath(parent, current);
            }

            for (String neighbor : getNeighbors(current, dictionary)) {
                int newCost = cost.get(current) + 1;
                if (!cost.containsKey(neighbor) || newCost < cost.get(neighbor)) {
                    cost.put(neighbor, newCost);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return Collections.emptyList();
    }

    private List<String> reconstructPath(Map<String, String> parent, String current) {
        List<String> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private List<String> getNeighbors(String word, Set<String> dictionary) {
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
