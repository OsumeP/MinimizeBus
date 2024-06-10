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
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
/**
 *
 * @author Juand
 */
public class GraphManager {
    HashMap<String, List<Pair>> graph;
    
    public GraphManager(){
        this.graph = new HashMap<String, List<Pair>>();
        try (FileReader fr = new FileReader("D:\\1_OsumeP\\UNAL\\EstructurasDatos\\text.txt")) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            int count = Integer.parseInt(br.readLine());
            while(count > 0 && (line = br.readLine())!= null){
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
            System.out.println("No se pudo leer correctamente el archivo");
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
            
            for(Pair i : graph.get(name)){
                String nameChild = (String) i.getFirst();
                int costChild = (Integer) i.getSecond();
                
                if(!track.containsKey( nameChild ) ){
                    track.put((String) i.getFirst(), new Trio(name, cost + costChild,(Integer) data.getThird() + 1));
                }
                else if((Integer) track.get(nameChild).getSecond() > costChild + cost){
                    track.replace(nameChild, new Trio(name, cost + costChild, (Integer) data.getThird() + 1));
                }
            }
        }
        
        return null;
    }
}
