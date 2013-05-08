package game.gameplayStates;

import game.StaticObject;
import game.interactables.Interactable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
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
		
		// buildings
		StaticObject building01 = new StaticObject("building01",
			14*SIZE, 18*SIZE, "assets/town/building01.png");	
		building01.setRenderPriority(6);
		this.addObject(building01, false);
		
		StaticObject building01TopA = new StaticObject("buildingWalkThrough01A",
			14*SIZE, 18*SIZE, "assets/town/building_walkthrough01A.png");
		building01TopA.setRenderPriority(6);
		this.addObject(building01TopA, false);
		
		StaticObject building01TopB = new StaticObject("buildingWalkThrough01B",
				17*SIZE, 21*SIZE, "assets/town/building_walkthrough01B.png");
		building01TopB.setRenderPriority(6);
		this.addObject(building01TopB, false);
		
		StaticObject building02 = new StaticObject("building02",
			18*SIZE, 17*SIZE, "assets/town/building02.png");
		building02.setRenderPriority(6);
		this.addObject(building02, false);
		
		StaticObject building02Top = new StaticObject("building02Top" ,
				18*SIZE+4, 17*SIZE, "assets/town/building02Top.png");
		building02Top.setRenderPriority(6);
		this.addObject(building02Top, false);
		
		StaticObject building03 = new StaticObject("building03",
				14*SIZE, 13*SIZE, "assets/town/building03.png");
		building03.setRenderPriority(6);
		this.addObject(building03, false);
		
		StaticObject building03Top = new StaticObject("building03Top",
				14*SIZE+4, 13*SIZE, "assets/town/building03Top.png");
		building03Top.setRenderPriority(6);
		this.addObject(building03Top, false);
		
		StaticObject building04 = new StaticObject("building04" ,
				21*SIZE, 15*SIZE, "assets/town/building04.png");
		building04.setRenderPriority(7);
		this.addObject(building04, false);
		
		StaticObject building04Top = new StaticObject("building04Top",
				21*SIZE, 15*SIZE, "assets/town/building04Top.png");
		building04Top.setRenderPriority(7);
		this.addObject(building04Top, false);
		
		
		StaticObject doormat = new StaticObject("doormat",11*SIZE, 28*SIZE, "assets/gameObjects/doormat.png");
		this.addObject(doormat, false);
		
		//hospital stuff
		StaticObject hospital = new StaticObject("hospital", 4*SIZE, 17*SIZE, "assets/town/hospital.png");
		hospital.setRenderPriority(6);
		this.addObject(hospital, false);
		
		StaticObject hospitalTop = new StaticObject("hospitalTop", 4*SIZE+4, 17*SIZE, "assets/town/hospitalTop.png");
		hospitalTop.setRenderPriority(6);
		this.addObject(hospitalTop, false);
		
		
		StaticObject hospitalTower = new StaticObject("hospitalTower", 2*SIZE, 15*SIZE, 
				"assets/town/hospitalTower.png");
		this.addObject(hospitalTower, false);
		hospitalTower.setRenderPriority(5);
		StaticObject hospitalTowerTop = new StaticObject("hospitalTowerTop", 2*SIZE+4, 15*SIZE, 
				"assets/town/hospitalTowerTop.png");
		hospitalTowerTop.setRenderPriority(5);
		this.addObject(hospitalTowerTop, false);
		
		// zoo
		StaticObject zoo = new StaticObject("zooBuilding", 7*SIZE, 11*SIZE, "assets/town/zoo.png");
		this.addObject(zoo, false);
		zoo.setRenderPriority(5);
		
		
		// random houses
		StaticObject randomHouse01 = new StaticObject("randomHouse1", 9*SIZE, 5*SIZE, 
				"assets/town/smallRandomHouse01.png");
		randomHouse01.setRenderPriority(5);
		this.addObject(randomHouse01, false);
		
		StaticObject randomHouse02 = new StaticObject("randomHouse2", 5*SIZE, 5*SIZE, 
				"assets/town/smallRandomHouse02.png");
		randomHouse02.setRenderPriority(5);
		this.addObject(randomHouse02, false);
		
		StaticObject tree1 = new StaticObject("tree1", 11*SIZE, 18*SIZE, "assets/gameObjects/tree_normal.png");
		tree1.setRenderPriority(6);
		this.addObject(tree1, false);
		
		m_blocked[11][20] = true;
		m_blocked[12][20] = true;
		
		StaticObject tree2 = new StaticObject("tree2", 19*SIZE, 5*SIZE, "assets/gameObjects/tree_normal.png");
		tree2.setRenderPriority(6);
		this.addObject(tree2, false);
		
		m_blocked[19][7] = true;
		m_blocked[20][7] = true;
		
		StaticObject tree3 = new StaticObject("tree3", 5*SIZE, 24*SIZE, "assets/gameObjects/tree_normal.png");
		tree3.setRenderPriority(6);
		this.addObject(tree3,false);
		m_blocked[5][26] = true;
		m_blocked[6][26] = true;
		
		StaticObject tree4 = new StaticObject("tree4", 8*SIZE, 24*SIZE, "assets/gameObjects/tree_normal.png");
		tree4.setRenderPriority(6);
		this.addObject(tree4, false);
		m_blocked[8][26] = true;
		m_blocked[9][26] = true;
		
		StaticObject tree5 = new StaticObject("tree5", 1*SIZE, 22*SIZE, "assets/gameObjects/tree_normal.png");
		tree5.setRenderPriority(6);
		this.addObject(tree5, false);
		m_blocked[1][24] = true;
		m_blocked[2][24] = true;
		
		StaticObject sign1 = new StaticObject("sign1", 7*SIZE, 13*SIZE, "assets/gameObjects/sign.png");
		sign1.setDialogue(new String[] {"\"Horse Stables (formerly the zoo)\""});
		sign1.setRenderPriority(7);
		this.addObject(sign1, true);
		
		StaticObject sign2 = new StaticObject("sign2", 4*SIZE, 21*SIZE, "assets/gameObjects/sign.png");
		sign2.setDialogue(new String[] {"\"Hospital\""});
		sign2.setRenderPriority(7);
		this.addObject(sign2, true);
		
		StaticObject fireHydrant = new StaticObject("fireHydrant", 6*SIZE, 23*SIZE, "assets/firehydrant.png");
		this.addObject(fireHydrant, true);
		m_blocked[6][23] = true;
		
		int[][] flowerpatches = {{5,9},{5,10},{5,11},{5,12},{5,13},
				{6,9},{7,9},{8,9},{9,9},{10,9},
				{11,12},{11,13},{11,14},{11,15},
				{6,15},{7,15},{8,15},{9,15},{10,15},
				{1,25},{4,23},{5,23},{5,24},{5,25},{4,24},{4,25},
				{11,24},{12,24},
				{11,26},{12,26},{13,26},
				{19,25},{19,27}};
				
		int flowerlength = flowerpatches.length;
		for (int i=0; i<flowerlength; i++){
			int xflowcoord = flowerpatches[i][0];
			int yflowcoord = flowerpatches[i][1];
			StaticObject flower = new StaticObject("flower"+i,xflowcoord*SIZE, yflowcoord*SIZE, "assets/flowers.png");
			flower.setRenderPriority(false);
			this.addObject(flower, false);
			m_blocked[xflowcoord][yflowcoord] = true;
		}
		
		
		if (city == 4) {
			
		}
		else if (city == 3) {
			
		}
		else if (city == 2) {
			
		}
		else if (city == 1) {
			
		}
		else if (city == 0) {
			
		}
		
		if (city < 4) {
			for (int i=1; i<=4; i++) {
				StaticObject tree = (StaticObject)this.getObject("tree"+i);
				tree.setSprite(new Image("assets/gameObjects/treebroken.png"));
				tree.setRenderPriority(0);
			}

			
			this.getObject("fireHydrant").setSprite(new Image("assets/gameObjects/firehydrantbroken.png"));
			
			m_blocked[19][7] = false;
			m_blocked[20][7] = false;
			m_blocked[11][20] = false;
			m_blocked[12][20] = false;
			m_blocked[5][26] = false;
			m_blocked[6][26] = false;
			m_blocked[8][26] = false;
			m_blocked[9][26] = false;
			m_blocked[1][24] = false;
			m_blocked[2][24] = false;

			for (int i=0; i<flowerlength; i++){
				int xflowcoord = flowerpatches[i][0];
				int yflowcoord = flowerpatches[i][1];
				this.removeObject("flower"+i);
				StaticObject flower = new StaticObject("flower"+i,xflowcoord*SIZE, yflowcoord*SIZE, "assets/flowersdead.png");
				this.addObject(flower, false);
				m_blocked[xflowcoord][yflowcoord] = false;
			}
		}
		
		// draw all door images (not necessarily functionlity);
		StaticObject hospitalDoor = new StaticObject("hospitalDoor", 5*SIZE, 21*SIZE,  "assets/gameObjects/door.png"); 
		this.addObject(hospitalDoor, false);
		hospitalDoor.setRenderPriority(8);
		StaticObject dolphinDoor = new StaticObject("dolphinDoor", 8*SIZE, 13*SIZE,  "assets/gameObjects/door.png"); 
		this.addObject(dolphinDoor, false);
		dolphinDoor.setRenderPriority(8);
		StaticObject virtualRealityDoor = new StaticObject("virtualRealityDoor", 22*SIZE, 17*SIZE,  "assets/gameObjects/door.png"); 
		this.addObject(virtualRealityDoor, false);
		virtualRealityDoor.setRenderPriority(8);
		
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
