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
    public void setperson(ArrayList<person> accounts) {
      this.persons = persons;
    }
    public person addperson() {
      person a = new person () ;
      persons.add (a) ;
      return a;
    }
    public void deleteperson(person person) {
    persons.remove(person);
    }
    
    public person searchperson(String person) {
        for (person p : persons) {
        if (p.getFirstName(). contains(person)) {
          return p;
        }
        else if (p.getLastName(). contains(person)) {
          return p;
        }
        else if(p.getHomeAddress().getStreetAddress().contains(person)){
                return p;
            }

    }
    return null;
}
}
