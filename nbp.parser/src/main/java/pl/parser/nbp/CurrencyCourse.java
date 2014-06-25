package pl.parser.nbp;

import org.joda.time.DateTime;

public class CurrencyCourse {
	private String type;
	private double sellCourse;
	private double buyCourse;
	private DateTime date;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getBuyCourse() {
		return buyCourse;
	}
	public void setBuyCourse(double course) {
		this.buyCourse = course;
	}
	public double getSellCourse() {
		return sellCourse;
	}
	public void setSellCourse(double course) {
		this.sellCourse = course;
	}
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}
}
