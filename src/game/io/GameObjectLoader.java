package game.io;

import game.GameObject;
import game.GameObject.Types;
import game.interactables.Chest;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

public class GameObjectLoader {
	
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
		GameObject o = null;
		try {
			o = (GameObject) objectClass.newInstance();
		} catch (InstantiationException e){
			 
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		o.setName(name);
		o.setX(x);
		o.setY(y);
		
		if(type.equals(Types.CHEST.toString())) {

		} else if(type.equals(Types.CHICKEN_WING.toString())) {
			//return ChickenWing.loadFromNode(node);
		} else if(type.equals(Types.CIGARETTE.toString())) {
			
		} else if(type.equals(Types.DOOR.toString())) {
			
		} else if(type.equals(Types.DOOR_MAT.toString())) {
			
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
