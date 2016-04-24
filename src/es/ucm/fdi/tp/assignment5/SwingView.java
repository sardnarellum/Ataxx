package es.ucm.fdi.tp.assignment5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.assignment5.Main.PlayerMode;
import es.ucm.fdi.tp.assignment5.SwingCommon;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public abstract class SwingView extends JFrame implements GameObserver {

	private Controller ctrl;
	private Piece localPiece;
	private Piece turn;
	private Player randPlayer;
	private Player aiPlayer;
	private Board board;
	private List<Piece> pieces;
	private Map<Piece, Color> pieceColors;
	private Map<Piece, PlayerMode> playerModes;
	private JTextArea messages;
	private JComboBox<String> playerColorsCB;
	private JComboBox<String> playerModesCB;
	private PlayerTableModel tmodel;

	public SwingView(Observable<GameObserver> g, Controller c, Piece lp, Player randPlayer, Player aiPlayer) {
		super();
		ctrl = c;
		localPiece = lp;
		playerModes = new HashMap<Piece, PlayerMode>();
		this.randPlayer = randPlayer;
		this.aiPlayer = aiPlayer;
		initGUI();
	}

	private void initGUI() {
		messages = new JTextArea();
		messages.setEditable(false);
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);

		JScrollPane messagePane = new JScrollPane(messages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messagePane.setPreferredSize(new Dimension(300, 300));

		Border b = BorderFactory.createLineBorder(Color.BLACK);

		tmodel = new PlayerTableModel();
		JTable playerInfoTable = new JTable(tmodel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				Color c = pieceColors.get(pieces.get(row));
				comp.setBackground(pieceColors.get(pieces.get(row)));

				if (c.getBlue() < 200 && c.getGreen() < 180 && c.getRed() < 160) {
					comp.setForeground(Color.white);
				} else {
					comp.setForeground(Color.black);
				}
				return comp;
			}
		};

		JPanel playerInfo = new JPanel();
		playerInfo.setBorder(BorderFactory.createTitledBorder(b, "Player Information"));
		playerInfo.add(new JScrollPane(playerInfoTable));
		playerInfoTable.setFillsViewportHeight(true);

		JPanel colors = new JPanel();
		colors.setLayout(new BoxLayout(colors, BoxLayout.X_AXIS));
		colors.setBorder(BorderFactory.createTitledBorder(b, "Piece Colors"));

		playerColorsCB = new JComboBox<String>();

		JButton chooseColorBtn = new JButton("Choose Color");
		colors.add(playerColorsCB);
		colors.add(chooseColorBtn);

		chooseColorBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Piece p = pieces.get(playerColorsCB.getSelectedIndex());
					ColorChooser c = new ColorChooser(new JFrame(), "Choose Line Color", pieceColors.get(p));
					if (c.getColor() != null) {
						pieceColors.put(p, c.getColor());
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								tmodel.refresh();
								redrawBoard();
							}
						});
					}
				} catch (Exception _e) {
					addMsg(_e.getMessage());
				}
			}
		});

		JPanel modes = new JPanel();
		modes.setLayout(new BoxLayout(modes, BoxLayout.X_AXIS));
		modes.setBorder(BorderFactory.createTitledBorder(b, "Player Modes"));

		playerModesCB = new JComboBox<String>();

		JComboBox<String> modesCBox = new JComboBox<String>();

		for (int i = 0; i < PlayerMode.values().length; ++i) {
			modesCBox.addItem(PlayerMode.values()[i].getDesc());
		}

		JButton setModeBtn = new JButton("Set");
		setModeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final Piece p = localPiece == null ? pieces.get(playerModesCB.getSelectedIndex()) : localPiece;
				PlayerMode m = PlayerMode.values()[modesCBox.getSelectedIndex()];
				playerModes.put(p, m);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						tmodel.setMode(p, m);
						tmodel.refresh();
					}
				});
			}

		});

		modes.add(playerModesCB);
		modes.add(modesCBox);
		modes.add(setModeBtn);

		JPanel autoOptions = new JPanel();
		// autoOptions.setLayout(new BoxLayout(autoOptions, BoxLayout.X_AXIS));
		autoOptions.setBorder(BorderFactory.createTitledBorder(b, "Automatic Moves"));

		JButton randomBtn = new JButton("Random");
		JButton automaticBtn = new JButton("Automatic");

		autoOptions.add(randomBtn);
		autoOptions.add(automaticBtn);

		JPanel quitRestartPanel = new JPanel();
		quitRestartPanel.setLayout(new BoxLayout(quitRestartPanel, BoxLayout.X_AXIS));

		JButton exitBtn = new JButton("Exit");
		exitBtn.setAlignmentX(CENTER_ALIGNMENT);

		JButton restartBtn = new JButton("Restart");
		restartBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.restart();
			}
		});
		restartBtn.setAlignmentX(CENTER_ALIGNMENT);

		quitRestartPanel.add(exitBtn);
		quitRestartPanel.add(restartBtn);

		JPanel dashboardPanel = new JPanel();
		dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
		dashboardPanel.add(messagePane);
		dashboardPanel.add(playerInfo);
		dashboardPanel.add(colors);
		dashboardPanel.add(modes);
		dashboardPanel.add(autoOptions);
		dashboardPanel.add(quitRestartPanel);

		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.add(dashboardPanel, BorderLayout.EAST);
		mainPanel.setOpaque(true);

		setContentPane(mainPanel);
		initBoardGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}

	final protected Piece getTurn() {
		return turn;
	}

	final protected Board getBoard() {
		return board;
	}

	final protected List<Piece> getPieces() {
		return pieces;
	}

	final protected Color getPieceColor(Piece p) {
		return pieceColors.get(p);
	}

	final protected Color setPieceColor(Piece p, Color c) {
		if (null == pieceColors) {
			pieceColors = new HashMap<Piece, Color>();
		}
		return pieceColors.put(p, c);
	}

	final protected void setBoardArea(JComponent c) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				getContentPane().add(c, BorderLayout.CENTER);
			}
		});
	}

	final protected void addMsg(String msg) {
		if (messages.getText().length() == 0) {
			messages.setText(msg);
		} else {
			messages.setText(messages.getText() + "\n" + msg);
		}
	}

	final protected void decideMakeManualMove(Player manualPlayer) {
		ctrl.makeMove(manualPlayer);
	}

	private void decideMakeAutomaticMove() {
		ctrl.makeMove(playerModes.get(turn) == PlayerMode.AI ? aiPlayer : randPlayer);
	}

	protected abstract void initBoardGUI();

	protected abstract void activateBoard();

	protected abstract void deActivateBoard();

	protected abstract void redrawBoard();

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnGameStart(board, gameDesc, pieces);
				handleOnChangeTurn(turn);
			}
		});
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnGameOver(state, winner);
			}
		});
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnMoveStart();
			}
		});
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (success) {
					handleOnMoveEnd(board);
				}
			}
		});
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnChangeTurn(turn);
			}
		});
	}

	@Override
	public void onError(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnError(msg);
			}
		});
	}

	private void handleOnGameStart(Board board, String gameDesc, List<Piece> pieces) {
		this.board = board;
		if (null != localPiece) {
			this.setTitle("Board Games: " + gameDesc + " (" + localPiece.toString() + ")");
		} else {
			this.setTitle("Board Games: " + gameDesc);
		}
		this.pieces = pieces;

		Random r = new Random();

		playerModesCB.removeAllItems();
		playerColorsCB.removeAllItems();

		for (Piece p : pieces) {
			setPieceColor(p, SwingCommon.createRandomColor(r));
			playerColorsCB.addItem(p.toString());

			if (null == localPiece) {
				playerModesCB.addItem(p.toString());
			}
			playerModes.put(p, PlayerMode.MANUAL);
			if (null != board.getPieceCount(p)) {
				tmodel.setRow(p, playerModes.get(p), board.getPieceCount(p));
			} else {
				tmodel.setRow(p, playerModes.get(p));
			}
		}

		if (null != localPiece) {
			playerModesCB.addItem(localPiece.toString());
		}

		tmodel.refresh();
		redrawBoard();
		activateBoard();
		addMsg(gameDesc + " is started");
	}

	private void handleOnGameOver(State state, Piece winner) {
		deActivateBoard();
		addMsg("GAME OVER");
		printState(state, winner);
	}

	protected void handleOnMoveStart() {
		deActivateBoard();
	}

	protected void handleOnMoveEnd(Board board) {
		this.board = board;
		
		for (Piece p : pieces){
			Integer c = board.getPieceCount(p);
			tmodel.setScore(p, c);
		}
		tmodel.refresh();
		redrawBoard();

		if (playerModes.get(turn) == PlayerMode.MANUAL) {
			activateBoard();
		}
	}

	protected void handleOnChangeTurn(Piece turn) {
		this.turn = turn;

		if (null != localPiece) {
			if (localPiece.equals(turn)) {
				addMsg("The next player is " + turn + " (You)");
				if (playerModes.get(turn) == PlayerMode.MANUAL) {
					activateBoard();
				}
				else {
					decideMakeAutomaticMove();					
				}
			} else {
				deActivateBoard();
				addMsg("The next player is " + turn);
			}
		} else {
			addMsg("The next player is " + turn);
			if (playerModes.get(turn) == PlayerMode.MANUAL) {
				activateBoard();
			}
			else {
				decideMakeAutomaticMove();				
			}
		}
	}

	protected void handleOnError(String msg) {
		addMsg("[Error] " + msg);
	}

	private void printState(State state, Piece winner) {
		switch (state) {
		case Draw:
			addMsg("The Game is ended with Draw.");
			break;
		case InPlay:
			addMsg("The Game is in progress.");
			break;
		case Starting:
			addMsg("The Game is about to start.");
			break;
		case Stopped:
			addMsg("The Game is stopped");
			break;
		case Won:
			if (null != winner) {
				addMsg("The winner is: " + winner);
			}
			break;
		}
	}
}