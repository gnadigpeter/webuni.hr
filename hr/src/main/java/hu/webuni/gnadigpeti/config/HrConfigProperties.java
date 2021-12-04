package hu.webuni.gnadigpeti.config;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix  = "hr")
@Component
public class HrConfigProperties {
	

	public static class Raise{
		private Default def = new Default();
		private Smart smart = new Smart();
		
		public Default getDef() {
			return def;
		}
		public void setDef(Default def) {
			this.def = def;
		}
		public Smart getSmart() {
			return smart;
		}
		public void setSmart(Smart smart) {
			this.smart = smart;
		}
		
		
	}
	
	public static class Default{
		private int percent;

		public int getPercent() {
			return percent;
		}

		public void setPercent(int percent) {
			this.percent = percent;
		}
		
		
	}
	public static class Smart{
		private float yearTen;
		private float yearFive;
		private float yearTwoPointFive;

		
		private int raiseTen;
		private int raiseFive;
		private int raiseTwo;
		private int raiseZero;
		public float getYearTen() {
			return yearTen;
		}
		public void setYearTen(float yearTen) {
			this.yearTen = yearTen;
		}
		public float getYearFive() {
			return yearFive;
		}
		public void setYearFive(float yearFive) {
			this.yearFive = yearFive;
		}
		public float getYearTwoPointFive() {
			return yearTwoPointFive;
		}
		public void setYearTwoPointFive(float yearTwoPointFive) {
			this.yearTwoPointFive = yearTwoPointFive;
		}
		public int getRaiseTen() {
			return raiseTen;
		}
		public void setRaiseTen(int raiseTen) {
			this.raiseTen = raiseTen;
		}
		public int getRaiseFive() {
			return raiseFive;
		}
		public void setRaiseFive(int raiseFive) {
			this.raiseFive = raiseFive;
		}
		public int getRaiseTwo() {
			return raiseTwo;
		}
		public void setRaiseTwo(int raiseTwo) {
			this.raiseTwo = raiseTwo;
		}
		public int getRaiseZero() {
			return raiseZero;
		}
		public void setRaiseZero(int raiseZero) {
			this.raiseZero = raiseZero;
		}
	
		
	}
	
	private Raise raise = new Raise();

	public Raise getRaise() {
		return raise;
	}

	public void setRaise(Raise raise) {
		this.raise = raise;
	}
	

}
