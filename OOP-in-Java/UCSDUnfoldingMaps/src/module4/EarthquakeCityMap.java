package module4;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {
	
	// We will use member variables, instead of local variables, to store the data
	// that the setUp and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = false;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	

	// Feed with magnitude 2.5+ Earthquakes
	// private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_month.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// Declare map
	private UnfoldingMap map;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	
	// Markers for each earthquake
	private List<Marker> quakeMarkers;

	// A List of country markers
	private List<Marker> countryMarkers;
	
	public void setup() {		
		// Initializing canvas and map tiles
		size(900, 700, OPENGL);
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 650, 600, new Microsoft.HybridProvider());
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// Test files
		// earthquakesURL = "test1.atom";
		// earthquakesURL = "test2.atom";
		
		// For quiz
		// earthquakesURL = "quiz1.atom";
		
		// Reading in earthquake data and geometric properties
	    // Load country features and add in the markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		// Load city features and add in the markers
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) {
		  cityMarkers.add(new CityMarker(city));
		}
	    
		// Load earthquake features
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    // Add the earthquakes into the marker list
	    for(PointFeature feature : earthquakes) {
		  // Check if LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // Or OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }

	    // could be used for debugging
	    printQuakes();
	 		
	    // Add all markers to map
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	    
	}
	
	public void draw() {
		background(0);
		map.draw();
		addKey();	
	}
	
	// Helper method to draw key in GUI
	private void addKey() {
		// Draw the shapes
		fill(255, 255, 255);
		rect(25, 50, 150, 420, 7);
		triangle(50 - 6 * (float) Math.sqrt(3), 120 + (float) 6, 
				 50 + 6 * (float) Math.sqrt(3), 120 + (float) 6, 
				 50, 120 - (float) 12);
		rect(40, 150, 20, 20);
		ellipse(50, 200, 20, 20);
		fill(255, 255, 0);
		ellipse(50, 290, 20, 20);
		fill(0, 0, 255);
		ellipse(50, 330, 20, 20);
		fill(255, 0, 0);
		ellipse(50, 370, 20, 20);
		fill(255, 255, 255);
		ellipse(50, 410, 20, 20);
		line(40, 400, 60, 420);
		line(40, 420, 60, 400);
		// Add texts
		textAlign(LEFT, CENTER);
		textSize(10);
		fill(0, 0, 0);
		text("City Marker", 80, 120);
		text("Land Quake", 80, 160);
		text("Ocean Quake", 80, 200);
		text("Size ~ Magnitude", 53, 240);
		text("Shallow", 80, 290);
		text("Intermediate", 80, 330);
		text("Deep", 80, 370);
		text("Past Day", 80, 410);
		textSize(16);
		text("Earthquake Key", 40, 80);
		
	}
	
	// Helper method to check if the earthquake is on land or under sea
	// If on land, it sets the "country" property to the country where it occurred
	// and returns true. Otherwise it returns false.
	// The helper method isInCountry set the "country" property already if true.  
	private boolean isLand(PointFeature earthquake) {
		// If isInCountry ever returns true, isLand should return true.
		for (Marker m : countryMarkers) {
			// TODO: Finish this method using the helper method isInCountry
			if (isInCountry(earthquake, m)) {
				return true;
			}
		}
		// If not in not country
		return false;
	}
	
	// TODO: Finish this method
	// Helper method to print number of earthquakes within all countries and oceans
	private void printQuakes() 
	{
		int oceanCounter = quakeMarkers.size();
		for (Marker cm : countryMarkers) {
			int countryCounter = 0;
			String name = (String)cm.getProperty("name");
			for (Marker m : quakeMarkers) {
				EarthquakeMarker em = (EarthquakeMarker)m;
				if (em.isOnLand()) {
					String country = (String)m.getProperty("country");
					if (country.equals(name)) {
						countryCounter++;
					}
				}
			}
			oceanCounter -= countryCounter;
			if (countryCounter > 0) {
				System.out.println(name + ": " + countryCounter);
			}
		}
		System.out.println("OCEAN QUAKES: " + oceanCounter);
	}
	
	// Helper method to test whether a given earthquake is in a given country
	// This will also add the country property to the properties of the earthquake 
	// feature if it's in one of the countries.
	// You should not have to modify this code
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// getting location of feature
		Location checkLoc = earthquake.getLocation();
		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {				
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {					
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));				
					// return if is inside one
					return true;
				}
			}
		}	
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			return true;
		}
		return false;
	}

}