package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;

public abstract class GameObject {
	
	private Image m_sprite;
	public static final int SIZE = 64;
	protected float m_x, m_y;
	protected String m_name; //name of the object
	private final int m_id;
	
	public float getX() {return m_x;}
	public float getY() {return m_y;}
	public void setX(float x) { m_x = x; }
	public void setY(float y) { m_y = y; }
	public Image getImage() {return getSprite();}
	protected boolean m_renderAfter = false;
	public void setRenderPriority(boolean s) {m_renderAfter = s;}
	public boolean renderAfter() {return m_renderAfter;}
	
	/**
	 * This automatically sets the ID, and adds the object to the
	 * global table.
	 */
	public GameObject() {
		m_id = StateManager.getKey();
		StateManager.addObject(m_id, this);
	}
	
	/**
	 * Initializes the GameObject with a name.
	 * @param name
	 */
	public GameObject(String name) {
		m_id = StateManager.getKey();
		StateManager.addObject(m_id, this);
		m_name = name;
	}
	
	/**
	 * Returns the key of the object in the global table.
	 * This is guaranteed to be globally unique.
	 * @return int key
	 */
	public final int getKey() {
		return m_id;
	}
	
	/**
	 * Returns the name of the object.
	 * Name is guaranteed to be unique within a GameState.
	 * @return
	 */
	public final String getName() {
		return m_name;
	}
	
	/**
	 * Sets the name of the object.
	 * @param s
	 */
	public final void setName(String s) {
		m_name = s;
	}
	
//	public abstract int[] getSquare();
	public abstract Types getType();
	
	public Image getSprite() {
		return m_sprite;
	}
	public void setSprite(Image m_sprite) {
		this.m_sprite = m_sprite;
	}

	/**
	 * Returns the position of the object on the tile map,
	 * in tiled coordinates. 
	 * @return [x, y] in tiled coordinates
	 */
	public int[] getSquare() {
		int[] loc = {(int)m_x/SIZE, (int)m_y/SIZE};
		return loc;
	}
	
	/**
	 * By default, two GameObjects of the same type are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if(o.getClass() == this.getClass()) {
			GameObject o1 = (GameObject) o;
			return o1.getType() == this.getType();
		}
		return false;
	}
	
	/**
	 * Returns true if the object blocks.
	 * @return
	 */
	public boolean isBlocking() {
		return true;
	}
	
	public enum Types {
		CHEST,
		CHICKEN_WING,
		CIGARETTE,
		DOOR,
		DOOR_MAT,
		BED,
		STATIC,
		PERSON,
		ANIMAL,
		FIRE_HYDRANT,
		WRENCH,
		PLUG,
		SMALL_PLUG,
		BIG_PLUG,
		CORRECT_PLUG, 
		BLOW_HOLE,
		HORSE,
		TABLE_TO_HACK,
		TRASHCAN;
	}
	
	/**
	 * Writes stuff to xml
	 * @param writer
	 * @throws XMLStreamException
	 */
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		//TODO- what needs to be written: position- x, y
		//type, id?
		//needs subclasses to write additional attributes
		//References- save these as actual objects
	}
}
