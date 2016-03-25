package cn.lottery.component;
import javax.swing.JLabel;

public class JLabelMy extends JLabel {
	private String place;// 坐标
	private String iconPath;// icon路径
	private String isPrice;// 是否奖品
	private String priceid;// 奖品id

	public String getPriceid() {
		return priceid;
	}

	public void setPriceid(String priceid) {
		this.priceid = priceid;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getIsPrice() {
		return isPrice;
	}

	public void setIsPrice(String isPrice) {
		this.isPrice = isPrice;
	}

	public JLabelMy() {
	}

	public JLabelMy(String title) {
		super(title);
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
