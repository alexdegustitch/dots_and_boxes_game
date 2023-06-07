package etf.dotsandboxes.pa160417d;

import java.util.ArrayList;

public class EasyPlayer {

	private ArrayList<Line> lines = new ArrayList<Line>();

	private ArrayList<Boolean> isClicked = new ArrayList<Boolean>();

	public EasyPlayer(ArrayList<Line> lines, ArrayList<Boolean> isClicked) {
		this.lines = lines;
		this.isClicked = isClicked;
	}
	
	private int evaluation_function = -1;
	public int play_move() {
	
		int num = (int) (Math.random() * lines.size());
		
		for (int i = num; i < lines.size(); i++) {
			Line l = lines.get(i);
			ArrayList<Integer> list = l.getFirstSquare();
			boolean ok = true;
			if (list.size() > 0 && !isClicked.get(i)) {
				for (int j = 0; j < list.size(); j++) {
					if (!isClicked.get(list.get(j))) {
						ok = false;
					}
				}
				if (ok) {
					evaluation_function = 1;
					return i;
				}
			}

			ok = true;
			list = l.getSecondSquare();
			if (list.size() > 0 && !isClicked.get(i)) {
				for (int j = 0; j < list.size(); j++) {
					if (!isClicked.get(list.get(j))) {
						ok = false;
					}
				}
				if (ok) {
					evaluation_function = 1;
					return i;
				}
			}
		}
		for (int i = 0; i < num; i++) {
			Line l = lines.get(i);
			ArrayList<Integer> list = l.getFirstSquare();
			boolean ok = true;
			if (list.size() > 0 && !isClicked.get(i)) {
				for (int j = 0; j < list.size(); j++) {
					if (!isClicked.get(list.get(j))) {
						ok = false;
					}
				}
				if (ok) {
					evaluation_function = 1;
					return i;
				}
			}

			ok = true;
			list = l.getSecondSquare();
			if (list.size() > 0 && !isClicked.get(i)) {
				for (int j = 0; j < list.size(); j++) {
					if (!isClicked.get(list.get(j))) {
						ok = false;
					}
				}
				if (ok) {
					evaluation_function = 1;
					return i;
				}
			}
		}
		//System.out.println("STIGAO SAM OVDE");
		num = (int) (Math.random() * lines.size());
		for (int i = num; i < lines.size(); i++) {
			if (!isClicked.get(i)) {
				evaluation_function = 0;
				return i;
			}
		}

		for (int i = 0; i < num; i++) {
			if (!isClicked.get(i)) {
				evaluation_function = 0;
				return i;
			}
		}
		return -1;
	}
	
	public int get_evalution_function() {
		return evaluation_function;
				
	}
}
