package etf.dotsandboxes.pa160417d;

import java.util.ArrayList;

public class MediumPlayer {

	private int depth;

	private ArrayList<Line> lines = new ArrayList<Line>();

	// private ArrayList<Boolean> isClicked = new ArrayList<Boolean>();
	// nisam siguran sada da li mi je isClicked potrebno
	private Node root;

	private boolean firstRow = true;

	int alfa, beta;
	// treba da im se dodele pocetne vrednosti

	int current_player;

	int points, opponent_points;
	
	int evaluation_function = 0;

	public MediumPlayer(ArrayList<Line> lines, int depth) {

		this.lines = lines;
		this.depth = depth;

		// vezano za polja itd
		// this.current_player = current_player;
		// this.opponent_points = opponent_points;
		// this.points = points;

	}

	public int play_move(int points, int opponent_points) {

		int value = play_move();

		if (value >= 0) {
			evaluation_function = value;
			return value;
		}
		this.opponent_points = opponent_points;
		this.points = points;

		ArrayList<Line> state = new ArrayList<Line>();

		state = assign_by_reference(lines);

		root = new Node(depth, -1, null, Node.MAX, state);
		root.setEvolutionFunction(points - opponent_points);
		root.setDepth(0);

		firstRow = true;
		value = minimax(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
		evaluation_function = value;
		for (int i = 0; i < root.getChildren().size(); i++) {
			if (root.getChildren().get(i).getEvolutionFunction() == value) {
				return root.getChildren().get(i).getMoveLine();
			}
		}
		return -1;
	}

	private int minimax(Node currentNode, int alfa, int beta) {
		int best_value = 0;
		int current_value;
		if (currentNode.isLeaf() || currentNode.getDepth() == depth) {
			return currentNode.getEvolutionFunction();
			// ovde treba da se izracuna ta funkcija, napisati funkciju
		}

		if (currentNode.getType() == Node.MAX) {
			best_value = Integer.MIN_VALUE;
		}

		if (currentNode.getType() == Node.MIN) {
			best_value = Integer.MAX_VALUE;
		}

		int num = -1;
		boolean entered = false;
		if (firstRow) {
			firstRow = false;
			entered = true;
			num = 0;
			for (int i = 0; i < currentNode.getState().size(); i++) {
				if (!currentNode.getState().get(i).isClicked()) {
					if (!bad_move(currentNode.getState(), i)) {
						num++;
						// racunanje typa
						ArrayList<Line> state = new ArrayList<Line>();

						state = assign_by_reference(currentNode.getState());

						state.get(i).setClicked(true);
						Node newNode;

						if (good_move(currentNode.getState(), i)) {
							newNode = new Node(currentNode.getDepth() + 1, i, currentNode, currentNode.getType(),
									state);

							if (currentNode.getType() == Node.MAX) {
								newNode.setEvolutionFunction(currentNode.getEvolutionFunction() + 1);
							} else {
								newNode.setEvolutionFunction(currentNode.getEvolutionFunction() - 1);
							}

						} else if (currentNode.getType() == Node.MAX) {
							newNode = new Node(currentNode.getDepth() + 1, i, currentNode, Node.MIN, state);
							newNode.setEvolutionFunction(currentNode.getEvolutionFunction());
						} else {
							newNode = new Node(currentNode.getDepth() + 1, i, currentNode, Node.MAX, state);
							newNode.setEvolutionFunction(currentNode.getEvolutionFunction());
						}

						currentNode.getChildren().add(newNode);
						current_value = minimax(newNode, alfa, beta);

						if (currentNode.getType() == Node.MAX) {
							if (current_value > best_value) {
								best_value = current_value;
							}

							if (alfa < current_value) {
								alfa = current_value;
							}

							currentNode.setEvolutionFunction(best_value);
							if (beta <= alfa) {
								break;
							}

						}
						if (currentNode.getType() == Node.MIN) {
							if (best_value > current_value) {
								best_value = current_value;
							}

							if (beta > current_value) {
								beta = current_value;
							}

							currentNode.setEvolutionFunction(best_value);
							if (beta <= alfa) {
								break;
							}

						}
					}
				}
			}
		}

		if ((!firstRow && !entered) || num == 0) {
			for (int i = 0; i < currentNode.getState().size(); i++) {
				if (!currentNode.getState().get(i).isClicked()) {

					// racunanje typa
					ArrayList<Line> state = new ArrayList<Line>();

					state = assign_by_reference(currentNode.getState());

					state.get(i).setClicked(true);
					Node newNode;

					if (good_move(currentNode.getState(), i)) {
						newNode = new Node(currentNode.getDepth() + 1, i, currentNode, currentNode.getType(), state);

						if (currentNode.getType() == Node.MAX) {
							newNode.setEvolutionFunction(currentNode.getEvolutionFunction() + 1);
						} else {
							newNode.setEvolutionFunction(currentNode.getEvolutionFunction() - 1);
						}

					} else if (currentNode.getType() == Node.MAX) {
						newNode = new Node(currentNode.getDepth() + 1, i, currentNode, Node.MIN, state);
						newNode.setEvolutionFunction(currentNode.getEvolutionFunction());
					} else {
						newNode = new Node(currentNode.getDepth() + 1, i, currentNode, Node.MAX, state);
						newNode.setEvolutionFunction(currentNode.getEvolutionFunction());
					}

					currentNode.getChildren().add(newNode);
					current_value = minimax(newNode, alfa, beta);

					if (currentNode.getType() == Node.MAX) {
						if (current_value > best_value) {
							best_value = current_value;
						}

						if (alfa < current_value) {
							alfa = current_value;
						}
						currentNode.setEvolutionFunction(best_value);
						if (beta <= alfa) {
							break;
						}

					}
					if (currentNode.getType() == Node.MIN) {
						if (best_value > current_value) {
							best_value = current_value;
						}

						if (beta > current_value) {
							beta = current_value;
						}

						currentNode.setEvolutionFunction(best_value);
						if (beta <= alfa) {
							break;
						}

					}
				}
			}
		}

		return best_value;
	}

	private boolean bad_move(ArrayList<Line> lines, int i) {
		Line l = lines.get(i);
		ArrayList<Integer> list = l.getFirstSquare();
		int num = 0;
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (lines.get(list.get(j)).isClicked()) {
					num++;
				}
			}
			if (num == 2) {
				return true;
			}
		}

		num = 0;
		list = l.getSecondSquare();
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (lines.get(list.get(j)).isClicked()) {
					num++;
				}
			}
			if (num == 2) {
				return true;
			}
		}
		return false;
	}

	private boolean good_move(ArrayList<Line> lines, int i) {
		Line l = lines.get(i);
		ArrayList<Integer> list = l.getFirstSquare();
		boolean good = true;
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (!lines.get(list.get(j)).isClicked()) {
					good = false;
					break;
				}
			}
			if (good) {
				return true;
			}
		}

		good = true;
		list = l.getSecondSquare();
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (!lines.get(list.get(j)).isClicked()) {
					good = false;
					break;
				}
			}
			if (good) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<Line> assign_by_reference(ArrayList<Line> assignFrom) {
		ArrayList<Line> assignTo = new ArrayList<Line>();
		for (int i = 0; i < assignFrom.size(); i++) {
			Line l = new Line(assignFrom.get(i).getId());
			l.setClicked(assignFrom.get(i).isClicked());
			l.setFirstSquareNum(assignFrom.get(i).getFirstSquareNum());
			l.setSecondSquareNum(assignFrom.get(i).getSecondSquareNum());
			ArrayList<Integer> list1 = new ArrayList<Integer>();
			for (int j = 0; j < assignFrom.get(i).getFirstSquare().size(); j++) {
				list1.add(assignFrom.get(i).getFirstSquare().get(j));
			}

			ArrayList<Integer> list2 = new ArrayList<Integer>();
			for (int j = 0; j < assignFrom.get(i).getSecondSquare().size(); j++) {
				list2.add(assignFrom.get(i).getSecondSquare().get(j));
			}
			l.setFirstSquare(list1);
			l.setSecondSquare(list2);

			assignTo.add(l);
		}
		return assignTo;
	}

	private int play_move() {
		int num = (int) (Math.random() * lines.size());

		for (int i = num; i < lines.size(); i++) {
			Line l = lines.get(i);
			ArrayList<Integer> list = l.getFirstSquare();
			boolean ok = true;
			if (list.size() > 0 && !lines.get(i).isClicked()) {
				for (int j = 0; j < list.size(); j++) {
					if (!lines.get(list.get(j)).isClicked()) {
						ok = false;
					}
				}
				if (ok) {
					return i;
				}
			}

			ok = true;
			list = l.getSecondSquare();
			if (list.size() > 0 && !lines.get(i).isClicked()) {
				for (int j = 0; j < list.size(); j++) {
					if (!lines.get(list.get(j)).isClicked()) {
						ok = false;
					}
				}
				if (ok) {
					return i;
				}
			}
		}
		for (int i = 0; i < num; i++) {
			Line l = lines.get(i);
			ArrayList<Integer> list = l.getFirstSquare();
			boolean ok = true;
			if (list.size() > 0 && !lines.get(i).isClicked()) {
				for (int j = 0; j < list.size(); j++) {
					if (!lines.get(list.get(j)).isClicked()) {
						ok = false;
					}
				}
				if (ok) {
					return i;
				}
			}

			ok = true;
			list = l.getSecondSquare();
			if (list.size() > 0 && !lines.get(i).isClicked()) {
				for (int j = 0; j < list.size(); j++) {
					if (!lines.get(list.get(j)).isClicked()) {
						ok = false;
					}
				}
				if (ok) {
					return i;
				}
			}
		}

		return -1;
	}

	
	public int get_evaluation_function() {
		return evaluation_function;
	}
}
