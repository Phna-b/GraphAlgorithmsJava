import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.*;

class Graph {
  private int countNodes;
  private int countEdges;
  private int[][] adjMatrix;
  private static final int INF = 99999;//Contornar problema de overflow do tipo infinity (Metodo floydWarshal)
  public Graph(int countNodes) {
    this.countNodes = countNodes;
    this.adjMatrix = new int[countNodes][countNodes];
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
    this.adjMatrix = new int[this.countNodes][this.countNodes];
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

  public int getCountNodes() {
    return countNodes;
  }

  public int getConuntEdges() {
    return countEdges;
  }

  public int[][] getAdjMatrix() {
    return adjMatrix;
  }

  public void addEdge(int source, int sink, int weigth) {
    if (source < 0 || source > this.countNodes - 1
        || sink < 0 || sink > this.countNodes - 1
        || weigth <= 0) {
      System.err.println("Invalid edge: " + source + " " + sink + " " + weigth);
      return;
    }
    this.adjMatrix[source][sink] = weigth;
    this.countEdges++;
  }

  public void addEdgeUnoriented(int u, int v, int w) {
    if (u < 0 || u > this.countNodes - 1
        || v < 0 || v > this.countNodes - 1
        || w <= 0) {
      System.err.println("Invalid edge: " + u + " " + v + " " + w);
      return;
    }
    this.adjMatrix[u][v] = w;
    this.adjMatrix[v][u] = w;
    this.countEdges += 2;
  }

  public int degree(int node) {
    // Retorna o grau de um node
    if (node < 0 || node > this.countNodes - 1) {
      System.err.println("Invalid edge");
      return 0;
    }
    int cont = 0;
    for (int i = 0; i < this.countNodes; i++) {
      if (adjMatrix[node][i] != 0)
        cont++;
    }
    return cont;
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

  public Graph complement() {
    Graph gC = new Graph(this.countNodes);
    for (int i = 0; i < adjMatrix.length; i++) {
      for (int j = 0; j < adjMatrix[i].length; j++) {
        if (adjMatrix[i][j] == 0 && i != j) {
          gC.addEdge(i, j, 1);
        }
      }
    }
    return gC;
  }

  public float density() {
    return (float) this.countEdges / (this.countNodes * (this.countNodes - 1));
  }

  public boolean subGraph(Graph g2) {
    if (g2.countNodes > this.countNodes || g2.countEdges > this.countEdges)
      return false;
    for (int i = 0; i < g2.adjMatrix.length; ++i) {
      for (int j = 0; j < g2.adjMatrix[i].length; ++j) {
        if (g2.adjMatrix[i][j] != 0 && this.adjMatrix[i][j] == 0)
          return false;
      }
    }
    return true;
  }

  public ArrayList<Integer> bfs(int s) {
    int[] desc = new int[this.countNodes];
    ArrayList<Integer> Q = new ArrayList();
    Q.add(s);
    ArrayList<Integer> R = new ArrayList();
    R.add(s);
    desc[s] = 1;

    // main loop
    while (Q.size() > 0) {
      int u = Q.remove(0);
      for (int v = 0; v < this.adjMatrix[u].length; v++) {
        if (this.adjMatrix[u][v] != 0) { // v é adjacente a u(existe uma aresta)
          if (desc[v] == 0) {
            Q.add(v);
            R.add(v);
            desc[v] = 1;
          }
        }
      }
    }
    return R;
  }

  public ArrayList<Integer> dfs(int s) { // busca em profundidade
    int[] desc = new int[this.countNodes];
    ArrayList<Integer> S = new ArrayList();
    S.add(s);
    ArrayList<Integer> R = new ArrayList();
    R.add(s);
    desc[s] = 1;
    // main loop
    while (S.size() > 0) {
      int u = S.get(S.size()-1);
      boolean unstack = true; // Desempilhar
      for(int v = 0; v < adjMatrix[u].length;++v){
        if(this.adjMatrix[u][v] != 0 && desc[v] == 0){
            S.add(v);
            R.add(v);
            desc[v] = 1;
            unstack = false;
            break;
          }
      } if(unstack)
          S.remove(S.size()-1);
    }
    return R;
  }
  
  public ArrayList<Integer> dfs_Rec(int s) {
    int[] desc = new int[this.countNodes];
    ArrayList<Integer> R = new ArrayList<>();
    dfsRecAux(s, desc,R);
    return R;
  }  
  
  public void dfsRecAux(int u, int[] desc, ArrayList<Integer> R){
    desc[u] = 1;
    R.add(u);
    for (int v = 0; v < this.adjMatrix[u].length; ++v) {
      if (this.adjMatrix[u][v] != 0 && desc[v] == 0) {
        dfsRecAux(v, desc, R);
      }
    }  
  }
  
  public boolean connected() {
    // verifica se o grafo é conexo
    return this.bfs(0).size() == this.countNodes;
  }


  public boolean nonOriented() {
    for (int i = 0; i < adjMatrix.length; i++) 
      for (int j = 0; j < adjMatrix[i].length; j++) 
        if (adjMatrix[i][j] != adjMatrix[j][i]) 
          return false;
        
  return true;
  }

  public void floydWarshal(){
      int [][] dist = new int [this.countNodes][this.countNodes];
      int [][] pred = new int [this.countNodes][this.countNodes];
    for(int i = 0;i < this.adjMatrix.length; i++){
      for(int j = 0;j < this.adjMatrix[i].length; j++){
        if(i == j){
          dist[i][j] = 0;
          pred[i][j] = -1;
        }
        else if(this.adjMatrix[i][j] != 0){//Há aresta (i,j)
          dist[i][j] = this.adjMatrix[i][j];
          pred[i][j] = i;
        } else{
          dist[i][j] = INF;//Representando infinito
          pred[i][j] = -1; //Representando null
        }
      }
    }
    for(int k = 0; k < this.adjMatrix.length;k++){
      for(int i = 0; i < this.adjMatrix.length;i++){
        for(int j = 0; j < this.countNodes;j++){
          if(dist[i][j] > dist[i][k] + dist[k][j]){
            dist[i][j] = dist[i][k]+ dist[k][j];
            pred[i][j] = pred[k][j];
          }
        }
      }
    }
    //TODO main loop
    System.out.println("=== Dist ===");
    for(int i = 0; i < dist.length;i++){
      for(int j = 0; j < dist[i].length;j++){
        System.out.print(dist[i][j] + "\t");
      }
      System.out.println();
    }
    System.out.println("=== Pred ===");
    for(int i = 0; i < pred.length;i++){
      for(int j = 0; j < pred[i].length;j++){
        System.out.print(pred[i][j] + "\t");
      }
      System.out.println();
    }
  }

  public String toString() {
    String str = "";

    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        str += this.adjMatrix[i][j] + "\t";
      }
      str += "\n";
    }
    return str;
  }

}