package module5;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.*;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.utils.ScreenPosition;

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
	
	public List<ScreenPosition> impactedCities = new ArrayList<ScreenPosition>();
	
	/** Draw the earthquake as a square */
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.rect(x-radius, y-radius, 2*radius, 2*radius);
		if (this.getClicked()) {
			for (ScreenPosition sp : impactedCities) {
				pg.pushStyle();
				pg.stroke(0, 0, 0);
				pg.line(x, y, sp.x - 200, sp.y - 50);
				pg.popStyle();
			}
		} else {
			for (ScreenPosition sp : impactedCities) {
				pg.pushStyle();
				pg.noStroke();
				pg.line(sp.x, sp.y, x, y);
				pg.popStyle();
			}
		}
	}
}