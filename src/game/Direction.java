package game;

public enum Direction {
	UP,
	DOWN,
	LEFT,
	RIGHT;
	public static int[] getDirOffsets(Direction dir){
		switch(dir){
			case UP:{
				int[] dirs = {0,-1};
				return dirs;
			}
			case DOWN:{
				int[] dirs = {0,1};
				return dirs;
			}
			case LEFT:{
				int[] dirs = {-1,0};
				return dirs;
			}
			case RIGHT:{
				int[] dirs = {1,0};
				return dirs;
			}default :{
				System.out.println("ERROR: DIRECTION " + dir + " SHOULD NOT EXIST");
				return null;
			}
		}
	}


}
