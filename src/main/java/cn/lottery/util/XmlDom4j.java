package cn.lottery.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.lottery.main.MainFrame;
import cn.lottery.model.Price;


public class XmlDom4j {

	public static boolean isRandom() {
		SAXReader sr = new SAXReader();
		Document document;
		String strIsPrice = "";
		if (FileUtil.isExists(Common.PRICE_PATH)) {
			try {
				document = sr.read(new FileReader(Common.PRICE_PATH));

				List ispriceList = document
						.selectNodes("/prices/price/entry/@isprice");

				for (int i = 0; i < ispriceList.size(); i++) {
					strIsPrice = ((Attribute) ispriceList.get(i)).getValue();
					if (strIsPrice.equals("是")) {
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return strIsPrice.equals("是") ? false : true;
	}

	public static void create2XML(Price price) {
		Document document = DocumentHelper.createDocument();

		Element root = DocumentHelper.createElement("prices");

		document.setRootElement(root);

		Element ae = DocumentHelper.createElement("price");

		Element entry = null;
		entry = DocumentHelper.createElement("entry");
		if ("".equals(price.getPriceid())) {
			price.setPriceid(DateUtils.getDateStr());
			price.setCreatedate(DateUtils.getCurrentDateTime("yyyy-MM-dd"));
		}
		entry.addAttribute("priceid", price.getPriceid());
		entry.addAttribute("pricename", price.getPricename());
		entry.addAttribute("imagename", price.getImagename());
		entry.addAttribute("quickkey", price.getQuickkey());
		entry.addAttribute("isprice", price.getIsprice());
		entry.addAttribute("createdate", price.getCreatedate());
		ae.add(entry);
		root.add(ae);
		OutputFormat outFmt = OutputFormat.createPrettyPrint();
		outFmt.setEncoding("UTF-8");
		outFmt.setIndent("    ");
		XMLWriter xmlWriter;
		if ("是".equals(price.getIsprice())) {
			MainFrame.resetPrice(price.getPriceid());
			MainFrame.isRandom = false;
		}

		try {
			xmlWriter = new XMLWriter(new FileWriter(Common.PRICE_PATH), outFmt);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static void update2XML(Price price) {
		SAXReader sr = new SAXReader();
		Document document;
		if (FileUtil.isExists(Common.PRICE_PATH)) {
			try {
				document = sr.read(new FileReader(Common.PRICE_PATH));
				List<Element> entryList = document.getRootElement()
						.element("price").elements("entry");
				for (int i = 0; i < entryList.size(); i++) {
					if (entryList.get(i).attributeValue("priceid")
							.equals(price.getPriceid())) {
						entryList.get(i).attribute("pricename")
								.setValue(price.getPricename());
						entryList.get(i).attribute("imagename")
								.setValue(price.getImagename());
						entryList.get(i).attribute("quickkey")
								.setValue(price.getQuickkey());
						entryList.get(i).attribute("isprice")
								.setValue(price.getIsprice());
					}
				}
				XMLWriter xmlWriter = new XMLWriter(new FileWriter(
						Common.PRICE_PATH));
				xmlWriter.write(document);
				xmlWriter.close();

				if ("是".equals(price.getIsprice())) {
					// 把所有其他的是否奖品设置为否
					document = sr.read(new FileReader(Common.PRICE_PATH));
					List<Element> entryLists = document.getRootElement()
							.element("price").elements("entry");
					for (int i = 0; i < entryLists.size(); i++) {// 先更新所有的都为否
						if (!entryLists.get(i).attributeValue("priceid")
								.equals(price.getPriceid()))
							entryLists.get(i).attribute("isprice")
									.setValue("否");
					}
					XMLWriter xmlWriter2 = new XMLWriter(new FileWriter(
							Common.PRICE_PATH));
					xmlWriter2.write(document);
					xmlWriter2.close();

					MainFrame.isRandom = false;
					MainFrame.resetPrice(price.getPriceid());
				} else {
					// 如果是否奖品为否，
					// 查询是奖品的编号，
					// 更新MainFrame的isRandom为false
					// 更新labs
					document = sr.read(new FileReader(Common.PRICE_PATH));
					List<Element> entryLists = document.getRootElement()
							.element("price").elements("entry");
					String updateID = "";
					for (int i = 0; i < entryLists.size(); i++) {
						if (entryLists.get(i).attributeValue("isprice")
								.equals("是")) {
							updateID = entryLists.get(i).attributeValue(
									"priceid");
							break;
						}
					}
					if ("".equals(updateID)) {
						MainFrame.isRandom = true;
					} else {
						MainFrame.isRandom = false;
						MainFrame.resetPrice(updateID);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void insert2XML(Price price) {
		SAXReader sr = new SAXReader();
		Document document;
		if (FileUtil.isExists(Common.PRICE_PATH)) {
			if ("".equals(price.getPriceid()) || price.getPriceid() == null) {
				price.setPriceid(DateUtils.getDateStr());
				price.setCreatedate(DateUtils.getCurrentDateTime("yyyy-MM-dd"));
				if ("是".equals(price.getIsprice())) {
					MainFrame.isRandom = false;
				}
			}
			try {
				document = sr.read(new FileReader(Common.PRICE_PATH));
				Element root = document.getRootElement();
				List<Element> elements = root.elements();
				if (elements.size() > 0) {
					Element ae = root.element("price");
					Element entry = DocumentHelper.createElement("entry");
					entry.addAttribute("priceid", price.getPriceid());
					entry.addAttribute("pricename", price.getPricename());
					entry.addAttribute("imagename", price.getImagename());
					entry.addAttribute("quickkey", price.getQuickkey());
					entry.addAttribute("isprice", price.getIsprice());
					entry.addAttribute("createdate", price.getCreatedate());
					ae.add(entry);
				} else { // 当只有跟节点存在时
					Element ae = DocumentHelper.createElement("price");
					Element entry = DocumentHelper.createElement("entry");
					entry.addAttribute("priceid", price.getPriceid());
					entry.addAttribute("pricename", price.getPricename());
					entry.addAttribute("imagename", price.getImagename());
					entry.addAttribute("quickkey", price.getQuickkey());
					entry.addAttribute("isprice", price.getIsprice());
					entry.addAttribute("createdate", price.getCreatedate());
					ae.add(entry);
					root.add(ae);// 记得添加到根节点下
				}

				// List priceidList = document
				// .selectNodes("/prices/price/entry/@priceid");
				// Element ae = null;
				// if(priceidList.size()>0){
				// ae = document.getRootElement().element("price");
				// }else{
				//
				// }

				OutputFormat outFmt = OutputFormat.createPrettyPrint();
				outFmt.setEncoding("UTF-8");
				outFmt.setIndent("    ");
				XMLWriter xmlWriter = new XMLWriter(new FileWriter(
						Common.PRICE_PATH), outFmt);
				xmlWriter.write(document);
				xmlWriter.close();

				if (MainFrame.isRandom == false) {
					// 把所有其他的是否奖品设置为否
					document = sr.read(new FileReader(Common.PRICE_PATH));
					List<Element> entryLists = document.getRootElement()
							.element("price").elements("entry");
					for (int i = 0; i < entryLists.size(); i++) {// 先更新所有的都为否
						if (!entryLists.get(i).attributeValue("priceid")
								.equals(price.getPriceid()))
							entryLists.get(i).attribute("isprice")
									.setValue("否");
					}
					XMLWriter xmlWriter2 = new XMLWriter(new FileWriter(
							Common.PRICE_PATH));
					xmlWriter2.write(document);
					xmlWriter2.close();
					MainFrame.resetPrice(price.getPriceid());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			create2XML(price);
		}
	}

	@SuppressWarnings("unchecked")
	public static void getXML(Vector vrow) {
		SAXReader sr = new SAXReader();
		Document document;
		if (FileUtil.isExists(Common.PRICE_PATH)) {
			try {
				document = sr.read(new FileReader(Common.PRICE_PATH));
				List priceidList = document
						.selectNodes("/prices/price/entry/@priceid");
				List pricenameList = document
						.selectNodes("/prices/price/entry/@pricename");
				List imagenameList = document
						.selectNodes("/prices/price/entry/@imagename");
				List quickkeyList = document
						.selectNodes("/prices/price/entry/@quickkey");
				List ispriceList = document
						.selectNodes("/prices/price/entry/@isprice");
				List createdateList = document
						.selectNodes("/prices/price/entry/@createdate");

				for (int i = 0; i < priceidList.size(); i++) {
					Vector vr1 = new Vector();
					vr1.addElement(((Attribute) priceidList.get(i)).getValue());
					vr1.addElement(((Attribute) pricenameList.get(i))
							.getValue());
					vr1.addElement(((Attribute) imagenameList.get(i))
							.getValue());
					vr1.addElement(((Attribute) quickkeyList.get(i)).getValue());
					vr1.addElement(((Attribute) ispriceList.get(i)).getValue());
					vr1.addElement(((Attribute) createdateList.get(i))
							.getValue());
					vrow.addElement(vr1);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		// System.out.println(book.element("书名").attributeValue("name"));
		// List list = document.selectNodes("/lll/AE/Entry[@num=\"3\"]/@id");
		// 比如Entry中存在Element为<name>JVK</name>
		// XPAHT可以这样写List list =
		// document.selectNodes("/lll/AE/Entry[name=\"JVK\"]/@id");
		// System.out.println(((Attribute) list.get(0)).getValue());
	}

	public static void delete2XML(String priceid) {
		SAXReader sr = new SAXReader();
		Document document;
		if (FileUtil.isExists(Common.PRICE_PATH)) {
			try {
				document = sr.read(new FileReader(Common.PRICE_PATH));
				// 只能删除下一级节点，不能超过一级；（需要在父元素的节点上删除子元素）
//				document.getRootElement().remove(
//						document.selectSingleNode(
//								"/prices/price/entry[@priceid=\"" + priceid
//										+ "\"]").asXPathResult(null));
				 //document.getRootElement().elements();
				List list = document.selectNodes("//prices/price/entry");
				for (int i = 0; i < list.size(); i++) {
					Element e = (Element)list.get(i);
					String value = e.attributeValue("priceid");
					if(priceid.equals(value)){
						//list.remove(e);//不行
						e.detach();
					}
				}

				
				
//				List itemList = document.selectNodes("//prices/price/entry");
//				for(int i=0;i<itemList.size();i++){
//					Element itemElement = (Element)itemList.get(i);
//					//Element guidElement = (Element)itemElement.element("priceid");					
//					if(priceid.equals(itemElement.attributeValue("isprice").toString())){
//						document.getRootElement().remove(itemElement);
//						//itemElement.detach();
//						//删除节点通过detach()方法，查了半天API文档才找到
//						break;
//					 }
////					}
//				}
				
			
//				document.getRootElement().remove(
//						document.selectSingleNode(
//								"/prices/price/entry[@priceid=\"" + priceid
//										+ "\"]").getParent());
				// List list = document.selectNodes("/bcaster/item/@id");
				// Iterator iter = list.iterator();
				// while(iter.hasNext()){
				// Attribute attribute = (Attribute)iter.next();
				// if(attribute.getValue().equals(itemvo.getId())){
				// document.getRootElement().remove(attribute.getParent());
				// }

				OutputFormat outFmt = OutputFormat.createPrettyPrint();
				outFmt.setEncoding("UTF-8");
				outFmt.setIndent("    ");
				XMLWriter xmlWriter = new XMLWriter(new FileWriter(
						Common.PRICE_PATH), outFmt);
				xmlWriter.write(document);
				xmlWriter.close();

				document = sr.read(new FileReader(Common.PRICE_PATH));
				List<Element> entryLists = document.getRootElement()
						.element("price").elements("entry");
				String updateID = "";
				for (int i = 0; i < entryLists.size(); i++) {
					if (entryLists.get(i).attributeValue("isprice").equals("是")) {
						updateID = entryLists.get(i).attributeValue("priceid");
						break;
					}
				}
				if ("".equals(updateID)) {
					MainFrame.isRandom = true;
				} else {
					MainFrame.isRandom = false;
					MainFrame.resetPrice(updateID);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

		}
	}
	
//	List itemList = document.selectNodes("//rss/channel/item");
//	//获得item节点list对象
//	int itemListsize = itemList.size();
//	int[] delarray = getInItemNumber(RssUtils.getFilename(bbsinfo.getTypeid()));
//	//获得每个item对象的guid 标识符并通过getInItemNumber方法对其排序，
//	//当然如果本来已经是有序的则不需另作排序
//	int delorder =0;
//	while (itemListsize >= rssCount) {//rssCount控制RSS输出数量	
//		for(int x =0;x <itemList.size();x++){								
//			Element itemElement = (Element)itemList.get(x);
//			Element guidElement = (Element)itemElement.element("guid");
//			if(guidElement.getText().equals(String.valueOf(delarray[delorder]))){
//				itemElement.detach();
//				//删除节点通过detach()方法，查了半天API文档才找到
//				break;
//			 }
//			}
//			delorder++;
//			itemListsize--;//这里的++ -- 一定不能忘,否则无限循环!
//	} 


	public static String setPrice(int keyCode) {
		if (keyCode == 112) {// F1:112
			return updatePriceValue("F1");
		} else if (keyCode == 113) {// F2:113
			return updatePriceValue("F2");
		} else if (keyCode == 114) {// F3:114
			return updatePriceValue("F3");
		} else if (keyCode == 115) {// F4:115
			return updatePriceValue("F4");
		} else if (keyCode == 116) {// F5:116
			return updatePriceValue("F5");
		} else if (keyCode == 117) {// F6:117
			return updatePriceValue("F6");
		} else if (keyCode == 118) {// F7:118
			return updatePriceValue("F7");
		} else if (keyCode == 119) {// F8:119
			return updatePriceValue("F8");
		} else if (keyCode == 120) {// F9:120
			return updatePriceValue("F9");
		} else if (keyCode == 121) {// F10:121
			return updatePriceValue("F10");
		} else {
			return "";
		}
	}

	public static String updatePriceValue(String key) {
		SAXReader sr = new SAXReader();
		Document document;
		String priceid = "";
		if (FileUtil.isExists(Common.PRICE_PATH)) {
			try {
				document = sr.read(new FileReader(Common.PRICE_PATH));
				List<Element> entryList = document.getRootElement()
						.element("price").elements("entry");
				for (int i = 0; i < entryList.size(); i++) {// 先更新所有的都为否
					entryList.get(i).attribute("isprice").setValue("否");
				}

				for (int i = 0; i < entryList.size(); i++) {// 再更新按下快捷键的那个为奖品
					if (entryList.get(i).attributeValue("quickkey").equals(key)) {
						entryList.get(i).attribute("isprice").setValue("是");
						priceid = entryList.get(i).attributeValue("priceid")
								.toString();
					}
				}
				XMLWriter xmlWriter = new XMLWriter(new FileWriter(
						Common.PRICE_PATH));
				xmlWriter.write(document);
				xmlWriter.close();
				MainFrame.isRandom = false;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return priceid;
	}

	public static void main(String[] args) {
		try {
			XmlDom4j j2x = new XmlDom4j();
			// delete2XML("1");
			// Price price = new Price();
			// price.setPriceid("1");
			// price.setPricename("书籍");
			// price.setImagename("book.gif");
			// price.setQuickkey("F5");
			// price.setIsprice("是");
			// price.setCreatedate("2011-12-16");
			// j2x.create2XML(price);

			// Price price = new Price();
			// price.setPriceid("1");
			// price.setPricename("书籍1");
			// price.setImagename("book.gif");
			// price.setQuickkey("F6");
			// price.setIsprice("否");
			// price.setCreatedate("2011-12-15");
			// j2x.update2XML(price);

			// j2x.create2XML();
			// j2x.update2XML();
			// j2x.insert2XML();
			// j2x.getXML();
			// getXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
