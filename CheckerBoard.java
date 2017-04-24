import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
   A component that shows a scene composed of shapes.
 */
public class CheckerBoard extends JPanel
{
	protected final static int SQUARES_1D = 8;

	private GameSquares squares;

	private GamePiece selectedPiece = null;
	
	public static Player currentPlayer = Player.RED;
	
	private static int redPieces = 12;
	
	private static int blackPieces = 12;

	public CheckerBoard()
	{
		super ();

		setLayout(new GridLayout (8,8,0,0));

		squares = new GameSquares();

		int numPieceSpots = 0;

		for (int r = 0; r < SQUARES_1D; r++)
			for (int c = 0; c < SQUARES_1D; c++)
			{
				if ((r + c) % 2 == 1)
					squares.setSquare (new GameSquare (r, c, new Color(190,246,237)));
				else
				{
					squares.setSquare (new GameSquare (r, c, new Color(2,94,78)));
					
					if (numPieceSpots <12)
						squares.getSquare(r, c).setPiece ( new BlackGamePiece (r, c));

					if (numPieceSpots >= 20)
						squares.getSquare(r, c).setPiece (new RedGamePiece (r, c));

					numPieceSpots++;

					final GameSquare sq = squares.getSquare(r, c);
					sq.addMouseListener (new GameSquareMouseListener (sq));
				}

				add (squares.getSquare(r, c));
			}
		
		
			
	}

	public class GameSquareMouseListener extends MouseAdapter
	{
		GameSquare sq;

		public GameSquareMouseListener (GameSquare sq) {
			this.sq = sq;
		}

		public void mousePressed (MouseEvent event)
		{
			Point mousePoint = event.getPoint();

			if (sq.contains (mousePoint))
			{
				if (selectedPiece == null)
				{
					if (sq.getPiece() != null)
					{
						if (sq.getPiece().getPlayer() == currentPlayer )
						{
							selectedPiece = sq.getPiece();
							sq.getPiece().setPieceSelected (true);
						}
					}
				}
				else
				{
					Position pos = sq.getPosition();
					GameSquare selSq = squares.getSquare (selectedPiece.getPosition());

					if (selectedPiece.getPosition().equals (sq.getPosition()))
					{
						selectedPiece.setPieceSelected(false);
						selectedPiece = null;
					}
					else if (selectedPiece.validMove (pos, squares))
					{
						selectedPiece.move (pos, squares);		// move selected piece
						sq.setPiece (selectedPiece);			// new square containing selected piece
						
						if (pos.r == 0 
								&& sq.getPiece().getPlayer() == Player.RED)
							sq.setPiece (new RedKingPiece (pos.r,pos.c)); //make piece a red king if it is a red piece in the king row
						else if (pos.r == 7
								&& sq.getPiece().getPlayer() == Player.BLACK)
							sq.setPiece (new BlackKingPiece (pos.r,pos.c)); //make piece a black king if it is a black piece in the king row
						
						selSq.setPiece (null);					// square that used to contain selected piece
						
						selectedPiece.setPieceSelected(false);	// deselect
						selectedPiece = null;	//  "    "
						
						currentPlayer = Player.switchPlayer(currentPlayer);
					}
				}
			}
			
			repaint();
			
			if (redPieces == 11 || blackPieces == 11)
			{
				WinnerDialog(currentPlayer.getOpponent(currentPlayer));
			}
			
			
			
		}
	}
	
	
	public static int getRed()
	{
		return redPieces;
	}
	
	public static int getBlack()
	{
		return blackPieces;
	}
	
	public static void removeRed()
	{
		redPieces = redPieces - 1;
	}
	
	public static void removeBlack()
	{
		blackPieces = blackPieces - 1;
	}
	
	public void WinnerDialog (Player p)
	   {
	   	int answer = JOptionPane.showConfirmDialog(null, "WINNER " + p + "\nNew Game?", "Winner!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
	   	
	   	if (answer == JOptionPane.YES_OPTION)
	   	{
	   		redPieces = 12;
	   		blackPieces = 12;
	   		currentPlayer = Player.RED;
	   		int numPieceSpots = 0;
			for (int r = 0; r < SQUARES_1D; r++)
			{
				for (int c = 0; c < SQUARES_1D; c++)
				{
					if ((r + c) % 2 == 1)
						;
					else
					{
						squares.getSquare(r, c).setPiece (null);
						
						if (numPieceSpots <12)	
							squares.getSquare(r, c).setPiece ( new BlackGamePiece (r, c));
						
						if (numPieceSpots >=12 && numPieceSpots < 20)
							squares.getSquare(r, c).setPiece (null);

						if (numPieceSpots >= 20)
							squares.getSquare(r, c).setPiece (new RedGamePiece (r, c));

						numPieceSpots++;

					}

					add (squares.getSquare(r, c));
				}
	   		repaint();
			}
			
			JOptionPane.showMessageDialog(null, 
					"First Player: RED", 
					"Player", 
					JOptionPane.INFORMATION_MESSAGE, 
					null );
	   	}
	   	else
	   		System.exit(0);
	   }
}
