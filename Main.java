import java.io.*;
import java.util.Scanner;

class Main {
  public static void main(String[] args) throws IOException {  

    Boolean condition = true;
    Scanner scan = new Scanner(System.in);
    while (condition) {
      System.out.println("Informe a tarefa:");
      System.out.println("\t 1 - Caminho Mínimo");
      System.out.println("\t 2 - Labirinto");
      System.out.println("\t 3 - Sair");
      int option = scan.nextInt();
      if (option == 1) {
        System.out.print("Arquivo:");
        String arquivo = scan.next();
        System.out.print("Algoritmo:");
        System.out.println("\n\t 1 - Dijkstra - Não funcional");
        System.out.println("\t 2 - Bellman-Ford");
        System.out.println("\t 3 - Bellman-Ford Melhorado");
        System.out.println("\t 4 - Floyd-Warshall");
        int alg = scan.nextInt();
        System.out.print("Origem:");
        int origem = scan.nextInt();
        System.out.print("Destino:");
        int destino = scan.nextInt();
          if(alg == 1){
                    long startTime = System.nanoTime();
                    GraphMatrix g1 = new GraphMatrix(arquivo);
                    g1.dijkstra(origem);
                    long endTime = System.nanoTime();
                    long timeElapsed = endTime - startTime;
                    System.out.println("Tempo: " + timeElapsed / 1000000000 +" seg");;     
          }
          if(alg == 2 || alg == 3){
                    long startTime = System.nanoTime();
                    GraphMatrix g1 = new GraphMatrix(arquivo);
                    g1.BellmanFord(origem,destino);
                    long endTime = System.nanoTime();
                    long timeElapsed = endTime - startTime;
                    System.out.println("Tempo: " + timeElapsed / 1000000000 +" seg");   } 
          if(alg == 4){
                    long startTime = System.nanoTime();
                    GraphMatrix g1 = new GraphMatrix(arquivo);
                    g1.floydWarshal(origem,destino);
                    long endTime = System.nanoTime();
                    long timeElapsed = endTime - startTime;
                    System.out.println("Tempo: " + timeElapsed / 1000000000 +" seg"); }   
      }
      if (option == 2) {
        System.out.print("Arquivo:");
        String arquivo = scan.next();
        System.out.println("\nO arquivo selecionado foi: " +arquivo + ", ainda não é possível realizar essa execução. Estamos trabalhando nisso!");
        
      }
      if (option == 3) {
        condition = false;
      }
    }

    GraphMatrix g2 = new GraphMatrix("graph2.txt");
    System.out.println(g2);
    g2.floydWarshal(0, 1);

    Graph g3 = new Graph("graph2.txt");

    GraphMatrix g4 = new GraphMatrix("cm/toy.txt");
    System.out.println(g4);
	  g4.BellmanFord(0,3);

  

    
    









    
  }
}