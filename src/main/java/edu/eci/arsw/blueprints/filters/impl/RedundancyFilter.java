/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.filters.Filter;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author danielagonzalez
 */
@Service
public class RedundancyFilter implements Filter{

    @Override
    public void removePoints(Blueprint bp) throws BlueprintPersistenceException {
        List<Point> points = new LinkedList<>(bp.getPoints());
        
        for (int i=0;i<points.size()-1;i++){
            if (points.get(i).getX() == points.get(i+1).getX() && points.get(i).getY() == points.get(i+1).getY()){
                points.remove(i);
            }
        }
        bp.setPoints(points);
    }
    
}
