package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pro2_flappy.game.GameBoard;
import pro2_flappy.game.Tile;
import pro2_flappy.game.tiles.WallTile;

public class CsvGameBoardLoader implements GameBoardLoader {

	private InputStream inputStream;

	public CsvGameBoardLoader(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public GameBoard loadLevel() {


		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			
			String[] line = br.readLine().split(";");
			int typeCount = Integer.parseInt(line[0]);

			for (int i = 0; i < typeCount; i++) {
				// preskocime nacitani definic dlazdic
				br.readLine();
			}
			
			line = br.readLine().split(";");
			int rowCount = Integer.parseInt(line[0]);
			int colCount = Integer.parseInt(line[1]);
			
			Tile[][] tiles = new Tile[rowCount][colCount];
			
			System.out.println(rowCount + "," + colCount);
			for (int i = 0; i < rowCount; i++){
				line = br.readLine().split(";");
				for (int j = 0; j < colCount; j++){
					String cell = (j<line.length) ? line[j] : "";
					if (!"".equals(cell)){
						tiles[i][j] = new WallTile();
					}
				}
			}
			
			GameBoard gb = new GameBoard(tiles);
			
			return gb;

		} catch (IOException e) {
			throw new RuntimeException("Chyba pri cteni souboru", e);
		}

		
	}

}
