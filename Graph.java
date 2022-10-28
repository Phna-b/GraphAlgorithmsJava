import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.*;


class Graph {
 private int countNodes;
 private int countEdges;
 private ArrayList<ArrayList<Edge>> adjList;

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

    // Read header
    String[] line = bufferedReader.readLine().split(" ");
    this.countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));

    // Create and fill adjMatrix with read edges
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