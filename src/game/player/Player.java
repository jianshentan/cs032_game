package game.player;

import game.Collectable;
import game.Direction;
import game.Enemy;
import game.MovingObject;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

public class Player extends MovingObject{
	
	private Inventory m_inventory;
	public boolean m_inInventory = false;
	public Inventory getInventory() { return m_inventory; }
	
	private int m_inputDelta = 0;
	private Animation m_up, m_down, m_left, m_right, m_sprite;
	private Direction m_dir;
	private Health m_health;
	private Enemy[] m_enemies;
	public Animation getAnimation() { return m_sprite; }
	public void setAnimation(Animation animation) { m_sprite = animation; }
	public float getX() { return m_x; }
	public void setX(float x) { m_x = x; }
	public float getY() { return m_y; }
	public void setY(float y) { m_y = y; }
	public Health getHealth() { return m_health; }
	
	public Player(GamePlayState game, GameContainer container, float x, float y) throws SlickException {
		super(game);
		m_x = x;
		m_y = y;
		m_enemies = null;
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
        
        // Set up health bar (x-coordinate, y-coordinate, max health)
        m_health = new Health(10,30,50);
        
        m_inventory = new Inventory(container);
	}
	
	public void setGame(GamePlayState game) {
		m_game = game;
	}


	public void playerControls(GameContainer container, int delta, Input input) {
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

//        	m_room.interact(squareFacing);
        	Interactable interactable = m_game.interact(squareFacing);
        	if (interactable instanceof Collectable)
        		m_inventory.addItem((Collectable) interactable);
        	m_inputDelta=500;

        }

	}
	
	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		m_inputDelta-=delta;	
		Boolean setDelta = false;
		if(m_inputDelta<0&&m_enemies!=null){
			for(Enemy e: m_enemies){
				if(checkCollision(this, e)){
					m_health.updateHealth(-5);
					setDelta = true;
				}
			}
		}
		// The lower the delta the slowest the sprite will animate.
        if (m_inputDelta<0&&input.isKeyDown(Input.KEY_I)) {
        	m_inInventory = m_inInventory ? false : true;
        	setDelta=true;
        }
        if(setDelta){
        	m_inputDelta=500;
        }
		
		if (m_inInventory) 
			m_inventory.update(container, delta);
		else 
			playerControls(container, delta, input);
		
	}
	//sets the enemies that collisions need to be checked against
	public void setEnemies(Enemy[] e){
		m_enemies = e;
	}
	
	/**
	 * Returns the collectable currently being used.
	 * @return
	 */
	public Collectable getUsing() {
		return this.m_inventory.getUsing();
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
		
		writer.writeAttribute("health", String.valueOf(this.m_health.getCurrentHealth()));
		this.m_inventory.writeToXML(writer);		
		
		writer.writeEndElement();
	}
	//had to comment this out, since m_enemies was added to constructor - Zak
	
	public static Player loadFromNode(Node node, GamePlayState room, GameContainer container) throws SlickException {
		float xLoc = Float.parseFloat(node.getAttributes().getNamedItem("m_x").getNodeValue());
		float yLoc = Float.parseFloat(node.getAttributes().getNamedItem("m_y").getNodeValue());
		
		Player p = new Player(room, container, xLoc, yLoc);
		//TODO: load inventory and health
		return p;
	}
	
}
