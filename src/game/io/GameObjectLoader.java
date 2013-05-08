package game.io;

import game.GameObject;
import game.GameObject.Types;
import game.interactables.Chest;
import game.interactables.ChickenWing;
import game.interactables.Cigarette;
import game.interactables.Door;
import game.interactables.InvisiblePortal;

import org.newdawn.slick.SlickException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class GameObjectLoader {
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws SlickException
	 */
	public static GameObject loadFromNode(Node node) throws SlickException {
		String type = node.getAttributes().getNamedItem("type").getNodeValue();
		int id = Integer.parseInt(node.getAttributes().getNamedItem("m_id").getNodeValue());
		String name = node.getAttributes().getNamedItem("m_name").getNodeValue();
		float x = Float.parseFloat(node.getAttributes().getNamedItem("m_x").getNodeValue());
		float y = Float.parseFloat(node.getAttributes().getNamedItem("m_y").getNodeValue());
		String className = node.getAttributes().getNamedItem("class").getNodeValue();
		Class<GameObject> objectClass = GameObject.class;
		try {
			objectClass = (Class<GameObject>) Class.forName(className);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		/*
		GameObject o = null;
		try {
			o = (GameObject) objectClass.newInstance();
		} catch (InstantiationException e){
			 e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(name);
		o.setName(name);
		o.setX(x);
		o.setY(y);
		//o.getClass().getField("m_id").set(o, id);
		*/
		NamedNodeMap attributes = node.getAttributes();
		
		if(type.equals(Types.CHEST.toString())) {

		} else if(type.equals(Types.CHICKEN_WING.toString())) {
			return new ChickenWing(name, (int) x, (int) y);
		} else if(type.equals(Types.CIGARETTE.toString())) {
			return new Cigarette(name, (int) x, (int) y);
		} else if(type.equals(Types.DOOR.toString())) {
			int m_destination = Integer.parseInt(attributes.getNamedItem("m_destination").getNodeValue());
			int m_xdestination = Integer.parseInt(attributes.getNamedItem("m_xDestination").getNodeValue());
			int m_ydestination = Integer.parseInt(attributes.getNamedItem("m_yDestination").getNodeValue());
			return new Door(name, (int) x, (int) y, m_destination, m_xdestination, m_ydestination);
		} else if(type.equals(Types.DOOR_MAT.toString())) {
			int m_destination = Integer.parseInt(attributes.getNamedItem("m_destination").getNodeValue());
			int m_xdestination = Integer.parseInt(attributes.getNamedItem("m_xDestination").getNodeValue());
			int m_ydestination = Integer.parseInt(attributes.getNamedItem("m_yDestination").getNodeValue());
			return new InvisiblePortal(name, (int) x, (int) y, m_destination, m_xdestination, m_ydestination);
		} else if(type.equals(Types.BED.toString())) {
			
		} else if(type.equals(Types.STATIC.toString())) {
			
		} else if(type.equals(Types.PERSON.toString())) {
			
		} else if(type.equals(Types.ANIMAL.toString())) {
			
		} else if(type.equals(Types.FIRE_HYDRANT.toString())) {
			
		} else if(type.equals(Types.WRENCH.toString())) {
			
		} else if(type.equals(Types.PLUG.toString())) {
			
		} else if(type.equals(Types.SMALL_PLUG.toString())) {
			
		} else if(type.equals(Types.BIG_PLUG.toString())) {
			
		} else if(type.equals(Types.CORRECT_PLUG.toString())) {
			
		} else if(type.equals(Types.BLOW_HOLE.toString())) {
			
		} else if(type.equals(Types.NONE.toString())) {
			
		} else if(type.equals(Types.HORSE.toString())) {
			
		} else if(type.equals(Types.TABLE_TO_HACK.toString())) {
			
		} else if(type.equals(Types.TRASHCAN.toString())) {
			
		} else if(type.equals(Types.VIRTUAL_DOOR.toString())) {
			
		} else if(type.equals(Types.PILL.toString())) {
			
		} else if(type.equals(Types.HOLDER.toString())) {
			
		}
		return o;
	}

}
