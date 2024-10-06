package model;


import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author manasvini
 */
public class PersonDirectory {
    
    private ArrayList<person>persons;
    
    public PersonDirectory(){
          this.persons = new ArrayList<person>();
    }
    
    public ArrayList<person> getPerson(){
         return persons;
    }
    public void setperson(ArrayList<person> persons) {
      this.persons = persons;
    }
    public person addperson() {
      person p = new person () ;
      persons.add (p) ;
      return p;
    }
    public void deleteperson(person person) {
    persons.remove(person);
    }
    
    public person searchperson(String details) {
        for (person p : persons) {
        if (p.getFirstName().equals(details)){ 
                return p;
            }
            else if(p.getLastName().equals(details)){
                return p;
            }
            else if(p.getHomeAddress().getStreetAddress().equals(details)){
                return p;
            }
            else if(p.getWorkAddress().getStreetAddress().equals(details)){
                return p;
            }

    }
    return null;
}
}
