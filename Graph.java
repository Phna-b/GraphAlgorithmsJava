class Graph {
  private int countNodes;
  private int countEdges;
  private int[][] adjMatrix;

  public Graph(int countNodes) {
    this.countNodes = countNodes;
    this.adjMatrix = new int[countNodes][countNodes];
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
    int cont = 0;
    int hg = 0;
    for (int i = 0; i < this.countNodes; i++) {
      if (hg < this.degree(i)) {
        hg = this.degree(i);
      }
    }
    return hg;
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