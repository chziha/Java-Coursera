package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	// Feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	// private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_month.atom"
	public void setup() {
		size(950, 600, OPENGL);
		
		// Set up the map
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.HybridProvider());
			//earthquakesURL = "2.5_week.atom";
		}
		
		// Set the default zoom level and make the map interactive
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // Declare the List for features and markers
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    List<Marker> markers = new ArrayList<Marker>();	    
	    
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    for (PointFeature eq : earthquakes) {
	    	SimplePointMarker marker;
	    	marker = createMarker(eq);
	    	markers.add(marker);
	    }
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature) {  
		System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
	    int yellow = color(255, 255, 0);
	    int blue = color(0, 0, 255);
	    int red = color(255, 0, 0);
	    
	    if (mag < THRESHOLD_LIGHT) {
	    	marker.setColor(blue);
	    	marker.setRadius(height/75);
	    } else if (mag >= THRESHOLD_MODERATE) {
	    	marker.setColor(red);
	    	marker.setRadius(height/30);
	    } else {
	    	marker.setColor(yellow);
	    	marker.setRadius(height/50);
	    }
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}

	// TODO: Implement this method to draw the key
	private void addKey() {
		// Draw the shapes
		fill(255, 255, 255);
		rect(25, 50, 150, 300, 7);
		fill(255, 0, 0);
		ellipse(50, 150, 20, 20);
		fill(255, 255, 0);
		ellipse(50, 200, 12, 12);
		fill(0, 0, 255);
		ellipse(50, 250, 8, 8);
		// Add texts
		textSize(8);
		fill(0, 0, 0);
		text("5.0+ Magnitude", 80, 150);
		text("4.0+ Magnitude", 80, 200);
		text("Below 4.0", 80, 250);
		textSize(16);
		text("Earthquake Key", 40, 80);
	}
}
