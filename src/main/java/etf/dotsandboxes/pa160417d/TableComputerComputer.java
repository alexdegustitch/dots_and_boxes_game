package etf.dotsandboxes.pa160417d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class TableComputerComputer extends JPanel {

	ArrayList<Rectangle2D> lines = new ArrayList<Rectangle2D>();
	ArrayList<Boolean> isClicked = new ArrayList<Boolean>();
	ArrayList<Rectangle2D> rects = new ArrayList<Rectangle2D>();
	ArrayList<Integer> isColored = new ArrayList<Integer>();

	ArrayList<Line> linesList = new ArrayList<Line>();

	private int m, n;
	private int width, lenght;
	private int entered = -1;
	// private boolean changed = false;
	private Graphics2D graph;

	Rectangle rec;
	int blue_player_points = 0;
	int red_player_points = 0;
	boolean blue_player_turn = true;
	static JLabel blue_points, red_points;
	static JLabel blue_player_turn_icon, red_player_turn_icon;
	boolean computer_turn = false;
	boolean done = false;

	int depth;
	int level;

	int evaluation_function;
	// Semaphore mutex = new Semaphore(1);
	// algoritmi

	Integer sync = 1;
	EasyPlayer easy_player;
	MediumPlayer medium_player;
	HardPlayer hard_player;
	boolean game_over = false;
	StringBuilder str = new StringBuilder();

	public TableComputerComputer(int m, int n, int depth, int level) {

		this.m = m;
		this.n = n;
		str.append(m + " " + n + "\n");
		width = 7;
		lenght = 80 * 500 / (3 * (47 * n + 7));
		width = 7 * lenght / 40;
		initLines();
		/*
		 * addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent e) { if (blue_player_turn) {
		 * for (int i = 0; i < lines.size(); i++) { if (!isClicked.get(i) &&
		 * lines.get(i).contains(e.getX(), e.getY())) { isClicked.set(i, true);
		 * linesList.get(i).setClicked(true); boolean play_again = good_move(i);
		 * 
		 * boolean game_over = true;
		 * 
		 * for (int j = 0; j < isColored.size(); j++) { if (isColored.get(j) == 0) {
		 * game_over = false; break; } }
		 * 
		 * repaint();
		 * 
		 * blue_points.setText(Integer.toString(blue_player_points));
		 * red_points.setText(Integer.toString(red_player_points));
		 * 
		 * done = false; if (!play_again) { str.append(linesList.get(i).getName() +
		 * "//prvi igrac povlaci liniju\n"); blue_player_turn = !blue_player_turn; }
		 * else { str.append(linesList.get(i).getName() +
		 * "//prvi igrac povlaci liniju, formira kvadrat i igra ponovo\n"); }
		 * 
		 * if (game_over) { try { writo_to_file(); } catch (IOException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); } }
		 * 
		 * if (blue_player_turn) { blue_player_turn_icon.setVisible(true);
		 * red_player_turn_icon.setVisible(false); } else {
		 * blue_player_turn_icon.setVisible(false);
		 * red_player_turn_icon.setVisible(true); // ovde krece da igra computer
		 * computer_move(); }
		 * 
		 * break; } } }
		 * 
		 * } });
		 */

		/*
		 * for (int i = 0; i < linesList.size(); i++) { System.out.println("Broj: " +
		 * i); System.out.println(linesList.get(i).getFirstSquare() + "-" +
		 * linesList.get(i).getFirstSquareNum()); System.out.println();
		 * System.out.println(linesList.get(i).getSecondSquare() + "-" +
		 * linesList.get(i).getSecondSquareNum());
		 * 
		 * }
		 */

		this.depth = depth;
		this.level = level;

		easy_player = new EasyPlayer(linesList, isClicked);
		medium_player = new MediumPlayer(linesList, this.depth);
		hard_player = new HardPlayer(linesList, this.depth);
	}

	private void writo_to_file() throws IOException {

		File file = new File("game.txt");

		if (!file.exists()) {
			file.createNewFile();
		}

		PrintWriter pw = new PrintWriter(file);
		pw.print(str.toString());
		pw.close();
		System.out.println("DONE");
	}

	public void computer_move() {

		boolean play_again = true;
		int move = -1;
		while (play_again) {

			switch (level) {
			case 0:
				move = easy_player.play_move();
				evaluation_function = easy_player.get_evalution_function();
				break;
			case 1:
				move = medium_player.play_move(red_player_points, blue_player_points);
				evaluation_function = medium_player.get_evaluation_function();
				break;
			case 2:
				move = hard_player.play_move(red_player_points, blue_player_points);
				evaluation_function = hard_player.get_evaluation_function();
				break;
			}
			if (move != -1) {
				linesList.get(move).setClicked(true);
				isClicked.set(move, true);
				play_again = good_move(move);
			} else {
				play_again = false;
			}

			repaint();

			blue_points.setText(Integer.toString(blue_player_points));
			red_points.setText(Integer.toString(red_player_points));

		}

		blue_player_turn = !blue_player_turn;

		if (blue_player_turn) {
			blue_player_turn_icon.setVisible(true);
			red_player_turn_icon.setVisible(false);
		} else {
			blue_player_turn_icon.setVisible(false);
			red_player_turn_icon.setVisible(true);
		}

		game_over = true;
		for (int i = 0; i < isColored.size(); i++) {
			if (isColored.get(i) == 0) {
				game_over = false;
				break;
			}
		}

		if (game_over) {
			try {
				writo_to_file();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private boolean good_move(int i) {
		Line l = linesList.get(i);
		ArrayList<Integer> list = l.getFirstSquare();
		boolean ok = true;
		boolean play_again = false;
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (!linesList.get(list.get(j)).isClicked()) {
					ok = false;
				}
			}
			if (ok) {
				play_again = true;
				if (blue_player_turn) {
					isColored.set(l.getFirstSquareNum(), 1);
					blue_player_points++;
					str.append(linesList.get(i).getName()
							+ "//prvi igrac povlaci liniju, formira kvadrat i igra ponovo\n");
				} else {
					isColored.set(l.getFirstSquareNum(), 2);
					red_player_points++;
					str.append(linesList.get(i).getName()
							+ "//drugi igrac povlaci liniju, formira kvadrat i igra ponovo\n");
				}

			} else {
				if (blue_player_turn) {
					str.append(linesList.get(i).getName() + "//prvi igrac povlaci liniju\n");
				} else {
					str.append(linesList.get(i).getName() + "//drugi igrac povlaci liniju\n");
				}
			}
		}

		ok = true;
		list = l.getSecondSquare();
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (!linesList.get(list.get(j)).isClicked()) {
					ok = false;
				}
			}
			if (ok) {
				play_again = true;
				if (blue_player_turn) {
					isColored.set(l.getSecondSquareNum(), 1);
					blue_player_points++;
					str.append(linesList.get(i).getName()
							+ "//prvi igrac povlaci liniju, formira kvadrat i igra ponovo\n");
				} else {
					isColored.set(l.getSecondSquareNum(), 2);
					red_player_points++;
					str.append(linesList.get(i).getName()
							+ "//drugi igrac povlaci liniju, formira kvadrat i igra ponovo\n");
				}
			} else {
				if (blue_player_turn) {
					str.append(linesList.get(i).getName() + "//prvi igrac povlaci liniju\n");
				} else {
					str.append(linesList.get(i).getName() + "//drugi igrac povlaci liniju\n");
				}
			}
		}
		return play_again;
	}

	private void initLines() {
		int dx, dy;
		rec = getBounds();
		System.out.print(rec.getHeight());
		dx = 500 / 6;
		dy = 500 / 6;
		int id = 0, k1, k2;
		// int pom = 5;
		for (int i = 0; i < m + 1; i++) {
			for (int j = 0; j < n; j++) {

				Rectangle2D line = new Rectangle2D.Double(dx + j * (lenght + width) + width, dy, lenght, width);
				lines.add(line);
				String name = Integer.toString(i) + NumberConverter.getLetter(j);

				isClicked.add(false);

				// dodavanje crte

				Line myLine = new Line(id);
				myLine.setName(name);
				k1 = (m + 1) * n + ((int) (id / n - 1)) * (n + 1) + id % n;
				k2 = (m + 1) * n + ((int) (id / n)) * (n + 1) + id % n;

				if (id >= n) {
					myLine.getFirstSquare().add(k1);
					myLine.getFirstSquare().add(k1 + 1);
					myLine.getFirstSquare().add(id - n);
					myLine.setFirstSquareNum(id - n);
				}

				if (id < n * m) {
					myLine.getSecondSquare().add(k2);
					myLine.getSecondSquare().add(k2 + 1);
					myLine.getSecondSquare().add(id + n);
					myLine.setSecondSquareNum(id);
				}

				id++;

				linesList.add(myLine);

				if (i < m) {
					Rectangle2D rect = new Rectangle2D.Double(dx + j * (lenght + width) + width, dy + width, lenght,
							lenght);

					rects.add(rect);
					isColored.add(0);
				}

			}
			dy += lenght + width;
			dx = 500 / 6;
		}

		dx = 500 / 6;
		dy = 500 / 6 + width;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n + 1; j++) {

				Rectangle2D line = new Rectangle2D.Double(dx + j * (lenght + width), dy, width, lenght);

				lines.add(line);

				String name = NumberConverter.getLetter(i) + Integer.toString(j);
				isClicked.add(false);

				// dodavanje crte

				Line myLine = new Line(id);
				myLine.setName(name);
				k2 = (int) ((id - (m + 1) * n) / (n + 1)) * n + (id - ((m + 1) * n + i * (n + 1)));

				k1 = k2 - 1;

				int br = (int) ((id - (m + 1) * n) / (n + 1)) * n + id - ((m + 1) * n + i * (n + 1) + 1);

				if (id > (m + 1) * n + i * (n + 1)) {
					myLine.getFirstSquare().add(k1);
					myLine.getFirstSquare().add(k1 + n);
					myLine.getFirstSquare().add(id - 1);
					myLine.setFirstSquareNum(br);
				}

				if (id < (m + 1) * n + (i + 1) * (n + 1) - 1) {
					myLine.getSecondSquare().add(k2);
					myLine.getSecondSquare().add(k2 + n);
					myLine.getSecondSquare().add(id + 1);
					myLine.setSecondSquareNum(br + 1);
				}

				id++;

				linesList.add(myLine);
			}

			dy += lenght + width;
			dx = 500 / 6;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		graph = (Graphics2D) g;

		for (int i = 0; i < lines.size(); i++) {
			if (linesList.get(i).isClicked()) {
				graph.setColor(new Color(134, 136, 138));
			} else if (entered == i) {
				graph.setColor(Color.RED);
			} else {
				graph.setColor(new Color(220, 220, 220));
			}

			graph.draw(lines.get(i));
			graph.fill(lines.get(i));
		}
		// graphic2d.fillRect(1000, 50, 60, 80);

		// graphic2d.draw(new Line2D.Double(10, 10, 20, 20));

		for (int i = 0; i < rects.size(); i++) {
			switch (isColored.get(i)) {
			case 0:
				graph.setColor(getBackground());
				break;
			case 1:
				graph.setColor(new Color(198, 229, 247));
				break;
			case 2:
				graph.setColor(new Color(252, 168, 159));
				break;
			}
			graph.fill(rects.get(i));

		}

		/*
		 * if (!blue_player_turn) { computer_move(); }
		 */
		done = true;

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Dots and Boxes");
		// frame.setLayout(new BorderLayout());
		JPanel resultPanel = new JPanel(new GridLayout(1, 2));

		JPanel blue_player_panel = new JPanel(new GridLayout(1, 3));

		blue_player_turn_icon = new JLabel();
		blue_player_turn_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon(Table.class.getResource("bulb.png"))
				.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

		blue_player_turn_icon.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel blue_player_icon = new JLabel();
		blue_player_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon(Table.class.getResource("blue_player.png"))
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		blue_player_icon.setHorizontalAlignment(SwingConstants.RIGHT);
		blue_points = new JLabel("0", SwingConstants.LEFT);

		blue_points.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));

		blue_player_panel.add(blue_player_turn_icon);
		blue_player_panel.add(blue_player_icon);
		blue_player_panel.add(blue_points);

		JPanel red_player_panel = new JPanel(new GridLayout(1, 3));

		red_player_turn_icon = new JLabel();
		red_player_turn_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon(Table.class.getResource("bulb.png"))
				.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

		red_player_turn_icon.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel red_player_icon = new JLabel();
		red_player_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon(Table.class.getResource("red_player.png"))
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		red_player_icon.setHorizontalAlignment(SwingConstants.LEFT);
		red_points = new JLabel("0", SwingConstants.RIGHT);

		red_points.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));

		red_player_panel.add(red_points);
		red_player_panel.add(red_player_icon);
		red_player_panel.add(red_player_turn_icon);
		red_player_turn_icon.setVisible(false);

		resultPanel.add(blue_player_panel);
		resultPanel.add(red_player_panel);
		frame.add(resultPanel, BorderLayout.NORTH);

		TableComputerComputer tabC_C = new TableComputerComputer(5, 5, 4, 2);
		frame.add(tabC_C, BorderLayout.CENTER);

		JPanel southPanel = new JPanel(new GridLayout(2, 1));

		// JPanel functionPanel = new JPanel();
		JLabel evolution_label = new JLabel("Funkcija procene: 0");
		evolution_label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		evolution_label.setHorizontalAlignment(SwingConstants.CENTER);
		southPanel.add(evolution_label);
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		JButton next_move = new JButton("Next move");
		JButton play_all_moves = new JButton("Play all moves");

		next_move.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabC_C.computer_move();
				int eval_num = tabC_C.evaluation_function;
				evolution_label.setText("Funkcija procene: " + Integer.toString(eval_num));
				if (tabC_C.game_over) {
					next_move.setEnabled(false);
					play_all_moves.setEnabled(false);
				}
			}
		});

		play_all_moves.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				while (!tabC_C.game_over) {
					tabC_C.computer_move();
					int eval_num = tabC_C.evaluation_function;
					evolution_label.setText("Funkcija procene: " + Integer.toString(eval_num));
				}
				next_move.setEnabled(false);
				play_all_moves.setEnabled(false);

			}
		});
		buttonPanel.add(next_move);
		buttonPanel.add(play_all_moves);

		southPanel.add(buttonPanel);
		frame.add(southPanel, BorderLayout.SOUTH);

		frame.setSize(500, 500);
		frame.setBounds(500, 300, 500, 600);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
