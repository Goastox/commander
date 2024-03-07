package com.goastox.commander.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Test2 {
    public static void main(String[] args) throws IOException {
        BufferedImage read = ImageIO.read(new File("/Users/konghanghang/Documents/v2-7759a9a02b82b69344a2029d652b2926_r.jpeg"));
        int height = read.getHeight();
        int width = read.getWidth();
        System.out.println(height);
        System.out.println(width);
        System.out.println(height * width);

        Color color = new Color(read.getRGB(0, 0));
        System.out.println(color.getRed());
        System.out.println(color.getGreen());
        System.out.println(color.getBlue());
    }
}
