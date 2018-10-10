/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author hcadavid
 */
@Service
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    
    @Autowired
    private BlueprintsServices blueprints;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprints(){
        try {
            return new ResponseEntity<>(blueprints.getAllBlueprints(), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("There are no blueprints",HttpStatus.NOT_FOUND);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error performing the filtering process", HttpStatus.NOT_IMPLEMENTED);
        }
        
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author){
        try {
            return new ResponseEntity<>(blueprints.getBlueprintsByAuthor(author),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404: There are no blueprints for the author "+author, HttpStatus.NOT_FOUND);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error performing the filtering process", HttpStatus.NOT_IMPLEMENTED);
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{author}/{bpname}")
    public ResponseEntity<?> getBlueprintByAuthorAndBpname(@PathVariable String author, @PathVariable String bpname){
        try {
            return new ResponseEntity<>(blueprints.getBlueprint(author, bpname), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404: The blueprint "+bpname+ " does not exists for the author "+author, HttpStatus.NOT_FOUND);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error performing the filtering process", HttpStatus.NOT_IMPLEMENTED);
        }
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postNewBlueprint(@RequestBody Blueprint bp){
        try {
            blueprints.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{author}/{bpname}")
    public ResponseEntity<?> putBlueprintByAuthor(@RequestBody Blueprint bp, @PathVariable String author, @PathVariable String bpname){
        try {
            blueprints.setBlueprintByAuthorAndBpname(author, bpname, bp);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error modifying the "+bpname+" blueprint of the author "+author, HttpStatus.FORBIDDEN);
        }
    }
}

