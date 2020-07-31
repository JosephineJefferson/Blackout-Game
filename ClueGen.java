//Generates clues

package Blackout;

import java.util.Random;

public class ClueGen {
  private int numC=(BlackoutApp.total -1)*2;
  private String[] clues = new String[numC];
  private Event[] events;
  private Random rand = new Random();
  private int count=0;
  
  public ClueGen(Event[] e){
    events=e;
    clues[count]="You're pretty sure '"+events[count].getName()+"' happened first... if it happened.";
    count++;
    if (allTrue())
      clues[count]="Sophie caught every single one of these events on video and has sent them to you. Everything happened.";
    else
      clues[count]="Something's not right... you're sure at least one of these events didn't occur";
    count++;
    if (getHighestIndex()>=0)
      clues[count]="You remember '"+events[getHighestIndex()].getName()+"' happening just before you fell into bed.";
    else
      clues[count]="Did anything actually happen? Maybe you don't drink and you've been kidnapped...";
    count++;
    if (numberTrue()==0){
      clues[count]="Yeah, you're pretty sure nothing happened";
    } else if (numberTrue()==1){
      clues[count]="The only thing you are sure happened is '"+events[getTrueIndex()].getName()+"'.";
    } else if (numberTrue()>1){
      sequenceClue(2,events.length);
    }//end of clue 4
    count++;
    if (numberTrue()==0){
      clues[count]="Nothing happened. Set everything to zero and consider the game lost, knobhead.";
    } else if (numberTrue()==1){
      clues[count]="Only one thing happened and you know what is is. Set everything else to zero.";
    } else {
      if (allTrue()){
        do{
          sequenceClue(2,events.length);
        } while(redundant());
      } else {
        int f=getFalseIndex();
        int tf;
        do{
          tf=rand.nextInt(events.length);
        }while (f==tf);
        int w = rand.nextInt(2);
        if (w==1){
          clues[count]="You remember '"+events[f].getName()+"' and '"+events[tf].getName()+"' happening at the same time... at least one must be false.";
        } else {
          clues[count]="You remember '"+events[tf].getName()+"' and '"+events[f].getName()+"' happening at the same time... at least one must be false.";
        }
      }
    }//end of clue 5
    count++;
    if (numberTrue()<=1){
      clues[count]="You should have figured it out by now. Consider the game lost and go back to school.";
    } else if (allTrue()){
      do{
        sequenceClue(2,events.length);
      } while(redundant());
    } else {
      clues[count]="'"+events[getTrueIndex(numberTrue()/2)].getName()+"' happened in the eariler portion of your night. Or so they say...";
    }
    //end of clue 6
    count++;
    //if 4 things clues end here
    if(count<numC && numberTrue()>1){
      clues[count]="'"+events[getTrueIndex(numberTrue()/2+1,numberTrue())].getName()+"' happened in the middle or later portion of your night. Or so they say...";
      count++;
      //end of 7
      if (allTrue()){
        clues[count]="'"+events[getTrueIndex(numberTrue()/2)].getName()+"' happened in the eariler portion of your night. Or so they say...";
      }else{
        int x=getFalseIndex();
        clues[count]="There was a unicorn present while '"+events[x].getName()+"' was happening.";
      }
      count++;
    }//clues 7&8
    //end of 5
    if(count<numC && numberTrue()>1){
      if (events.length-numberTrue()<4){
        laterThan();
      } else {
        givePosit();
      }
      count++;
      if (allTrue()){
        do{
          laterThan();
        }while(redundant());
      } else {
        clues[count]="You were only awake long enough for "+numberTrue()+" things to have actually happened.";
      }
      count++;
    }//clues 9&10
    //end of 6
    if(count<numC && numberTrue()>1){
      if(numberTrue()<=events.length/2){
        do{
          int fal;
          do{
            fal=getFalseIndex();
            clues[count]="All your friends agree that '"+events[fal].getName()+"' didn't happen.";
          } while(redundant());
            clues[count]="There was a unicorn present while '"+events[fal].getName()+"' was happening.";
        }while (redundant());
      }//carried out if half or fewer are true
      else if (allTrue()){
        do{
          clues[count]="'"+events[getTrueIndex(numberTrue()/2+1,numberTrue())].getName()+"' happened in the middle or later portion of your night. Or so they say...";
        } while(redundant());
      } else {
        do{
          laterThan();
        }while(redundant());
      }
      count++;
      //end of 11
      if(numberTrue()>3){
        do{
          sequenceClue(2,events.length);
        } while (redundant());
      } else {
        do{
          givePosit();
        } while(redundant());
      }
      count++;
    }//clues 11&12
    //end of 7
    if(count<numC&& numberTrue()>1){
      if(numberTrue()==2)
        clues[count]="Only 2 things happened and you should know the order. Consider the game lost, baboon.";
      else if (numberTrue()<5){
        do{
          givePosit();
        }while(redundant());
      } else {
        do{
          sequenceClue(2,events.length);
        } while (redundant());
      }
      count++;
      if(numberTrue()<4)
        clues[count]="3 or FEWER things happened and you should know the order. Consider the game lost, brainlet.";
        else if(allTrue()){
        do{
          laterThan();
        }while(redundant());
      } else{
        do{
          sequenceClue(2,events.length);
        } while (redundant());
      }//
      count++;
    }//clues 13&14
    //end of 8
    if(count<numC && numberTrue()>3){
      do{
        clues[count]="'"+events[getTrueIndex(numberTrue()/2)].getName()+"' happened in the eariler portion of your night. Or so they say...";
      } while(redundant());
      count++;
      int tell = rand.nextInt(3);
      if (tell>0){
        do{
          sequenceClue(2,events.length);
        } while (redundant());
      } else {
        do {
          givePosit(2,numberTrue()-1);
        } while(redundant());
      }
    }//clues 15&16
    //end of 9
  }//end of constructor
  
  public String[] getClues(){
    return clues;
  }//end of getClues

  
  private int getHighestIndex(){
    int high =-1;
    int highIndex=-1;
    for(int i=0; i<BlackoutApp.total;i++){
      if (events[i].getPosition()>high){
        high=events[i].getPosition();
        highIndex=i;
      }//end of if
    }//end of loop
    if (high==0)
      return -1;
    else
      return highIndex;
  }//end of method gethigh
  
  private boolean allTrue(){
    boolean check=true;
    for(Event e:events){
      if (e.getPosition()==0)
        check=false;
    }//end of loop
    return check;
  }//end of allTrue
  
  private int numberTrue(){
    int check=0;
    for(Event e:events){
      if (e.getPosition()!=0)
        check++;
    }//end of loop
    return check;
  }//end of number true
  
  private int getTrueIndex(int lo,int hi){
    if (lo>numberTrue()){
      return -1;
    } else if(hi>numberTrue()){
      hi=numberTrue();
    }
    int posit=rand.nextInt(hi-lo+1)+lo;
    int index=-1;
    for(int i=0;i<events.length;i++){
      if(events[i].getPosition()==posit)
        index=i;
    }
    return index;
  }//end of getTrueIndex 2 args
  
  private int getTrueIndex(int hi){
    int lo=1;
    if(hi>numberTrue()){
      hi=numberTrue();
    }
    int posit;
    if (hi==0)
      posit=lo;
    else
      posit=rand.nextInt(hi)+lo;
    int index=-1;
    for(int i=0;i<events.length;i++){
      if(events[i].getPosition()==posit)
        index=i;
    }
    return index;
  }//end of getTrueIndex 1 arg
  
  private int getTrueIndex(){
    int lo=1;
    int hi=numberTrue();
    int posit=rand.nextInt(hi)+lo;
    int index=-1;
    for(int i=0;i<events.length;i++){
      if(events[i].getPosition()==posit)
        index=i;
    }
    return index;
  }//end of getTrueIndex 1 arg
  
  private int getFalseIndex(){
    int[] indexes= new int[events.length-numberTrue()];
    int filled=0;
    int index;
    for(int i=0;i<events.length;i++){
      if(events[i].getPosition()==0){
        indexes[filled]=i;
        filled++;
      }
    }
    index=rand.nextInt(indexes.length);
    return indexes[index];
  }//end of getFalseIndex no arg
  
  private void sequenceClue(int lo, int hi){
    int notFirst = getTrueIndex(lo,hi);
    int before = notFirst;
    do {
      before-=1;
    } while(events[before].getPosition()==0);
    clues[count]="Someone explains that you did '"+events[before].getName()+"' followed by '"+events[notFirst].getName()+"'.";
  }//end of sequence clue
  
  private boolean redundant(){
    boolean check= false;
    for(int i=0; i<count; i++){
      if (clues[i].equals(clues[count]))
        check=true;
    }
    return check;
  }//end of redundant
  
  private void givePosit(){
    int hap=getTrueIndex(numberTrue());
        int seq= events[hap].getPosition();
        if (seq==1)
          clues[count]="'"+events[hap].getName()+"' happened 1st.";
        else if(seq==2)
          clues[count]="'"+events[hap].getName()+"' happened 2nd.";
        else if (seq==3)
          clues[count]="'"+events[hap].getName()+"' happened 3rd.";
        else
          clues[count]="'"+events[hap].getName()+"' happened "+seq+"th.";
  }//end of give posit no args
  
  private void givePosit(int lo, int hi){
    int hap=getTrueIndex(lo,hi);
        int seq= events[hap].getPosition();
        if (seq==1)
          clues[count]="'"+events[hap].getName()+"' happened 1st.";
        else if(seq==2)
          clues[count]="'"+events[hap].getName()+"' happened 2nd.";
        else if (seq==3)
          clues[count]="'"+events[hap].getName()+"' happened 3rd.";
        else
          clues[count]="'"+events[hap].getName()+"' happened "+seq+"th.";
  }//end of give posit between 2 args
  
  private void laterThan(){
    int notFirst=getTrueIndex(2,numberTrue());
    int before=getTrueIndex(events[notFirst].getPosition()-1);
    clues[count]="'"+events[notFirst].getName()+"' happened later than '"+events[before].getName()+"'.";
  }//end of laterThan
  
}//end of class