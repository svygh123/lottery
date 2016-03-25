package cn.lottery.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;

import cn.lottery.component.JLabelMy;
import cn.lottery.util.Common;
import cn.lottery.util.Lotteryini;
import cn.lottery.util.XmlDom4j;

public class MainFrame extends JFrame implements ActionListener, KeyListener {
	public int FIELDSIZE = 70;
	public static JLabelMy[][] labs = new JLabelMy[8][8];
	public JButton btnStart = null;
	int x = 0;
	int y = 0;
	public int TIME = 1;
	public static Vector vrow = new Vector();//存放price(奖品)数据(不包含表头标题)
	public static Random random = new Random();
	public static int i = 0;
	public JLabel bgJLabel = new JLabel("background");//背景图JLabel
	public static int base = 60;//70
	public static int setColor2BeginTime = 280;
	public static boolean isRandom = false;

	static {
		XmlDom4j.getXML(vrow);
		i = Math.abs(random.nextInt()) % vrow.size();
		isRandom = XmlDom4j.isRandom();
	}

	public MainFrame() {
		Font font = new Font("宋体", Font.PLAIN, 15);
		setFont(font);
		initGlobalFontSetting(font);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Common.IMAGES_PATH + "logo.gif"));
		setTitle("快乐抽奖");
		setName("test");
		setBounds(400, 150, 566, 627);
		JFrame.setDefaultLookAndFeelDecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setResizable(false);

		JPanel boardPane = new JPanel();
		boardPane.setLayout(null);
		btnStart = new JButton("开始");
		btnStart.setIcon(new ImageIcon(Common.IMAGES_PATH + "start.png"));
		btnStart.addKeyListener(this);
		this.add(btnStart, BorderLayout.SOUTH);
		add(boardPane, BorderLayout.CENTER);
		bgJLabel.setBounds(70, 70, 420, 420);
		String currBgImg = Lotteryini.getIniKey("bgname");
		if (!"".equals(currBgImg)) {
			File file = new File(Common.IMAGES_PATH + currBgImg);
			if (file.exists()) {
				bgJLabel.setIcon(new ImageIcon(Common.IMAGES_PATH + currBgImg));
			}
		}
		boardPane.add(bgJLabel);

		for (int x = 0; x < 8; x = x + 7) {
			/** 第一列和最后一列的8格 */
			for (int y = 0; y < 8; y++) {
				JLabelMy backgroundLabel = new JLabelMy();
				backgroundLabel.setPlace(String.valueOf(x) + ","
						+ String.valueOf(y));
				backgroundLabel.setOpaque(true);
				backgroundLabel.setBounds(x * FIELDSIZE, y * FIELDSIZE,
						FIELDSIZE, FIELDSIZE);
				boardPane.add(backgroundLabel, new Integer(1), 0);
				backgroundLabel.setBackground(Color.GRAY);
				backgroundLabel.setBorder(BorderFactory.createLineBorder(
						Color.BLACK, 2));
				backgroundLabel
						.setHorizontalTextPosition(SwingConstants.CENTER);
				labs[x][y] = backgroundLabel;

				if (i < vrow.size()) {
					Vector vi = (Vector) vrow.get(i);
					labs[x][y].setIcon(new ImageIcon(Common.IMAGES_PATH
							+ vi.get(2).toString()));
					labs[x][y].setIsPrice(vi.get(4).toString());
					labs[x][y].setPriceid(vi.get(0).toString());
					i++;
				} else {
					i = Math.abs(random.nextInt()) % vrow.size();
					Vector vi = new Vector();
					if(vrow.size()>i){
						vi = (Vector) vrow.get(i);
						labs[x][y].setIcon(new ImageIcon(Common.IMAGES_PATH
								+ vi.get(2).toString()));
						labs[x][y].setIsPrice(vi.get(4).toString());
						labs[x][y].setPriceid(vi.get(0).toString());
						i++;
					}					
				}

				// 0,0
				// 0,1
				// 0,2
				// 0,3
				// 0,4
				// 0,5
				// 0,6
				// 0,7

				// 7,0
				// 7,1
				// 7,2
				// 7,3
				// 7,4
				// 7,5
				// 7,6
				// 7,7
			}
		}

		for (int x = 0; x < 8; x = x + 7) {
			/** 第一行和最后一行的6格 */
			for (int y = 1; y < 7; y++) {
				JLabelMy backgroundLabel = new JLabelMy();//
				backgroundLabel.setPlace(String.valueOf(y) + ","
						+ String.valueOf(x));
				backgroundLabel.setOpaque(true);
				backgroundLabel.setBounds(y * FIELDSIZE, x * FIELDSIZE,
						FIELDSIZE, FIELDSIZE);
				boardPane.add(backgroundLabel, new Integer(1), 0);
				backgroundLabel.setBackground(Color.GRAY);
				backgroundLabel.setBorder(BorderFactory.createLineBorder(
						Color.BLACK, 2));
				labs[y][x] = backgroundLabel;

				if (i < vrow.size()) {
					Vector vi = (Vector) vrow.get(i);
					labs[y][x].setIcon(new ImageIcon(Common.IMAGES_PATH
							+ vi.get(2).toString()));
					labs[y][x].setIsPrice(vi.get(4).toString());
					labs[y][x].setPriceid(vi.get(0).toString());
					i++;
				} else {
					i = Math.abs(random.nextInt()) % vrow.size();
					;
					Vector vi = new Vector();
					if(vrow.size()>i){
						vi = (Vector) vrow.get(i);
						labs[y][x].setIcon(new ImageIcon(Common.IMAGES_PATH
								+ vi.get(2).toString()));
						labs[y][x].setIsPrice(vi.get(4).toString());
						labs[y][x].setPriceid(vi.get(0).toString());
						i++;
					}
				}

				// 1,0
				// 2,0
				// 3,0
				// 4,0
				// 5,0
				// 6,0

				// 1,7
				// 2,7
				// 3,7
				// 4,7
				// 5,7
				// 6,7
			}
		}

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnStart.setEnabled(false);
				TIME = 1;
				setColor();
			}
		});

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "确定要关闭吗?", "确认提示",
						JOptionPane.YES_NO_OPTION);

				if (n == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setVisible(true);
	}

	private void setColor() {
		new Thread() {
			@Override
			public void run() {
				int num = 0;
				JLabelMy[][] lab = MainFrame.labs;
				// int a = (int) Math.round((Math.random() * 19) + 1);
				// Random rdm = new Random();
				// int a = 5 + rdm.nextInt(10);
				// System.out.println("a=" + a);
				// System.out.print("a*10=");
				// System.out.println(a*10);
				// int a = 5;
				while (num <= (base)) {
					try {
						Thread.sleep(TIME);
						TIME += 10;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (lab[i][j] != null) {
								lab[i][j].setBackground(Color.GRAY);
							}
						}
					}

					if ((x == 0) && (y < 7)) {
						lab[x][y].setBackground(Color.RED);
						y++;
					} else if ((y == 7) && (x < 7)) {
						lab[x][y].setBackground(Color.RED);
						x++;
					} else if ((x == 7) && (y > 0)) {
						lab[x][y].setBackground(Color.RED);
						y--;
					} else if ((y == 0) && (x > 0)) {
						lab[x][y].setBackground(Color.RED);
						x--;
					}

					num++;
				}

				if (x == 0) {
					if (y == 0) {
					} else {
						y -= 1;
					}
				} else {
					if (x == 7) {
						if (y == 0) {
							y += 1;
						} else if (y == 7) {
							x -= 1;
						} else {
							y += 1;
						}
					} else {
						if ((x > 0) && (x < 7)) {
							if (y == 7) {
								x -= 1;
							} else if (y == 0) {
								x += 1;
							}
						}
					}
				}

				JLabelMy jl = labs[x][y];
				// String value = jl.getPlace();
				String isWinPrice = jl.getIsPrice();
				// System.out.println(value);
				// if (value.equals("0,0") || value.equals("7,7")
				// || value.equals("0,7") || value.equals("7,0")) {
				if(isRandom){
					MainFrame.this.btnStart.setEnabled(true);
				} else{
					if ("是".equals(isWinPrice)) {
						MainFrame.this.btnStart.setEnabled(true);
						//base += 1;
					} else {
						setColor2();
					}
				}				
			}
		}.start();
	}

	private void setColor2() {
		new Thread() {
			@Override
			public void run() {
				int num = 0;
				JLabelMy[][] lab = MainFrame.labs;
				//int a = (int) Math.round((Math.random() * 3) + 1);
				//System.out.println("a=" + a);
				// int a = 1;
				// while (num <= (a * 10)) {
				while (num <= 1) {
					try {
//						if (num != 0) {
//							Thread.sleep(TIME);
//							TIME += 10;
//						} else{
//							Thread.sleep(TIME+10);
//						}
						//TIME += 10;
						//Thread.sleep(TIME-25);
						Thread.sleep(setColor2BeginTime);
						setColor2BeginTime += 5;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (lab[i][j] != null) {
								lab[i][j].setBackground(Color.GRAY);
							}
						}
					}

					if ((x == 0) && (y < 7)) {
						lab[x][y].setBackground(Color.RED);
						y++;
					} else if ((y == 7) && (x < 7)) {
						lab[x][y].setBackground(Color.RED);
						x++;
					} else if ((x == 7) && (y > 0)) {
						lab[x][y].setBackground(Color.RED);
						y--;
					} else if ((y == 0) && (x > 0)) {
						lab[x][y].setBackground(Color.RED);
						x--;
					}
					//System.out.println("place:" + lab[x][y].getPlace());

					num++;
				}

				if (x == 0) {
					if (y == 0) {
					} else {
						y -= 1;
					}
				} else {
					if (x == 7) {
						if (y == 0) {
							y += 1;
						} else if (y == 7) {
							x -= 1;
						} else {
							y += 1;
						}
					} else {
						if ((x > 0) && (x < 7)) {
							if (y == 7) {
								x -= 1;
							} else if (y == 0) {
								x += 1;
							}
						}
					}
				}
				JLabelMy jl = labs[x][y];
				// String value = jl.getPlace();
				// System.out.println(value);
				String isWinPrice = jl.getIsPrice();
				// if (value.equals("0,0") || value.equals("7,7")
				// || value.equals("0,7") || value.equals("7,0")) {
				if ("是".equals(isWinPrice)) {
					MainFrame.this.btnStart.setEnabled(true);
					//base += 1;
				} else {
					setColor2();
				}
			}
		}.start();
	}

	public static void main(String[] args) {
		new MainFrame();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String newPriceId = "";
		newPriceId = XmlDom4j.setPrice(keyCode);
		if (!"".equals(newPriceId)) {
			resetPrice(newPriceId);
		}
		//System.out.println("keyPressed:" + e.getKeyCode());
		// Ctrl+X弹出设置
		if ((e.isControlDown() == true) && (e.getKeyCode() == KeyEvent.VK_X)) {
			new SystemSet();
		}

		// F1:112
		// F2:113
		// F3:114
		// F4:115
		// F5:116
		// F6:117
		// F7:118
		// F8:119
		// F9:120
		// F10:121
	}

	public static void resetPrice(String newPriceId) {
		//System.out.println("resetPrice:newPriceId=" + newPriceId);

		for (int x = 0; x < 8; x = x + 7) {
			/** 第一列和最后一列的8格 */
			for (int y = 0; y < 8; y++) {
				if (newPriceId.equals(labs[x][y].getPriceid())) {
					labs[x][y].setIsPrice("是");
//					System.out.println(x + "," + y + ","
//							+ labs[x][y].getIsPrice());
				} else {
					labs[x][y].setIsPrice("否");
//					System.out.println(x + "," + y + ","
//							+ labs[x][y].getIsPrice());
				}
			}
		}

		for (int x = 0; x < 8; x = x + 7) {
			/** 第一行和最后一行的6格 */
			for (int y = 1; y < 7; y++) {
				if (newPriceId.equals(labs[y][x].getPriceid())) {
					labs[y][x].setIsPrice("是");
//					System.out.println(y + "," + x + ","
//							+ labs[y][x].getIsPrice());
				} else {
					labs[y][x].setIsPrice("否");
//					System.out.println(y + "," + x + ","
//							+ labs[y][x].getIsPrice());
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
	}

	public static void initGlobalFontSetting(Font _font) {
		FontUIResource fontRes = new FontUIResource(_font);

		for (Enumeration keys = UIManager.getDefaults().keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);

			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
}
