/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.filters.Filter;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    BlueprintsPersistence bpp;
    
    @Autowired
    Filter filter;
    
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException{
        bpp.saveBlueprint(bp);
    }
    
    /**
     * 
     * @return all the blueprints
     * @throws BlueprintNotFoundException if doesn't exists blueprints
     * @throws BlueprintPersistenceException if the points were not eliminated
     */
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException,BlueprintPersistenceException{
        for (Blueprint bp: bpp.getAllBlueprints()){
            filter.removePoints(bp);
        }
        return bpp.getAllBlueprints();
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     * @throws BlueprintPersistenceException if the points were not eliminated
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException, BlueprintPersistenceException{
        filter.removePoints(bpp.getBlueprint(author, name));
        return bpp.getBlueprint(author, name);
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     * @throws BlueprintPersistenceException if the points were not eliminated
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException, BlueprintPersistenceException{
        for (Blueprint bp:bpp.getBlueprintByAuthor(author)){
            filter.removePoints(bp);
        }
        return bpp.getBlueprintByAuthor(author); 
    }
    
    public void setBlueprintByAuthorAndBpname(String author, String name, Blueprint bp) throws BlueprintPersistenceException{
        if (author.equals(bp.getAuthor())){
            bpp.setBlueprintByAuthorAndBpname(author, name, bp);
        }else{
            throw new BlueprintPersistenceException("El autor no es mismo ingresado en el plano");
        }
    }
}