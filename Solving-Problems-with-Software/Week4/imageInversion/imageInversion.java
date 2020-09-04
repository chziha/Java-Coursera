
/**
 * Write a description of batchGrayscale here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.io.*;

public class imageInversion {
    // Method to convert an image into grayscale
    public ImageResource makeInversion(ImageResource inImage) {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel : outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            pixel.setRed(255 - inPixel.getRed());
            pixel.setGreen(255 - inPixel.getGreen());
            pixel.setBlue(255 - inPixel.getBlue());
        }
        return outImage;
    }
    
    // Method to select images and convert them to grayscale and save
    public void selectAndConvert() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageResource outImage = makeInversion(inImage);
            String inName = inImage.getFileName();
            String outName = "inverted-" + inName;
            outImage.setFileName(outName);
            outImage.save();
        }
    }
}
