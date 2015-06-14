package exam;

import java.io.Serializable;

public class userinfo implements Serializable {
	String name;
	String address;
	String phone;

	public userinfo(String name,String address, String phone)
	{
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public String getname() {
		return name;
	}

	public String getaddress() {
		return address;
	}
	public String getphone() {
		return phone;
	}
	public void setname(String name) {
		this.name = name;
	}


	public void setaddress(String address) {
		this.address = address;
	}

	public void setphon(String phone) {
		this.phone = phone;
	}
	public String toString(){
		return getname() + "," + getaddress() + "," + getphone();
	}
}
