public class FSCscLinkedDevices {
private FSCscDevice head;
private FSCscDevice back;

	public FSCscLinkedDevices() {
		this.head = null;
		this.back = null;
	}

//
	// boolean | isEmpty()
	//
	public boolean isEmpty() {
		return head == null;
	}
	
	
	//
	// void | PrintQueue()
	//
	
	
//	public void PrintQueue() {
//		PrintQueue(head);
//	}

	
	private void PrintQueue(FSCscDevice front) {
		// We need to traverse...so we need a help ptr
		FSCscDevice helpPtr = front;
		// Traverse to correct insertion point
		while (helpPtr != null) {
			// Print the data value of the node
			System.out.print(helpPtr.getMACnumber() + ", ");
			// Step one node over
			helpPtr = helpPtr.getNext();
		}
		System.out.println();
	}
	
	
	//
	// boolean | search(int)
	//
	
	public boolean search(int data) {
		return search(head, data);
	}

	private boolean search(FSCscDevice p, int data) {
		// To search, we must traverse. Therefore, we need helpPtr.
		FSCscDevice helpPtr = p;
		while (helpPtr != null) {
			if (helpPtr.getMACnumber() == data)
				return true;
			helpPtr = helpPtr.getNext(); // step one node over		
		}
		return false;
	}


	//
	// void | insert(int)
	//
	public void insert(int data) {
		head = insert(head, data);
	}
	//
	// LLnode | insert(LLnode, value)
	//
	private FSCscDevice insert(FSCscDevice head, int data) {
		// IF there is no list, newNode will be the first node, so just return it
		if (head == null || head.getMACnumber() > data) {
			head = new FSCscDevice(data, head);
			return head;
		}

		// ELSE, we have a list. Insert the new node at the correct location
		else {
			// We need to traverse to the correct insertion location...so we need a help ptr
			FSCscDevice helpPtr = head;
			// Traverse to correct insertion point
			while (helpPtr.getNext() != null) {
				if (helpPtr.getNext().getMACnumber() > data)
					break; // we found our spot and should break out of the while loop
				helpPtr = helpPtr.getNext();
			}
			// Now make the new node. Set its next to point to the successor node.
			// And then make the predecessor node point to the new node
			FSCscDevice newNode = new FSCscDevice(data, helpPtr.getNext());
			helpPtr.setNext(newNode);
		}
		// Return head

		return head;
	}


	//
	// void | delete(int)
	//
	public void delete(int data) {
		head = delete(head, data);
	}
	//
	// LLnode | delete(LLnode, value)
	//
	private FSCscDevice delete(FSCscDevice head, int data) {
		// We can only delete if the list has nodes (is not empty)
		if (!isEmpty()) {
			// IF the first node (at the head) has the data value we are wanting to delete
			// we found it. Delete by skipping the node and making head point to the next node.
			if (head.getMACnumber() == data) {
				head = head.getNext();
			}
			// ELSE, the data is perhaps somewhere else in the list...so we must traverse and look for it
			else {
				// We need to traverse to find the data we want to delete...so we need a help ptr
				FSCscDevice helpPtr = head;
				// Traverse to correct deletion point
				while (helpPtr.getNext() != null) {
					if (helpPtr.getNext().getMACnumber() == data) {
						helpPtr.setNext(helpPtr.getNext().getNext());
						break; // we deleted the value and should break out of the while loop
					}
					helpPtr = helpPtr.getNext();
				}
			}
			// return the possibly updated head of the list
			return head;
		}
		return head;
	}
	
	
	//
	// int | peek()
	//
	public int peek() {
		// Invoke the peek method with front as a parameter
		int frontValue = peek(head);
		
		// return topValue
		return frontValue;
	}
	//
	// int | peek(QueueNode)
	//
	private int peek(FSCscDevice front) {
		// Return the data value of the front node.
		// You can see that we do NOT dequeue. We are only returning the data value.
		return front.getMACnumber();
	}

	// Public showConnections
	public void showConnections(FSCstudent temp, FSCscBST tree){
		showConnections(head, temp, tree);
	}

	// Private showConnections
	private void showConnections(FSCscDevice p, FSCstudent temp, FSCscBST tree){

		// Checks if node is null
		if (p == null){
			System.out.printf("MAC %d has no links.\n", temp.getMACnumber());
			return;
		}

		// Variables required
		int links = 0;
		int active_links = 0;
		FSCscDevice helpPtr = p;

		// Loops through list
		while(helpPtr != null){

			// Saves nodes
			FSCstudent connection = tree.findNode(helpPtr.getMACnumber());

			// Tests if nodes are within range
			if(withinRange(connection, temp, tree)){
				// Increments links
				links += 1;
				active_links += 1;
				helpPtr = helpPtr.getNext();
			}
			else{
				// Increments links
				links += 1;
				helpPtr = helpPtr.getNext();
			}
		}

		// Prints current connections
		System.out.printf("Connections for MAC %d, %s %s, currently at position (%d, %d):\n", temp.getMACnumber(), temp.getFirstName(), temp.getLastName(), temp.getX(), temp.getY());
		System.out.printf("\tThere are a total of %d link(s).\n", links);

		// Prints active links if applicable
		if (active_links == 0){
			System.out.printf("\tThere are NO active links within the broadcast range of %d.\n", temp.getBroadcastRange());
		}
		else{
			System.out.printf("\tThere are %d active link(s) within the broadcast range of %d:\n", active_links, temp.getBroadcastRange());

			FSCscDevice helpPtr2 = p;
			while(helpPtr2 != null){
				FSCstudent connection = tree.findNode(helpPtr2.getMACnumber());
				if(withinRange(connection, temp, tree)){
					FSCstudent connection2 = tree.findNode(helpPtr2.getMACnumber());
					System.out.printf("\t\tMAC %d, %s %s, currently at position (%d, %d)\n", connection2.getMACnumber(), connection2.getFirstName(), connection2.getLastName(), connection2.getX(), connection2.getY());
					helpPtr2 = helpPtr2.getNext();
				}
				else{
					helpPtr2 = helpPtr2.getNext();
				}
			}
		}
	}

	// Method for withinRange
	private boolean withinRange(FSCstudent connection, FSCstudent temp, FSCscBST tree){

		// Saves x and y locations
		double x1 = temp.getX();
		double x2 = connection.getX();
		double y1 = temp.getY();
		double y2 = connection.getY();

		// Finds distance between 2 people
		double distance = Math.floor(Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)));

		// Returns true if its within range
		if (temp.getBroadcastRange() >= distance){
			return true;
		}
		else{
			return false;
		}
	}

	// Public removeAllLinks
	public void removeAllLinks(FSCstudent temp, FSCscBST tree){
		removeAllLinks(head, temp, tree);
	}

	// Private removeAllLinks
	private void removeAllLinks(FSCscDevice p, FSCstudent temp, FSCscBST tree){
		// While loop to go through all links in a list
		while (p != null){
			// Finds node in tree and saves node
			FSCstudent toBeRemoved = tree.findNode(p.getMACnumber());
			// Deletes removed participant from link
			toBeRemoved.getAllowedMACs().delete(temp.getMACnumber());
			// Lowers link count
			toBeRemoved.decrementNumLinks();
			// Gets next node
			p = p.getNext();
		}
		// Resets list of parent nodde
		temp.setAllowedMACs(new FSCscLinkedDevices());
	}
}
