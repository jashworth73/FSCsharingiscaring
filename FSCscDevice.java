public class FSCscDevice {
private int MACnumber;
private FSCscDevice next;

	// CONSTRUCTORS
	public FSCscDevice() {
		this.MACnumber = 0;
		this.next = null;
	}
	
	public FSCscDevice(int MACnumber) {
		this.MACnumber = MACnumber;
		this.next = null;
	}
	
	public FSCscDevice(int MACnumber, FSCscDevice next) {
		this.MACnumber = MACnumber;
		this.next = next;
	}

	// GETTERS AND SETTERS
	public int getMACnumber() {
		return MACnumber;
	}

	public void setMACnumber(int MACnumber) {
		this.MACnumber = MACnumber;
	}

	public FSCscDevice getNext() {
		return next;
	}

	public void setNext(FSCscDevice next) {
		this.next = next;
	}


}
