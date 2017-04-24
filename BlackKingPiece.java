

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.*;

import javax.swing.ImageIcon;


public class BlackKingPiece extends GamePiece
{
	public BlackKingPiece (int row, int col) {
		super (row, col, Player.BLACK);
	}

	public BlackKingPiece (Position pos) {
		this (pos.r, pos.c);
	}
	
	protected boolean validNonJump (int dr, int dc, GameSquares squares)
	{
		if (( (dr == 1) || (dr == -1) ) && ( (dc == 1) || (dc == -1) ))
		{
			if (squares.getSquare(pos.r + dr, pos.c + dc).getPiece() == null)
				return true;
		}
		
		return false;
	}
	
	protected boolean validJump (int dr, int dc, GameSquares squares)
	{		
		if (( (dr == 2) || (dr == -2) ) && ( (dc == -2) || (dc == 2) ))
		{
			if ((squares.getSquare(pos.r + dr, pos.c + dc).getPiece() == null)
					&& (validNonJump(dr, dc, squares) == false)
					&& ((squares.getSquare(pos.r + dr/2, pos.c + dc/2).getPiece() != null))
					&& (squares.getSquare(pos.r + dr/2, pos.c + dc/2).getPiece().getPlayer() == Player.RED))
			{
				return true;
			}
		}
				
		return false;
	}
	
	public void drawPiece (Graphics2D g2, int x, int y, int width, int height)
	{
		Ellipse2D.Double outline = new Ellipse2D.Double (x + width * DIST_FROM_EDGE + LINE_WIDTH / 2,
														 y + height * DIST_FROM_EDGE + LINE_WIDTH / 2,
														 width * (1 - 2 * DIST_FROM_EDGE) - LINE_WIDTH,
														 height * (1 - 2 * DIST_FROM_EDGE) - LINE_WIDTH);

		g2.setColor (Color.black);

		ImageIcon imageIcon = new ImageIcon("Black_Crown_large.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance((int)(width * (1 - 2 * DIST_FROM_EDGE) - LINE_WIDTH), (int)(height * (1 - 2 * DIST_FROM_EDGE) - LINE_WIDTH),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back

		imageIcon.paintIcon(null, g2, (int)(x + width * DIST_FROM_EDGE + LINE_WIDTH / 2),
				 (int)(y + height * DIST_FROM_EDGE + LINE_WIDTH / 2));
		
		
		BasicStroke stroke = new BasicStroke (LINE_WIDTH);
		g2.setStroke(stroke);
		

		if (selected)
		{
			g2.setColor (new Color(255,236,33));
			g2.draw (outline);
		}

	}
}
