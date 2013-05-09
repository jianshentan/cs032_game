package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;

public abstract class GameObject {
	
	private Image m_sprite;
	public static final int SIZE = 64;
	
	/**
	 * Render priority of 5 & above are rendered after the player
	 */
	public static final int MAX_RENDER_PRIORITY = 10;
	protected float m_x, m_y;
	protected String m_name; //name of the object
	private final int m_id;
	private int m_renderPriority;
	
	public float getX() {return m_x;}
	public float getY() {return m_y;}
	public void setX(float x) { m_x = x; }
	public void setY(float y) { m_y = y; }
	public Image getImage() {return getSprite();}
	protected boolean m_renderAfter = false;
	@Deprecated
	public void setRenderPriority(boolean s) {m_renderAfter = s;}
	public boolean renderAfter() {return m_renderAfter;}
	/**
	 * Render priority describes the order objects are rendered in the 
	 * GamePlayState. Higher priorities are rendered later.
	 * @param i - render priority to use.
	 */
	public void setRenderPriority(int i) { 
		if(i<=MAX_RENDER_PRIORITY)
			m_renderPriority = i; 
	}
	/**
	 * Returns the render priority- 0 is default.
	 * @return
	 */
	public int getRenderPriority() { return m_renderPriority; }
	
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
	 * By default, two GameObjects of the same name are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if(o.getClass() == this.getClass()) {
			GameObject o1 = (GameObject) o;
			return o1.getKey() == this.getKey();
		}
		return false;
	}
	
	public int hashCode() {
		return this.m_id;
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
		NONE,
		HORSE,
		TABLE_TO_HACK,
		TRASHCAN, 
		VIRTUAL_DOOR,
		PILL,
		HOLDER, 
		REALITY_EXECUTABLE, 
		OUTSIDE_TRASHCAN,
		VIRTUAL_TRASHCAN, 
		BANANA, 
		HAIR_VIAL, 
		TRADER_COUNTER, 
		HORSE_MEME;
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
		writer.writeStartElement("GameObject");
		writer.writeAttribute("class", this.getClass().getName());
		writer.writeAttribute("m_id", String.valueOf(this.getKey()));
		writer.writeAttribute("m_name", this.m_name);
		if(this.getType()!=null)
			writer.writeAttribute("type", this.getType().toString());
		writer.writeAttribute("m_x", String.valueOf(this.m_x));
		writer.writeAttribute("m_y", String.valueOf(this.m_y));
		writer.writeAttribute("m_renderPriority", String.valueOf(m_renderPriority));
		
		this.writeAttributes(writer);
		
		writer.writeEndElement();
	}
	
	/**
	 * Override this to write additional attributes.
	 * No need to write start/end element tags, stuff in GameObject.
	 * @param writer
	 * @throws XMLStreamException
	 */
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		
	}
}
