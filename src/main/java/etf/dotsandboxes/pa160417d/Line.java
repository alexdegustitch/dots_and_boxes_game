package etf.dotsandboxes.pa160417d;

import java.util.ArrayList;

public class Line {
	private ArrayList<Integer> firstSquare = new ArrayList<Integer>();
	private ArrayList<Integer> secondSquare = new ArrayList<Integer>();

	private int firstSquareNum = -1;
	private int secondSquareNum = -1;
	private int id = -1;

	private boolean isClicked = false;

	private String name;

	public Line(int id) {
		this.id = id;
	}

	public ArrayList<Integer> getFirstSquare() {
		return firstSquare;
	}

	public void setFirstSquare(ArrayList<Integer> firstSquare) {
		this.firstSquare = firstSquare;
	}

	public ArrayList<Integer> getSecondSquare() {
		return secondSquare;
	}

	public void setSecondSquare(ArrayList<Integer> secondSquare) {
		this.secondSquare = secondSquare;
	}

	public int getFirstSquareNum() {
		return firstSquareNum;
	}

	public void setFirstSquareNum(int firstSquareNum) {
		this.firstSquareNum = firstSquareNum;
	}

	public int getSecondSquareNum() {
		return secondSquareNum;
	}

	public void setSecondSquareNum(int secondSquareNum) {
		this.secondSquareNum = secondSquareNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}