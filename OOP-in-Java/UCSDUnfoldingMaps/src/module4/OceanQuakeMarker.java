package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		// setting field in earthquake marker
		isOnLand = false;
	}
	
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		// Draw a centered square for ocean earthquakes
		// Color is set in the EarthquakeMarker
		// TODO: Implement this method
		float r = this.radius;
		pg.rect(x + r, y + r, 2 * r, 2 * r);	
	}
}
