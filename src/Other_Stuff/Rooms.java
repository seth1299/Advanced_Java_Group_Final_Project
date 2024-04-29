package Other_Stuff;

import java.util.List;

public class Room {
	
	private int roomNum;
	private String roomName;
	private int exitN;
	private int exitNW;
	private int exitNE;
	private int exitW;
	private int exitE;
	private int exitSW;
	private int exitS;
	private int exitSE;
	private int exitUP;
	private int exitDN;
	private int locked;
	private String roomItem1;
	private String roomItem2;
	private String roomItem3;
	private String roomItem4;
	private String roomItem5;
	private String enemy;
	private String roomDescription;

	//Default constructor with garbarge values
	public Room() {
		int roomNum = 0;
		String roomName = "";
		int exitN = 0;
		int exitNW = 0;
		int exitNE = 0;
		int exitW = 0;
		int exitE = 0;
		int exitSW = 0;
		int exitS = 0;
		int exitSE = 0;
		int exitUP = 0;
		int exitDN = 0;
		int locked = 0;
		String roomItem1 = "";
		String roomItem2 = "";
		String roomItem3 = "";
		String roomItem4 = "";
		String roomItem5 = "";
		String enemy = "";
		String roomDescription = "";
	}

	public Room(int roomNum, String roomName, int exitN, int exitNW, int exitNE, int exitW, int exitE, int exitSW, int exitS, int exitSE, int exitUP, int exitDN, int locked, String roomItem1, String roomItem2, String roomItem3, String roomItem4, String roomItem5, String enemy, String roomDescription) {
		this.roomNum = roomNum;
		this.roomName = roomName;
		this.exitN = exitN;
		this.exitNW = exitNW;
		this.exitNE = exitNE;
		this.exitW = exitW;
		this.exitE = exitE;
		this.exitSW = exitSW;
		this.exitS = exitS;
		this.exitSE = exitSE;
		this.exitUP = exitUP;
		this.exitDN = exitDN;
		this.locked = locked;
		this.roomItem1 = roomItem1;
		this.roomItem2 = roomItem2;
		this.roomItem3 = roomItem3;
		this.roomItem4 = roomItem4;
		this.roomItem5 = roomItem5;
		this.enemy = enemy;
		this.roomDescription = roomDescription;
	}

	@Override
	public String toString() {
		return "Room{" +
				"roomNum=" + roomNum +
				", roomName='" + roomName + '\'' +
				", exitN=" + exitN +
				", exitNW=" + exitNW +
				", exitNE=" + exitNE +
				", exitW=" + exitW +
				", exitE=" + exitE +
				", exitSW=" + exitSW +
				", exitS=" + exitS +
				", exitSE=" + exitSE +
				", exitUP=" + exitUP +
				", exitDN=" + exitDN +
				", locked=" + locked +
				", roomItem1='" + roomItem1 + '\'' +
				", roomItem2='" + roomItem2 + '\'' +
				", roomItem3='" + roomItem3 + '\'' +
				", roomItem4='" + roomItem4 + '\'' +
				", roomItem5='" + roomItem5 + '\'' +
				", enemy='" + enemy + '\'' +
				", roomDescription='" + roomDescription + '\'' +
				'}';
	}

	//Displays the room to the player
	public void display() {
		System.out.println(roomName);
		System.out.println(roomDescription);
		//Only lists an exit if it is valid
		System.out.print("The exit(s) are to the: ");
		if(exitN>0){
			System.out.print("N ");
		}
		if(exitNW>0){
			System.out.print("NW ");
		}
		if(exitNE>0){
			System.out.print("NE ");
		}
		if(exitW>0){
			System.out.print("W ");
		}
		if(exitE>0) {
			System.out.print("E ");
		}
		if(exitSW>0){
			System.out.print("SW ");
		}
		if(exitS>0){
			System.out.print("S ");
		}
		if(exitSE>0){
			System.out.print("SE ");
		}
		if(exitUP>0){
			System.out.print("UP ");
		}
		if(exitDN>0){
			System.out.print("DN");
		}
		System.out.print("The room contains: ");
		//Only lists items if there is one
		if(!roomItem1.equals("no")) {
			System.out.print(roomItem1);
		}
		else {
			System.out.print("no items.");
		}
		if(!roomItem2.equals("no")) {
			System.out.print(roomItem2);
		}
		if(!roomItem3.equals("no")) {
			System.out.print(roomItem3);
		}
		if(!roomItem4.equals("no")) {
			System.out.print(roomItem4);
		}
		if(!roomItem5.equals("no")) {
			System.out.print(roomItem5);
		}
		//Only lists an enemy if there is one
		if(!enemy.equals("no")) {
			System.out.println("There is a " + enemy + " in this room!");
		}
	}

	//Getters and Setters
	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getExitN() {
		return exitN;
	}

	public void setExitN(int exitN) {
		this.exitN = exitN;
	}

	public int getExitNW() {
		return exitNW;
	}

	public void setExitNW(int exitNW) {
		this.exitNW = exitNW;
	}

	public int getExitNE() {
		return exitNE;
	}

	public void setExitNE(int exitNE) {
		this.exitNE = exitNE;
	}

	public int getExitW() {
		return exitW;
	}

	public void setExitW(int exitW) {
		this.exitW = exitW;
	}

	public int getExitE() {
		return exitE;
	}

	public void setExitE(int exitE) {
		this.exitE = exitE;
	}

	public int getExitSW() {
		return exitSW;
	}

	public void setExitSW(int exitSW) {
		this.exitSW = exitSW;
	}

	public int getExitS() {
		return exitS;
	}

	public void setExitS(int exitS) {
		this.exitS = exitS;
	}

	public int getExitSE() {
		return exitSE;
	}

	public void setExitSE(int exitSE) {
		this.exitSE = exitSE;
	}

	public int getExitUP() {
		return exitUP;
	}

	public void setExitUP(int exitUP) {
		this.exitUP = exitUP;
	}

	public int getExitDN() {
		return exitDN;
	}

	public void setExitDN(int exitDN) {
		this.exitDN = exitDN;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public String getRoomItem1() {
		return roomItem1;
	}

	public void setRoomItem1(String roomItem1) {
		this.roomItem1 = roomItem1;
	}

	public String getRoomItem2() {
		return roomItem2;
	}

	public void setRoomItem2(String roomItem2) {
		this.roomItem2 = roomItem2;
	}

	public String getRoomItem3() {
		return roomItem3;
	}

	public void setRoomItem3(String roomItem3) {
		this.roomItem3 = roomItem3;
	}

	public String getRoomItem4() {
		return roomItem4;
	}

	public void setRoomItem4(String roomItem4) {
		this.roomItem4 = roomItem4;
	}

	public String getRoomItem5() {
		return roomItem5;
	}

	public void setRoomItem5(String roomItem5) {
		this.roomItem5 = roomItem5;
	}

	public String getEnemy() {
		return enemy;
	}

	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}
}
