package game;

public abstract class Collectable extends GameObject {
	private int m_key;
	
	public abstract String getItemName();
	public abstract String getItemText();
	public int getKey() {return m_key;}
	public void setKey(int key) {
		if(m_key==0)
			m_key = key;
	}
}
