//class representing an event.

package Blackout;

public class Event{
  private int sequence;
  //private boolean happened;
  private String name;
  
  /**Default constructor, needed so array 'events' in main method can be initialised with null values*/
  public Event(){
  }//end of default constructor
  
  /** Main constructor. 
   Takes int s to set data field 'sequence'
   Takes int h to set data feild 'happened'(<5 sets to true, other sets to false)
   Takes int n to set name from a series of options
  */
  public Event(int s, int h, int n){
    boolean happened;
    if(h<4)
      happened=true;
    else
      happened=false;
    if(happened)
      sequence=s;
    else
      sequence=0;
    if(n==1)
      name="Beer Pong";
    else if(n==2)
      name="Peeing in car park";
    else if(n==3)
      name="Dancing on Beer Pong Table";
    else if(n==4)
      name="Going to Maccas";
    else if(n==5)
      name="Trying to fight Ben";
    else if(n==6)
      name="Confessing love to bouncer";
    else if(n==7)
      name="Stealing a shopping cart";
    else if(n==8)
      name="Falling in the pool";
    else if(n==9)
      name="Serenading a Stop sign";
    else if(n==10){
      name="Tequila shots";
      sequence=s;
      happened=true;
    }
    else if(n==11)
      name="Falling off a Jungle Gym";
  }//end of constructor method
  
  /**Accessor method for name*/
  public String getName(){
    return name;
  }//end of accessor getName
  
  /**Accessor method for sequence*/
  public int getPosition(){
    return sequence;
  }//end of accessor getPosition
  
  /**Mutator for position*/
  public void setPosition(int x){
    sequence=x;
  }
  
  /**toString method - primarily for debugging*/
  public String toString(){
    String niceGap="";
    while(niceGap.length()<27-name.length()){
      niceGap+=" ";
    }//end of while loop
    return "Event: "+name+niceGap+"\t Sequence position: "+sequence+"\t Happened: "+(sequence==0);
  }//end of toString method
  
}//end of class