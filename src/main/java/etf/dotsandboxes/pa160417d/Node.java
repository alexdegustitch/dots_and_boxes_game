package etf.dotsandboxes.pa160417d;

import java.util.ArrayList;

public class Node {

	public static int MAX = 0, MIN = 1;
	private int depth;
	private int moveLine;
	private int evolutionFunction;
	private int chains_points;
	private ArrayList<Node> children = new ArrayList<Node>();
	private Node parent;
	private int type;
	private ArrayList<Line> state;
	private boolean leaf;

	private ArrayList<Chain> chains = new ArrayList<Chain>();

	public Node(int depth, int moveLine, Node parent, int type, ArrayList<Line> state) {
		this.depth = depth;
		this.moveLine = moveLine;
		this.parent = parent;
		this.type = type;
		this.state = state;
		leaf = true;
		for (int i = 0; i < state.size(); i++) {
			if (!state.get(i).isClicked()) {
				leaf = false;
				break;
			}
		}
		chains_points = 0;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getMoveLine() {
		return moveLine;
	}

	public void setMoveLine(int moveLine) {
		this.moveLine = moveLine;
	}

	public int getEvolutionFunction() {
		return evolutionFunction;
	}

	public void setEvolutionFunction(int evolutionFunction) {
		this.evolutionFunction = evolutionFunction;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ArrayList<Line> getState() {
		return state;
	}

	public void setState(ArrayList<Line> state) {
		this.state = state;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public int getChains_points() {
		return chains_points;
	}

	public void setChains_points(int chains_points) {
		this.chains_points = chains_points;
	}

	public ArrayList<Chain> getChains() {
		return chains;
	}

	public void setChains(ArrayList<Chain> chains) {
		this.chains = chains;
	}

}
