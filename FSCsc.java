// Import needed files
import java.util.*;


// Main class
public class FSCsc {

    // Main method
    public static void main(String[] args) {

        // Creates scanner to read input and creates BST
        Scanner in = new Scanner(System.in);
        FSCscBST tree = new FSCscBST();

        // Generates seed and rng number
        int seed = in.nextInt();
        Random rng = new Random(seed);

        // Declares field sizes
        int xSize = in.nextInt();
        int ySize = in.nextInt();

        // Declares number of commands
        int size = in.nextInt();

        // Reads input given size
        for(int i = 0; i < size; i++){

            // Reads input
            String command = in.next();

            // If input was JOIN
            if (command.equals("JOIN")){
                JOIN(tree, in.nextInt(), in.next(), in.next(), in.nextInt(), in.nextInt(), in.nextInt());
            }

            // If input was FINDMAC
            else if (command.equals("FINDMAC")){
                FINDMAC(tree, in.nextInt());
            }

            // If input was LINK
            else if (command.equals("LINK")){
                LINK(tree, in.nextInt(), in.nextInt());
            }

            // If input was UNLINK
            else if (command.equals("UNLINK")){
                UNLINK(tree, in.nextInt(), in.nextInt());
            }

            // If input was QUIT
            else if (command.equals("QUIT")){
                QUIT(tree, in.nextInt());
            }

            // If input was SHOWCONNECTIONS
            else if (command.equals("SHOWCONNECTIONS")){
                SHOWCONNECTIONS(tree, in.nextInt());
            }

            // If input was SHOWCONNECTIONS
            else if (command.equals("PRINTMEMBERS")){
                PRINTMEMBERS(tree);
            }

            // If input was MOVEDEVICES
            else if (command.equals("MOVEDEVICES")){
                MOVEDEVICES(tree, rng, xSize, ySize);
            }

            // If input is not a valid command
            else{
                System.out.println("Invalid Command");
            }
        }
    }

    // Method for JOIN
    public static void JOIN(FSCscBST tree, int MAC, String firstName, String lastName, int range, int x, int y){
        // Finds if node is already in tree, if not
        if(tree.findNode(MAC) == null){

            // Prints participant joining
            System.out.printf("%s %s, MAC %d, joined the FSC Sharing is Caring system.\n", firstName, lastName, MAC);

            // Inserts participant
            tree.insert(firstName, lastName, MAC, range, x, y, 0, null, null, null);
        }
        // If node is already in tree
        else{

            // Prints fail to join
            System.out.println("Cannot Perform JOIN Command:");
            System.out.printf("\tMAC %d, %s %s - already a participant in the FSC Sharing is Caring system.\n", MAC, firstName, lastName);
        }
    }

    // Method for FINDMAC
    public static void FINDMAC(FSCscBST tree, int MAC){

        // Checks if node is in the tree, if it is in it
        if(tree.findNode(MAC) != null){

            // Saves node to temp node for use
            FSCstudent temp = tree.findNode(MAC);

            // If node has only one link, prints information
            if (temp.getNumLinks() == 1){
                System.out.printf("Found:  MAC %d, %s %s, currently at position (%d, %d), %d Link\n", MAC, temp.getFirstName(), temp.getLastName(), temp.getX(), temp.getY(), temp.getNumLinks());
            }
            // If node has 0 or more than 1 links
            else{
                System.out.printf("Found:  MAC %d, %s %s, currently at position (%d, %d), %d Links\n", MAC, temp.getFirstName(), temp.getLastName(), temp.getX(), temp.getY(), temp.getNumLinks());
            }
        }
        // If node is not found
        else{
            System.out.printf("MAC %d not found in the FSC Sharing is Caring system.\n", MAC);
        }
    }

    // Method for LINK
    public static void LINK(FSCscBST tree, int MAC1, int MAC2){

        // Finds nodes for both addresses
        FSCstudent temp = tree.findNode(MAC1);
        FSCstudent temp2 = tree.findNode(MAC2);

        // Checks if either node is null
        if (temp == null || temp2 == null){

            // Prints failure to perform command
            System.out.println("Cannot Perform LINK Command:");
            System.out.printf("\tMAC %d - This MAC Address is not in the FSC Sharing is Caring system.\n", MAC1);
            System.out.printf("\tMAC %d - This MAC Address is not in the FSC Sharing is Caring system.\n", MAC2);
        }

        // Checks if nodes are already linked
        else if (temp.getAllowedMACs().search(MAC2) && temp2.getAllowedMACs().search(MAC1)){

            // Prints that they are linked
            System.out.println("Cannot Perform LINK Command:");
            System.out.printf("\tMAC %d and MAC %d are already linked.\n", MAC1, MAC2);

        }

        // Checks if its an attempt to link two identical addresses
        else if (MAC1 == MAC2){

            // Prints failure to perform command
            System.out.println("Cannot Perform LINK Command:");
            System.out.printf("\tMAC %d cannot link to itself.\n", MAC1);
        }

        // Else it performs link
        else{

            // Adds each participant to the others list
            // Increments links for both participants
            temp.getAllowedMACs().insert(MAC2);
            temp.incrementNumLinks();
            temp2.getAllowedMACs().insert(MAC1);
            temp2.incrementNumLinks();

            // Prints confirmation
            System.out.printf("MAC %d and MAC %d are now linked.\n", MAC1, MAC2);
        }
    }

    // Method for UNLINK
    public static void UNLINK(FSCscBST tree, int MAC1, int MAC2){

        // Finds both nodes from tree
        FSCstudent temp = tree.findNode(MAC1);
        FSCstudent temp2 = tree.findNode(MAC2);

        // Checks if either node is null
        if (temp == null || temp2 == null){

            // Prints failure to perform command
            System.out.println("Cannot Perform UNLINK Command:");
            System.out.printf("\tMAC %d - This MAC Address is not in the FSC Sharing is Caring system.\n", MAC1);
            System.out.printf("\tMAC %d - This MAC Address is not in the FSC Sharing is Caring system.\n", MAC2);
        }

        // Checks if nodes are already unlinked
        else if (!temp.getAllowedMACs().search(MAC2) && !temp2.getAllowedMACs().search(MAC1)){

            // Prints that they aren't linked
            System.out.println("Cannot Perform UNLINK Command:");
            System.out.printf("\tMAC %d and MAC %d are not currently linked.\n", MAC1, MAC2);
        }
        else{

            // Removed each other from the others list
            // Decrements the link number of each participant
            temp.getAllowedMACs().delete(MAC2);
            temp.decrementNumLinks();
            temp2.getAllowedMACs().delete(MAC1);
            temp2.decrementNumLinks();

            // Prints confirmation of command
            System.out.printf("MAC %d and MAC %d are no longer linked.\n", MAC1, MAC2);
        }
    }

    // Method for QUIT
    public static void QUIT(FSCscBST tree, int MAC){

        // Finds node and saves its value to temp node
        FSCstudent temp = tree.findNode(MAC);
        if (temp == null){

            // Prints failure to remove participant and returns
            System.out.println("Cannot Perform QUIT Command:");
            System.out.printf("\tMAC %d not found in the FSC Sharing is Caring system.\n", MAC);
            return;
        }

        // Removes the links from removed participants and participant from linked lists
        temp.getAllowedMACs().removeAllLinks(temp, tree);
        tree.delete(MAC);

        // Prints confirmation
        System.out.printf("MAC %d has been removed from the FSC Sharing is Caring system.\n", MAC);
    }

    // Method for SHOWCONNECTIONS
    public static void SHOWCONNECTIONS(FSCscBST tree, int MAC){

        // Finds MAC address and saves result to temp node
        FSCstudent temp = tree.findNode(MAC);

        // If node is null
        if (temp == null){

            // Prints failure to perform command
            System.out.println("Cannot Perform SHOWCONNECTIONS Command:");
            System.out.printf("\tMAC %d - This MAC Address is not in the FSC Sharing is Caring system.\n", MAC);
            return;
        }

        // Prints all found connections
        temp.getAllowedMACs().showConnections(temp, tree);
    }

    // Method for PRINTMEMBERS
    public static void PRINTMEMBERS(FSCscBST tree){

        // Checks if tree is empty
        if (tree.isEmpty()){

            // Prints failure to perform command and returns
            System.out.println("Cannot Perform PRINTMEMBERS Command:");
            System.out.println("\tThere are currently no participants in the FSC Sharing is Caring system.");
            return;
        }
        // Prints all memebers participating
        System.out.println("Members of FSC Sharing is Caring System:");
        tree.printmembers();
    }

    // Method for MOVEDEVICES
    public static void MOVEDEVICES(FSCscBST tree, Random rng, int sizeX, int sizeY){

        // Checks if tree is empty
        if (tree.isEmpty()){

            // Prints failure to perform command and returns
            System.out.println("Cannot Perform MOVEDEVICES Command:");
            System.out.println("\tThere are currently no participants in the FSC Sharing is Caring system.");
            return;
        }
        // Changes all devices x and y coordinates
        tree.moveDevices(rng, sizeX, sizeY);
        System.out.println("All devices successfully moved.");
    }
}
