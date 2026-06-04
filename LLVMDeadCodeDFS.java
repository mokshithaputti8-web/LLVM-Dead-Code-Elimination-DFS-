import java.util.*;

public class LLVMDeadCodeDFS {

    static void dfs(String node,
                    Map<String, List<String>> graph,
                    Set<String> visited) {

        visited.add(node);

        for(String next : graph.getOrDefault(node,
                new ArrayList<>())) {

            if(!visited.contains(next))
                dfs(next, graph, visited);
        }
    }

    static void postOrder(String node,
                          Map<String, List<String>> graph,
                          Set<String> visited,
                          List<String> order) {

        visited.add(node);

        for(String next : graph.getOrDefault(node,
                new ArrayList<>())) {

            if(!visited.contains(next))
                postOrder(next, graph, visited, order);
        }

        order.add(node);
    }

    public static void main(String[] args) {

        Map<String,List<String>> cfg = new HashMap<>();

        cfg.put("B0", Arrays.asList("B1","B2"));
        cfg.put("B1", Arrays.asList("B3","B4"));
        cfg.put("B2", Arrays.asList("B5"));
        cfg.put("B3", Arrays.asList("B6"));
        cfg.put("B4", Arrays.asList("B6"));
        cfg.put("B5", Arrays.asList("B6"));
        cfg.put("B6", Arrays.asList("B8"));
        cfg.put("B7", Arrays.asList("B8"));
        cfg.put("B8", new ArrayList<>());

        Set<String> reachable = new LinkedHashSet<>();

        dfs("B0", cfg, reachable);

        System.out.println("Reachable Blocks:");
        System.out.println(reachable);

        Set<String> dead = new HashSet<>(cfg.keySet());
        dead.removeAll(reachable);

        System.out.println("\nDead Blocks:");
        System.out.println(dead);

        List<String> postOrderList = new ArrayList<>();

        Set<String> visited = new LinkedHashSet<>();

        postOrder("B0", cfg, visited, postOrderList);

        System.out.println("\nPost Order Traversal:");
        System.out.println(postOrderList);
    }
}
