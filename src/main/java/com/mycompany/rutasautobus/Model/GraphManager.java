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
           
        }
    }
}
