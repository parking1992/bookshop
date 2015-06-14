package exam;
/*
 * 저자명 검색///// 완료
 * 도든항목 보기
 * 대출 수정불가능으로 /// 완료
 * 회원관리 - 회원가입 - 회원목록 보여주기
 *         회원 검색 - 대출기록
 */


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class bookmain{
	static ArrayList<bookinfo> b = new ArrayList<bookinfo>(); //책 추가에 대한 책 정보
	static ArrayList<userinfo> u = new ArrayList<userinfo>(); //회원 정보
	ArrayList<bookinfo> list = new ArrayList<bookinfo>();
	ArrayList<userinfo> list2 = new ArrayList<userinfo>();

	static bookgui gui = new bookgui();
	static String mat;
	static int re;

	//리스트 b 에 책 추가
	void addbook(String b_author,String b_title,String b_rend,String b_price){
		if(b_author.isEmpty()||b_title.isEmpty()||b_price.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"빈칸이 있습니다. 다시 입력 해주세요");
		}
		else
		{
			if(b.size() == 0)
			{
				b.add(new bookinfo(b_author,b_title,b_rend,b_price));
			}
			else
			{
				int count = 0;
				for(int i=0; i<b.size();i++)
				{	
					if(b.get(i).author.equals(b_author) && b.get(i).title.equals(b_title) && b.get(i).price.equals(b_price)){
						count = 1;
					}
				}
				if(count == 0){
					b.add(new bookinfo(b_author,b_title,b_rend,b_price));
					System.out.println("추가완료");
				}
				else{
					JOptionPane.showMessageDialog(null,"같은책이 존재 합니다.");
				}
			}	
		}
	}//도서추가
	
	
	//리스트 b 책 삭제
	void removebook(String b_author,String b_title,String b_price){
		if(b.size() < 1){
			JOptionPane.showMessageDialog(null,"책이 한권도 없습니다..");
		}
		else{
			if(b_author.isEmpty()||b_title.isEmpty()||b_price.isEmpty()){
				JOptionPane.showMessageDialog(null,"빈칸이 있습니다. 다시 입력 해주세요");
			}
			else{
				int size= b.size();
				for(int i=0; i< b.size(); i++)
				{
					if(b.get(i).author.equals(b_author) && b.get(i).title.equals(b_title)&& b.get(i).price.equals(b_price))
					{
						JOptionPane.showMessageDialog(null,"삭제 완료");
						b.remove(i);			
					}
				}
				//삭제안할때 - b 사이즈 안줄어듬
				if(size == b.size())
				{
					JOptionPane.showMessageDialog(null,"선택한 책이 없습니다.");
				}
			}	
		}	
	}//도서 삭제
	
	
	//대여 관리 
	void rendbook(String b_author,String b_title,String user,int select){
		int num = 0;
		if(b_author.isEmpty()||b_title.isEmpty()||user.isEmpty()){
			JOptionPane.showMessageDialog(null,"빈칸이 있습니다. 다시 입력 해주세요");
		}
		else{
			for(int i=0; i<u.size();i++){
				if(u.get(i).name.equals(user)){
					num = 1;
				}
			}
			if(num == 0){
				JOptionPane.showMessageDialog(null,"잘못된 정보입니다.");
			}
			else{
				String prices = b.get(select).price;
				b.get(select).susu(b_title, b_author, user, prices);
			}
		}
	}
	
	//대출 관리
	void returnbook(String b_author,String b_title,String user,int select){
		int num = 0;
		//if 필요없음
		for(int i=0; i<b.size();i++){	
			if(b.get(i).title.equals(b_title)){
				num = i;
				}
		}	
		String prices = b.get(num).price;
		b.get(num).susu(b_title, b_author, "대출가능", prices);	
	}

	//책 정보 파일 읽기
	void readfile(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream("bookshop.dat");
			ois = new ObjectInputStream(fin);
			
			String[] loadstr = new String[4];
			for(int i=0; i<b.size(); i++){
				loadstr[0] = b.get(i).toString().split(",")[0];
				loadstr[1] = b.get(i).toString().split(",")[1];
				loadstr[2] = b.get(i).toString().split(",")[3];
				loadstr[3] = b.get(i).toString().split(",")[2];
				gui.dtm.addRow(loadstr);
			
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally		
	}//파일읽기
	
	//책정보 파일 저장
	void savefile(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try{
			fout = new FileOutputStream("bookshop.dat");
			oos = new ObjectOutputStream(fout);
			
			list.clear();
			
			for(int i=0; i<b.size();i++)
			{
				list.add((bookinfo) b.get(i));
			}
	
			oos.writeObject(list);
			oos.reset();
			oos.writeObject(list);
			oos.reset();
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}//파일저장
	
	//변수를 받아 xx값에 따라 저자명,제목,회원명 을 골라서 검색
	void readsearch(String filename,String search,int xx){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
		
			ArrayList u = (ArrayList)ois.readObject();
			String[] loadstr = new String[4];
			if(xx == 0 ){
				for(int i=0; i<u.size(); i++){
					mat = b.get(i).author;
					re = mat.indexOf(search);
					if(re != -1){
						loadstr[0] = b.get(i).author;
						loadstr[1] = b.get(i).title;
						loadstr[2] = b.get(i).rend;
						loadstr[3] = b.get(i).price;
						gui.dtm.addRow(loadstr);
					}
				}
			}
			else if(xx == 1){
				for(int i=0; i<u.size(); i++){
					mat = b.get(i).title;
					re = mat.indexOf(search);
					if(re != -1){
						loadstr[0] = b.get(i).author;
						loadstr[1] = b.get(i).title;
						loadstr[2] = b.get(i).rend;
						loadstr[3] = b.get(i).price;
						gui.dtm.addRow(loadstr);
					}
				}
			}
			else{
				for(int i=0; i<u.size(); i++){
					mat = b.get(i).rend;
					re = mat.indexOf(search);
					if(re != -1){
						loadstr[0] = b.get(i).author;
						loadstr[1] = b.get(i).title;
						loadstr[2] = b.get(i).rend;
						loadstr[3] = b.get(i).price;
						gui.dtm.addRow(loadstr);
					}
				}
			}
				
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally		
	}//파일읽기
	
	//////////////////회원 가입
	void adduser(String u_name,String u_address,String u_phone){
		if(u_name.isEmpty()||u_address.isEmpty()||u_phone.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"빈칸이 있습니다. 다시 입력 해주세요");
		}
		else
		{	
			if(u.size() == 0)
			{
				u.add(new userinfo(u_name,u_address,u_phone));
			}
			else
			{
				int count = 0;
				//이름과 주소 같으면 중복회원
				for(int i=0; i<u.size();i++)
				{	
					if(u.get(i).name.equals(u_name) && u.get(i).address.equals(u_address)){
						count = 1;
					}
				}
				if(count == 0){
					u.add(new userinfo(u_name,u_address,u_phone));
				}
				else{
					JOptionPane.showMessageDialog(null,"중복 회원 입니다.");
				}
			}
		}
	}//회원 추가
	
	void removeuser(String u_name,String u_address,String u_phone){
		if(b.size() < 1){
			JOptionPane.showMessageDialog(null,"회원이 없습니다.");
		}
		else{
			if(u_name.isEmpty()||u_address.isEmpty()||u_phone.isEmpty()){	
				JOptionPane.showMessageDialog(null,"빈칸이 있습니다. 다시 입력 해주세요");
			}
			else{
				int size= u.size();
				for(int i=0; i< u.size(); i++)
				{
					if(u.get(i).name.equals(u_name) && u.get(i).address.equals(u_address)&& u.get(i).phone.equals(u_phone))
					{
						JOptionPane.showMessageDialog(null,"삭제 완료");
						u.remove(i);			
					}
				}
				//삭제안할때 - b 사이즈 안줄어듬
				if(size == u.size())
				{
					JOptionPane.showMessageDialog(null,"회원이 없습니다.");
				}
			}	
		}	
	}
	void readuser(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream("user.dat");
			ois = new ObjectInputStream(fin);
			
			String[] loadstr = new String[3];
			for(int i=0; i<u.size(); i++){
				loadstr[0] = u.get(i).toString().split(",")[0];
				loadstr[1] = u.get(i).toString().split(",")[1];
				loadstr[2] = u.get(i).toString().split(",")[2];
				gui.dtms.addRow(loadstr);
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally		
	}//파일읽기
	
	void saveuser(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try{
			fout = new FileOutputStream("user.dat");
			oos = new ObjectOutputStream(fout);			
			
			list2.clear();
			for(int i=0; i<u.size();i++)
			{
				list2.add((userinfo) u.get(i));
			}
			
			oos.writeObject(list2);
			oos.reset();
			oos.writeObject(list2);
			oos.reset();
			
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}//파일저장
	
	void readsearchuser(String filename,String search,int xx){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try{
			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);	
			ArrayList b = (ArrayList)ois.readObject();
			String[] loadstr = new String[3];
			if(xx == 0){
				for(int i=0; i<u.size(); i++){
					mat = u.get(i).name;
					re = mat.indexOf(search);
					if(re != -1){
						loadstr[0] = u.get(i).name;
						loadstr[1] = u.get(i).address;
						loadstr[2] = u.get(i).phone;
						gui.dtms.addRow(loadstr);
					}
				}
			}
			else{
				for(int i=0; i<u.size(); i++){
					mat = u.get(i).phone;
					re = mat.indexOf(search);
					if(re != -1){
						loadstr[0] = u.get(i).name;
						loadstr[1] = u.get(i).address;
						loadstr[2] = u.get(i).phone;
						gui.dtms.addRow(loadstr);
					}
				}
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally		
	}//파일읽기
	

	//책 리스트
	void resetbook() {
	
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		b.add(new bookinfo("오다 에이치로", "원피스1", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스2", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스3", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스4", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스5", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스6", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스7", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스8", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스9", "대출가능","3500"));
		b.add(new bookinfo("오다 에이치로", "원피스10", "대출가능","3500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼1", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼2", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼3", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼4", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼5", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼6", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼7", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼8", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼9", "대출가능","2500"));
		b.add(new bookinfo("토리야마 아키라", "드래곤볼10", "대출가능","2500"));
		b.add(new bookinfo("허영만", "식객1", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객2", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객3", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객4", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객5", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객6", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객7", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객8", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객9", "대출가능","4500"));
		b.add(new bookinfo("허영만", "식객10", "대출가능","4500"));
		b.add(new bookinfo("윤태호", "미생1", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생2", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생3", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생4", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생5", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생6", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생7", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생8", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생9", "대출가능","4700"));
		b.add(new bookinfo("윤태호", "미생10", "대출가능","4700"));
		b.add(new bookinfo("강풀", "타이밍1", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍2", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍3", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍4", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍5", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍6", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍7", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍8", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍9", "대출가능","3000"));
		b.add(new bookinfo("강풀", "타이밍10", "대출가능","3000"));
		b.add(new bookinfo("양경일", "신암행어사1", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사2", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사3", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사4", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사5", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사6", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사7", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사8", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사9", "대출가능","3900"));
		b.add(new bookinfo("양경일", "신암행어사10", "대출가능","3900"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스1", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스2", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스3", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스4", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스5", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스6", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스7", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스8", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스9", "대출가능","3300"));
		b.add(new bookinfo("류금철", "떠돌이용병 아레스10", "대출가능","3300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서1", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서2", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서3", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서4", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서5", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서6", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서7", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서8", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서9", "대출가능","2300"));
		b.add(new bookinfo("토카시 요시히로", "유유백서10", "대출가능","2300"));
		b.add(new bookinfo("신영우", "키드갱1", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱2", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱3", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱4", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱5", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱6", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱7", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱8", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱9", "대출가능","2800"));
		b.add(new bookinfo("신영우", "키드갱10", "대출가능","2800"));
		b.add(new bookinfo("양영순", "덴마1", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마2", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마3", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마4", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마5", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마6", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마7", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마8", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마9", "대출가능","1300"));
		b.add(new bookinfo("양영순", "덴마10", "대출가능","1300"));
		
	
			 
		try{
			fout = new FileOutputStream("bookshop.dat");
			oos = new ObjectOutputStream(fout);
			
			oos.writeObject(b);
			oos.reset();
			oos.writeObject(b);
			oos.reset();
			
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}
	
	//회원 리스트 
	void resetuser() {
		
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		u.add(new userinfo("홍길동", "경기도 군포시 산본동", "0102334201"));
		u.add(new userinfo("짱구", "경기도 안산시 상록구", "0102334202"));
		u.add(new userinfo("짱아", "경기도 안양시 만안구", "0102334203"));
		u.add(new userinfo("철수", "경기도 군포시 금정동", "0102334204"));
		u.add(new userinfo("철이", "서울특별시 금천구 ", "0102334205"));
		u.add(new userinfo("김영길", "서울특별시 영등포구", "0102334206"));
		u.add(new userinfo("강백호", "부산광역시 금정구", "0102334207"));
		u.add(new userinfo("서태웅", "강원도 춘천시", "0102334208"));
		u.add(new userinfo("채치수", "경기도 군포시 산본동", "0102334209"));
		u.add(new userinfo("정대만", "경기도 안산시 상록구", "0102334210"));
		u.add(new userinfo("송태섭", "경기도 안양시 만안구", "0102334211"));
		u.add(new userinfo("유재석", "경기도 군포시 금정동", "0102334212"));
		u.add(new userinfo("김태희", "서울특별시 금천구 ", "0102334213"));
		u.add(new userinfo("소지섭", "서울특별시 영등포구", "0102334214"));
		u.add(new userinfo("조인성", "부산광역시 금정구", "0102334215"));
		u.add(new userinfo("김하늘", "강원도 춘천시", "0102334216"));
		u.add(new userinfo("강동원", "경기도 안산시 단원구", "0102334217"));
		u.add(new userinfo("원빈", "경기도 군포시 궁내동", "0102334218"));
		u.add(new userinfo("이나영", "강원도 춘천시", "0102334219"));
		u.add(new userinfo("장동건", "강원도 원주시", "0102334220"));
		
		
		try{
			fout = new FileOutputStream("user.dat");
			oos = new ObjectOutputStream(fout);
			
			oos.writeObject(u);
			oos.reset();
			oos.writeObject(u);
			oos.reset();
			
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}
	//프로그램 실행시 bookshop 파일을 읽고 화면에 표시
	void freadfile(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream("bookshop.dat");
			ois = new ObjectInputStream(fin);
			
			ArrayList list = (ArrayList)ois.readObject();
			
			String[] loadstr = new String[4];
			for(int i=0; i<list.size(); i++){
				loadstr[0] = list.get(i).toString().split(",")[0];
				loadstr[1] = list.get(i).toString().split(",")[1];
				loadstr[2] = list.get(i).toString().split(",")[3];
				loadstr[3] = list.get(i).toString().split(",")[2];
				b.add(new bookinfo(loadstr[0], loadstr[1], loadstr[2],loadstr[3]));
				
				gui.dtm.addRow(loadstr);
			
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally		
	}//파일읽기
	
	//초기파일 실행기 bookshop 파일을 초기화
	void savef(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try{
			fout = new FileOutputStream("bookshop.dat");
			oos = new ObjectOutputStream(fout);
			
			b.clear();
			
			oos.writeObject(b);
			oos.reset();
			oos.writeObject(b);
			oos.reset();
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		}
	}//파일저장
	
	//프로그램 실행시 user 파일을 읽고 화면에 표시
	void freadfileuser(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream("user.dat");
			ois = new ObjectInputStream(fin);
			
			
			ArrayList list2 = (ArrayList)ois.readObject();
			
			String[] loadstr = new String[3];
			for(int i=0; i<list2.size(); i++){
				loadstr[0] = list2.get(i).toString().split(",")[0];
				loadstr[1] = list2.get(i).toString().split(",")[1];
				loadstr[2] = list2.get(i).toString().split(",")[2];
				u.add(new userinfo(loadstr[0], loadstr[1], loadstr[2]));
				
				gui.dtms.addRow(loadstr);
			
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} 		
	}//파일읽기
	
	//초기파일 실행기 user 파일을 초기화
	void savefuser(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try{
			fout = new FileOutputStream("user.dat");
			oos = new ObjectOutputStream(fout);
			
			u.clear();
			
			oos.writeObject(u);
			oos.reset();
			oos.writeObject(u);
			oos.reset();
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}//파일저장
	
	public static void main(String[] args)
	{
		bookmain book = new bookmain();
		book.freadfile();
		book.freadfileuser();		
		gui.setVisible(true);
		
		
	}
}
