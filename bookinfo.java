package exam;
import java.io.Serializable;

public class bookinfo implements Serializable {
	//저자, 책이름, 가격 
	String author;
	String title;
	String rend;
	String price;
	
	public bookinfo(String author,String title,String rend, String price){
		this.author = author;
		this.title = title;
		this.rend = rend;
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public String getPrice() {
		return price;
	}
	public String getrend() {
		return rend;
	}

	public String getTitle() {
		return title;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}


	public void setPrice(String price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setrend(String rend) {
		this.rend = rend;
	}


	public String toString(){
		return getAuthor() + "," + getTitle() + "," + getPrice()+","+getrend();
	}
	void susu(String titles, String authors,String rends, String prices)
	{	
		title = titles;
		author = authors;
		price = prices;
		rend = rends;
	}

}
