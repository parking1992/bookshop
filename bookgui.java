package exam;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.event.WindowAdapter;
import java.awt.SystemColor;


//bookgui 클래스 gui 를 구성한다.
public class bookgui extends JFrame {
	
	bookmain mana = new bookmain();

	private JPanel contentPane;
	private JTextField textsearchauthor;
	private JTextField textsearchbook;
	private JTextField textaddauthor;
	private JTextField textaddbook;
	private JTextField textaddprice;
	
	
	private JTextField textrendauthor;
	private JTextField textrendbook;
	private JTextField textrenduser;
	
	private JTextField textadduser;
	private JTextField textaddaddress;
	private JTextField textaddphone;
	private JTextField textsearchuser;
	private JTextField textsearchphone;

	
	//////////////책 정보 테이블
	private JPanel cenTable = new JPanel();
	private JTable table;
	private JScrollPane spTable;
	public static String[] str = {"저 자 명", "책 제 목", "대출여부"," 가 격 "};
	public static DefaultTableModel dtm = new DefaultTableModel(str, 0);
	DefaultTableColumnModel dtcm = new DefaultTableColumnModel(); //열너비 조절위해
	private JButton btnAll = new JButton("모든목록 보기");
	private JButton btnRendReturn = new JButton("대출 / 반납");
	private int selected = -1;

	
	//////////////회원 정보 테이블
	private JPanel ucenTable = new JPanel();
	private JTable utable;
	private JScrollPane uspTable;
	public static String[] strs = {"회 원 명 ", " 주 소", "전화번호"};
	public static DefaultTableModel dtms = new DefaultTableModel(strs, 0);
	DefaultTableColumnModel dtcms = new DefaultTableColumnModel();
	private JButton ubtnAll = new JButton("모든목록 보기");
	private JButton ubtnremoveinfo = new JButton("정보입력");
	private JTextField textsearchname;
	
	private int shownum=0;
	private String filename;
	private String textget;
	private int xx;
		

	public bookgui() {
		
		createTable();
		createuser();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 450);
		setLocation(200, 200);
		setResizable(false);
		
		//메뉴바 생성
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnNewMenu = new JMenu("메뉴");
		menuBar.add(mnNewMenu);		
		JMenuItem mntmNewMenuItem = new JMenuItem("초기파일");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mana.savef();
				mana.savefuser();
				mana.resetbook();
				mana.resetuser();
				initTable();
				inituser();
				mana.readfile();
				mana.readuser();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("종료");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		getContentPane().setLayout(null);
		
		//메인텝   - 도서관리/회원관리
		JTabbedPane tabmain = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); 
		tabmain.setSize(795, 390);
		tabmain.setLocation(0, 0);
		
		//도서 관리 탭
		JPanel managebook = new JPanel();
		managebook.setBackground(Color.YELLOW);
		managebook.setBounds(0, 0, 300, 185);		
		getContentPane().add(tabmain);
		tabmain.addTab("도서 관리", managebook);
		managebook.setLayout(null);
		
		//도서관리 왼쪽 오른쪽
		JPanel west = new JPanel();
		west.setBackground(Color.WHITE);
		JPanel east = new JPanel();
		east.setBackground(Color.WHITE);
		west.setBounds(0, 0, 250, 370);
		east.setBounds(250, 0, 540, 370);
		west.setLayout(null);
		east.setLayout(new BorderLayout());
		managebook.add(west);
		managebook.add(east);
		
		//왼쪽 위 아래 탭 위치
		JTabbedPane tab_addfind = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); 
		tab_addfind.setBackground(Color.WHITE);
		JTabbedPane tab_rendreturn = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); 
		tab_rendreturn.setBackground(Color.WHITE);
		tab_addfind.setBounds(0, 0, 250, 180);
		tab_rendreturn.setBounds(0, 180, 250, 180);
		
		//왼쪽 위 패널- 추가/검색
		JPanel add_book = new JPanel();
		add_book.setBackground(Color.BLUE);
		tab_addfind.addTab("추가",add_book);
		add_book.setLayout(null);
		
		JPanel find_book = new JPanel();
		tab_addfind.addTab("검색",find_book);	
		find_book.setLayout(null);
		
		//왼쪽 위 패널 - 검색
		JPanel searchbook = new JPanel();
		searchbook.setBackground(new Color(176, 224, 230));
		searchbook.setBounds(0, 0, 245, 150);
		searchbook.setBorder(new TitledBorder("도서 검색"));
		find_book.add(searchbook);
		searchbook.setLayout(null);
		
		//왼쪽 위 패널 - 검색 - 저자 검색
		JLabel lblsearchauthor = new JLabel(" 저자명");
		lblsearchauthor.setBounds(5, 25, 55, 25);
		searchbook.add(lblsearchauthor);
		
		textsearchauthor = new JTextField();
		textsearchauthor.setBounds(60, 25, 105, 23);
		searchbook.add(textsearchauthor);
		textsearchauthor.setColumns(10);
		//저자명 검색
		JButton btnsearchauthor = new JButton("검색");
		btnsearchauthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shownum = 1;
				filename = "bookshop.dat";
				textget = textsearchauthor.getText();
				xx = 0;
				initTable();
				//mana.savesearch(textget);
				mana.readsearch(filename,textget,xx);
				textsearchauthor.setText("");
			}
		});
		btnsearchauthor.setBounds(175, 25, 60, 25);
		searchbook.add(btnsearchauthor);
		
		//왼쪽 위 패널 - 검색 - 책 검색
		JLabel lblsearchbook = new JLabel(" 책제목");
		lblsearchbook.setBounds(5, 60, 55, 25);
		searchbook.add(lblsearchbook);
		
		textsearchbook = new JTextField();
		textsearchbook.setColumns(10);
		textsearchbook.setBounds(60, 60, 105, 23);
		searchbook.add(textsearchbook);
		
		//책이름 검색
		JButton btnsearchbook = new JButton("검색");
		btnsearchbook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shownum =1;
				filename = "bookshop.dat";
				textget = textsearchbook.getText();
				xx = 1;
				initTable();
				mana.readsearch(filename,textget,xx);
				textsearchbook.setText("");
			}
		});
		btnsearchbook.setBounds(175, 60, 60, 25);
		searchbook.add(btnsearchbook);
		
		JLabel lblsearchname = new JLabel(" 회원명");
		lblsearchname.setBounds(5, 95, 55, 25);
		searchbook.add(lblsearchname);
		
		textsearchname = new JTextField();
		textsearchname.setColumns(10);
		textsearchname.setBounds(60, 95, 105, 23);
		searchbook.add(textsearchname);
		
		//회원명 검색
		JButton btnsearchname = new JButton("검색");
		btnsearchname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shownum = 1;
				filename = "bookshop.dat";
				textget = textsearchname.getText();
				xx = 2;
				initTable();
				mana.readsearch(filename,textget,xx);
				textsearchname.setText("");
			}
		});
		btnsearchname.setBounds(175, 95, 60, 25);
		searchbook.add(btnsearchname);
		
		//왼쪽 위 패널 - 책 추가
		JPanel addbook = new JPanel();
		addbook.setBackground(new Color(176, 224, 230));
		addbook.setBounds(0, 0, 245, 150);
		addbook.setBorder(new TitledBorder("도서 추가"));
		add_book.add(addbook);
		addbook.setLayout(null);
		
		JLabel lbladdauthor = new JLabel(" 저자명");
		lbladdauthor.setBounds(10, 20, 55, 25);
		addbook.add(lbladdauthor);
		
		textaddauthor = new JTextField();
		textaddauthor.setBounds(85, 20, 116, 25);
		addbook.add(textaddauthor);
		textaddauthor.setColumns(10);
		
		JLabel lbladdbook = new JLabel(" 책제목");
		lbladdbook.setBounds(10, 50, 55, 25);
		addbook.add(lbladdbook);
		
		textaddbook = new JTextField();
		textaddbook.setBounds(85, 50, 116, 25);
		addbook.add(textaddbook);
		textaddbook.setColumns(10);
		
		JLabel lbladdprice = new JLabel(" 가 격");
		lbladdprice.setBounds(10, 80, 55, 25);
		addbook.add(lbladdprice);
		
		textaddprice = new JTextField();
		textaddprice.setColumns(10);
		textaddprice.setBounds(85, 80, 116, 25);
		addbook.add(textaddprice);
		
		//책 삭제버튼
		JButton btnremove = new JButton("삭제");
		btnremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String author = textaddauthor.getText();
				String title = textaddbook.getText();
				String price = textaddprice.getText();
				mana.removebook(author,title,price);
				initTable();
				mana.savefile();
				mana.readfile();
				textaddauthor.setText("");
				textaddbook.setText("");
				textaddprice.setText("");
			}
		});
		btnremove.setBounds(30, 115, 65, 25);
		addbook.add(btnremove);
		
		//책 추가 버튼
		JButton btnadd = new JButton("추가");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String author = textaddauthor.getText();
				String title = textaddbook.getText();
				String price = textaddprice.getText();
				String rend = "대출가능";
				mana.addbook(author,title, rend, price);
				initTable();
				mana.savefile();
				mana.readfile();
				textaddauthor.setText("");
				textaddbook.setText("");
				textaddprice.setText("");
				
				}
			});
		btnadd.setBounds(120, 115, 65, 25);
		addbook.add(btnadd);
		
		//왼쪽 아래 
		
		//왼쪽아래 - 대출 - 구성품들
		JPanel rend_book = new JPanel();
		rend_book.setBackground(new Color(176, 224, 230));

		tab_rendreturn.addTab("대출",rend_book);
		rend_book.setLayout(null);
		
		JLabel lblrendauthor = new JLabel(" 저자명");
		lblrendauthor.setBounds(10, 20, 55, 25);
		rend_book.add(lblrendauthor);
		
		textrendauthor = new JTextField();
		textrendauthor.setColumns(10);
		textrendauthor.setBounds(85, 20, 116, 25);
		textrendauthor.setEditable(false);
		rend_book.add(textrendauthor);
 

		JLabel lblrendbook = new JLabel(" 책제목");
		lblrendbook.setBounds(10, 50, 55, 25);
		rend_book.add(lblrendbook);
		
		textrendbook = new JTextField();
		textrendbook.setColumns(10);
		textrendbook.setBounds(85, 50, 116, 25);
		textrendbook.setEditable(false);
		rend_book.add(textrendbook);
		
		JLabel lblrenduser = new JLabel(" 회원명");
		lblrenduser.setBounds(10, 80, 55, 25);
		rend_book.add(lblrenduser);
		
		textrenduser = new JTextField();
		textrenduser.setColumns(10);
		textrenduser.setBounds(85, 80, 116, 25);
		rend_book.add(textrenduser);
		
		//대출 버튼
		JButton btnrend = new JButton("대출");
		btnrend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String author = textrendauthor.getText();
				String title = textrendbook.getText();
				String user = textrenduser.getText();
				String rend = "대출가능";
				mana.rendbook(author, title, user,selected);
				initTable();
				mana.savefile();
				mana.readfile();
				//텍스트 초기화
				textrendauthor.setText("");
				textrendbook.setText("");
				
			}
		});
		btnrend.setBounds(75, 115, 65, 25);
		rend_book.add(btnrend);
		
		
		//왼쪽에 패널 붙이기
		west.add(tab_addfind);
		west.add(tab_rendreturn);
		
		//오른쪽 패널
		east.setBorder(new TitledBorder("목록보기"));
		JPanel cenButton = new JPanel();
		cenButton.setBackground(Color.WHITE);
		cenTable.setBackground(Color.WHITE);
		cenTable.add(spTable);
		cenButton.setLayout(new FlowLayout());
		
		
		//모든목록 버튼
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTable();
				mana.readfile();
			}
		});
		cenButton.add(btnAll);
		//대출/반납 액션
		btnRendReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = table.getSelectedRow();
				String authorp;
				String titlep;
				String userp;
				if(selected == -1) {
					JOptionPane.showMessageDialog(null, "대출/반납하고자 하는 목록을 선택하세요!", "에러!", 0);
					return;
				}
				String[] infoTemp = new String[4];
				for(int i = 0 ; i < infoTemp.length; i++) {
					infoTemp[i] = (String)dtm.getValueAt(selected, i);
				}
				if(infoTemp[2].equals("대출가능"))	{
					textrendauthor.setText(infoTemp[0]);
					textrendbook.setText(infoTemp[1]);		
				}
				else{
					authorp = infoTemp[0];
					titlep = infoTemp[1];
					userp = infoTemp[2];
					mana.returnbook(authorp, titlep, userp,selected);
					initTable();
					mana.savefile();
					if(shownum == 1)
					{
						mana.readsearch(filename, textget, xx);
					//	shownum = 0;
					}
					else{
						mana.readfile();
					}
					
					JOptionPane.showMessageDialog(null,"반납 완료");
					
				}
			}
		});
		cenButton.add(btnRendReturn);
		east.add(cenTable, "Center");
		east.add(cenButton, "South");
	
		//회원관리탭
		JPanel manageuser = new JPanel();
		manageuser.setBackground(Color.YELLOW);
		manageuser.setBounds(0, 0, 300, 185);

	    ////////////////회원관리////////////////////////
		
		getContentPane().add(tabmain);
		tabmain.addTab("회원 가입", manageuser);
		manageuser.setLayout(null);
		
		//왼쪽 오른쪽 메인
		JPanel west2 = new JPanel();
		west2.setBackground(Color.WHITE);
		JPanel east2 = new JPanel();
		east2.setBackground(Color.WHITE);
		west2.setBounds(0, 0, 250, 370);
		east2.setBounds(250, 0, 540, 370);
		west2.setLayout(null);
		east2.setLayout(new BorderLayout());
		manageuser.add(west2);
		//manageuser.add(east2);
		
		/////////////////////
		JPanel easts = new JPanel();
		easts.setBackground(Color.WHITE);
		easts.setBounds(250, 0, 540, 370);
		easts.setLayout(new CardLayout());
		manageuser.add(easts);
		easts.add(east2);
		//////////////////
		JPanel easts2 = new JPanel();
		easts.setBackground(Color.BLACK);
		easts.setBounds(250, 0, 540, 370);
		//easts.add(easts2);
		
		
		
		
		
		//왼쪽 위  탭 - 위치
		JTabbedPane tab_adduser = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);  
		tab_adduser.setBounds(0, 0, 250, 180);
		
		//왼쪽 위 패널- 추가/검색
		JPanel add_user = new JPanel();
		add_user.setBackground(Color.BLUE);
		tab_adduser.addTab("추가",add_user);
		add_user.setLayout(null);
		
		//왼쪽 위 패널 - 책 추가
		JPanel adduser = new JPanel();
		adduser.setBackground(new Color(176, 224, 230));
		adduser.setBounds(0, 0, 245, 150);
		adduser.setBorder(new TitledBorder("회원 가입"));
		add_user.add(adduser);
		adduser.setLayout(null);
		
		JLabel lbladduser = new JLabel(" 이 름");
		lbladduser.setBounds(10, 20, 55, 25);
		adduser.add(lbladduser);
		
		textadduser = new JTextField();
		textadduser.setBounds(85, 20, 116, 25);
		adduser.add(textadduser);
		textadduser.setColumns(10);
		
		JLabel lbladdaddress = new JLabel(" 주 소");
		lbladdaddress.setBounds(10, 50, 55, 25);
		adduser.add(lbladdaddress);
		
		textaddaddress = new JTextField();
		textaddaddress.setBounds(85, 50, 116, 25);
		adduser.add(textaddaddress);
		textaddaddress.setColumns(10);
		
		JLabel lbladdphone = new JLabel(" 전화번호");
		lbladdphone.setBounds(10, 80, 55, 25);
		adduser.add(lbladdphone);
		
		textaddphone = new JTextField();
		textaddphone.setColumns(10);
		textaddphone.setBounds(85, 80, 116, 25);
		adduser.add(textaddphone);
		
		//회원 삭제버튼
		JButton ubtnremove = new JButton("삭제");
		ubtnremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textadduser.getText();
				String address = textaddaddress.getText();
				String phone = textaddphone.getText();
				mana.removeuser(user, address, phone);
				inituser();
				mana.saveuser();
				mana.readuser();
				textadduser.setText("");
				textaddaddress.setText("");
				textaddphone.setText("");
			}
		});
		ubtnremove.setBounds(30, 115, 65, 25);
		adduser.add(ubtnremove);
		
		//회원 추가 버튼
		JButton ubtnadd = new JButton("추가");
		ubtnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textadduser.getText();
				String address = textaddaddress.getText();
				String phone = textaddphone.getText();
				mana.adduser(user, address, phone);
				inituser();
				mana.saveuser();
				mana.readuser();
				textadduser.setText("");
				textaddaddress.setText("");
				textaddphone.setText("");
			}
		});
		ubtnadd.setBounds(120, 115, 65, 25);
		adduser.add(ubtnadd);
		
		//왼쪽 아래 위치
		JTabbedPane tab_searchuser = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tab_searchuser.setBounds(0, 180, 250, 180);
		west2.add(tab_searchuser);
		
		JPanel search_user = new JPanel();
		search_user.setLayout(null);
		search_user.setBorder(new TitledBorder("회원 검색"));
		search_user.setBackground(new Color(176, 224, 230));
		tab_searchuser.addTab("검색", null, search_user, null);
		
		JLabel lblsearchuser = new JLabel(" 이 름");
		lblsearchuser.setBounds(5, 35, 55, 25);
		search_user.add(lblsearchuser);
		
		textsearchuser = new JTextField();
		textsearchuser.setColumns(10);
		textsearchuser.setBounds(60, 35, 105, 23);
		search_user.add(textsearchuser);
		
		JButton butsearchuser = new JButton("검색");
		butsearchuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = "user.dat";
				String user = textsearchuser.getText();
				int xx = 0;
				inituser();

				mana.readsearchuser(filename,user,xx);
				textsearchuser.setText("");
			}
		});
		butsearchuser.setBounds(175, 35, 60, 25);
		search_user.add(butsearchuser);
		
		JLabel lblsearchphone = new JLabel(" 전화번호");
		lblsearchphone.setBounds(5, 75, 55, 25);
		search_user.add(lblsearchphone);
		
		textsearchphone = new JTextField();
		textsearchphone.setColumns(10);
		textsearchphone.setBounds(60, 75, 105, 23);
		search_user.add(textsearchphone);
		
		JButton butsearchphone = new JButton("검색");
		butsearchphone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = "user.dat";
				String phone = textsearchphone.getText();
				int xx = 1;
				inituser();
				//mana.savesearchuser(phone);
				mana.readsearchuser(filename,phone,xx);
				textsearchphone.setText("");
			}
		});
		butsearchphone.setBounds(175, 75, 60, 25);
		search_user.add(butsearchphone);
		
		//왼쪽에 패널 붙이기
		west2.add(tab_adduser);
		
		//오른쪽 패널
		east2.setBorder(new TitledBorder("목록보기"));
		JPanel ucenButton = new JPanel();
		ucenButton.setBackground(Color.WHITE);
		ucenTable.setBackground(Color.WHITE);
		ucenTable.add(uspTable);
		ucenButton.setLayout(new FlowLayout());
		ubtnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inituser();
				mana.readuser();
			}
		});
		ubtnremoveinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = utable.getSelectedRow();
				String authorp;
				String titlep;
				String userp;
				if(selected == -1) {
					JOptionPane.showMessageDialog(null, "삭제 하는 목록을 선택하세요!", "에러!", 0);
					return;
				}
				String[] infoTemp = new String[3];
				for(int i = 0 ; i < infoTemp.length; i++) {
					infoTemp[i] = (String)dtms.getValueAt(selected, i);
				}
				textadduser.setText(infoTemp[0]);
				textaddaddress.setText(infoTemp[1]);
				textaddphone.setText(infoTemp[2]);	
			
			}
		});
		
		//모든목록 버튼
		ucenButton.add(ubtnAll);
		ucenButton.add(ubtnremoveinfo);
		east2.add(ucenTable, "Center");
		east2.add(ucenButton, "South");
		

	}

	
	public void createTable() { //테이블을 만듬
		table = new JTable(dtm, dtcm);
		spTable = new JScrollPane(table);
		TableColumn tc1, tc2, tc3, tc4; //열 너비 지정
		spTable.setPreferredSize(new Dimension(500, 300));

		tc1 = new TableColumn(0, 50);
		tc1.setHeaderValue(str[0]);
		tc2 = new TableColumn(1, 110);
		tc2.setHeaderValue(str[1]);
		tc3 = new TableColumn(2, 50);
		tc3.setHeaderValue(str[2]);
		tc4 = new TableColumn(3, 50);
		tc4.setHeaderValue(str[3]);	
		dtcm.addColumn(tc1);
		dtcm.addColumn(tc2);
		dtcm.addColumn(tc3);
		dtcm.addColumn(tc4);
	}

	public void initTable() { //테이블 초기화
		cenTable.remove(spTable);
		dtm = new DefaultTableModel(str, 0);
		table = new JTable(dtm, dtcm);
		spTable = new JScrollPane(table);
		spTable.setPreferredSize(new Dimension(500, 300));
		cenTable.add(spTable);
		cenTable.validate();
	}
	public void createuser() { //테이블을 만듬
		utable = new JTable(dtms, dtcms);
		uspTable = new JScrollPane(utable);
		TableColumn tc1, tc2, tc3; //열 너비 지정
		uspTable.setPreferredSize(new Dimension(500, 300));

		tc1 = new TableColumn(0, 50);
		tc1.setHeaderValue(strs[0]);
		tc2 = new TableColumn(1, 110);
		tc2.setHeaderValue(strs[1]);
		tc3 = new TableColumn(2, 50);
		tc3.setHeaderValue(strs[2]);
			
		dtcms.addColumn(tc1);
		dtcms.addColumn(tc2);
		dtcms.addColumn(tc3);
		
	}

	public void inituser() { //테이블 초기화
		ucenTable.remove(uspTable);
		dtms = new DefaultTableModel(strs, 0);
		utable = new JTable(dtms, dtcms);
		uspTable = new JScrollPane(utable);
		uspTable.setPreferredSize(new Dimension(500, 300));
		ucenTable.add(uspTable);
		ucenTable.validate();
	}
}
