package etf.dotsandboxes.pa160417d;

import java.util.ArrayList;

public class Chain {

	public static final int CLOSED = 0, HALF_OPENED = 1, OPENED = 2;
	private ArrayList<Line> lines = new ArrayList<Line>();
	private int type;

	private int start_num;
	private int last_num;

	public Chain(int type, ArrayList<Line> lines) {
		this.type = type;
		this.lines = lines;
	}

	public Chain() {

	}

	public ArrayList<Line> getLines() {
		return lines;
	}

	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSize() {
		return lines.size();
	}

	public int getStart_num() {
		return start_num;
	}

	public void setStart_num(int start_num) {
		this.start_num = start_num;
	}

	public int getLast_num() {
		return last_num;
	}

	public void setLast_num(int last_num) {
		this.last_num = last_num;
	}

}
