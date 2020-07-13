package cst438_assignment2.domain;

public class CityWeather {
	
	private double temp;
	private String condition;
	
	public CityWeather() {
		
	}
	
	public CityWeather( double temp, String condition) {
		this.temp = temp;
		this.condition = condition;
	}

	public double getTemp() {
		return temp;
	}
	
	public void setTemp(double temp) {
		this.temp = temp;
	}

	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition=condition;
	}

	@Override
	public String toString() {
		return "CityWeather [temp=" + temp + ", condition=" + condition + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityWeather other = (CityWeather) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (Double.doubleToLongBits(temp) != Double.doubleToLongBits(other.temp))
			return false;
		return true;
	}

}
