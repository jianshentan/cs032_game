package game.gameplayStates;

import game.StaticObject;
import game.interactables.Interactable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public abstract class Town extends GamePlayState {

	public Town() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		// Generate gameobjects that will always appear in both TownNight and TownDay here
		StaticObject homeBuilding = new StaticObject("home_building", 
				10*SIZE, 29*SIZE, "assets/town/home_building.png");
		this.addObject(homeBuilding, false);
		m_blocked[10][29] = true;
		m_blocked[11][29] = true;
		m_blocked[12][29] = true;
		
		StaticObject building01 = new StaticObject("building01",
			14*SIZE, 18*SIZE, "assets/town/building01.png");	
		this.addObject(building01, false);
		StaticObject buildingWalkThrough01A = new StaticObject("buildingWalkThrough01A",
			14*SIZE, 18*SIZE, "assets/town/building_walkthrough01A.png");
		this.addObject(buildingWalkThrough01A, false);
		buildingWalkThrough01A.setRenderPriority(true);
		StaticObject buildingWalkThrough01B = new StaticObject("buildingWalkThrough01B",
				17*SIZE, 21*SIZE, "assets/town/building_walkthrough01B.png");
		this.addObject(buildingWalkThrough01B, false);
		buildingWalkThrough01B.setRenderPriority(true);
		
		StaticObject doormat = new StaticObject("doormat",11*SIZE, 28*SIZE, "assets/gameObjects/doormat.png");
		this.addObject(doormat, false);
		
		// townDay and townNight will call this
		additionalSetupObjects(city, dream);
	}
	
	public abstract void additionalSetupObjects(int city, int dream) throws SlickException;

	@Override
	public void setupDialogue(GameContainer container, int city, int dream)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void dialogueListener(Interactable i) {
		// TODO Auto-generated method stub

	}

}
