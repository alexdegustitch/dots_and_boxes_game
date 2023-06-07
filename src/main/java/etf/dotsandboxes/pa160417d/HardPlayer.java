package etf.dotsandboxes.pa160417d;

import java.util.ArrayList;

public class HardPlayer {
	private int depth;

	private ArrayList<Line> lines = new ArrayList<Line>();

	private Node root;

	private boolean firstRow = true;

	int alfa, beta;

	int current_player;

	int points, opponent_points;

	ArrayList<Line> finish_all_list;

	// private boolean advanced_mode = false;

	boolean num0_found = true;

	
	int evaluation_function = 0;
	
	public HardPlayer(ArrayList<Line> lines, int depth) {
		this.lines = lines;
		this.depth = depth;
	}

	private int advanced_minimax() {
		ArrayList<Chain> chains = find_chains(lines);

		evaluation_function =  calculate_evaluation_function(chains);
		int num_closed = 0;
		int num_half_opened = 0;
		int num_opened = 0;

		int sum_opened = 0;

		int min_closed = Integer.MAX_VALUE;

		int min_half_opened = Integer.MAX_VALUE;

		for (int i = 0; i < chains.size(); i++) {
			switch (chains.get(i).getType()) {
			case 0:
				num_closed++;
				if (min_closed > chains.get(i).getSize()) {
					min_closed = chains.get(i).getSize();
				}
				break;
			case 1:
				num_half_opened++;
				if (min_half_opened > chains.get(i).getSize()) {
					min_half_opened = chains.get(i).getSize();
				}
				break;
			case 2:
				num_opened++;
				sum_opened += chains.get(i).getSize();
				break;
			}
		}

		if (num_closed == 1 && min_closed == 3 && (sum_opened > 4 * num_opened) && num_half_opened == 0) {
			for (int i = 0; i < chains.size(); i++) {
				if (chains.get(i).getType() == Chain.CLOSED) {
					return chains.get(i).getLines().get(1).getId();
				}
			}
		}

		for (int i = 0; i < chains.size(); i++) {
			if (chains.get(i).getType() == Chain.CLOSED && chains.get(i).getSize() == min_closed) {
				return chains.get(i).getLines().get(0).getId();
			}
		}

		num_half_opened = 0;
		num_opened = 0;
		min_half_opened = Integer.MAX_VALUE;
		for (int i = 0; i < chains.size(); i++) {
			if (chains.get(i).getType() == Chain.HALF_OPENED) {
				num_half_opened++;
				if (chains.get(i).getSize() < min_half_opened) {
					min_half_opened = chains.get(i).getSize();
				}
			} else if (chains.get(i).getType() == Chain.OPENED) {
				num_opened++;
			}
		}

		for (int i = 0; i < chains.size(); i++) {
			if (chains.get(i).getType() == Chain.HALF_OPENED && chains.get(i).getSize() == min_half_opened) {

				if (chains.get(i).getSize() != 2 || num_half_opened > 1 || (num_opened == 0 && num_half_opened == 1)) {
					return chains.get(i).getStart_num();

				} else if (chains.get(i).getSize() == 2) {
					return chains.get(i).getLast_num();
				}

			}
		}

		int min = Integer.MAX_VALUE;
		for (int i = 0; i < chains.size(); i++) {
			if (chains.get(i).getType() == Chain.OPENED) {
				if (min > chains.get(i).getSize()) {
					min = chains.get(i).getSize();
				}
			}
		}

		
		for (int i = 0; i < chains.size(); i++) {
			if (chains.get(i).getType() == Chain.OPENED && chains.get(i).getSize() == min) {
				return chains.get(i).getLines().get(min - 1).getId();
			}
		}

		return -1;
	}

	private void finish_game(Line l) {

		ArrayList<Integer> list1 = l.getFirstSquare();
		ArrayList<Integer> list2 = l.getSecondSquare();

		int num_clicked_first = num_clicked(finish_all_list, l.getId(), true);
		int num_clicked_second = num_clicked(finish_all_list, l.getId(), false);

		if (num_clicked_first == 3 || num_clicked_second == 3) {
			finish_all_list.get(l.getId()).setClicked(true);

			for (int i = 0; i < finish_all_list.size(); i++) {
				if (!finish_all_list.get(i).isClicked()) {
					// finish_all_list.get(i).setClicked(true);
					finish_game(finish_all_list.get(i));

				}
			}
		}

	}

	private int play_clever_minimax() {
		Line l = last_move(lines);
		ArrayList<Integer> list1 = l.getFirstSquare();
		ArrayList<Integer> list2 = l.getSecondSquare();
		ArrayList<Integer> list;
		ArrayList<Chain> chains;

		boolean first = true;
		for (int i = 0; i > list1.size(); i++) {
			if (lines.get(list1.get(i)).isClicked()) {
				first = false;
			}
		}

		if (list1 == null || list1.size() == 0) {
			list = list2;
		} else if (list2 == null || list2.size() == 0) {
			list = list1;
		} else if (first) {
			list = list1;
		} else {
			list = list2;
		}

		ArrayList<Line> lines_helper = assign_by_reference(lines);
		Chain min_chain = new Chain();

		int num = Integer.MAX_VALUE;
		
		chains = find_chains(lines_helper);
evaluation_function = calculate_evaluation_function(chains);
		for (int i = 0; i < chains.size(); i++) {

			if (chains.get(i).getType() == Chain.HALF_OPENED) {
				boolean found = true;
				int first_num = chains.get(i).getLines().get(0).getId();
				int second_num = chains.get(i).getLines().get(chains.get(i).getLines().size() - 1).getId();
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j) == first_num || list.get(j) == second_num) {
						found = false;
						break;
					}
				}
				if (found) {
					return first_num;
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			lines_helper.get(list.get(i)).setClicked(true);
			if (good_move(lines_helper, list.get(i))) {
				return list.get(i);
			}
			chains = find_chains(lines_helper);
			evaluation_function = calculate_evaluation_function(chains);
			Chain chain = find_smallest_closed_chain(chains);
			if (chain != null
					&& (chain.getSize() >= 2 && chain.getType() == Chain.CLOSED || chain.getType() == Chain.HALF_OPENED)
					&& chain.getSize() < num) {
				num = chain.getSize();
				min_chain = chain;
			}
			lines_helper.get(list.get(i)).setClicked(false);
		}

		if (min_chain.getSize() > 0) {
			return min_chain.getLines().get(min_chain.getSize() - 1).getId();
		} else {
			return -1;
		}
	}

	private Chain find_smallest_closed_chain(ArrayList<Chain> chains) {
		int min = Integer.MAX_VALUE;
		int min_i = -1;
		for (int i = 0; i < chains.size(); i++) {
			if ((chains.get(i).getType() == Chain.CLOSED || chains.get(i).getType() == Chain.HALF_OPENED)
					&& chains.get(i).getSize() < min) {
				min = chains.get(i).getSize();
				min_i = i;
			}
		}

		if (min_i != -1) {
			return chains.get(min_i);
		} else {
			return null;
		}

	}

	public int play_move(int points, int opponent_points) {
		int value = -1;
		finish_all_list = assign_by_reference(lines);
		for (int i = 0; i < finish_all_list.size(); i++) {
			if (!finish_all_list.get(i).isClicked()) {
				// finish_all_list.get(i).setClicked(true);
				finish_game(finish_all_list.get(i));

			}
		}
		boolean done = true;
		for (int i = 0; i < finish_all_list.size(); i++) {
			if (!finish_all_list.get(i).isClicked()) {
				done = false;
				// break;
			}
		}

		if (done) {
			value = play_move();
			// System.out.println("MOZE DA ZAVRSI SVE");
			num0_found = false;
			return value;
		}
		int moves_left = moves_left(lines);

		if (moves_left > 4) {
			num0_found = false;
			value = play_move();
		} else if (moves_left == 0) {
			value = advanced_minimax();
		} else if (moves_left == 4) {
			if (num0_found) {
				num0_found = false;
				value = play_move();
			} else {
				num0_found = false;
				value = play_clever_minimax();
			}
		}

		if (value >= 0) {
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
			return currentNode.getEvolutionFunction() + currentNode.getChains_points();
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

						int check = moves_left(state);
						int eval_func = 0;

						ArrayList<Chain> chains = new ArrayList<Chain>();

						/*
						 * if (check) { chains = find_chains(state); eval_func =
						 * calculate_evaluation_function(chains);
						 * 
						 * }
						 */

						if (good_move(currentNode.getState(), i)) {
							newNode = new Node(currentNode.getDepth() + 1, i, currentNode, currentNode.getType(),
									state);

							if (currentNode.getType() == Node.MAX) {
								newNode.setEvolutionFunction(currentNode.getEvolutionFunction() + 1);
								newNode.setChains(chains);
								newNode.setChains_points(eval_func);
							} else {
								newNode.setEvolutionFunction(currentNode.getEvolutionFunction() - 1);
								newNode.setChains(chains);
								newNode.setChains_points(-eval_func);
							}

						} else if (currentNode.getType() == Node.MAX) {
							newNode = new Node(currentNode.getDepth() + 1, i, currentNode, Node.MIN, state);
							newNode.setEvolutionFunction(currentNode.getEvolutionFunction());
							newNode.setChains(chains);
							newNode.setChains_points(-eval_func);
						} else {
							newNode = new Node(currentNode.getDepth() + 1, i, currentNode, Node.MAX, state);
							newNode.setEvolutionFunction(currentNode.getEvolutionFunction());
							newNode.setChains(chains);
							newNode.setChains_points(eval_func);
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

					int check = moves_left(state);
					int eval_func = 0;

					ArrayList<Chain> chains = new ArrayList<Chain>();

					/*
					 * if (check) { chains = find_chains(state); eval_func =
					 * calculate_evaluation_function(chains);
					 * 
					 * }
					 */

					if (good_move(currentNode.getState(), i)) {
						newNode = new Node(currentNode.getDepth() + 1, i, currentNode, currentNode.getType(), state);

						if (currentNode.getType() == Node.MAX) {
							newNode.setEvolutionFunction(currentNode.getEvolutionFunction() + 1);
							newNode.setChains(chains);
							newNode.setChains_points(eval_func);
						} else {
							newNode.setEvolutionFunction(currentNode.getEvolutionFunction() - 1);
							newNode.setChains(chains);
							newNode.setChains_points(-eval_func);
						}

					} else if (currentNode.getType() == Node.MAX) {
						newNode = new Node(currentNode.getDepth() + 1, i, currentNode, Node.MIN, state);
						newNode.setEvolutionFunction(currentNode.getEvolutionFunction());
						newNode.setChains(chains);
						newNode.setChains_points(-eval_func);
					} else {
						newNode = new Node(currentNode.getDepth() + 1, i, currentNode, Node.MAX, state);
						newNode.setEvolutionFunction(currentNode.getEvolutionFunction());
						newNode.setChains(chains);
						newNode.setChains_points(eval_func);
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

	public int calculate_evaluation_function(ArrayList<Chain> chains) {

		int sum = 0;
		for (int i = 0; i < chains.size(); i++) {
			if (chains.get(i).getType() == Chain.CLOSED) {
				sum += chains.get(i).getSize() + 1;
			}
		}

		ArrayList<Chain> half_opened_chains = new ArrayList<Chain>();
		ArrayList<Chain> opened_chains = new ArrayList<Chain>();

		for (int i = 0; i < chains.size(); i++) {
			if (chains.get(i).getType() == Chain.HALF_OPENED) {
				half_opened_chains.add(chains.get(i));
			} else if (chains.get(i).getType() == Chain.OPENED) {
				half_opened_chains.add(chains.get(i));
			}
		}
		for (int i = 0; i < half_opened_chains.size() - 1; i++) {
			for (int j = i + 1; j < half_opened_chains.size(); j++) {
				if (half_opened_chains.get(i).getSize() > half_opened_chains.get(j).getSize()) {
					Chain temp = half_opened_chains.get(i);
					half_opened_chains.set(i, half_opened_chains.get(j));
					half_opened_chains.set(j, temp);
				}
			}
		}

		for (int i = 0; i < opened_chains.size() - 1; i++) {
			for (int j = i + 1; j < opened_chains.size(); j++) {
				if (opened_chains.get(i).getSize() > opened_chains.get(j).getSize()) {
					Chain temp = opened_chains.get(i);
					opened_chains.set(i, opened_chains.get(j));
					opened_chains.set(j, temp);
				}
			}
		}

		for (int i = 0; i < half_opened_chains.size() - 1; i++) {
			sum += half_opened_chains.get(i).getSize();
		}

		if (half_opened_chains.size() > 0) {
			if (opened_chains.size() == 0) {
				sum += half_opened_chains.get(half_opened_chains.size() - 1).getSize();
			} else {
				sum += half_opened_chains.get(half_opened_chains.size() - 1).getSize() - 2;
			}
		}

		for (int i = 0; i < opened_chains.size() - 1; i++) {
			sum += opened_chains.get(i).getSize() - 3;
		}

		if (opened_chains.size() > 0) {
			sum += opened_chains.get(opened_chains.size() - 1).getSize() - 1;
		}
		return sum;
	}

	private Line last_move(ArrayList<Line> lines) {

		for (int i = 0; i < lines.size(); i++) {
			Line l = lines.get(i);
			ArrayList<Integer> list = l.getFirstSquare();
			int num = 0;
			if (l.isClicked()) {
				num++;
			}
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if (lines.get(list.get(j)).isClicked()) {
						num++;
					}
				}
				if (num < 2 && l.isClicked()) {
					return l;
				}
			}

			num = 0;

			if (l.isClicked()) {
				num++;
			}
			list = l.getSecondSquare();
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if (lines.get(list.get(j)).isClicked()) {
						num++;
					}
				}
				if (num < 2 && l.isClicked()) {
					return l;
				}
			}
		}
		return null;
	}

	private int moves_left(ArrayList<Line> lines) {
		num0_found = false;
		int sum = 0;
		for (int i = 0; i < lines.size(); i++) {
			Line l = lines.get(i);
			ArrayList<Integer> list = l.getFirstSquare();
			int num = 0;
			if (l.isClicked()) {
				num++;
			}
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if (lines.get(list.get(j)).isClicked()) {
						num++;
					}
				}
				if (num == 1 || num == 0) {
					if (num == 0) {
						num0_found = true;
					}
					sum++;
				}
			}

			num = 0;

			if (l.isClicked()) {
				num++;
			}
			list = l.getSecondSquare();
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if (lines.get(list.get(j)).isClicked()) {
						num++;
					}
				}
				if (num == 1 || num == 0) {
					if (num == 0) {
						num0_found = true;
					}
					sum++;
				}
			}
		}
		return sum;
	}

	public boolean isLoop(ArrayList<Line> lines, Line l) {
		int num1_clicked = num_clicked(lines, l.getId(), true);
		int num2_clicked = num_clicked(lines, l.getId(), false);

		int id = l.getId();
		int next_id = 0;
		int prev_id = 0;

		for (int i = 0; i < l.getFirstSquare().size(); i++) {
			if (!lines.get(l.getFirstSquare().get(i)).isClicked()) {
				next_id = l.getFirstSquare().get(i);
				break;
			}
		}

		prev_id = id;

		while (num1_clicked == 2 && num2_clicked == 2) {
			int current_id = next_id;
			Line ll = lines.get(next_id);
			num1_clicked = num_clicked(lines, ll.getId(), true);
			num2_clicked = num_clicked(lines, ll.getId(), false);

			if (ll.getFirstSquare().size() == 0) {
				return false;
			}
			for (int i = 0; i < ll.getFirstSquare().size(); i++) {
				if (!lines.get(ll.getFirstSquare().get(i)).isClicked()) {

					next_id = ll.getFirstSquare().get(i);
					break;
				}
			}

			if (next_id == prev_id) {

				if (ll.getSecondSquare().size() == 0) {
					return false;
				}
				for (int i = 0; i < ll.getSecondSquare().size(); i++) {
					if (!lines.get(ll.getSecondSquare().get(i)).isClicked()) {
						next_id = ll.getSecondSquare().get(i);
						break;
					}
				}
			}
			if (next_id == prev_id) {
				return false;
			}
			prev_id = current_id;

			if (next_id == id) {
				return true;
			}

		}
		return false;
	}

	private ArrayList<Chain> find_chains(ArrayList<Line> lines) {
		ArrayList<Line> help_list = assign_by_reference(lines);
		ArrayList<Chain> chains = new ArrayList<Chain>();
		int first = 0, second = 0;
		Chain chain;
		boolean loop = false;
		for (int i = 0; i < help_list.size(); i++) {
			Line l = help_list.get(i);
			if (!l.isClicked()) {
				int num1 = num_clicked(help_list, i, true);
				int num2 = num_clicked(help_list, i, false);

				if (num1 == 2 && num2 == 2) {
					loop = isLoop(lines, l);
					if (!loop)
						continue;
				}

				ArrayList<Integer> list1 = l.getFirstSquare();
				// ArrayList<Integer> list2 = l.getSecondSquare();
				ArrayList<Line> list_for_chain = new ArrayList<Line>();

				chain = new Chain();
				boolean firstOpen;
				boolean secondOpen = false;
				if (num1 == 0 || num2 == 0) {
					firstOpen = true;
				} else {
					firstOpen = false;
				}

				first = l.getId();
				boolean has_more = true;
				l.setClicked(true);
				int k = i;
			
				int num_iter = 0;
				while (has_more) {
					num_iter++;
					list_for_chain.add(l);
					int firstNumClicked = num_clicked(help_list, k, true);
					int secondNumClicked = num_clicked(help_list, k, false);

					if (firstNumClicked == 2) {
						has_more = true;
						for (int j = 0; j < l.getFirstSquare().size(); j++) {
							if (!help_list.get(l.getFirstSquare().get(j)).isClicked()) {
								k = l.getFirstSquare().get(j);
								break;
							}
						}
						l = help_list.get(k);
						l.setClicked(true);
					} else if (secondNumClicked == 2) {
						has_more = true;
						for (int j = 0; j < l.getSecondSquare().size(); j++) {
							if (!help_list.get(l.getSecondSquare().get(j)).isClicked()) {
								k = l.getSecondSquare().get(j);
								break;
							}
						}
						l = help_list.get(k);
						l.setClicked(true);
					} else if ((firstNumClicked == 0 || secondNumClicked == 0) && num_iter > 1) {
						second = k;
						has_more = false;
						secondOpen = true;
					} else {
						second = k;
						has_more = false;
						secondOpen = false;
					}
				}
				chain.setLines(list_for_chain);
				if (loop) {
					chain.setType(Chain.OPENED);
					chain.setLast_num(chain.getStart_num());

					loop = false;
				} else if (firstOpen && secondOpen) {
					chain.setType(Chain.OPENED);
				} else if (!(firstOpen || secondOpen)) {
					chain.setType(Chain.CLOSED);
				} else {
					if (firstOpen) {
						chain.setStart_num(second);
						chain.setLast_num(first);
					} else if (secondOpen) {
						chain.setStart_num(first);
						chain.setLast_num(second);
					}
					chain.setType(Chain.HALF_OPENED);
				}

				chains.add(chain);
			}
		}
		return chains;
	}

	private int num_clicked(ArrayList<Line> list, int i, boolean first) {
		int num = 0;
		Line l = list.get(i);
		if (first) {
			for (int j = 0; j < l.getFirstSquare().size(); j++) {
				if (list.get(l.getFirstSquare().get(j)).isClicked()) {
					num++;
				}
			}
		} else {
			for (int j = 0; j < l.getSecondSquare().size(); j++) {
				if (list.get(l.getSecondSquare().get(j)).isClicked()) {
					num++;
				}
			}
		}

		return num;
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
					evaluation_function++;
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
					evaluation_function++;
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
					evaluation_function++;
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
					evaluation_function++;
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
