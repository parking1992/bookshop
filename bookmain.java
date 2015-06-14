package exam;
/*
 * ���ڸ� �˻�///// �Ϸ�
 * �����׸� ����
 * ���� �����Ұ������� /// �Ϸ�
 * ȸ������ - ȸ������ - ȸ����� �����ֱ�
 *         ȸ�� �˻� - ������
 */


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class bookmain{
	static ArrayList<bookinfo> b = new ArrayList<bookinfo>(); //å �߰��� ���� å ����
	static ArrayList<userinfo> u = new ArrayList<userinfo>(); //ȸ�� ����
	ArrayList<bookinfo> list = new ArrayList<bookinfo>();
	ArrayList<userinfo> list2 = new ArrayList<userinfo>();

	static bookgui gui = new bookgui();
	static String mat;
	static int re;

	//����Ʈ b �� å �߰�
	void addbook(String b_author,String b_title,String b_rend,String b_price){
		if(b_author.isEmpty()||b_title.isEmpty()||b_price.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"��ĭ�� �ֽ��ϴ�. �ٽ� �Է� ���ּ���");
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
					System.out.println("�߰��Ϸ�");
				}
				else{
					JOptionPane.showMessageDialog(null,"����å�� ���� �մϴ�.");
				}
			}	
		}
	}//�����߰�
	
	
	//����Ʈ b å ����
	void removebook(String b_author,String b_title,String b_price){
		if(b.size() < 1){
			JOptionPane.showMessageDialog(null,"å�� �ѱǵ� �����ϴ�..");
		}
		else{
			if(b_author.isEmpty()||b_title.isEmpty()||b_price.isEmpty()){
				JOptionPane.showMessageDialog(null,"��ĭ�� �ֽ��ϴ�. �ٽ� �Է� ���ּ���");
			}
			else{
				int size= b.size();
				for(int i=0; i< b.size(); i++)
				{
					if(b.get(i).author.equals(b_author) && b.get(i).title.equals(b_title)&& b.get(i).price.equals(b_price))
					{
						JOptionPane.showMessageDialog(null,"���� �Ϸ�");
						b.remove(i);			
					}
				}
				//�������Ҷ� - b ������ ���پ��
				if(size == b.size())
				{
					JOptionPane.showMessageDialog(null,"������ å�� �����ϴ�.");
				}
			}	
		}	
	}//���� ����
	
	
	//�뿩 ���� 
	void rendbook(String b_author,String b_title,String user,int select){
		int num = 0;
		if(b_author.isEmpty()||b_title.isEmpty()||user.isEmpty()){
			JOptionPane.showMessageDialog(null,"��ĭ�� �ֽ��ϴ�. �ٽ� �Է� ���ּ���");
		}
		else{
			for(int i=0; i<u.size();i++){
				if(u.get(i).name.equals(user)){
					num = 1;
				}
			}
			if(num == 0){
				JOptionPane.showMessageDialog(null,"�߸��� �����Դϴ�.");
			}
			else{
				String prices = b.get(select).price;
				b.get(select).susu(b_title, b_author, user, prices);
			}
		}
	}
	
	//���� ����
	void returnbook(String b_author,String b_title,String user,int select){
		int num = 0;
		//if �ʿ����
		for(int i=0; i<b.size();i++){	
			if(b.get(i).title.equals(b_title)){
				num = i;
				}
		}	
		String prices = b.get(num).price;
		b.get(num).susu(b_title, b_author, "���Ⱑ��", prices);	
	}

	//å ���� ���� �б�
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
	}//�����б�
	
	//å���� ���� ����
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
	}//��������
	
	//������ �޾� xx���� ���� ���ڸ�,����,ȸ���� �� ��� �˻�
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
	}//�����б�
	
	//////////////////ȸ�� ����
	void adduser(String u_name,String u_address,String u_phone){
		if(u_name.isEmpty()||u_address.isEmpty()||u_phone.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"��ĭ�� �ֽ��ϴ�. �ٽ� �Է� ���ּ���");
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
				//�̸��� �ּ� ������ �ߺ�ȸ��
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
					JOptionPane.showMessageDialog(null,"�ߺ� ȸ�� �Դϴ�.");
				}
			}
		}
	}//ȸ�� �߰�
	
	void removeuser(String u_name,String u_address,String u_phone){
		if(b.size() < 1){
			JOptionPane.showMessageDialog(null,"ȸ���� �����ϴ�.");
		}
		else{
			if(u_name.isEmpty()||u_address.isEmpty()||u_phone.isEmpty()){	
				JOptionPane.showMessageDialog(null,"��ĭ�� �ֽ��ϴ�. �ٽ� �Է� ���ּ���");
			}
			else{
				int size= u.size();
				for(int i=0; i< u.size(); i++)
				{
					if(u.get(i).name.equals(u_name) && u.get(i).address.equals(u_address)&& u.get(i).phone.equals(u_phone))
					{
						JOptionPane.showMessageDialog(null,"���� �Ϸ�");
						u.remove(i);			
					}
				}
				//�������Ҷ� - b ������ ���پ��
				if(size == u.size())
				{
					JOptionPane.showMessageDialog(null,"ȸ���� �����ϴ�.");
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
	}//�����б�
	
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
	}//��������
	
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
	}//�����б�
	

	//å ����Ʈ
	void resetbook() {
	
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		b.add(new bookinfo("���� ����ġ��", "���ǽ�1", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�2", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�3", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�4", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�5", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�6", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�7", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�8", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�9", "���Ⱑ��","3500"));
		b.add(new bookinfo("���� ����ġ��", "���ǽ�10", "���Ⱑ��","3500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ1", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ2", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ3", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ4", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ5", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ6", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ7", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ8", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ9", "���Ⱑ��","2500"));
		b.add(new bookinfo("�丮�߸� ��Ű��", "�巡�ﺼ10", "���Ⱑ��","2500"));
		b.add(new bookinfo("�㿵��", "�İ�1", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�2", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�3", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�4", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�5", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�6", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�7", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�8", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�9", "���Ⱑ��","4500"));
		b.add(new bookinfo("�㿵��", "�İ�10", "���Ⱑ��","4500"));
		b.add(new bookinfo("����ȣ", "�̻�1", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�2", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�3", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�4", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�5", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�6", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�7", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�8", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�9", "���Ⱑ��","4700"));
		b.add(new bookinfo("����ȣ", "�̻�10", "���Ⱑ��","4700"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�1", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�2", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�3", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�4", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�5", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�6", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�7", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�8", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�9", "���Ⱑ��","3000"));
		b.add(new bookinfo("��Ǯ", "Ÿ�̹�10", "���Ⱑ��","3000"));
		b.add(new bookinfo("�����", "�ž�����1", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����2", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����3", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����4", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����5", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����6", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����7", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����8", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����9", "���Ⱑ��","3900"));
		b.add(new bookinfo("�����", "�ž�����10", "���Ⱑ��","3900"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���1", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���2", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���3", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���4", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���5", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���6", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���7", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���8", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���9", "���Ⱑ��","3300"));
		b.add(new bookinfo("����ö", "�����̿뺴 �Ʒ���10", "���Ⱑ��","3300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭1", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭2", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭3", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭4", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭5", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭6", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭7", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭8", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭9", "���Ⱑ��","2300"));
		b.add(new bookinfo("��ī�� �������", "�����鼭10", "���Ⱑ��","2300"));
		b.add(new bookinfo("�ſ���", "Ű�尻1", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻2", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻3", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻4", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻5", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻6", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻7", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻8", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻9", "���Ⱑ��","2800"));
		b.add(new bookinfo("�ſ���", "Ű�尻10", "���Ⱑ��","2800"));
		b.add(new bookinfo("�翵��", "����1", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����2", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����3", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����4", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����5", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����6", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����7", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����8", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����9", "���Ⱑ��","1300"));
		b.add(new bookinfo("�翵��", "����10", "���Ⱑ��","1300"));
		
	
			 
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
	
	//ȸ�� ����Ʈ 
	void resetuser() {
		
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		u.add(new userinfo("ȫ�浿", "��⵵ ������ �꺻��", "0102334201"));
		u.add(new userinfo("¯��", "��⵵ �Ȼ�� ��ϱ�", "0102334202"));
		u.add(new userinfo("¯��", "��⵵ �Ⱦ�� ���ȱ�", "0102334203"));
		u.add(new userinfo("ö��", "��⵵ ������ ������", "0102334204"));
		u.add(new userinfo("ö��", "����Ư���� ��õ�� ", "0102334205"));
		u.add(new userinfo("�迵��", "����Ư���� ��������", "0102334206"));
		u.add(new userinfo("����ȣ", "�λ걤���� ������", "0102334207"));
		u.add(new userinfo("���¿�", "������ ��õ��", "0102334208"));
		u.add(new userinfo("äġ��", "��⵵ ������ �꺻��", "0102334209"));
		u.add(new userinfo("���븸", "��⵵ �Ȼ�� ��ϱ�", "0102334210"));
		u.add(new userinfo("���¼�", "��⵵ �Ⱦ�� ���ȱ�", "0102334211"));
		u.add(new userinfo("���缮", "��⵵ ������ ������", "0102334212"));
		u.add(new userinfo("������", "����Ư���� ��õ�� ", "0102334213"));
		u.add(new userinfo("������", "����Ư���� ��������", "0102334214"));
		u.add(new userinfo("���μ�", "�λ걤���� ������", "0102334215"));
		u.add(new userinfo("���ϴ�", "������ ��õ��", "0102334216"));
		u.add(new userinfo("������", "��⵵ �Ȼ�� �ܿ���", "0102334217"));
		u.add(new userinfo("����", "��⵵ ������ �ó���", "0102334218"));
		u.add(new userinfo("�̳���", "������ ��õ��", "0102334219"));
		u.add(new userinfo("�嵿��", "������ ���ֽ�", "0102334220"));
		
		
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
	//���α׷� ����� bookshop ������ �а� ȭ�鿡 ǥ��
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
	}//�����б�
	
	//�ʱ����� ����� bookshop ������ �ʱ�ȭ
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
	}//��������
	
	//���α׷� ����� user ������ �а� ȭ�鿡 ǥ��
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
	}//�����б�
	
	//�ʱ����� ����� user ������ �ʱ�ȭ
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
	}//��������
	
	public static void main(String[] args)
	{
		bookmain book = new bookmain();
		book.freadfile();
		book.freadfileuser();		
		gui.setVisible(true);
		
		
	}
}
