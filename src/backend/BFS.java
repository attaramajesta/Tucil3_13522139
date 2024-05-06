package backend;
import java.util.*;

public class BFS {
    public List<String> findLadder(String startWord, String endWord, List<String> wordList) {
        Map<String, List<String>> combinations = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>(); 
        
        wordList.forEach(word -> {
            for (int i = 0; i < word.length(); i++) {
                String wordRoot = word.substring(0, i) + '*' + word.substring(i + 1);
                if (!combinations.containsKey(wordRoot)) {
                    combinations.put(wordRoot, new ArrayList<>());
                }
                combinations.get(wordRoot).add(word);
            }
        });

        Queue<String> queue = new LinkedList<>();
        queue.offer(startWord);
        parentMap.put(startWord, null); 
        Set<String> visitedWords = new HashSet<>();
        visitedWords.add(startWord);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                String currentWord = queue.poll();
                for (int i = 0; i < currentWord.length(); i++) {
                    String wordRoot = currentWord.substring(0, i) + '*' + currentWord.substring(i + 1);
                    for (String neighbor : combinations.getOrDefault(wordRoot, new ArrayList<>())) {
                        if (!visitedWords.contains(neighbor)) {
                            visitedWords.add(neighbor);
                            parentMap.put(neighbor, currentWord); 
                            queue.offer(neighbor);
                        }
                    }
                }
            }
            
            if (visitedWords.contains(endWord)) {
                List<String> path = new ArrayList<>();
                String node = endWord;
                while (node != null) {
                    path.add(node);
                    node = parentMap.get(node);
                }
                Collections.reverse(path);
                return path;
            }
        }
        return Collections.emptyList();
    }
}

