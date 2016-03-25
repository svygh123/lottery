package cn.lottery.main;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cn.lottery.model.Price;
import cn.lottery.util.Common;
import cn.lottery.util.FileFilterMy;
import cn.lottery.util.FileUtil;
import cn.lottery.util.Lotteryini;
import cn.lottery.util.XmlDom4j;

public class SystemSet extends WindowAdapter implements ActionListener {
	static JFrame frame = new JFrame("系统设置");

	static {
		Font font = new Font("宋体", Font.PLAIN, 15);
		frame.setFont(font);
		initGlobalFontSetting(font);
	}

	JTabbedPane tabPane = new JTabbedPane(); // 选项卡布局
	Container con = new Container(); // 布局1
	Container con1 = new Container(); // 布局2
	JLabel priceNameJLabel = new JLabel("奖品名称:");
	JLabel priceImageJLabel = new JLabel("奖品图片:");
	JLabel priceQuickKeyJLabel = new JLabel("设奖快捷键:");
	JLabel isPriceJLabel = new JLabel("是否奖品:");
	JLabel priceImg = new JLabel();
	JLabel bgImgShowLabel = new JLabel("");
	JTextField priceNameJTextField = new JTextField();
	JTextField priceImageJTextField = new JTextField();
	JTextField priceQuickKeyJTextField = new JTextField();
	JButton selectImageJButton = new JButton("选择");
	JRadioButton yesJRadioButton = new JRadioButton("是");
	JRadioButton noJRadioButton = new JRadioButton("否");
	JButton okJButton = new JButton("确定");
	JFileChooser jfc = new JFileChooser(); // 文件选择器
	JButton btnDelButton = new JButton("删除");
	JButton btnUpdateButton = new JButton("编辑");
	JButton btnCancelEditButton = new JButton("取消编辑");
	private final JScrollPane scrollPane = new JScrollPane();
	private JTable table;
	private JTextField bgUrlTextField;
	public static String isEdit = "";

	public SystemSet() {
		jfc.setCurrentDirectory(new File("d:\\")); // 文件选择器的初始目录定为d盘
													// 下面两行是取得屏幕的高度和宽度
		jfc.addChoosableFileFilter(new FileFilterMy());
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150)); // 设定窗口出现位置
		frame.setSize(650, 450); // 设定窗口大小
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Common.IMAGES_PATH + "set.gif"));
		frame.setContentPane(tabPane); // 设置布局
										// 下面设定标签等的出现位置和高宽

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// int n = JOptionPane.showConfirmDialog(null, "确定要关闭吗?",
				// "确认提示",
				// JOptionPane.YES_NO_OPTION);
				//
				// if (n == JOptionPane.YES_OPTION) {
				frame.setVisible(false);				
				// }

			}
		});

		priceNameJLabel.setBounds(10, 10, 70, 20);
		priceImageJLabel.setBounds(10, 30, 100, 20);
		priceQuickKeyJLabel.setBounds(10, 50, 130, 20);
		priceImg.setBounds(340, 10, 70, 70);
		isPriceJLabel.setBounds(10, 77, 160, 20);

		priceNameJTextField.setBounds(100, 10, 120, 20);
		priceImageJTextField.setBounds(100, 30, 120, 20);
		priceQuickKeyJTextField.setBounds(100, 50, 120, 20);
		priceQuickKeyJTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				System.out.println("keyCode:" + k);

				if (k == KeyEvent.VK_F1) {
					priceQuickKeyJTextField.setText("F1");
				} else if (k == KeyEvent.VK_F2) {
					priceQuickKeyJTextField.setText("F2");
				} else if (k == KeyEvent.VK_F3) {
					priceQuickKeyJTextField.setText("F3");
				} else if (k == KeyEvent.VK_F4) {
					priceQuickKeyJTextField.setText("F4");
				} else if (k == KeyEvent.VK_F5) {
					priceQuickKeyJTextField.setText("F5");
				} else if (k == KeyEvent.VK_F6) {
					priceQuickKeyJTextField.setText("F6");
				} else if (k == KeyEvent.VK_F7) {
					priceQuickKeyJTextField.setText("F7");
				} else if (k == KeyEvent.VK_F8) {
					priceQuickKeyJTextField.setText("F8");
				} else if (k == KeyEvent.VK_F9) {
					priceQuickKeyJTextField.setText("F9");
				} else if (k == KeyEvent.VK_F10) {
					priceQuickKeyJTextField.setText("F10");
				}
			}
		});

		selectImageJButton.setBounds(230, 30, 50, 20);
		okJButton.setBounds(73, 105, 80, 25);

		okJButton.addActionListener(this); // 添加事件处理
		selectImageJButton.addActionListener(this); // 添加事件处理

		con.add(priceNameJLabel);
		con.add(priceImageJLabel);
		con.add(priceQuickKeyJLabel);
		con.add(priceImg);
		con.add(isPriceJLabel);

		con.add(priceNameJTextField);
		con.add(priceImageJTextField);
		con.add(priceQuickKeyJTextField);

		con.add(selectImageJButton);
		con.add(okJButton);

		con.add(jfc);
		tabPane.add("奖品设置", con);

		yesJRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (yesJRadioButton.isSelected()) {
					noJRadioButton.setSelected(false);
				}
			}
		});
		yesJRadioButton.setBounds(103, 76, 50, 23);
		con.add(yesJRadioButton);

		noJRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (noJRadioButton.isSelected()) {
					yesJRadioButton.setSelected(false);
				}
			}
		});
		noJRadioButton.setBounds(163, 76, 57, 23);
		con.add(noJRadioButton);

		btnDelButton.setBounds(190, 105, 95, 25);
		con.add(btnDelButton);
		btnDelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				int sRow = 0;
				sRow = table.getSelectedRow();
				
				int m = table.getSelectedColumn();
				
				if (sRow == -1) {
					JOptionPane.showMessageDialog(null, "请选择要选中的数据", "删除提示",
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (JOptionPane.showConfirmDialog(null, "确定要删除吗?", "确认提示",
							JOptionPane.YES_NO_OPTION) == 0) {// YES_OPTION = 0;
																// NO_OPTION =
																// 1;
						String priceid = table.getValueAt(sRow, 0).toString();
						System.out.println("123");
						XmlDom4j.delete2XML(priceid);
//						((DefaultTableModel) table.getModel()).removeRow(sRow);
						loadData();
						MainFrame.isRandom = XmlDom4j.isRandom();
					}
				}

			}
		});
		scrollPane.setSize(617, 237);
		con.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		tabPane.add("背景设置", con1); // 添加布局2
		// bgImgShowLabel.setToolTipText("单击选择新背景图");

		bgImgShowLabel.setBounds(104, 0, 421, 337);
		bgImgShowLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		bgImgShowLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectBgImgFile();
			}
		});
		con1.add(bgImgShowLabel);

		JButton btnSetBgButton = new JButton("设为背景");
		btnSetBgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String strBgUrlTextField = bgUrlTextField.getText();
				if (!FileUtil.isImage(strBgUrlTextField)) {
					JOptionPane.showMessageDialog(null,
							"请选择有效的图片文件(jpg,jpeg,bmp,gif,png)", "选择提示",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String bgImgName = strBgUrlTextField
							.substring(strBgUrlTextField.lastIndexOf("\\") + 1);
					FileUtil.copyFile(strBgUrlTextField, Common.IMAGES_PATH
							+ bgImgName, "该图片已经存在,是否要覆盖原有文件");
					Lotteryini.setIniKey("bgname", bgImgName);
					Lotteryini.saveIni(new String[] { "bgname" });
					JOptionPane.showMessageDialog(null, "设置背景图片成功", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnSetBgButton.setBounds(116, 342, 104, 23);
		con1.add(btnSetBgButton);

		bgUrlTextField = new JTextField();
		bgUrlTextField.setBounds(196, 242, 195, 21);
		con1.add(bgUrlTextField);
		bgUrlTextField.setColumns(10);
		JLabel lblNewLabel = new JLabel(
				"<html><font color='red'>(可以单击图片选择您要设置的新背景图)</font></html>");
		lblNewLabel.setBounds(225, 347, 306, 15);
		con1.add(lblNewLabel);
		bgUrlTextField.setVisible(false);
		frame.setVisible(true); // 窗口可见
		noJRadioButton.setSelected(true);
		scrollPane.setLocation(10, 140);

		btnUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int sRow = 0;
				sRow = table.getSelectedRow();
				if (sRow == -1) {
					JOptionPane.showMessageDialog(null, "请选择要编辑的行数据", "提示信息",
							JOptionPane.WARNING_MESSAGE);
				} else {
					btnDelButton.setEnabled(false);
					btnUpdateButton.setEnabled(false);
					btnCancelEditButton.setEnabled(true);

					isEdit = table.getValueAt(sRow, 0).toString();
					priceNameJTextField.setText(table.getValueAt(sRow, 1)
							.toString());
					priceImageJTextField.setText(Common.IMAGES_PATH
							+ table.getValueAt(sRow, 2).toString());
					priceQuickKeyJTextField.setText(table.getValueAt(sRow, 3)
							.toString());
					if ("是".equals(table.getValueAt(sRow, 4).toString())) {
						yesJRadioButton.setSelected(true);
						noJRadioButton.setSelected(false);
					} else {
						noJRadioButton.setSelected(true);
						yesJRadioButton.setSelected(false);
					}
					// doDelete(Integer.parseInt(bookid));
					// ((DefaultTableModel) table.getModel()).removeRow(sRow);
				}

			}
		});
		btnUpdateButton.setBounds(323, 105, 95, 25);
		con.add(btnUpdateButton);
		btnCancelEditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearData();
			}
		});

		btnCancelEditButton.setBounds(451, 105, 95, 25);
		btnCancelEditButton.setEnabled(false);
		con.add(btnCancelEditButton);
		loadData();
		showCurrBgImg();
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 事件处理

		if (e.getSource().equals(okJButton)) { // 判断触发方法的按钮是哪个
			submit();
			MainFrame.isRandom = XmlDom4j.isRandom();
		}

		if (e.getSource().equals(selectImageJButton)) {
			selectFile();
		}
	}

	public void showCurrBgImg() {
		String currBgImg = Lotteryini.getIniKey("bgname");
		if (!"".equals(currBgImg)) {
			File file = new File(Common.IMAGES_PATH + currBgImg);
			if (file.exists()) {
				bgImgShowLabel.setIcon(new ImageIcon(Common.IMAGES_PATH
						+ currBgImg));
			}
		}
	}

	public void loadData() {
		try {
			Vector vcol = new Vector();
			Vector vrow = new Vector();
			doSelect(vcol, vrow);
			table = new JTable(vrow, vcol);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);

			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
//					if (row == 2) {
//						setBackground(Color.GRAY);
//					} else {
//						setBackground(Color.WHITE);
//					}

					Component cell = super.getTableCellRendererComponent(table,
							value, isSelected, hasFocus, row, column);
					String valueAT = (String) table.getModel().getValueAt(row,
							4);
					if (valueAT.equals("是")) {
						//cell.setBackground(Color.RED);
						cell.setBackground(new java.awt.Color(255, 128, 64));
					} else {
						//cell.setBackground(Color.yellow);
						cell.setBackground(new java.awt.Color(255, 255, 255));
					}

					// if ((column == 0 && row != 3) || column == 6
					// || (column == 3 && row == 3))
					// setBackground(new java.awt.Color(255, 198, 198));
					// else
					// setBackground(new Color(204, 253, 200));
					return cell;
//					return super.getTableCellRendererComponent(table, value,
//							isSelected, hasFocus, row, column);
				}
			};
			for (int i = 0; i < 6; i++) {
				// table.getColumn(head[i]).setCellRenderer(tcr);
				table.getColumnModel().getColumn(i).setCellRenderer(tcr);
			}

			scrollPane.setViewportView(table);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	public void doSelect(Vector vcol, Vector vrow) {
		vcol.addElement("奖品编号");// priceid
		vcol.addElement("奖品名称");// pricename
		vcol.addElement("奖品图片");// imagename
		vcol.addElement("设奖快捷键");// quickkey
		vcol.addElement("是否奖品");// isprice
		vcol.addElement("创建时间");// createdate
		XmlDom4j.getXML(vrow);
	}

	public void submit() {
		String txtPriceName = priceNameJTextField.getText();
		String txtPriceImage = priceImageJTextField.getText();
		String txtQuickKey = priceQuickKeyJTextField.getText();
		if (!"".equals(txtPriceName) && !"".equals(txtPriceImage)) {
			if (!FileUtil.isImage(txtPriceImage)) {
				JOptionPane.showMessageDialog(null,
						"请选择有效的图片文件(jpg,jpeg,bmp,gif,png)", "选择提示",
						JOptionPane.ERROR_MESSAGE);
				priceImageJTextField.requestFocus();
			} else {
				String imgName = txtPriceImage.substring(txtPriceImage
						.lastIndexOf("\\") + 1);
				if (!txtPriceImage.equals(Common.IMAGES_PATH + imgName)) {
					FileUtil.copyFile(txtPriceImage,
							Common.IMAGES_PATH + imgName, "该图片已经存在,是否要覆盖原有文件");
				}

				Price price = new Price();
				price.setPricename(txtPriceName);
				price.setImagename(imgName);
				price.setQuickkey(txtQuickKey);
				String isPriced = yesJRadioButton.isSelected() ? "是" : "否";
				price.setIsprice(isPriced);
				if ("".equals(isEdit)) {
					XmlDom4j.insert2XML(price);
					JOptionPane.showMessageDialog(null, "添加成功", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					price.setPriceid(isEdit);
					XmlDom4j.update2XML(price);
					JOptionPane.showMessageDialog(null, "修改成功", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				}

				clearData();
				loadData();
			}

		} else {
			if ("".equals(txtPriceName)) {
				JOptionPane.showMessageDialog(null, "请输入奖品名字", "信息提示",
						JOptionPane.WARNING_MESSAGE);
				priceNameJTextField.requestFocus();
			} else if ("".equals(txtPriceImage)) {
				JOptionPane.showMessageDialog(null, "请选择奖品图片", "信息提示",
						JOptionPane.WARNING_MESSAGE);
				priceImageJTextField.requestFocus();
			}
		}
	}

	public void selectFile() {
		jfc.setFileSelectionMode(0); // 设定只能选择到文件

		int state = jfc.showOpenDialog(null); // 此句是打开文件选择器界面的触发语句

		if (state == 1) {
			return; // 撤销则返回
		} else {
			File f = jfc.getSelectedFile(); // f为选择到的文件
			priceImageJTextField.setText(f.getAbsolutePath());
			priceImg.setIcon(new ImageIcon(f.getAbsolutePath()));
		}
	}

	public void selectBgImgFile() {
		jfc.setFileSelectionMode(0); // 设定只能选择到文件

		int state = jfc.showOpenDialog(null); // 此句是打开文件选择器界面的触发语句

		if (state == 1) {
			return; // 撤销则返回
		} else {
			File f = jfc.getSelectedFile(); // f为选择到的文件
			bgUrlTextField.setText(f.getAbsolutePath());
			bgImgShowLabel.setText("");
			bgImgShowLabel.setIcon(new ImageIcon(f.getAbsolutePath()));
		}
	}

	public void clearData() {
		btnUpdateButton.setEnabled(true);
		btnDelButton.setEnabled(true);
		btnCancelEditButton.setEnabled(false);

		isEdit = "";
		priceNameJTextField.setText("");
		priceImageJTextField.setText("");
		priceQuickKeyJTextField.setText("");
		yesJRadioButton.setSelected(false);
		noJRadioButton.setSelected(true);
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

	public static void main(String[] args) {
		new SystemSet();
	}
}
