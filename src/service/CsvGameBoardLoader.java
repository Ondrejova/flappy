package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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
			
			Map<String, Tile> tileTypes = new HashMap<>();
			for (int i = 0; i < typeCount; i++) {
				line = br.readLine().split(";");
				String tileType = line[0];
				String clazz = line[1];
				int tileX = Integer.parseInt(line[2]);
				int tileY = Integer.parseInt(line[3]);
				int tileW = Integer.parseInt(line[4]);
				int tileH = Integer.parseInt(line[5]);
				String source = line[6];
				Tile tile = createTile(clazz, tileX, tileY, tileW, tileH);
				tileTypes.put(tileType, tile);
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
					
				tiles[i][j] = tileTypes.get(cell);
					
				}
			}
			
			GameBoard gb = new GameBoard(tiles);
			
			return gb;

		} catch (IOException e) {
			throw new RuntimeException("Chyba pri cteni souboru", e);
		}

		
	}

	private Tile createTile(String clazz, int tileX, int tileY, int tileW, int tileH) {
		// TODO Auto-generated method stub
		return null;
	}

}
