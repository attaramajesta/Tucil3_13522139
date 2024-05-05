import java.util.*;

public class BFS {
    public List<String> findLadder(String beginWord, String endWord, List<String> wordList) {
        Map<String, List<String>> combinations = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>(); // Map to store parent of each word
        
        // Find all the possible generic/intermediate states.
        wordList.forEach(word -> {
            for (int i = 0; i < word.length(); i++) {
                String wordRoot = word.substring(0, i) + '*' + word.substring(i + 1);
                if (!combinations.containsKey(wordRoot)) {
                    combinations.put(wordRoot, new ArrayList<>());
                }
                combinations.get(wordRoot).add(word);
            }
        });
        
        // Push the start word into the queue
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        parentMap.put(beginWord, null); // Start word has no parent
        // To prevent cycles, we will keep track of what nodes/words we have visited
        Set<String> visitedWords = new HashSet<>();
        visitedWords.add(beginWord);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                String currentWord = queue.poll();
                // Find all the intermediate transformations of the current word and find if any of
                // these intermediate transformations is also an intermediate of other words in the
                // wordList.
                for (int i = 0; i < currentWord.length(); i++) {
                    String wordRoot = currentWord.substring(0, i) + '*' + currentWord.substring(i + 1);
                    // We do this by checking the combinations map, each intermediate transformation
                    // points to an array that contains all words that can map to this intermediate.
                    // We then mark that we have visited this node/word and push these nodes into the
                    // queue for consideration.
                    for (String neighbor : combinations.getOrDefault(wordRoot, new ArrayList<>())) {
                        if (!visitedWords.contains(neighbor)) {
                            visitedWords.add(neighbor);
                            parentMap.put(neighbor, currentWord); // Store parent of neighbor
                            queue.offer(neighbor);
                        }
                    }
                }
            }
            // If the end word is reached, reconstruct the path and return it
            if (visitedWords.contains(endWord)) {
                List<String> path = new ArrayList<>();
                String node = endWord;
                while (node != null) {
                    path.add(node);
                    node = parentMap.get(node);
                }
                Collections.reverse(path); // Reverse the path to get it from start to end
                return path;
            }
        }
        // If we reach here, there's no path from start to end
        return Collections.emptyList();
    }
}

