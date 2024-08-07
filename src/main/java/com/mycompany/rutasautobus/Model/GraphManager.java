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
    HashMap<String, List<Pair>> graph;
    
    // Metodo encargado de crear el gráfo apenas se selecciona el archivo.
    public GraphManager(File file){
        this.graph = new HashMap<String, List<Pair>>();
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            int count = Integer.parseInt(br.readLine());
            // Se leen las distintas rutas
            while(count > 0 && (line = br.readLine())!= null){
                // Lista de paradas en la ruta
                String[] arr = line.split(" ");
                System.out.println(Arrays.toString(arr));
                count--;
                for(int i = 1; i < arr.length - 2; i += 2){
                    graph.putIfAbsent(arr[i], new ArrayList<Pair>());
                    Pair<String, Integer> curr = new Pair<String, Integer>(arr[i+2], Integer.valueOf(arr[i+1]));
                    graph.get(arr[i]).add(curr);
                    System.out.println("Key: " + arr[i] + " " + graph.get(arr[i]));
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public List<String> Dijsktra(String origin, String goal){
        Comparator<Pair> comparator = (Pair o1, Pair o2) -> o1.getSecond().compareTo(o2.getSecond());
        PriorityQueue<Pair> queue = new PriorityQueue<Pair>(comparator);
        //Trio: Previous vertex name, cost, number of vertexs.
        HashMap<String, Trio> track = new HashMap<String, Trio>();
        track.put(origin, new Trio<String, Integer, Integer>(null, 0, 0));
        queue.add(new Pair<String, Integer>(origin, 0));
        
        while(!queue.isEmpty()){
            Pair node = queue.poll();
            int cost = (Integer) node.getSecond();
            String name = (String) node.getFirst();
            Trio data = track.get(name);
            
            if(name.compareTo(goal) == 0){
                return RebuildPath(origin, goal, track);
            }
            if(graph.get(name) == null) continue;
            for(Pair i : graph.get(name)){
                String nameChild = (String) i.getFirst();
                int costChild = (Integer) i.getSecond();
                
                if(!track.containsKey( nameChild ) ){
                    track.put((String) i.getFirst(), new Trio(name, cost + costChild,(Integer) data.getThird() + 1));
                    queue.add(new Pair<>(nameChild, cost + costChild));
                }
                else if((Integer) track.get(nameChild).getSecond() > costChild + cost){
                    track.replace(nameChild, new Trio(name, cost + costChild, (Integer) data.getThird() + 1));
                    queue.add(new Pair<>(nameChild, cost + costChild));
                }
                else if((Integer) track.get(nameChild).getSecond() == costChild + cost && (Integer) track.get(nameChild).getThird() > (Integer) data.getThird() + 1){
                    track.replace(nameChild, new Trio(name, cost + costChild, (Integer) data.getThird() + 1));
                }
            }
        }
        
        return null;
    }
    
    private List<String> RebuildPath(String origin, String goal, HashMap<String, Trio> track){
        String name = goal;
        Trio<String, Integer, Integer> data;
        List<String> result = new ArrayList<>((Integer) track.get(goal).getThird());
        while(name != null){
            data = track.get(name);
            result.add(name);
            name = data.getFirst();
        }
        return result;
    }
}
