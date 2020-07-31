//GUI class

//plan on re-numbering and then shuffling in application class.

package Blackout;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EventPanel extends JPanel{
  JLabel [] names = new JLabel[BlackoutApp.numEvents];
  private JLabel checker= new JLabel("");
  private JPanel eventPanel = new JPanel();
  private JPanel cluePanel = new JPanel();
  private JPanel instructionPanel = new JPanel();
  private JPanel buttonPanel= new JPanel();
  private JLabel scoreLabel = new JLabel("Score:");
  private JLabel scoreShow = new JLabel();
  private JLabel instruc1 = new JLabel("You wake up with a screaming headache in a room you do not recognise. Your memory is murky and your friends have been messaging, but some of them might be messing with you...");
  private JLabel instruc1_1 = new JLabel("Select the order in which you think events happened. If you don't think an event happened, select zero.");
  private JLabel instruc2 = new JLabel("An incorrect guess will subtract one from your score. Requesting a clue will subtract 3.");
  private JLabel instruc3 = new JLabel("If your score reaches zero, you lose the game.");
  private JPanel[] each = new JPanel[BlackoutApp.numEvents];
  private JLabel[] clueLabels = new JLabel[(BlackoutApp.total-1)*2];
  private JButton check = new JButton("Check Sequence");
  private JButton clue = new JButton("Clue");
  private JComboBox[] orderList = new JComboBox[BlackoutApp.numEvents];
  Event[] events;
  String[] clues;
  private int score= -5+((BlackoutApp.total-1)*6);
  private int clueCount =0;
  
  public EventPanel(Event[] events, String[] clues){
    this.events= events;
    for(int i=0; i<clueLabels.length; i++){
      clueLabels[i]= new JLabel(""+(i+1)+")");
    }//loop initialising clueLabels
    this.clues=clues;
    ButtonListener b1= new ButtonListener();
    cluePanel.setLayout(new GridLayout(((BlackoutApp.total-1)*2),1));
    for(int p =0;p<each.length;p++){
      each[p] = new JPanel();
      each[p].setPreferredSize(new Dimension(250,30));
      each[p].setLayout(new BorderLayout());
    }
    for(int j=0;j<names.length;j++){
      names[j]= new JLabel(events[j].getName());
      //add listener/comboBox for name
      each[j].add(names[j], BorderLayout.WEST);
    }//end of for loop initialising JTextFields and adding to event panel
    Integer[] possSeq=new Integer[BlackoutApp.numEvents+1];
    for(int x=0;x<possSeq.length;x++){
      possSeq[x]=x;
    }
    for(int b=0;b<orderList.length;b++){
      orderList[b]=new JComboBox(possSeq);
      each[b].add(orderList[b],BorderLayout.EAST);
    }//end of for loop initialising JComboBoxes
    for(JPanel p:each){
      eventPanel.add(p);
    }
    for(JLabel j: clueLabels){
      cluePanel.add(j);
    }//end of loop adding clueLabels to cluePanel
    scoreShow.setText(""+score);
    check.addActionListener(b1);
    clue.addActionListener(b1);
    buttonPanel.add(scoreLabel);
    buttonPanel.add(scoreShow);
    buttonPanel.add(check);
    buttonPanel.add(checker);
    buttonPanel.add(clue);
    instructionPanel.add(instruc1);
    instructionPanel.add(instruc1_1);
    instructionPanel.add(instruc2);
    instructionPanel.add(instruc3);
    eventPanel.setPreferredSize(new Dimension(250,450));
    cluePanel.setPreferredSize(new Dimension(850,450));
    instructionPanel.setPreferredSize(new Dimension(1300,150));
    buttonPanel.setPreferredSize(new Dimension(150,450));
    add(instructionPanel);
    add(eventPanel);
    add(cluePanel);
    add(buttonPanel);
    setPreferredSize(new Dimension(1350,650));
    //displaying first 3 clues:
    for(clueCount=0;clueCount<3;clueCount++){
      clueLabels[clueCount].setText((clueCount+1)+") "+this.clues[clueCount]);
    }//end of loop displaying first clues 
  }//end of constructor
  
  private class ButtonListener implements ActionListener{
    
    public void actionPerformed(ActionEvent e){
      if (score<=0){
        score=0;
        scoreShow.setText(""+score);
        checker.setText("Game Over");
      }//end of score check
      else if(e.getSource()==check){
        int c = 0;
        for(int i=0;i<each.length;i++){
          if (orderList[i].getSelectedIndex()!= events[i].getPosition()){
            c++;
          }}
        if (c==0){
          checker.setText("Correct! Well Done!");
          scoreLabel.setText("Final Score:");
        } else{
          checker.setText("Incorrect, try again");
          score-=1;
          scoreShow.setText(""+score);
        }
      }//end of check event
      else if(e.getSource()==clue){
        if (clueCount<clues.length){
          clueLabels[clueCount].setText((clueCount+1)+") "+clues[clueCount]);
          score-=3;
          scoreShow.setText(""+score);
          clueCount+=1;
        }
      }//end of clue event
      if (score<=0){
        score=0;
        scoreShow.setText(""+score);
        checker.setText("Game Over");
      }//end of score check
    }//end of method action performed
  }//end of inner class ButtonListener
  
}//end of class