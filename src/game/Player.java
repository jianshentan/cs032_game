package game;



import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

public class Player extends MovingObject{
	
	private Room m_room;
	private Inventory m_inventory;
	
	private int m_inputDelta = 0;
	private Animation m_up, m_down, m_left, m_right, m_sprite;
	private Direction m_dir;
	public Animation getAnimation() { return m_sprite; }
	public void setAnimation(Animation animation) { m_sprite = animation; }
	
	
	public Player(Room room, GameContainer container, float x, float y) throws SlickException {
		super(room);
		m_room = room;
		m_x = x;
		m_y = y;
		
		Image [] movementUp = {new Image("assets/Sprite1Back.png"), new Image("assets/Sprite1Back.png")};
        Image [] movementDown = {new Image("assets/Sprite1Front.png"), new Image("assets/Sprite1Front.png")};
        Image [] movementLeft = {new Image("assets/Sprite1Left.png"), new Image("assets/Sprite1Left.png")};
        Image [] movementRight = {new Image("assets/Sprite1Right.png"), new Image("assets/Sprite1Right.png")};
        int [] duration = {300, 300}; 
        /*
         * false variable means do not auto update the animation.
         * By setting it to false animation will update only when
         * the user presses a key.
         */
        m_up = new Animation(movementUp, duration, false);
        m_down = new Animation(movementDown, duration, false);
        m_left = new Animation(movementLeft, duration, false);
        m_right = new Animation(movementRight, duration, false);	
        
        // Original orientation of the sprite. It will look right.
        m_sprite = m_right;
        m_dir = Direction.RIGHT;
        
        m_inventory = new Inventory(container);
	}
	
	public void update(GameContainer container, int delta) {
		// The lower the delta the slowest the sprite will animate.
		Input input = container.getInput();
		m_inputDelta-=delta;
        if (input.isKeyDown(Input.KEY_UP)) {
        	m_sprite = m_up;
        	m_dir = Direction.UP;
            if (!isBlocked(m_x, m_y - delta * 0.1f, Direction.UP)) {
            	m_sprite.update(delta);
            	m_y -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN)) {
            m_sprite = m_down;
            m_dir = Direction.DOWN;
            if (!isBlocked(m_x, m_y + SIZE + delta * 0.1f, Direction.DOWN)) {
                m_sprite.update(delta);
                m_y += delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT)) {
            m_sprite = m_left;
            m_dir = Direction.LEFT;
            if (!isBlocked(m_x - delta * 0.1f, m_y, Direction.LEFT)) {
                m_sprite.update(delta);
                m_x -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {
            m_sprite = m_right;
            m_dir = Direction.RIGHT;
            if (!isBlocked(m_x + SIZE + delta * 0.1f, m_y, Direction.RIGHT)) {
                m_sprite.update(delta);
                m_x += delta * 0.1f;
            }
        }
        if(m_inputDelta<0&&input.isKeyDown(Input.KEY_SPACE)){
        	int currentX = (int) (m_x + (SIZE/2))/SIZE;
        	int currentY = (int) (m_y + (SIZE/2))/SIZE;
        	int[] dirOffset = Direction.getDirOffsets(m_dir);
        	int[] squareFacing = {currentX + dirOffset[0], currentY + dirOffset[1]};
        	m_room.interact(squareFacing);
        	m_inputDelta=500;
        }
        if (m_inputDelta<0&&input.isKeyDown(Input.KEY_I)) {
        	//open inventory
        }
		
	}
	
	/**
	 * Writes data needed to reconstruct the player.
	 * @param writer
	 * @throws XMLStreamException
	 */
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("Player");
		writer.writeAttribute("m_x", String.valueOf(m_x));
		writer.writeAttribute("m_y", String.valueOf(m_y));
		
		writer.writeStartElement("Inventory");
		writer.writeEndElement();
		
		writer.writeEndElement();
	}
	
	public static Player loadFromNode(Node node, Room room) throws SlickException {
		float xLoc = Float.parseFloat(node.getAttributes().getNamedItem("m_x").getNodeValue());
		float yLoc = Float.parseFloat(node.getAttributes().getNamedItem("m_y").getNodeValue());
		return new Player(room,/*TODO*/null, xLoc, yLoc);
	}
	
}
