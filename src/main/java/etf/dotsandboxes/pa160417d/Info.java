package etf.dotsandboxes.pa160417d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Info extends JPanel {

	int game_mode = 0;
	int m = 4, n = 4;
	int level = 0;
	int depth = 4;

	public Info() {
		super(new GridLayout(5, 1));
		JRadioButton easy_radio = new JRadioButton("Easy", true);
		JRadioButton med_radio = new JRadioButton("Medium");
		JRadioButton hard_radio = new JRadioButton("Hard");

		JTextField depth_text = new JTextField(3);
		// sve promenljive
		JPanel game_mode_panel = new JPanel(new BorderLayout());
		JLabel game_mode_name_label = new JLabel("Izaberite mode igre", SwingConstants.CENTER);
		game_mode_name_label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		game_mode_panel.add(game_mode_name_label, BorderLayout.NORTH);

		JPanel game_mode_center_panel = new JPanel(new GridLayout(2, 3));

		/*
		 * JLabel p_vs_p = new JLabel("2 players"); JLabel p_vs_c = new
		 * JLabel("player vs computer"); JLabel c_vs_c = new JLabel("2 computers");
		 */

		JLabel picPvsCLabel = new JLabel();
		picPvsCLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("/player_vs_computer.png")
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		picPvsCLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel picPvsPLabel = new JLabel();
		picPvsPLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("/player_vs_player.png")
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		picPvsPLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel picCvsCLabel = new JLabel();
		picCvsCLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("/computer_vs_computer.png")
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		picCvsCLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JRadioButton p_vs_c_radio = new JRadioButton("Player vs computer");
		JRadioButton p_vs_p_radio = new JRadioButton("2 players", true);
		JRadioButton c_vs_c_radio = new JRadioButton("Computer vs computer");

		p_vs_c_radio.setHorizontalAlignment(SwingConstants.CENTER);
		c_vs_c_radio.setHorizontalAlignment(SwingConstants.CENTER);
		p_vs_p_radio.setHorizontalAlignment(SwingConstants.CENTER);

		p_vs_p_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					depth_text.setEditable(false);
					easy_radio.setEnabled(false);
					med_radio.setEnabled(false);
					hard_radio.setEnabled(false);
					game_mode = 0;
				}

			}
		});

		c_vs_c_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					depth_text.setEditable(true);
					easy_radio.setEnabled(true);
					med_radio.setEnabled(true);
					hard_radio.setEnabled(true);
					game_mode = 2;
				}

			}
		});

		p_vs_c_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					depth_text.setEditable(true);
					easy_radio.setEnabled(true);
					med_radio.setEnabled(true);
					hard_radio.setEnabled(true);
					game_mode = 1;
				}

			}
		});
		ButtonGroup btn_grp_mode = new ButtonGroup();
		btn_grp_mode.add(p_vs_p_radio);
		btn_grp_mode.add(p_vs_c_radio);
		btn_grp_mode.add(c_vs_c_radio);

		game_mode_center_panel.add(picPvsPLabel);
		game_mode_center_panel.add(picPvsCLabel);
		game_mode_center_panel.add(picCvsCLabel);
		game_mode_center_panel.add(p_vs_p_radio);
		game_mode_center_panel.add(p_vs_c_radio);
		game_mode_center_panel.add(c_vs_c_radio);

		game_mode_panel.add(game_mode_center_panel, BorderLayout.CENTER);
		// zavrseno postavljanje mode-a igre

		JPanel game_level_panel = new JPanel(new BorderLayout());
		JLabel game_level_name_label = new JLabel("Izaberite nivo igre", SwingConstants.CENTER);
		game_level_name_label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		game_level_panel.add(game_level_name_label, BorderLayout.NORTH);

		JPanel game_level_center_panel = new JPanel(new GridLayout(2, 3));

		/*
		 * JLabel p_vs_p = new JLabel("2 players"); JLabel p_vs_c = new
		 * JLabel("player vs computer"); JLabel c_vs_c = new JLabel("2 computers");
		 */

		JLabel pic_level_easy = new JLabel();
		pic_level_easy.setIcon(new ImageIcon(new javax.swing.ImageIcon("easy.png").getImage()
				.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		pic_level_easy.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel pic_level_med = new JLabel();
		pic_level_med.setIcon(new ImageIcon(new javax.swing.ImageIcon("medium.png").getImage()
				.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		pic_level_med.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel pic_level_hard = new JLabel();
		pic_level_hard.setIcon(new ImageIcon(new javax.swing.ImageIcon("hard.png").getImage()
				.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		pic_level_hard.setHorizontalAlignment(SwingConstants.CENTER);

		easy_radio.setHorizontalAlignment(SwingConstants.CENTER);
		med_radio.setHorizontalAlignment(SwingConstants.CENTER);
		hard_radio.setHorizontalAlignment(SwingConstants.CENTER);

		easy_radio.setEnabled(false);
		med_radio.setEnabled(false);
		hard_radio.setEnabled(false);

		easy_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					level = 0;
				}

			}
		});

		med_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					level = 1;
				}

			}

		});

		hard_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					level = 2;
				}

			}
		});
		ButtonGroup btn_grp_level = new ButtonGroup();
		btn_grp_level.add(easy_radio);
		btn_grp_level.add(med_radio);
		btn_grp_level.add(hard_radio);

		game_level_center_panel.add(pic_level_easy);
		game_level_center_panel.add(pic_level_med);
		game_level_center_panel.add(pic_level_hard);
		game_level_center_panel.add(easy_radio);
		game_level_center_panel.add(med_radio);
		game_level_center_panel.add(hard_radio);

		game_level_panel.add(game_level_center_panel, BorderLayout.CENTER);
		// zavrseno postavljanje level-a igre

		JPanel depth_panel = new JPanel(new FlowLayout());

		JLabel depth_label = new JLabel("<html>Upisati dubinu<br>razvijanja stabla: </html>", SwingConstants.RIGHT);
		depth_label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

		depth_text.setHorizontalAlignment(SwingConstants.LEFT);
		// depth_text.setPreferredSize(new Dimension(10, 10));
		depth_text.setText("4");
		depth_text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		depth_text.setEditable(false);
		JPanel wrapper_depth = new JPanel(new FlowLayout(0, 0, FlowLayout.LEADING));
		wrapper_depth.add(depth_text);
		depth_panel.add(depth_label);
		depth_panel.add(wrapper_depth);
		// stavljanje dubine stabla

		JPanel table_panel = new JPanel(new FlowLayout());

		JLabel table_label = new JLabel("<html>Upisati broj vrsta<br>i kolona table: </html>", SwingConstants.RIGHT);
		table_label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

		JTextField table_row_text = new JTextField(3);
		table_row_text.setHorizontalAlignment(SwingConstants.LEFT);
		table_row_text.setText("4");
		table_row_text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		JPanel wrapper_rows = new JPanel(new FlowLayout(0, 0, FlowLayout.LEADING));
		wrapper_rows.add(table_row_text);

		JTextField table_columns_text = new JTextField(3);
		table_columns_text.setHorizontalAlignment(SwingConstants.LEFT);
		table_columns_text.setText("4");
		table_columns_text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		JPanel wrapper_columns = new JPanel(new FlowLayout(0, 0, FlowLayout.LEADING));
		wrapper_columns.add(table_columns_text);

		table_panel.add(table_label);
		table_panel.add(wrapper_rows);
		table_panel.add(wrapper_columns);

		// dodali smo broj vrsti i kolona

		JButton play_button = new JButton("Start the game");
		play_button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		play_button.setHorizontalAlignment(SwingConstants.CENTER);

		play_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				m = Integer.parseInt(table_row_text.getText());
				n = Integer.parseInt(table_columns_text.getText());
				
				depth = Integer.parseInt(depth_text.getText());
				
				switch (game_mode) {
				case 0:
					make_p_vs_p_game(m, n);
					
					break;
				case 1:
					make_p_vs_c_game(m, n, depth, level);
					
					break;
				case 2:
					
					make_c_vs_c_game(m, n, depth, level);
					
					break;
				}
			}
		});
		add(game_mode_panel);
		add(game_level_panel);
		add(depth_panel);
		add(table_panel);
		add(play_button);

	}

	private void make_c_vs_c_game(int m, int n, int depth, int level) {
		TableComputerComputer tabC_C = new TableComputerComputer(m, n, depth, level);
		JFrame frame = new JFrame("Dots and Boxes");
		// frame.setLayout(new BorderLayout());
		JPanel resultPanel = new JPanel(new GridLayout(1, 2));

		JPanel blue_player_panel = new JPanel(new GridLayout(1, 3));

		TableComputerComputer.blue_player_turn_icon = new JLabel();
		TableComputerComputer.blue_player_turn_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("bulb.png")
				.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

		TableComputerComputer.blue_player_turn_icon.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel blue_player_icon = new JLabel();
		blue_player_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("blue_player.png")
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		blue_player_icon.setHorizontalAlignment(SwingConstants.RIGHT);
		TableComputerComputer.blue_points = new JLabel("0", SwingConstants.LEFT);

		TableComputerComputer.blue_points.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));

		blue_player_panel.add(TableComputerComputer.blue_player_turn_icon);
		blue_player_panel.add(blue_player_icon);
		blue_player_panel.add(TableComputerComputer.blue_points);

		JPanel red_player_panel = new JPanel(new GridLayout(1, 3));

		TableComputerComputer.red_player_turn_icon = new JLabel();
		TableComputerComputer.red_player_turn_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon(Table.class.getResource("bulb.png"))
				.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

		TableComputerComputer.red_player_turn_icon.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel red_player_icon = new JLabel();
		red_player_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon(Table.class.getResource("red_player.png"))
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		red_player_icon.setHorizontalAlignment(SwingConstants.LEFT);
		TableComputerComputer.red_points = new JLabel("0", SwingConstants.RIGHT);

		TableComputerComputer.red_points.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));

		red_player_panel.add(TableComputerComputer.red_points);
		red_player_panel.add(red_player_icon);
		red_player_panel.add(TableComputerComputer.red_player_turn_icon);
		TableComputerComputer.red_player_turn_icon.setVisible(false);

		resultPanel.add(blue_player_panel);
		resultPanel.add(red_player_panel);
		frame.add(resultPanel, BorderLayout.NORTH);

		
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
	private void make_p_vs_c_game(int m, int n, int depth, int level) {
		TablePlayerComputer table = new TablePlayerComputer(m, n, depth, level);
		JFrame frame = new JFrame("Dots and Boxes");
		// frame.setLayout(new BorderLayout());
		JPanel resultPanel = new JPanel(new GridLayout(1, 2));

		JPanel blue_player_panel = new JPanel(new GridLayout(1, 3));

		TablePlayerComputer.blue_player_turn_icon = new JLabel();
		TablePlayerComputer.blue_player_turn_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("bulb.png")
				.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

		TablePlayerComputer.blue_player_turn_icon.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel blue_player_icon = new JLabel();
		blue_player_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("blue_player.png")
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		blue_player_icon.setHorizontalAlignment(SwingConstants.RIGHT);
		TablePlayerComputer.blue_points = new JLabel("0", SwingConstants.LEFT);

		TablePlayerComputer.blue_points.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));

		blue_player_panel.add(TablePlayerComputer.blue_player_turn_icon);
		blue_player_panel.add(blue_player_icon);
		blue_player_panel.add(TablePlayerComputer.blue_points);

		JPanel red_player_panel = new JPanel(new GridLayout(1, 3));

		TablePlayerComputer.red_player_turn_icon = new JLabel();
		TablePlayerComputer.red_player_turn_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("bulb.png")
				.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

		TablePlayerComputer.red_player_turn_icon.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel red_player_icon = new JLabel();
		red_player_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("red_player.png")
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		red_player_icon.setHorizontalAlignment(SwingConstants.LEFT);
		TablePlayerComputer.red_points = new JLabel("0", SwingConstants.RIGHT);

		TablePlayerComputer.red_points.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));

		red_player_panel.add(TablePlayerComputer.red_points);
		red_player_panel.add(red_player_icon);
		red_player_panel.add(TablePlayerComputer.red_player_turn_icon);
		TablePlayerComputer.red_player_turn_icon.setVisible(false);

		resultPanel.add(blue_player_panel);
		resultPanel.add(red_player_panel);
		frame.add(resultPanel, BorderLayout.NORTH);

		frame.add(table, BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setBounds(500, 300, 500, 800);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void make_p_vs_p_game(int m, int n) {
		Table table = new Table(m, n);
		JFrame frame = new JFrame("Dots and Boxes");
		// frame.setLayout(new BorderLayout());
		JPanel resultPanel = new JPanel(new GridLayout(1, 2));

		JPanel blue_player_panel = new JPanel(new GridLayout(1, 3));

		Table.blue_player_turn_icon = new JLabel();
		Table.blue_player_turn_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("bulb.png")
				.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

		Table.blue_player_turn_icon.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel blue_player_icon = new JLabel();
		blue_player_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("blue_player.png")
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		blue_player_icon.setHorizontalAlignment(SwingConstants.RIGHT);
		Table.blue_points = new JLabel("0", SwingConstants.LEFT);

		Table.blue_points.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));

		blue_player_panel.add(Table.blue_player_turn_icon);
		blue_player_panel.add(blue_player_icon);
		blue_player_panel.add(Table.blue_points);

		JPanel red_player_panel = new JPanel(new GridLayout(1, 3));

		Table.red_player_turn_icon = new JLabel();
		Table.red_player_turn_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("bulb.png")
				.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

		Table.red_player_turn_icon.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel red_player_icon = new JLabel();
		red_player_icon.setIcon(new ImageIcon(new javax.swing.ImageIcon("red_player.png")
				.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		red_player_icon.setHorizontalAlignment(SwingConstants.LEFT);
		Table.red_points = new JLabel("0", SwingConstants.RIGHT);

		Table.red_points.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));

		red_player_panel.add(Table.red_points);
		red_player_panel.add(red_player_icon);
		red_player_panel.add(Table.red_player_turn_icon);
		Table.red_player_turn_icon.setVisible(false);

		resultPanel.add(blue_player_panel);
		resultPanel.add(red_player_panel);
		frame.add(resultPanel, BorderLayout.NORTH);

		frame.add(table, BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setBounds(500, 300, 500, 800);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Dots and Boxes");
		frame.add(new Info());
		frame.setSize(500, 500);
		frame.setBounds(500, 100, 450, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
