public class FSCstudent {
private String firstName;
private String lastName;
private int MACnumber;
private int broadcastRange;
private int x;
private int y;
private int numLinks;
private FSCscLinkedDevices allowedMACs;
private FSCstudent right;
private FSCstudent left;

	// Constructor
	public FSCstudent(String firstName, String lastName, int MACnumber, int broadcastRange, int x, int y, int numLinks, FSCscLinkedDevices allowedMACs, FSCstudent right, FSCstudent left) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.MACnumber = MACnumber;
		this.broadcastRange = broadcastRange;
		this.x = x;
		this.y = y;
		this.numLinks = numLinks;
		this.allowedMACs = new FSCscLinkedDevices();
		this.right = right;
		this.left = left;
	}

	// Getters and Setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getMACnumber() {
		return MACnumber;
	}

	public void setMACnumber(int MACnumber) {
		this.MACnumber = MACnumber;
	}

	public int getBroadcastRange() {
		return broadcastRange;
	}

	public void setBroadcastRange(int broadcastRange) {
		this.broadcastRange = broadcastRange;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getNumLinks() {
		return numLinks;
	}

	public void setNumLinks(int numLinks) {this.numLinks = numLinks;}

	// Increment and decrement links
	public void incrementNumLinks() {
		this.numLinks += 1;
	}
	public void decrementNumLinks() {
		this.numLinks -= 1;
	}

	public FSCscLinkedDevices getAllowedMACs() {
		return allowedMACs;
	}

	public void setAllowedMACs(FSCscLinkedDevices allowedMACs) {
		this.allowedMACs = allowedMACs;
	}

	public FSCstudent getRight() {
		return right;
	}

	public void setRight(FSCstudent right) {
		this.right = right;
	}

	public FSCstudent getLeft() {
		return left;
	}

	public void setLeft(FSCstudent left) {
		this.left = left;
	}

}
