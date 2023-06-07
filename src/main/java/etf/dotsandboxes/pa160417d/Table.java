package etf.dotsandboxes.pa160417d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Table extends JPanel {

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

	//algoritmi
	
	EasyPlayer easy_player;
	
	public Table(int m, int n) {

		this.m = m;
		this.n = n;
		width = 7;
		lenght = 80 * 500 / (3 * (47 * n + 7));
		width = 7 * lenght / 40;
		initLines();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!computer_turn) {
					for (int i = 0; i < lines.size(); i++) {
						if (!isClicked.get(i) && lines.get(i).contains(e.getX(), e.getY())) {
							isClicked.set(i, true);
							boolean play_again = good_move(i);
							repaint();
							blue_points.setText(Integer.toString(blue_player_points));
							red_points.setText(Integer.toString(red_player_points));
							if (!play_again) {
								blue_player_turn = !blue_player_turn;
							}
							if (blue_player_turn) {
								blue_player_turn_icon.setVisible(true);
								red_player_turn_icon.setVisible(false);
							} else {
								blue_player_turn_icon.setVisible(false);
								red_player_turn_icon.setVisible(true);
							}

						}
					}
				}

			}
		});

		for (int i = 0; i < linesList.size(); i++) {
			System.out.println("Broj: " + i);
			System.out.println(linesList.get(i).getFirstSquare() + "-" + linesList.get(i).getFirstSquareNum());
			System.out.println();
			System.out.println(linesList.get(i).getSecondSquare() + "-" + linesList.get(i).getSecondSquareNum());

		}
		
	}
	
	private boolean good_move(int i) {
		Line l = linesList.get(i);
		ArrayList<Integer> list = l.getFirstSquare();
		boolean ok = true;
		boolean play_again = false;
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (!isClicked.get(list.get(j))) {
					ok = false;
				}
			}
			if (ok) {
				play_again = true;
				if (blue_player_turn) {
					isColored.set(l.getFirstSquareNum(), 1);
					blue_player_points++;
				} else {
					isColored.set(l.getFirstSquareNum(), 2);
					red_player_points++;
				}
			}
		}

		ok = true;
		list = l.getSecondSquare();
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (!isClicked.get(list.get(j))) {
					ok = false;
				}
			}
			if (ok) {
				play_again = true;
				if (blue_player_turn) {
					isColored.set(l.getSecondSquareNum(), 1);
					blue_player_points++;
				} else {
					isColored.set(l.getSecondSquareNum(), 2);
					red_player_points++;
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
				isClicked.add(false);

				// dodavanje crte

				Line myLine = new Line(id);
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
				isClicked.add(false);

				// dodavanje crte

				Line myLine = new Line(id);

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
			if (isClicked.get(i)) {
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

		
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Demo");
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

		frame.add(new Table(4, 4), BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setBounds(500, 300, 500, 800);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
