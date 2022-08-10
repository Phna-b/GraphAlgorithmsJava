class Main {
  public static void main(String[] args) {

    Graph g1 = new Graph(4);
    System.out.println(g1);
    System.out.println(g1.getConuntEdges());
    System.out.println(g1.getCountNodes() + "\n");

    // Teste add Edge
    g1.addEdge(3, 4, 4); // Error.
    g1.addEdge(0, 1, 3);
    g1.addEdge(1, 0, 3);
    g1.addEdge(0, 3, 4);
    g1.addEdge(3, 0, 4);
    g1.addEdge(3, 4, 2); // Error.
    g1.addEdge(3, 3, 4);
    g1.addEdge(3, 2, 4);

    // Teste Degree
    System.out.println(g1);
    System.out.println(g1.degree(0));
    System.out.println(g1.degree(3));

    // Teste maior grau
    System.out.println("\nMaior grau " + g1.highestDegree());
    
    System.out.println(g1);

  }
}