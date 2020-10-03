package module6;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
import de.fhpotsdam.unfolding.utils.ScreenPosition;
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
	// that the setup and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = false;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	// private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_month.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	// Markers for each earthquake
	private List<Marker> quakeMarkers;

	// A List of country markers
	private List<Marker> countryMarkers;
	
	// NEW IN MODULE 5
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	public void setup() {		
		// (1) Initializing canvas and map tiles
		size(900, 700, OPENGL);
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 650, 600, new Microsoft.HybridProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
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
		  //Check if LandQuake
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
	    
	    // Print sorted earthquakes in the order from high to low in magnitude
	    sortAndPrint(100);
	 		
	    // Add all markers to map
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	}
	
	public void draw() {
		background(0);
		map.draw();
		addKey();	
	}
	
	// TODO: Add the method:
		private void sortAndPrint(int numToPrint) {
			Object[] EMList = quakeMarkers.toArray();
			Arrays.sort(EMList);
			if (numToPrint > EMList.length) {
				for (Object EM : EMList) {
					System.out.println(EM);
				}
			} else {
				for (int i = 0; i < numToPrint; i++) {
					System.out.println(EMList[i]);
				}
			}
		}
	
	// Event handler that gets called automatically when the mouse moves
	@Override
	public void mouseMoved() {
		// Clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		}
		selectMarkerIfHover(quakeMarkers);
		selectMarkerIfHover(cityMarkers);
	}
	
	// If there is a marker under the cursor, and lastSelected is null 
	// set the lastSelected to be the first marker found under the cursor
	// Make sure you do not select two markers.
	private void selectMarkerIfHover(List<Marker> markers) {
		// TODO: Implement this method
		if (lastSelected == null) {
			for (Marker m : markers) {
				if (m.isInside(map, mouseX, mouseY)) {
					lastSelected = (CommonMarker) m;
					lastSelected.setSelected(true);
					break;
				}
			}
		}
	}
	
	/** The event handler for mouse clicks
	 * It will display an earthquake and its threat circle of cities
	 * Or if a city is clicked, it will display all the earthquakes 
	 * where the city is in the threat circle
	 */
	@Override
	public void mouseClicked() {
		// TODO: Implement this method
		if (lastClicked != null) {
			lastClicked.setClicked(false);
			lastClicked = null;
			unhideMarkers();
		} else {
			selectMarkerIfClicked(quakeMarkers);
			selectMarkerIfClicked(cityMarkers);
			hideMarkers();
		}
	}
	
	// If there is a marker clicked, and lastClicked is null 
	// set the lastClicked to be the first marker clicked
	// Make sure you do not select two markers.
	private void selectMarkerIfClicked(List<Marker> markers) {
		// TODO: Implement this method
		if (lastClicked == null) {
			for (Marker m : markers) {
				if (m.isInside(map, mouseX, mouseY)) {
					lastClicked = (CommonMarker) m;
					lastClicked.setClicked(true);
					break;
				}
			}
		}
	}
	
	// Loop over and hide unrelated markers
	private void hideMarkers() {
		if (lastClicked instanceof OceanQuakeMarker) {
			((OceanQuakeMarker) lastClicked).impactedCities = new ArrayList<ScreenPosition>();
		}
		if (lastClicked instanceof CityMarker) {
			for (Marker marker : quakeMarkers) {
				double threatCircle = ((EarthquakeMarker) marker).threatCircle();
				if (marker.getDistanceTo(lastClicked.getLocation()) >  threatCircle) {
					marker.setHidden(true);
				}
			}
			for (Marker marker : cityMarkers) {
				marker.setHidden(true);
			}
		} else {
			for (Marker marker : cityMarkers) {
				double threatCircle = ((EarthquakeMarker) lastClicked).threatCircle();
				if (marker.getDistanceTo(lastClicked.getLocation()) >  threatCircle) {
					marker.setHidden(true);
					} else {
						if (lastClicked instanceof OceanQuakeMarker) {
							ScreenPosition sp = ((CityMarker) marker).getScreenPosition(map);
							((OceanQuakeMarker) lastClicked).impactedCities.add(sp);
						}
					}
			}
			for (Marker marker : quakeMarkers) {
				marker.setHidden(true);
			}
		}
		lastClicked.setHidden(false);
	}
	
	// Loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : quakeMarkers) {
			marker.setHidden(false);
		}	
		for(Marker marker : cityMarkers) {
			marker.setHidden(false);
		}
	}
	
	// helper method to draw key in GUI
	private void addKey() {	
		// Remember you can use Processing's graphics methods here
		fill(255, 250, 240);
		
		int xbase = 25;
		int ybase = 50;
		
		rect(xbase, ybase, 150, 250);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Earthquake Key", xbase+25, ybase+25);
		
		fill(150, 30, 30);
		int tri_xbase = xbase + 35;
		int tri_ybase = ybase + 50;
		triangle(tri_xbase - CityMarker.TRI_SIZE / 2 * (float) Math.sqrt(3), tri_ybase + CityMarker.TRI_SIZE / 2, 
				tri_xbase + CityMarker.TRI_SIZE / 2 * (float) Math.sqrt(3), tri_ybase + CityMarker.TRI_SIZE / 2, 
				tri_xbase, tri_ybase - CityMarker.TRI_SIZE);

		fill(0, 0, 0);
		textAlign(LEFT, CENTER);
		text("City Marker", tri_xbase + 15, tri_ybase);
		
		text("Land Quake", xbase+50, ybase+70);
		text("Ocean Quake", xbase+50, ybase+90);
		text("Size ~ Magnitude", xbase+25, ybase+110);
		
		fill(255, 255, 255);
		ellipse(xbase+35, 
				ybase+70, 
				10, 
				10);
		rect(xbase+35-5, ybase+90-5, 10, 10);
		
		fill(color(255, 255, 0));
		ellipse(xbase+35, ybase+140, 12, 12);
		fill(color(0, 0, 255));
		ellipse(xbase+35, ybase+160, 12, 12);
		fill(color(255, 0, 0));
		ellipse(xbase+35, ybase+180, 12, 12);
		
		textAlign(LEFT, CENTER);
		fill(0, 0, 0);
		text("Shallow", xbase+50, ybase+140);
		text("Intermediate", xbase+50, ybase+160);
		text("Deep", xbase+50, ybase+180);

		text("Past hour", xbase+50, ybase+200);
		
		fill(255, 255, 255);
		int centerx = xbase+35;
		int centery = ybase+200;
		ellipse(centerx, centery, 12, 12);

		strokeWeight(2);
		line(centerx-8, centery-8, centerx+8, centery+8);
		line(centerx-8, centery+8, centerx+8, centery-8);	
	}
	
	// Helper method to check if the earthquake is on land or under sea
	// If on land, it sets the "country" property to the country where it occurred
	// and returns true. Otherwise it returns false.
	// The helper method isInCountry set the "country" property already if true.  
	private boolean isLand(PointFeature earthquake) {
		// If isInCountry ever returns true, isLand should return true.
		for (Marker country : countryMarkers) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		// If not inside any country
		return false;
	}
	
	// Helper method to print number of earthquakes within all countries and oceans
	private void printQuakes() {
		int totalWaterQuakes = quakeMarkers.size();
		for (Marker country : countryMarkers) {
			String countryName = country.getStringProperty("name");
			int numQuakes = 0;
			for (Marker marker : quakeMarkers)
			{
				EarthquakeMarker eqMarker = (EarthquakeMarker)marker;
				if (eqMarker.isOnLand()) {
					if (countryName.equals(eqMarker.getStringProperty("country"))) {
						numQuakes++;
					}
				}
			}
			if (numQuakes > 0) {
				totalWaterQuakes -= numQuakes;
				System.out.println(countryName + ": " + numQuakes);
			}
		}
		System.out.println("OCEAN QUAKES: " + totalWaterQuakes);
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
	
	// Get the cityMarkers
	public List<Marker> getCityMarkers() {
		return cityMarkers;
	}
}
