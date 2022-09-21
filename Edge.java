import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.*;

class Edge {
 int source;
 int sink; //destino
 int weight; //peso

  public Edge(int source, int sink, int weight){
    this.source = source;
    this.sink = sink;
    this.weight = weight;
  }
  public void setSink(int sink) {
    this.sink = sink;
}
  public void setSource(int source) {
    this.source = source;
}
  public void setWeight(int weight) {
    this.weight = weight;
}
  public int getSink() {
    return sink;
}
  public int getSource() {
    return source;
}
  public int getWeight() {
    return weight;
}

    public String toString() {
        return "(" + source + ", " + sink + ", " + weight + ")";
    }
}