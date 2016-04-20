package es.ucm.fdi.tp.assignment5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import es.ucm.fdi.tp.assignment5.Main.PlayerMode;
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
	private Board board;
	private List<Piece> pieces;
	private Map<Piece, Color> pieceColors;
	private Map<Piece, PlayerMode> playerModes;

	public SwingView(Observable<GameObserver> g, Controller c, Piece lp, Player randPlayer, Player aiPlayer) {
		super("hello");
		ctrl = c;
		localPiece = lp;
		initGUI();
		g.addObserver(this);
	}

	private void initGUI() {
		// TODO control init
		
		
		JTextArea messages = new JTextArea("here will be messages");
		messages.setEditable(false);
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		
		JScrollPane messagePane = new JScrollPane(messages,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		Border b = BorderFactory.createLineBorder(Color.BLACK);
		
		JPanel playerInfo = new JPanel();
		playerInfo.setBorder(BorderFactory.createTitledBorder(b, "Players"));
		
		JPanel quitRestartPanel = new JPanel();
		quitRestartPanel.setLayout(new BoxLayout(quitRestartPanel, BoxLayout.X_AXIS));
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setAlignmentX(CENTER_ALIGNMENT);
		
		JButton restartBtn = new JButton("Restart");
		restartBtn.setAlignmentX(CENTER_ALIGNMENT);
		
		quitRestartPanel.add(exitBtn);
		quitRestartPanel.add(restartBtn);

		JPanel dashboardPanel = new JPanel();
		dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
		dashboardPanel.add(messagePane);
		dashboardPanel.add(playerInfo);
		//dashboardPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		dashboardPanel.add(quitRestartPanel);

		JPanel mainPanel = new JPanel(new BorderLayout(10,10));
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
		return pieceColors.put(p, c);
	}

	final protected void setBoardArea(JComponent c) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {

				getContentPane().add(c, BorderLayout.CENTER);
				
			}
			
		});
	}

	final protected void addMsg(String msg) {
		// TODO implement
	}

	final protected void decideMakeManulaMove(Player manualPlayer) {
		// TODO implement
	}

	private void decideMakeAutomaticMove() {

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
				handleGameStart();
			}
		});
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnGameOver();
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
				handleOnMoveEnd();
			}
		});
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnChangeTurn();
			}
		});
	}

	@Override
	public void onError(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnError();
			}
		});
	}

	private void handleGameStart() {
		// TODO Auto-generated method stub
		
	}

	private void handleOnGameOver() {
		// TODO Auto-generated method stub
		
	}

	protected void handleOnMoveStart() {
		// TODO Auto-generated method stub
		
	}

	protected void handleOnMoveEnd() {
		// TODO Auto-generated method stub
		
	}

	protected void handleOnChangeTurn() {
		// TODO Auto-generated method stub
		
	}

	protected void handleOnError() {
		// TODO Auto-generated method stub
		
	}
}
