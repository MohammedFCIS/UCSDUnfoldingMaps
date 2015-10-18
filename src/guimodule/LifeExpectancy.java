package guimodule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet {
	private UnfoldingMap map;
	private Map<String, Float> lifeExpectationByCountry;
	private List<Feature> countries;
	private List<Marker> countryMarkers;

	public void setup() {
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		// Load lifeExpectancy data
		lifeExpectationByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
		println("Loaded " + lifeExpectationByCountry.size() + " data entries");

		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		// Country markers are shaded according to life expectancy (only once)
		shadeCountries();
	}

	public void draw() {
		// Draw map tiles and country markers
		map.draw();
	}

	// Helper method to color each country based on life expectancy
	// Red-orange indicates low (near 40)
	// Blue indicates high (near 100)
	private void shadeCountries() {
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
			if (lifeExpectationByCountry.containsKey(countryId)) {
				float lifeExp = lifeExpectationByCountry.get(countryId);
				// Encode value as brightness (values range: 40-90)
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255 - colorLevel, 100, colorLevel));
			} else {
				marker.setColor(color(150, 150, 150));
			}
		}
	}

	// Helper method to load life expectancy data from file
	private HashMap<String, Float> loadLifeExpectancyFromCSV(String fileName) {
		HashMap<String, Float> lifeExpMap = new HashMap<String, Float>();

		String[] rows = loadStrings(fileName);
		for (String row : rows) {
			// Reads country name and population density value from CSV row
			// NOTE: Splitting on just a comma is not a great idea here, because
			// the csv file might have commas in their entries, as this one
			// does.
			// We do a smarter thing in ParseFeed, but for simplicity,
			// we just use a comma here, and ignore the fact that the first
			// field is split.
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
				lifeExpMap.put(columns[4], Float.parseFloat(columns[5]));
			}
		}

		return lifeExpMap;
	}

}
