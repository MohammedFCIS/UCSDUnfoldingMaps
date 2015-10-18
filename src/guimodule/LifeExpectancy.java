package guimodule;

import java.util.HashMap;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet{
	private UnfoldingMap map;
	private Map<String,Float> lifeExpectationByCountry;
	
	public void setup(){
		lifeExpectationByCountry = loadLifeExpectancyFromCSV(""
				+ "data/LifeExpectancyWorldBank.csv");
		size(800, 600, OPENGL);
		map =  new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
	}
	public void drow(){
		map.draw();
	}
	
	private Map<String, Float> loadLifeExpectancyFromCSV(String filename) {
		Map<String,Float> lifeExpecByCountry = new HashMap<String,Float>();
		String[] rows = loadStrings(filename);
		for(String row:rows){
			String[] colmuns = row.split(",");
			if(true){
				float value = Float.parseFloat(colmuns[5]);
				lifeExpecByCountry.put(colmuns[4], value);
			}
		}
		return lifeExpecByCountry;
	}

	

}
