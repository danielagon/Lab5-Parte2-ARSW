/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;

/**
 *
 * @author danielagonzalez
 */
public interface Filter {
    
    /**
     * 
     * @param bp the blueprint
     * @return the blueprint after removing the points.
     * @throws BlueprintPersistenceException if the blueprint does not exists, 
     *         or any other low-level persistence error occurs.
     */
    public void removePoints(Blueprint bp) throws BlueprintPersistenceException;
    
}
