/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts1=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp1=new Blueprint("_authorname_", "_bpname_",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        
        Point[] pts2 = new Point[] {new Point(22,35), new Point(15,20), new Point(15,20)};
        Blueprint bp2 = new Blueprint("Joe","Home",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        
        Point[] pts3 = new Point[] {new Point(5,17), new Point(30,8)};
        Blueprint bp3 = new Blueprint("Joe", "Test", pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        
        Point[] pts4 = new Point[] {new Point(58,22), new Point(20,25), new Point(58,22)};
        Blueprint bp4 = new Blueprint("Alex", "Blueprint", pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        blueprints.putIfAbsent(new Tuple<>(bp.getAuthor(),bp.getName()), bp);   
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintsByAuthor = new HashSet<>();
        Set<Tuple<String,String>> tuple = blueprints.keySet();
        
        for (Tuple t: tuple){
            if (t.o1.toString().equals(author)){
                blueprintsByAuthor.add(blueprints.get(t));
            }
        }
        
        return blueprintsByAuthor;
    }
    
    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException{
        Set<Tuple<String,String>> tuple = blueprints.keySet();
        Set<Blueprint> newBlueprints = new HashSet<>();
        
        for (Tuple t: tuple){
            newBlueprints.add(blueprints.get(t));
        }
        
        return newBlueprints;
    }

    @Override
    public void setBlueprintByAuthorAndBpname(String author, String bpname, Blueprint bp) throws BlueprintPersistenceException{
        blueprints.replace(new Tuple<>(author,bpname), bp);
    }
    
}
