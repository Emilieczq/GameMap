/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class PlottingVisitor implements Visitor<SquareCell> {

    int w, h, gridSize;
    Color color;
    String shape;

    PlottingVisitor(int w, int h, int gridSize, Color color, String shape) {
        this.w = w;
        this.h = h;
        this.gridSize = gridSize;
        this.color = color;
        this.shape = shape;
    }

    public boolean visit(SquareCell cell) {
        StdDraw.setPenColor(color);
        double x = cell.c * gridSize + gridSize / 2;
        double y = h - (cell.r * gridSize + gridSize / 2);
        double size = gridSize / 4.;
        switch (shape) {
            case "filledCircle": {
                StdDraw.filledCircle(x, y, size);
                break;
            }
            case "circle": {
                StdDraw.circle(x, y, size);
                break;
            }
            case "cross": {
                StdDraw.line(x - size, y - size, x + size, y + size);
                StdDraw.line(x + size, y - size, x - size, y + size);
                break;

            }
        }
        return true;
    }
}

public class IslandTour {

    /**
     *
     */
    public static enum ColorData {

        UNKNOWN(0x000000), OCEAN(0x44447a), LAKE(0x336699), BEACH(0xa09077), SNOW(0xffffff),
        TUNDRA(0xbbbbaa), BARE(0x888888), SCORCHED(0x555555), TAIGA(0x99aa77),
        TEMPERATE_DESERT(0xc9d29b),
        TEMPERATE_RAIN_FOREST(0x448855), TEMPERATE_DECIDUOUS_FOREST(0x679459),
        GRASSLAND(0x88aa55), SUBTROPICAL_DESERT(0xd2b98b), SHRUBLAND(0x889977),
        ICE(0x99ffff), MARSH(0x2f6666), TROPICAL_RAIN_FOREST(0x337755),
        TROPICAL_SEASONAL_FOREST(0x559944), COAST(0x33335a),
        LAKESHORE(0x225588), RIVER(0x225588);
        public int color;

        ColorData(int color) {
            this.color = color;
        }
    }

    public final static Map<Integer, Integer> colorToCost = new HashMap<Integer, Integer>();

    static {
        colorToCost.put(ColorData.UNKNOWN.color, Integer.MAX_VALUE);
        colorToCost.put(ColorData.OCEAN.color, Integer.MAX_VALUE);
        colorToCost.put(ColorData.LAKE.color, Integer.MAX_VALUE);
        colorToCost.put(ColorData.BEACH.color, 10);
        colorToCost.put(ColorData.SNOW.color, 20);
        colorToCost.put(ColorData.TUNDRA.color, 15);
        colorToCost.put(ColorData.BARE.color, 12);
        colorToCost.put(ColorData.SCORCHED.color, 13);
        colorToCost.put(ColorData.TAIGA.color, 2);
        colorToCost.put(ColorData.TEMPERATE_DESERT.color, 13);
        colorToCost.put(ColorData.TEMPERATE_RAIN_FOREST.color, 5);
        colorToCost.put(ColorData.TEMPERATE_DECIDUOUS_FOREST.color, 6);
        colorToCost.put(ColorData.TROPICAL_SEASONAL_FOREST.color, 5);
        colorToCost.put(ColorData.GRASSLAND.color, 1);
        colorToCost.put(ColorData.SUBTROPICAL_DESERT.color, 8);
        colorToCost.put(ColorData.SHRUBLAND.color, 13);
        colorToCost.put(ColorData.MARSH.color, 25);
        colorToCost.put(ColorData.TROPICAL_RAIN_FOREST.color, 7);
        colorToCost.put(ColorData.COAST.color, 4);
        colorToCost.put(ColorData.LAKESHORE.color, 3);
        colorToCost.put(ColorData.RIVER.color, 30);

    }

    private static int[] dimension(int imgW, int imgH, int screenW, int screenH) {
        System.err.println("" + imgW + "," + imgH + "," + screenW + "," + screenH);
        int possibleW = Math.min(imgW, screenW);
        int possibleH = Math.min(imgH, screenH);
        float wRatio = (float) possibleW / imgW;
        float hRatio = (float) possibleH / imgH;
        float ratio = Math.min(wRatio, hRatio);
        System.err.println(ratio);
        return new int[]{(int) (imgW + ratio), (int) (imgH + ratio)};
    }

    public static void drawGrid(int imgWidth, int imgHeight, int gridSize) {
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 0; x < imgWidth; x += gridSize) {
            StdDraw.line(x, 0, x, imgHeight);
        }
        for (int y = 0; y < imgHeight; y += gridSize) {
            StdDraw.line(0, y, imgWidth, y);
        }
    }

    public static int saturatingAddPos(int v0, int v1) {
        return Integer.MAX_VALUE - v0 > v1 ? v0 + v1 : Integer.MAX_VALUE;
    }

    public static int[][] makeCostGrid(BufferedImage image, int gridSize) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        int[][] res = new int[imgHeight / gridSize][imgWidth / gridSize];
        for (int  r = 0; r != res.length;++r){
            for (int c = 0; c != res[r].length; ++c) {
                for (int y = r * gridSize; y != (r + 1) * gridSize; ++y) {
                    for(int x = c*gridSize; x != (c+1)*gridSize; ++x) {
                        try{
                            res[r][c] = saturatingAddPos(res[r][c], colorToCost.get(image.getRGB(x,y)& 0xFFFFFF));
                        }catch(Exception e){
                            System.err.println("e:"+e+"\nfor color "+String.format("0x%06%", (image.getRGB(x,y)& 0xFFFFFF)));
                        }
                    }
                }

            }
        }
        return res;
    }
    
    private static boolean isInGrid(int[][] grid, SquareCell c) {
        return (c.r >= 0) && (c.r < grid.length) && (c.c >= 0) && (c.c < grid[c.r].length);
    }
    
    /**
     * VisitableAdjacencyWeightedDiGraphWithHeuristic ??
     * not finished
     */
    private static VisitableAdjacencyWeightedDiGraph<SquareCell, Integer> makeCostGraph(int[][] costGrid) {
        VisitableAdjacencyWeightedDiGraph<SquareCell, Integer> res =
                new VisitableAdjacencyWeightedDiGraphWithHeuristic<SquareCell, Integer>(new DistanceHeuristic<SquareCell>(){
                    public int distance(SquareCell c0, SquareCell c1){
                        return c0.manhattanDistanceTo(c1);
                    }
                });
        return res;
    }
    
    private static void drawBackground(String filename, int w, int h, int gridSize, Color gridColorOrNull) {
        StdDraw.picture(w / 2, h / 2, filename, w, h);
        if (gridColorOrNull != null) {
            StdDraw.setPenColor(gridColorOrNull);
            for (int x = 0; x < w; x += gridSize) {
                StdDraw.line(x, 0, x, h);
            }
            for (int y = 0; y < h; y += gridSize) {
                StdDraw.line(0, y, w, y);
            }
        }
    }

    private static void drawDots(int w, int h, int gridSize, Collection<SquareCell> cells, Color color, boolean isFilled) {
        PlottingVisitor v = new PlottingVisitor(w, h, gridSize, color, isFilled ? "filledCircle" : "circle");
        for(SquareCell cell : cells){
            v.visit(cell);
        }
    }
    
    private static Collection<SquareCell> readTour(int gridSize, int imgWidth, int imgHeight, InputStream is) throws IOException {
        List<SquareCell> res = new ArrayList<SquareCell>();
        Scanner sc = new Scanner(is);
        while (sc.hasNext()) {
            final int x = sc.nextInt();
            final int y = sc.nextInt();
            res.add(new SquareCell((imgHeight - y) / gridSize, x / gridSize));
        }
        return res;
    }
    
    private static void displayDistance(int gridSize, int imgWidth, int imgHeight,
            String filename,
            VisitableAdjacencyWeightedDiGraph<SquareCell, Integer> distances) {
        for (SquareCell src = new SquareCell(0, 0), dest = new SquareCell(0, 0);;) {
            boolean change = false;
            SquareCell current = new SquareCell((int) ((imgHeight - StdDraw.mouseY()) / gridSize), (int) (StdDraw.mouseX() / gridSize));
            if (StdDraw.isMousePressed()) {
                change = !current.equals(src);
                src = current;
            }else{
                change = !current.equals(dest);
                dest = current;
            }
            if (change) {
                drawBackground(filename, imgWidth, imgHeight, gridSize, null);
                distances.setVerticesVisitor(new PlottingVisitor(imgWidth, imgHeight, gridSize, StdDraw.BLACK, "circle"));
                drawDots(imgWidth, imgHeight, gridSize, distances.shortestPath(src, dest), StdDraw.RED, true);
                StdDraw.show();
            }
        }
    }
    
    private static Collection<SquareCell> shortestTour(VisitableAdjacencyWeightedDiGraph<SquareCell, Integer> distances, 
            Collection<SquareCell> verticesToVisit){
        //TODO
        return new ArrayList<SquareCell>();
    }
    
    public static void main(String args[]) throws IOException, InterruptedException {
        StdDraw.enableDoubleBuffering();
        String filename = args[0];
        int gridSize = Integer.parseInt(args[1]);
        File file = new File(filename);
        BufferedImage image = ImageIO.read(file);
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int[] wh = dimension(imgWidth, imgHeight, (int) (screenSize.getWidth() * 0.9),
                (int) (screenSize.getHeight() * 0.9));
        StdDraw.setCanvasSize(wh[0], wh[1]);
        StdDraw.setXscale(0, imgWidth);
        StdDraw.setYscale(0, imgHeight);
        VisitableAdjacencyWeightedDiGraph<SquareCell, Integer> distances = makeCostGraph(makeCostGrid(image, gridSize));
        if (args.length < 3) { // distance distances
            displayDistance(gridSize, imgHeight, imgWidth, filename, distances);
        } else {// display tour
            Collection<SquareCell> tour = readTour(gridSize, imgWidth, imgHeight, new FileInputStream(new File(args[2])));
            drawBackground(filename, imgWidth, imgHeight, gridSize, null);
            drawDots(imgWidth, imgHeight, gridSize, tour, StdDraw.RED, true);
            Collection<SquareCell> result = shortestTour(distances, tour);
            drawDots(imgWidth, imgHeight, gridSize, result, StdDraw.ORANGE, true);
            StdDraw.show();
        }
    }
}