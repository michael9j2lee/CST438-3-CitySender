package cst438_assignment3.domain;

public class TempAndTime {
	public double temp;
	public long time;
	public int timezone;
	
	
	public TempAndTime() {
		this.temp = 0;
		this.time = 0;
		this.timezone = 0;
	}
	
	public TempAndTime(double temp, long time, int timezone){
		this.temp = convertTemp(temp);
		this.time = time;
		this.timezone = timezone;
	}
	
	public double convertTemp(double temp) {
		double tempF = Math.round(((temp) - 273.15) * 9.0/5.0 + 32.0); 
		return tempF;
	}
	
	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}
	
	
 }
