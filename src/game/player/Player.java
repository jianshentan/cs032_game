package game.player;

import game.Collectable;
import game.Direction;
import game.Enemy;
import game.GameObject;
import game.MovingObject;
import game.animation.SmokeEmitter;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.w3c.dom.Node;

public class Player extends MovingObject{
	
	private Inventory m_inventory;
	public boolean m_inInventory = false;
	public Inventory getInventory() { return m_inventory; }
	public boolean m_usingItem = false;
	
	private int m_inputDelta = 0;
	private Animation m_up, m_down, m_left, m_right, m_sprite, m_up_stand, m_down_stand, m_left_stand, m_right_stand, m_traumaLeft, m_traumaRight, m_traumaUp, m_traumaDown;
	private Direction m_dir;
	private Health m_health;
	private Enemy[] m_enemies;
	
	//The currently equipped item
	private Collectable m_currentItem;

	//stuff for implementing scenes - scripted movements
	private int[][] m_patrolPoints;
	private int[] m_currentSquare, m_destination;
	private AStarPathFinder m_finder;
	private Path m_path;
	private int m_pathLength, m_currentStep, m_roamCounter;
	private boolean m_sceneMode; //this is true if the object is in scene mode.
	public boolean getSceneMode() { return this.m_sceneMode; }
	public void setSceneMode(boolean mode) { this.m_sceneMode = mode; }
	
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
		SpriteSheet spritesheet = new SpriteSheet("assets/characters/player_standing.png", 64,64);
		Image [] standingUp = {spritesheet.getSprite(1,0), spritesheet.getSprite(1,0)};
        Image [] standingDown = {spritesheet.getSprite(0,0), spritesheet.getSprite(0,0)};
        Image [] standingLeft = {spritesheet.getSprite(3,0), spritesheet.getSprite(3,0)};
        Image [] standingRight = {spritesheet.getSprite(2,0), spritesheet.getSprite(2,0)};
        
        Image [] movementUp = {spritesheet.getSprite(1,1), spritesheet.getSprite(1,2)};
        Image [] movementDown = {spritesheet.getSprite(0,1), spritesheet.getSprite(0,2)};
        Image [] movementLeft = {spritesheet.getSprite(3,1), spritesheet.getSprite(3,2)};
        Image [] movementRight = {spritesheet.getSprite(2,1), spritesheet.getSprite(2,2)};
		
		
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
        m_up_stand = new Animation(standingUp, duration, false);
        m_down_stand = new Animation(standingDown, duration, false);
        m_left_stand = new Animation(standingLeft, duration,false);
        m_right_stand = new Animation(standingRight,duration,false);	
        
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
        else {
        	switch (m_dir) {
        	case UP:
        		m_sprite = m_up_stand;
        		break;
        	case DOWN:
        		m_sprite = m_down_stand;
        		break;
        	case LEFT:
        		m_sprite = m_left_stand;
        		break;
        	case RIGHT:
        		m_sprite = m_right_stand;
        		break;
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
		if(this.getSceneMode()) {
			this.updateScene(delta);
			return;
		}
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
		//updates used item
		if(getUsing()!=null && (m_currentItem==null || m_currentItem != getUsing() ) ) {
			if(m_currentItem != null && m_currentItem != getUsing()) {
				m_currentItem.onStopUse(this, m_game);
			}
			this.m_currentItem = this.getUsing();
			m_currentItem.onUse(this, m_game);
			//If the item has a message to be displayed:
			if(m_currentItem.useDialogue()!=null) {
				m_game.enterScene();
				m_game.displayDialogue(m_currentItem.useDialogue());
				//this.m_inInventory = false;
			}
			if(m_currentItem.isConsumable()) {
				this.m_inventory.removeItem(getUsing());
				m_currentItem = null;
			}
		}

		if (m_inventory.getCurrItem() != null) {
			m_usingItem = true;
			m_inventory.getCurrItem().update(delta);
		}
		else
			m_usingItem = false;
	}
	
	public void renderItem(GameContainer container, StateBasedGame stateManager, Graphics g) {
		m_inventory.getCurrItem().render(container, stateManager, g);
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
		return this.m_inventory.getCurrItem();
	}
	
	/**
	 * Makes this MovingObject follow a certain path.
	 * @param room
	 * @param patrolPoints
	 */
	public void enterScene(GamePlayState room, int[][] patrolPoints) {
		m_sceneMode = true;
		m_game = room;
		m_currentSquare = new int[2];
		m_destination = new int[2];
		m_currentSquare[0] =(int) (m_x/SIZE);
		m_currentSquare[1] = (int) (m_y/SIZE);
		m_patrolPoints = patrolPoints;
		m_currentStep=0;
		m_finder = new AStarPathFinder(room.getMap(), 50, false);
		this.patrolUpdate();
		//System.out.println(m_pathLength);
	}
	
	/**
	 * This is used for "updating" when the player is in a scene.
	 * @param delta
	 */
	public void updateScene(int delta){
		//System.out.println(m_path);
		if(m_sceneMode){
			//when you've moved a square
			if(Math.abs(m_x/64-m_currentSquare[0])>=1||Math.abs(m_y/64-m_currentSquare[1])>=1){
				//the new current is now the old destination
				setCurrent(m_currentStep);
				m_currentStep+=1;
				if(m_currentStep>=m_pathLength){
					m_sceneMode = false;
					this.patrolUpdate();
					if(!m_sceneMode)
						m_game.exitScene();
				}else{
					//System.out.println(m_currentStep + " "  + m_pathLength);
					setDestination();
				}
			}
			int x = m_destination[0]-m_currentSquare[0];
			int y = m_destination[1]-m_currentSquare[1];
			if(x<0) {
				m_dir = Direction.LEFT;
				m_sprite = m_left;
			} else if(x>0) {
				m_dir = Direction.RIGHT;
				m_sprite = m_right;
			} else if(y<0) {
				m_dir = Direction.UP;
				m_sprite = m_up;
			} else if(y>0) {
				m_dir = Direction.DOWN;
				m_sprite = m_down;
			}
			m_sprite.update(delta);
			m_x+= x * delta*0.1f;
			m_y+= y * delta*0.1f;
		}
	}
	private void setDestination(){
		m_destination[0] = m_path.getX(m_currentStep);
		m_destination[1] = m_path.getY(m_currentStep);
		//updateSprite();
	}
	private void setCurrent( int i){
		m_currentSquare[0] = m_path.getX(i);
		m_currentSquare[1] = m_path.getY(i);
	}
	private void patrolUpdate(){
		//finds the path from current point to next point in the roam list
		if(m_roamCounter>=m_patrolPoints.length){
			return;
		}
		int xDest = m_patrolPoints[m_roamCounter][0];
		int yDest = m_patrolPoints[m_roamCounter][1];
		if(xDest==m_currentSquare[0]&&yDest==m_currentSquare[1]){
			m_roamCounter+=1;
			patrolUpdate();
		}else{
			m_path = m_finder.findPath(null, m_currentSquare[0], m_currentSquare[1], xDest, yDest );
			m_pathLength = m_path.getLength();
			m_currentStep=1;
			setDestination();
			m_sceneMode=true;
		}
	}
	
	public Direction getDirection() {
		return m_dir;
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
