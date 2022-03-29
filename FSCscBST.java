import java.util.*;

public class FSCscBST {
private FSCstudent root;

    // CONSTRUCTORS
    public FSCscBST() {
        root = null;
    }

    /* Below are MANY methods that are used on Binary Search Trees.
     *
     * Examples:
     * search, insert, delete, isEmpty, minVal, maxVal, inorder, sumNodes, and many more
     */


    //
    // boolean | isEmpty()
    //
    public boolean isEmpty() {
        return root == null;
    }


    //
    // boolean | search(int)
    //
    public boolean search(int data) {
        return search(root, data);
    }
    //
    // boolean | search(BSTnode, int)
    //
    private boolean search(FSCstudent p, int data) {
        if (p == null)
            return false;
        else {
            // if the data we are searching for is found at p (at the current root)
            if (data == p.getMACnumber())
                return true;
            else if (data < p.getMACnumber())
                return search(p.getLeft(), data);
            else
                return search(p.getRight(), data);
        }
    }


    //
    // void | insert(int)
    //
    public void insert(String firstName, String lastName, int MACnumber, int broadcastRange, int x, int y, int numLinks, FSCscLinkedDevices allowedMACs, FSCstudent right, FSCstudent left) {
        FSCstudent newNode = new FSCstudent(firstName, lastName, MACnumber, broadcastRange, x, y, numLinks, allowedMACs, right, left);
        root = insert(root, newNode);
    }
    //
    // BSTnode | insert(BSTnode, BSTnode)
    //
    private FSCstudent insert(FSCstudent p, FSCstudent newNode) {
        // IF there is no tree, newNode will be the root, so just return it
        if (p == null)
            return newNode;

            // ELSE, we have a tree. Insert to the right or the left
        else {
            // Insert to the RIGHT of root
            if (newNode.getMACnumber() > p.getMACnumber()) {
                // Recursively insert into the right subtree
                // The result of insertion is an updated root of the right subtree
                FSCstudent temp = insert(p.getRight(), newNode);
                // Save this newly updated root of right subtree into p.right
                p.setRight(temp);
            }
            // Insert to the LEFT of root
            else {
                // Recursively insert into the left subtree
                // The result of insertion is an updated root of the left subtree
                FSCstudent temp = insert(p.getLeft(), newNode);
                // Save this newly updated root of left subtree into p.left
                p.setLeft(temp);
            }
        }
        // Return root of updated tree
        return p;
    }


    //
    // void | printmembers()
    //
    public void printmembers() {
        printmembers(root);
    }
    //
    // void | printmembers(BSTnode)
    //
    private void printmembers(FSCstudent p) {
        // Checks if node is null
        if (p != null) {
            // Recursivly finds lowest node of tree
            printmembers(p.getLeft());
            // Checks if links have 1 link or something else for grammar and prints particpants information
            if (p.getNumLinks() == 1){
                System.out.printf("\tMAC %d, %s %s, currently at position (%d, %d), %d Link\n", p.getMACnumber(), p.getFirstName(), p.getLastName(), p.getX(), p.getY(), p.getNumLinks());
            }
            else{
                System.out.printf("\tMAC %d, %s %s, currently at position (%d, %d), %d Links\n", p.getMACnumber(), p.getFirstName(), p.getLastName(), p.getX(), p.getY(), p.getNumLinks());
            }
            printmembers(p.getRight());
        }
    }


    //
    // void | delete(int)
    //
    public void delete(int data) {
        root = delete(root, data);
    }
    //
    // BSTnode | delete(BSTnode, int)
    //
    private FSCstudent delete(FSCstudent p, int data) {
        FSCstudent node2delete, newnode2delete, node2save, parent;
        int saveValue;

        // Step 1: Find the node we want to delete
        node2delete = findNode(p, data);
        // If node is not found (does not exist in tree), we clearly cannot delete it.
        if (node2delete == null)
            return root;

        // Step 2: Find the parent of the node we want to delete
        parent = parent(p, node2delete);

        // Step 3: Perform Deletion based on three possibilities

        // **1** :  node2delete is a leaf node
        if (isLeaf(node2delete)) {
            // if parent is null, this means that node2delete is the ONLY node in the tree
            if (parent == null)
                return null; // we return null as the updated root of the tree

            // Delete node if it is a left child
            if (data < parent.getMACnumber())
                parent.setLeft(null);
                // Delete node if it is a right child
            else
                parent.setRight(null);

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **2** : node2delete has only a left child
        if (hasOnlyLeftChild(node2delete)) {
            // if node2delete is the root
            if (parent == null)
                return node2delete.getLeft();

            // If node2delete is not the root,
            // it must the left OR the right child of some node

            // IF it is the left child of some node
            if (data < parent.getMACnumber())
                parent.setLeft(parent.getLeft().getLeft());
                // ELSE it is the right child of some node
            else
                parent.setRight(parent.getRight().getLeft());

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **3** :  node2delete has only a right child
        if (hasOnlyRightChild(node2delete)) {
            // if node2delete is the root
            if (parent == null)
                return node2delete.getRight();

            // If node2delete is not the root,
            // it must the left OR the right child of some node

            // IF it is the left child of some node
            if (data < parent.getMACnumber())
                parent.setLeft(parent.getLeft().getRight());
                // ELSE it is the right child of some node
            else
                parent.setRight(parent.getRight().getRight());

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **4** :  node2delete has TWO children.
        // Remember, we have two choices: the minVal from the right subtree (of the deleted node)
        // or the maxVal from the left subtree (of the deleted node)
        // We choose to find the minVal from the right subtree.
        newnode2delete = minNode(node2delete.getRight());
        // Now we need to temporarily save the data value(s) from this node
        saveValue = newnode2delete.getMACnumber();
        String name = newnode2delete.getFirstName();
        String lastName = newnode2delete.getLastName();
        int links = newnode2delete.getNumLinks();
        FSCscLinkedDevices list = newnode2delete.getAllowedMACs();
        int range = newnode2delete.getBroadcastRange();

        // Now, we recursively call our delete method to actually delete this node that we just copied the data from
        p = delete(p, saveValue);

        // Now, put the saved data (in saveValue) into the correct place (the original node we wanted to delete)
        node2delete.setMACnumber(saveValue);
        node2delete.setFirstName(name);
        node2delete.setLastName(lastName);
        node2delete.setNumLinks(links);
        node2delete.setAllowedMACs(list);
        node2delete.setBroadcastRange(range);

        // Finally, return the root of the tree (in case the root got updated)
        return p;
    }


    //
    // BSTnode | minNode(BSTnode)
    //
    public FSCstudent minNode(FSCstudent root) {
        if (root == null) {
            return null;
        }
        else {
            if (root.getLeft() == null) {
                return root;
            }
            else {
                return minNode(root.getLeft());
            }
        }
    }


    //
    // BSTnode | maxNode(BSTnode)
    //
    public FSCstudent maxNode(FSCstudent root) {
        if (root == null) {
            return null;
        }
        else {
            if (root.getRight() == null) {
                return root;
            }
            else {
                return maxNode(root.getRight());
            }
        }
    }


    //
    // BSTnode | findNode(int)
    //
    public FSCstudent findNode(int data) {
        return findNode(root, data);
    }
    //
    // BSTnode | findNode(BSTnode, int)
    //
    private FSCstudent findNode(FSCstudent p, int data) {
        if (p == null)
            return null;
        else {
            // if the data we are searching for is found at p (at the current root)
            if (data == p.getMACnumber())
                return p;
            else if (data < p.getMACnumber())
                return findNode(p.getLeft(), data);
            else
                return findNode(p.getRight(), data);
        }
    }

    //
    // BSTnode | parent(BSTnode)
    //
    public FSCstudent parent(FSCstudent p) {
        return parent(root, p);
    }
    //
    // BSTnode | parent(BSTnode, BSTnode)
    //
    private FSCstudent parent(FSCstudent root, FSCstudent p) {
        // Take care of NULL cases
        if (root == null || root == p)
            return null; // because there is on parent

        // If root is the actual parent of node p
        if (root.getLeft()==p || root.getRight()==p)
            return root; // because root is the parent of p

        // Look for p's parent in the left side of root
        if (p.getMACnumber() < root.getMACnumber())
            return parent(root.getLeft(), p);

            // Look for p's parent in the right side of root
        else if (p.getMACnumber() > root.getMACnumber())
            return parent(root.getRight(), p);

            // Take care of any other cases
        else
            return null;
    }


    //
    // boolean | isLeaf(BSTnode)
    //
    public boolean isLeaf(FSCstudent p) {
        return (p.getLeft()==null && p.getRight()==null);
    }


    //
    // boolean | hasOnlyLeftChild(BSTnode)
    //
    public boolean hasOnlyLeftChild(FSCstudent p) {
        return (p.getLeft()!=null && p.getRight()==null);
    }


    //
    // boolean | hasOnlyRightChild(BSTnode)
    //
    public boolean hasOnlyRightChild(FSCstudent p) {
        return (p.getLeft()==null && p.getRight()!=null);
    }

    // Method for moving Devices
    public void moveDevices(Random rng, int sizeX, int sizeY){
        moveDevices(root, rng, sizeX, sizeY);
    }
    private void moveDevices (FSCstudent p, Random rng, int sizeX, int sizeY){
        // Loops through devices in ascending order
        if (p != null) {
            moveDevices(p.getLeft(), rng, sizeX, sizeY);
            // Sets a random integer based on sizes and seed
            p.setX(rng.nextInt(sizeX));
            p.setY(rng.nextInt(sizeY));
            moveDevices(p.getRight(), rng, sizeX, sizeY);
        }
    }
}
