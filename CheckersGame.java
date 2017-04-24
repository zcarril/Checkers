import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

/**
   A program that allows users to edit a scene composed
   of items.
*/
public class CheckersGame
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("Checkers");

      final CheckerBoard scene = new CheckerBoard();

      frame.add (scene, BorderLayout.CENTER);
      
      
      
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.setSize (600, 600);
      frame.setVisible (true);
      
      JOptionPane.showMessageDialog(null, 
				"First Player: RED", 
				"Player", 
				JOptionPane.INFORMATION_MESSAGE, 
				null );
   }
   
   public static void WinnerDialog (Player p)
   {
   	int answer = JOptionPane.showConfirmDialog(null, "WINNER " + p + "\nNew Game?", "Winner!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
   	
   	if (answer == JOptionPane.YES_OPTION)
   		System.out.println("I am broken");
   	
   	else
   		System.exit(0);
   }
}
