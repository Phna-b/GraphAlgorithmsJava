import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.*;


class Graph {
 private int countNodes;
 private int countEdges;
 private ArrayList<ArrayList<Edge>> adjList;
private static final int INF = 99999;

 public Graph(int countNodes){
   this.countNodes = countNodes;
   adjList = new ArrayList<>(this.countNodes);
   for(int i  = 0; i < this.countNodes; i++){
     adjList.add(new ArrayList<Edge>());
   }
 }
  public Graph(String fileName) throws IOException {
    File file = new File(fileName);
    FileReader reader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(reader);

    String[] line = bufferedReader.readLine().split(" ");
    this.countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));

    adjList = new ArrayList<>(this.countNodes);
        for (int i = 0; i < this.countNodes; ++i) {
            adjList.add(new ArrayList<Edge>());
        }
    for (int i = 0; i < fileLines; ++i) {
      String[] edgeInfo = bufferedReader.readLine().split(" ");
      int source = Integer.parseInt(edgeInfo[0]);
      int sink = Integer.parseInt(edgeInfo[1]);
      int weight = Integer.parseInt(edgeInfo[2]);
      addEdge(source, sink, weight);
    }
    bufferedReader.close();
    reader.close();
  }

 public void addEdge(int source, int sink, int weight) {
        if (source < 0 || source > this.countNodes - 1
                || sink < 0 || sink > this.countNodes - 1 || weight <= 0) {
            System.err.println("Invalid edge: " + source + sink + weight);
            return;
        }
        adjList.get(source).add(new Edge(source, sink, weight));
        this.countEdges++;
    }
 public void addEdgeUnoriented(int source, int sink, int weight){
 addEdge(source, sink, weight);
 addEdge(sink, source, weight);
 }
 public int getCountNodes() {
  return countNodes;
}
 public void setCountNodes(int countNodes) {
  this.countNodes = countNodes;
}
 public int getCountEdges() {
  return countEdges;
}
 public void setCountEdges(int countEdges) {
  this.countEdges = countEdges;
}
 public ArrayList<ArrayList<Edge>> getAdjList() {
  return adjList;
}
 public void setAdjList(ArrayList<ArrayList<Edge>> adjList) {
  this.adjList = adjList;
}
 
 public int degree(int node) { //Retorna o grau de um nodo
    if (node < 0 || node > this.countNodes - 1) {
      System.err.println("Invalid edge");
      return 0;
    }
  return this.adjList.get(node).size();
  }

  public int highestDegree() {
    int hg = 0;

    for (int i = 0; i < this.countNodes; i++) {
      int degreeNodeI = this.degree(i);
      if (hg < degreeNodeI)
        hg = this.degree(i);
    }
    return hg;
  }
  public int lowestDegree() {
    int lw = this.countNodes;

    for (int i = 0; i < this.countNodes; i++) {
      int degreeNodeI = this.degree(i);
      if (lw > degreeNodeI)
        lw = degreeNodeI;
    }
    return lw;

  }

  public float density() {
    return (float) this.countEdges / (this.countNodes * (this.countNodes - 1));
  }


    public boolean isOriented() {
        // TDOO tarefa da parte 1
        for (int u = 0; u < this.adjList.size(); ++u) {
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                boolean hasEdgeVU = false;
                for (int idx2 = 0; idx2 < this.adjList.get(v).size(); ++idx2) {
                    int u2 = this.adjList.get(v).get(idx2).getSink();
                    if (u == u2) {
                        hasEdgeVU = true;
                        break;
                    }
                }
                if (!hasEdgeVU) {
                    return true;
                }
            }
        }
        return false;
    }


  /*public Graph complement() {
  Graph gC = new Graph(this.countNodes);
    
  for (int i = 0; i < adjMatrix.length; i++) {
    for (int j = 0; j < adjMatrix[i].length; j++) {
      if (adjMatrix[i][j] == 0 && i != j) {
        gC.addEdge(i, j, 1);
      }
    }
  }
  return gC;
}*/
      public Graph complement() {
        Graph g2 = new Graph(this.countNodes);
        for (int u = 0; u < this.adjList.size(); ++u) {
            for (int v = 0; v < this.countNodes; ++v) {
                boolean addEdgeUV = true;
                for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                    int v2 = this.adjList.get(u).get(idx).getSink();
                    if (v2 == v) { // Edge (u, v) exists and should not be added
                        addEdgeUV = false;
                        break;
                    }
                }
                if (addEdgeUV && u != v) {
                    g2.addEdge(u, v, 1); // It assumes edges are unweighted
                }
            }
        }
        return g2;
    }

    public ArrayList<Edge> kruskal() {
        ArrayList<Edge> T = new ArrayList<Edge>(this.countNodes - 1);
        int[] F = new int[this.countNodes];
        // makeset(u)
        for (int u = 0; u < this.countNodes; ++u)
            F[u] = u;
        this.adjList.sort(null);
        for (int idx = 0; idx < adjList.size(); ++idx) {
            int u = this.adjList.get(idx).get(idx).getSource();
            int v = this.adjList.get(idx).get(idx).getSink();
            if (F[u] != F[v]) { // findset(u) != findset(v)
                T.add(adjList.get(idx).get(idx));
                // Save some iterations if tree is already built
                if (T.size() == countNodes - 1)
                    break;
                // union(u, v)
                int k = F[v];
                for (int i = 0; i < F.length; ++i) {
                    if (F[i] == k) {
                        F[i] = F[u];
                    }
                }
            }
        }
        return T;
    } //Revisar

    public ArrayList<Edge> prim() {
        ArrayList<Edge> T = new ArrayList<Edge>(this.countNodes - 1);
        int s = 0;
        int[] dist = new int[this.countNodes];
        int[] parent = new int[this.countNodes];
        // Q represents the nodes that were not connected yet
        ArrayList<Integer> Q = new ArrayList<Integer>(this.countNodes);
        for (int u = 0; u < this.countNodes; ++u) {
            dist[u] = INF;
            parent[u] = -1;
            Q.add(u);
        }
        dist[s] = 0;
        while (Q.size() != 0) {
            int u = -1;
            int min = INF;
            for (int idx = 0; idx < Q.size(); ++idx) {
                int i = Q.get(idx);
                if (dist[i] < min) {
                    min = dist[i];
                    u = i;
                }
            }
            // Node u is gonna be connected
            Q.remove((Integer) u);
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                int w = this.adjList.get(u).get(idx).getWeight();
                if (Q.contains(v) && w < dist[v]) {
                    dist[v] = w;
                    parent[v] = u;
                }
            }
        }
        // Recover the tree from parent array
        for (int u = 0; u < parent.length; ++u) {
            if (parent[u] != -1) {
                T.add(new Edge(u, parent[u], 1));
            }
        }
        return T;
    } //Revisar

    public boolean subgraph(Graph g2) {
        if (g2.countNodes > this.countNodes || g2.countEdges > this.countEdges)
            return false;
        for (int u = 0; u < g2.adjList.size(); ++u) {
            boolean foundEdge = false;
            for (int idx = 0; idx < g2.adjList.size(); ++idx) {
                 int v = g2.adjList.get(u).get(idx).getSink();
                // Check if edge (u,v) exists in this graph
                for (int idx2 = 0; idx2 < this.adjList.get(u).size(); ++idx2) {
                    int v2 = this.adjList.get(u).get(idx2).getSink();
                    if (v == v2) { // Edge exists
                        foundEdge = true;
                        break;
                    }
                }
                if (!foundEdge)
                    return false;
            }
        }
        return true;
    }//revisar
  

public String toString() {
        String str = "";
        for (int i = 0; i < this.adjList.size(); ++i) {
            str += i + ": ";
            for (int j = 0; j < this.adjList.get(i).size(); ++j) {
                str += this.adjList.get(i).get(j) + "\t";
            }
            str += "\n";
        }
        return str;
    }
  
}