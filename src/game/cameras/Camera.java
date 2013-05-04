package game.cameras;

public abstract class Camera {
	
	public Camera(){
		
	}
	public abstract int[] getOffset();
	public abstract int[] getPlayerOffset();
	public abstract void shake(int shakeL);
}
