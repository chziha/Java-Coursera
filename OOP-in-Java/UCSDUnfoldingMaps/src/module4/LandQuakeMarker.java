package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for land earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public class LandQuakeMarker extends EarthquakeMarker {
	public LandQuakeMarker(PointFeature quake) {
		// calling EarthquakeMarker constructor
		super(quake);
		// setting field in earthquake marker
		isOnLand = true;
	}

	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		// Draw a centered circle for land quakes
		// Color is set in the EarthquakeMarker
		// TODO: Implement this method
		float r = this.radius;
		pg.ellipse(x, y, 2 * r, 2 * r);	
	}
	
	// Get the country the earthquake is in
	public String getCountry() {
		return (String) getProperty("country");
	}	
}