/*
* Date Created Jan, 14th 2023
* Author: Abdulmuhaimin Ali
* Description: This program shows a pop up with 5 emotions and stores the feelings of the user in an array*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class popEmotion extends JFrame {

    private JButton sadButton;
    private JButton badButton;
    private JButton happyButton;
    private JButton angryButton;
    private JButton afriadButton;
    private JLabel messageLabel;


    /**/
    public popEmotion(){
        super("Emoji Selector");

        setLayout(new FlowLayout());

        sadButton = new JButton("SAD");
        badButton = new JButton("BAD");
        happyButton = new JButton("HAPPY");
        angryButton = new JButton("ANGRY");
        afriadButton = new JButton("AFRAID");



        add(sadButton);
        add(badButton);
        add(happyButton);
        add(angryButton);
        add(afriadButton);

        messageLabel = new JLabel("How you are you feeling right now");
        add(messageLabel);

        buttonHandler handler = new buttonHandler();
        sadButton.addActionListener(handler);
        badButton.addActionListener(handler);
        happyButton.addActionListener(handler);
        angryButton.addActionListener(handler);
        afriadButton.addActionListener(handler);




    }

    private class buttonHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == sadButton){
                messageLabel.setText("It's okay to feel sad. Remember that time heals all wounds and tomorrow is a new day.");
            }
            else if(event.getSource() == badButton){
                messageLabel.setText("Things may seem bad now, but remember that every obstacle is an opportunity for growth.");
            }
            else if (event.getSource()== happyButton){
                messageLabel.setText("Keep that positive energy flowing! Every day is a chance to make new memories.");
            }
            else if(event.getSource() == angryButton){
                messageLabel.setText("It's okay to be angry, but remember to channel that anger in a positive way.");
            }
            else if(event.getSource() == afriadButton){
                messageLabel.setText("Anxiety can be overwhelming, but try to focus on the present and take things one step at a time.");
            }
        }
    }

    public static void main(String[]args){

        popEmotion emojiSelect = new popEmotion();
        emojiSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emojiSelect.setSize(600,600);
        emojiSelect.setVisible(true);
    }

}
