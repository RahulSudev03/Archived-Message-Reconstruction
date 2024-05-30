package edu.iastate.cs228.hw4;
/**
 * @author Rahul Sudev
 */

import java.util.Stack;

/**
  * MsgTree is a binary tree that is going to be used for decoding messages.
 */
public class MsgTree {

    /**
     * The character payload for this current node
     */
    public char payloadChar;

    /**
     * left sub tree of node
     */
    public MsgTree left;

    /**
     * right sub tree of node
     */
    public MsgTree right;

    /**
     * counter to keep track of characters that are decoded
     */
    private static int decodedCounter;

    /**
     * A static character index used to traverse the encoding string recursively
     */
    private static int encodingStringCharIndex = 0;

    /**
     * Constructs a binary tree from a given string .
     *
     * @param encodingString the string encoding of the binary tree
     */
    public MsgTree(String encodingString) {
        if (encodingString == null || encodingString.length() < 2) {
            return;
        }

        int index = 0;
        String direction = "left";

        Stack<MsgTree> nodeStack = new Stack<>();
        this.payloadChar = encodingString.charAt(index++);
        nodeStack.push(this);

        MsgTree currentTree = this;

        while (index < encodingString.length()) {
            MsgTree newNode = new MsgTree(encodingString.charAt(index++));

            if (direction.equals("left")) {
                currentTree.left = newNode;

                if (newNode.payloadChar == '^') {
                    currentTree = nodeStack.push(newNode);
                    direction = "left";
                }
                else {
                    if (!nodeStack.empty()) {
                        currentTree = nodeStack.pop();
                        direction = "right";
                    }
                }
            }
            else {
                currentTree.right = newNode;

                if (newNode.payloadChar == '^') {
                    currentTree = nodeStack.push(newNode);
                    direction = "left";
                }
                else {
                    if (!nodeStack.empty()) {
                        currentTree = nodeStack.pop();
                        direction = "right";
                    }
                }
            }
        }
    }

    /**
     * Constructs a MsgTree object with one node and no children.
     *
     * @param payloadChar the character payload for this node
     */
    public MsgTree(char payloadChar) {
        this.payloadChar = payloadChar;
        this.left = null;
        this.right = null;
    }

    /**
     * Prints binary codes and the characters that correspong them in the tree.
     *
     * @param root:- this is the root of the binary tree
     * @param code :- the binary code for the current node
     */
    public static void printCodes(MsgTree root, String code) {
        if (root.payloadChar != '^') {
            if (root.payloadChar == '\n') {
                System.out.println("   \\n     " + code);
            }
            else {
                System.out.println("   " + root.payloadChar + " " + "     " + code);
            }
            return;
        }

        printCodes(root.left, code + "0");
        printCodes(root.right, code + "1");
    }

    /**
     * Decodes the given binary message and prints the decoded message to standard output.
     *
     * @param tree    the binary tree used for decoding the message
     * @param message the binary message to decode
     */
    public static void decode(MsgTree tree, String message) {
        MsgTree decodeString = tree;

        for (int i = 0; i < message.length(); ) {
            while (tree.payloadChar == '^') {
                char c = message.charAt(i++);

                if (c == '0') {
                    tree = tree.left;
                }
                else{
                    tree = tree.right;
                }
            }
            System.out.print(tree.payloadChar);
            tree = decodeString;
            decodedCounter++;
        }
    }

    /**
     * to keep track of all the counts that have been done
     * @return
     */
    public static int countKeeper() {
        return decodedCounter;
    }

}
