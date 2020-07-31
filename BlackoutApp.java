//application class for event
 
//make a class 'clue generator', runs 3 times at start. After that runs at click of button, and makes score-1
 
package Blackout;

import java.util.Random;
import javax.swing.*;

public class BlackoutApp{
  static Random rand = new Random();
  static int numEvents=0;
  static int total= rand.nextInt(6)+4;
  static int[] soFar = new int[total];
  static Event[] events = new Event[total];
  
  public static void main(String[] args){
    int count=0;
    while(count<total){
      events[count]=generate(count);
      count++;
    }//end of while loop
    renumber();
    ClueGen clueA = new ClueGen(events);
    //graphics code
    JFrame frame = new JFrame ("BLACKOUT");
    frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new EventPanel(shuffle(), clueA.getClues())); 
    frame.pack();
    frame.setVisible(true);
  }//end of main method
   
  /**Randomly generates instances of event class*/
   public static Event generate(int seq){
     int eventNum=rand.nextInt(12);
     while(isIn(soFar,eventNum))
       eventNum=rand.nextInt(12);
     soFar[numEvents]=eventNum;
     Event x = new Event(seq+1,rand.nextInt(5),eventNum);
     numEvents++;
     return x;
      
  }//end of method generate
   
   /**Checks if an array of type int contains a specific int*/
   public static boolean isIn(int[] x,int y){
     boolean c=false;
     for(int n:x){
       if(n==y){
         c=true;
       }//end of conditional
     }//end of for each loop
     return c;
   }//end of method isIn
   
   /**Renumbers events so they are properly sequential (1203 instead of 1204)*/
   public static void renumber(){
     boolean zeros = false;
     Event [] done = new Event[total];
     for(Event x: events){
       if (x.getPosition()==0)
         zeros=true;
     }//end of for each to determine if there are any false events
     if(zeros){
       int count=1;
       int c2 = 0;
       for (Event x: events){
         if(x.getPosition() != 0){
           x.setPosition(count);
           count++;
         }
         done[c2]=x;
         c2++;
       }//end of for each renumbering
       events = done;
     }//end of conditional for those with false events
   }// end of method renumber
   
   /**Shuffles events. Returns new shuffled array*/
   public static Event[] shuffle(){
     Event[] shuffled = new Event[total];
     Event temp;
     for(int i=0;i<total;i++){
       shuffled[i]=events[i];
     }//end of for loop filling array 'shuffled'
     for(int i=0;i<total;i++){
       int x = rand.nextInt(total);
       temp = shuffled[i];
       shuffled[i]=shuffled[x];
       shuffled[x] = temp;
     }//end of for each loop, shuffling
     return shuffled;
   }//end of method shuffle
  
}//end of class