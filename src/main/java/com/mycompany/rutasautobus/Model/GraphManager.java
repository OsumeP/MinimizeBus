/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rutasautobus.Model;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
/**
 *
 * @author Juand
 */
public class GraphManager {
    HashMap<String, Integer> indexes;
    List<List<Pair>> graph;
    int vertexCount;
    
    // Metodo encargado de crear el gráfo apenas se selecciona el archivo.
    public GraphManager(File file){
        // HashMap diseñado para asignar un indice a cada parada
        this.indexes = new HashMap<String, Integer>();
        //Grafo representado por medio de una matriz de listas
        this.graph = new ArrayList<>();
        //Contador que representa el número de vértices.
        this.vertexCount = 0;
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            int count = Integer.parseInt(br.readLine());
            // Se leen las distintas rutas
            while(count > 0 && (line = br.readLine())!= null){
                // Lista de paradas en la ruta
                String[] arr = line.split(" ");
                count--;
                for(int i = 1; i < arr.length - 2; i += 2){
                    //Si la parada no está indexada, lo hace. Aumenta en 1 la cantidad de vertices
                    if(!indexes.containsKey(arr[i])){
                        indexes.putIfAbsent(arr[i], vertexCount++);
                        graph.add(new ArrayList<Pair>());
                    }
                    
                    // Par de datos (nombre de la parada, costo) se añade a la lista correspondiente;
                    Pair<String, Integer> curr = new Pair<String, Integer>(arr[i+2], Integer.valueOf(arr[i+1]));
                    graph.get(indexes.get(arr[i])).add(curr);
                }
                //Se indexa la última parada de la ruta si no lo estaba.
                if(!indexes.containsKey(arr[arr.length - 1])){
                    indexes.putIfAbsent(arr[arr.length - 1], vertexCount++);
                    graph.add(new ArrayList<Pair>());
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // Este método es una implementación del algoritmo de Dijsktra que se ejecuta cuando el usuario
    //define la parada de origen y destino.
    public List<String> Dijsktra(String origin, String goal){
        //Definicion de objeto comparador para que compare la propiedad "second" del objeto Pair.
        Comparator<Pair> comparator = (Pair o1, Pair o2) -> o1.getSecond().compareTo(o2.getSecond());
        //Creación de la cola de prioridad.
        PriorityQueue<Pair> queue = new PriorityQueue<Pair>(comparator);
        //Arreglo de "Trio" para trackear los caminos más cortos a cada vértice.
        //Trio: vertice predecesor, costo, número de vertices.
        Trio[] track = new Trio[vertexCount];
        
        boolean[] completed = new boolean[vertexCount];
        
        //Si el nombre del origen o meta no esta indexado, significa que no está en el gráfo, por lo tanto no hay camino.
        if(indexes.get(origin) == null || indexes.get(goal) == null){
            return null;
        }
        track[indexes.get(origin)] = new Trio<String, Integer, Integer>(null, 0, 0);
        queue.add(new Pair<String, Integer>(origin, 0));
        
        //Ciclo while que se ejecuta mientras la cola de prioridad tenga elementos o hasta que se
        //ejecute el algoritmo sobre el nodo destino, significando que se encontró el camino más corto.
        while(!queue.isEmpty()){
            Pair node = queue.poll();
            int cost = (Integer) node.getSecond();
            String name = (String) node.getFirst();
            int index = this.indexes.get(name);
            Trio data = track[index];
            
            if(completed[index]){
                continue;
            }
            //Si el nombre del nodo es igual a la meta, entonces ya encontramos el camino más corto.
            if(name.compareTo(goal) == 0){
                return RebuildPath(origin, goal, track);
            }
            if(graph.get(index) == null) continue;
            for(Pair i : graph.get(index)){
                String nameChild = (String) i.getFirst();
                int costChild = (Integer) i.getSecond();
                if(indexes.get(nameChild) == null) continue;
                int indexChild = indexes.get(nameChild);
                
                //Si vertice no ha sido descubierto, le añade el nuevo trio al arreglo.
                if(track[indexChild] == null){
                    track[indexChild] = new Trio(name, cost + costChild,(Integer) data.getThird() + 1);
                    queue.add(new Pair<>(nameChild, cost + costChild));
                }
                //Si el costo de esta nueva ruta es menor al que estaba registrado, lo remplazamos.
                else if((Integer) track[indexChild].getSecond() > costChild + cost){
                    track[indexChild] = new Trio(name, cost + costChild, (Integer) data.getThird() + 1);
                    queue.add(new Pair<>(nameChild, cost + costChild));
                }
                //Si el costo es igual al registrado, revisamos la cantidad de nodos necesarios para saber
                //si necesita menos paradas.
                else if((Integer) track[indexChild].getSecond() == costChild + cost && (Integer) track[indexChild].getThird() > (Integer) data.getThird() + 1){
                    track[indexChild] = new Trio(name, cost + costChild, (Integer) data.getThird() + 1);
                }
            }
            completed[index] = true;
        }
        
        return null;
    }
    
    //Método dedicado a reconstruir el camino desde la meta hasta el origen.
    private List<String> RebuildPath(String origin, String goal, Trio[] track){
        String name = goal;
        Trio<String, Integer, Integer> data;
        List<String> result = new ArrayList<>((Integer) track[indexes.get(goal)].getThird());
        while(name != null){
            data = track[indexes.get(name)];
            result.add(name);
            name = data.getFirst();
        }
        return result;
    }
}
