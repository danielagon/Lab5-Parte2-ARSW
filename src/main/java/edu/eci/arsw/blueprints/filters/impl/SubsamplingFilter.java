/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.model.Point;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author danielagonzalez
 */
//@Service
public class SubsamplingFilter implements Filter{

    @Override
    public void removePoints(Blueprint bp) throws BlueprintPersistenceException {
        List<Point> points = new LinkedList<>(bp.getPoints());
        List<Point> newPoints = new LinkedList<>();
        
        for (int i=0;i<points.size();i++){
            if (i%2 == 0){
                newPoints.add(points.get(i));
            }
        }
        bp.setPoints(newPoints);
    }
    
}
