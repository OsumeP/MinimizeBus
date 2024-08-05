/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rutasautobus.Controller;

/**
 *
 * @author Juand
 */
import com.mycompany.rutasautobus.Model.GraphManager;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;

public class GraphController {
    
    GraphManager graphManager;
    
    //Inicializa el objeto GraphManager
    public GraphController (File file){
        this.graphManager = new GraphManager(file);
    }
    
    //Método para retornar el String que contiene la secuencia de buses
    public String getBuses(String origin, String goal){
        
        List<String> route = graphManager.Dijsktra(origin, goal);
        //Si es null no hay ruta.
        if(route == null){
            return "No existe una ruta para llegar desde el origen hasta la meta.";
        }
        int multiple = 1;
        StringBuilder result = new StringBuilder();
        
        //Ciclo que recorre la lista desde el final para crear llenar el String.
        for(int i = route.size() - 1; i >= 0; i--){
            result.append(route.get(i));
            if(i == 0){
                break;
            }
            result.append(", ");
            if(result.length() >= 85 * multiple){
                result.append("\n");
                multiple++;
            }
        }
        return result.toString();
    }
}
