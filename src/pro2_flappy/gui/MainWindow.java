package pro2_flappy.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import pro2_flappy.game.GameBoard;
import service.CsvGameBoardLoader;

public class MainWindow extends JFrame {

	BoardPanel pnl = new BoardPanel();
	GameBoard gameBoard;
	long x = 0;

	class BoardPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);

			gameBoard.draw(g);
		}
	}

	public MainWindow() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		CsvGameBoardLoader loader;
		
		try(InputStream is = new FileInputStream("level.csv")){
			loader = new CsvGameBoardLoader(is);
			gameBoard = loader.loadLevel();
		} 
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}

		
		this.add(pnl, BorderLayout.CENTER);
		pnl.setPreferredSize(new Dimension(200, gameBoard.getHeightPix())); // TODO

		pack();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// zavolame metodu kickTheBird
				gameBoard.kickTheBird();
			}
		});
		
		// z balicku Java.Swing - z d�vodu kompatibility se swing oknem
		Timer t = new Timer(20, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				gameBoard.tick(x++);
				pnl.repaint();
			}
		});
		t.start();

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MainWindow mainWindow = new MainWindow();

			mainWindow.setVisible(true);

		});
	}

}
