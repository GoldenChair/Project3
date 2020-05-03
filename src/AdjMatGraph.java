import java.util.*;

public class AdjMatGraph {
    private int[][] matrix;
    private int vertices;

    public AdjMatGraph(int size) {
        matrix = new int[size][size];
        vertices = size;
    }

    public void addEdge(int i, int j) {
        matrix[i][j] = 1;
    }

    public void addEdge(int i, int j, int w) {
        matrix[i][j] = w;
    }

    public void removeEdge(int i, int j) {
        matrix[i][j] = 0;
    }

    public boolean hasEdge(int i, int j) {
        return matrix[i][j] != 0;
    }

    public int getEdgeWeight(int i, int j) {
        return matrix[i][j];
    }

    public void print2D()
    {
        // Loop through all rows
        for (int[] row : matrix)

            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }

    public List<Integer> outEdges(int i) {
        ArrayList<Integer> edges = new ArrayList<>();
        for (int j=0; j<matrix[i].length; ++j)
            if (matrix[i][j] != 0)
                edges.add(j);
        return edges;
    }

    // in-degree: the # of edges going into a vertex
    public List<Integer> inEdges(int j) {
        ArrayList<Integer> edges = new ArrayList<>();
        for (int i=0; i<matrix.length; ++i)
            if (matrix[i][j] != 0)
                edges.add(i);
        return edges;
    }

    int[] dfs(int start) {
        int[] pred = new int[matrix.length];
        Arrays.fill(pred, -1);
        boolean[] visited = new boolean[matrix.length];

        // for for vertex i in the graph, pred[i] will be the index of the
        // (discovered) path from start to i. This is a clever data structure
        // that we can use to reconstruct all the paths we discover. For any
        // vertex in the graph, use pred[] to work backwards to the start.

        Stack<Integer> s = new Stack<>();
        s.add(start);
        visited[start] = true;

        // O(N^2) vs O(N+M)
        // dense graph - M = O(N^2) (really big-theta)
        //       --> dfs for AM is O(M) = O(N^2).
        //           dfs for AL is O(N + M) = O(M) = O(N^2)
        // sparse graph - M = O(N)
        //       --> dfs for AM is O(N^2) = O(M^2)
        //       --> dfs for AL is O(N+M) = O(M) = O(N)
        //
        while (!s.isEmpty()) {
            int i = s.pop();

            for (int j=0; j < matrix[i].length; ++j) {
                if (matrix[i][j] != 0 && !visited[j]) {
                    s.add(j);
                    visited[j] = true;
                    pred[j] = i;
                }
            }
        }
        return pred;
    }

    private int getMinimumVertex(boolean[] isKnown, int[] bestWeight){
        int minKey = Integer.MAX_VALUE;
        int vertex = -1;
        for (int i = 0; i <vertices ; i++) {
            if(isKnown[i]==false && minKey>bestWeight[i]){
                minKey = bestWeight[i];
                vertex = i;
            }
        }
        return vertex;
    }

    public int[] dijkstra(int starting) {
        // Starts with all vertices being unknown distance(weight) away.
        boolean[] isKnown = new boolean[vertices];
        int[] bestWeight = new int[vertices];
        int INFINITY = Integer.MAX_VALUE;

        //Initialize all the distance to infinity. Unknown distance away being represented as infinity.
        for (int i = 0; i <vertices ; i++) {
            bestWeight[i] = INFINITY;
        }

        // Because we know stating point we know we are 0 distance away.
        bestWeight[starting] = 0;

        // Starts process to find min distance away from starting vector for every vector
        for(int i = 0; i < vertices; i++){

            // gets index of unchecked vertex that is closest to starting vector that we know of.
            // We keep track of the ones we have not checked and already checked by looking at isKnown boolean array.
            int minDistanceVertex = getMinimumVertex(isKnown, bestWeight);

            // sets that vector to known
            isKnown[minDistanceVertex] = true;

            // loops over vertexes to find new known min distances from selected vertex(minDistanceVertex)
            for (int traverseVertex = 0; traverseVertex <vertices ; traverseVertex++) {

                // if there is an edge from minDistanceVertex to traverseVertex(Every other vertex)
                if(matrix[minDistanceVertex][traverseVertex]>0){

                    // if we havent checked the distance from the traverseVeterx to starting and if we know there is a path to minDistanceVertex to the traverseVertex
                    if(!isKnown[traverseVertex] && matrix[minDistanceVertex][traverseVertex]!=INFINITY){

                        //adds the weight from minDistanceVertex to the traverseVertex to the weight from our minDistanceVertex to the source vertex
                        int newWeight =  matrix[minDistanceVertex][traverseVertex] + bestWeight[minDistanceVertex];
                        if(newWeight < bestWeight[traverseVertex])
                            bestWeight[traverseVertex] = newWeight;
                    }
                }
            }

        }
        return bestWeight;
    }
}

