package csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import terrain.Terrain;

public class CSVwriter {
	
	private static final String DELIM = ";";
	
	private static Random random = new Random();
	
	public static void randomInitCSV(Terrain terrain){
		try {
			
			File file = new File("Resources/res/scenes/forest/entities/entityData.csv");
			if (file.exists()){
				file.delete();
			}
			
			FileWriter writer = new FileWriter("Resources/res/scenes/forest/entities/entityData.csv", false);
			writer.append("entity id;model id;pos.x;pos.y;pos.z;rot.x;rot.y;rot.z;scale;atlas dim;tex idx;fake light;transparent;"
						+ "light offset;col.x;col.y;col.z;att.x;att.y;att.z\n");

			// pine
			int model_id = 0;
			int entity_id = 0;
			for (int i = 0; i < 1600; i++){
				float x = random.nextFloat() * Terrain.SIZE;
				float z = random.nextFloat() * Terrain.SIZE;
				float y = terrain.getHeightOfTerrain(x, z);
				if (y < -8){
					continue;
				}
				float rx = 0;
				float ry = random.nextFloat() * 360;
				float rz = 0;
				float scale = 1;
				int atlasDim = 1;
				int texIndex = 0;
				int fakeLight = 0;
				int transparent = 0;
				writer.append(entity_id+DELIM+model_id+DELIM+x+DELIM+y+DELIM+z+DELIM+rx+DELIM+ry+DELIM+rz+DELIM+scale+
							DELIM+atlasDim+DELIM+texIndex+DELIM+fakeLight+DELIM+transparent+"\n");
				entity_id++;
			}
			model_id++;
			
			// cherry
			for (int i = 0; i < 400; i++){
				float x = random.nextFloat() * Terrain.SIZE;
				float z = random.nextFloat() * Terrain.SIZE;
				float y = terrain.getHeightOfTerrain(x, z);
				if (y < -8){
					continue;
				}
				float rx = 0;
				float ry = random.nextFloat() * 360;
				float rz = 0;
				float scale = 2;
				int atlasDim = 1;
				int texIndex = 0;
				int fakeLight = 0;
				int transparent = 0;
				writer.append(entity_id+DELIM+model_id+DELIM+x+DELIM+y+DELIM+z+DELIM+rx+DELIM+ry+DELIM+rz+DELIM+scale+
						DELIM+atlasDim+DELIM+texIndex+DELIM+fakeLight+DELIM+transparent+"\n");			
				entity_id++;
			}
			model_id++;
			
			// fern
			for (int i = 0; i < 800; i++){
				float x = random.nextFloat() * Terrain.SIZE;
				float z = random.nextFloat() * Terrain.SIZE;
				float y = terrain.getHeightOfTerrain(x, z);
				if (y < -8){
					continue;
				}
				float rx = 0;
				float ry = random.nextFloat() * 360;
				float rz = 0;
				float scale = 1;
				int atlasDim = 2;
				int texIndex = random.nextInt(4);
				int fakeLight = 0;
				int transparent = 0;
				writer.append(entity_id+DELIM+model_id+DELIM+x+DELIM+y+DELIM+z+DELIM+rx+DELIM+ry+DELIM+rz+DELIM+scale+
						DELIM+atlasDim+DELIM+texIndex+DELIM+fakeLight+DELIM+transparent+"\n");			
				entity_id++;
			}
			model_id++;
			model_id++;
			
			// lamp
			for (int i = 0; i < 50; i++){
				float x = random.nextFloat() * Terrain.SIZE;
				float z = random.nextFloat() * Terrain.SIZE;
				float y = terrain.getHeightOfTerrain(x, z);
				if (y < -8){
					continue;
				}
				float rx = 0;
				float ry = random.nextFloat() * 360;
				float rz = 0;
				float scale = 1;
				int atlasDim = 1;
				int texIndex = 0;
				int fakeLight = 1;
				int transparent = 0;
				float offset = 19f;
				float cx = 4*random.nextFloat();
				float cy = 3*random.nextFloat();
				float cz = 3*random.nextFloat();
				float ax = 1;
				float ay = 0.01f;
				float az = 0.001f;
				writer.append(entity_id+DELIM+model_id+DELIM+x+DELIM+y+DELIM+z+DELIM+rx+DELIM+ry+DELIM+rz+DELIM+scale+DELIM+atlasDim+
								DELIM+texIndex+DELIM+fakeLight+DELIM+transparent+DELIM+offset+DELIM+cx+DELIM+cy+DELIM+cz+DELIM+ax+DELIM+ay+DELIM+az+"\n");
				entity_id++;
			}
			model_id++;

			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("Could not generate CSV file.");
			e.printStackTrace();
		}

		
	}

}
