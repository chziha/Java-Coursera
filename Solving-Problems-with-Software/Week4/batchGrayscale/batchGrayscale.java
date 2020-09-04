
/**
 * Write a description of batchGrayscale here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.io.*;

public class batchGrayscale {
    // Method to convert an image into grayscale
    public ImageResource makeGray(ImageResource inImage) {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel : outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            int avg = (inPixel.getRed() + inPixel.getGreen() + inPixel.getBlue()) / 3;
            pixel.setRed(avg);
            pixel.setGreen(avg);
            pixel.setBlue(avg);
        }
        return outImage;
    }
    
    // Method to select images and convert them to grayscale and save
    public void selectAndConvert() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageResource outImage = makeGray(inImage);
            String inName = inImage.getFileName();
            String outName = "gray-" + inName;
            outImage.setFileName(outName);
            outImage.save();
        }
    }
}
