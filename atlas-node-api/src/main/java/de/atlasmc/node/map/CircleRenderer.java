package de.atlasmc.node.map;

public final class CircleRenderer implements Renderer {

	private int radius;
	private int count;
	private int[][] points;
	private byte color;
	private boolean shadow;
	
	public CircleRenderer(int radius) {
		this(radius, MapColor.SNOW);
	}
	
	public CircleRenderer(int radius, MapColor color) {
		this(radius, color, true);
	}
	
	public CircleRenderer(int radius, MapColor color, boolean shadow) {
		this.radius = radius;
		this.points = new int[4][radius*2+1];
		this.color = color.getID();
		this.shadow = shadow;
	}
	
	public CircleRenderer() {}

	/**
	 * 
	 * @return the calculated array 
	 * --- upper half left<br>
	 * array[0][-x+r] = y;<br>
	 * array[2][-y+r] = x;<br>
	 * --- upper half right<br>
	 * array[0][x+r] = y;
	 * array[2][y+r] = x;
	 * --- lower half left
	 * array[1][-x+r] = -y;
	 * array[3][-y+r] = -x;
	 * --- lower half right;
	 * array[1][x+r] = -y;
	 * array[3][y+r] = -x;
	 */
	public int[][] calc() {
		final int r = radius;
		int x = r;
		int d = -r;
		count = 0;
		final int[][] pix = new int[r+1-r/4][16];
		for (int y = 0; y < x+1; y++) {
			pix[count][0] = x+r;
			pix[count][1] = y+r;
			
			pix[count][2] = -x+r;
			pix[count][3] = -y+r;
			
			pix[count][4] = x+r;
			pix[count][5] = -y+r;
			
			pix[count][6] = -x+r;
			pix[count][7] = y+r;
			
			pix[count][9] = x+r;
			pix[count][8] = y+r;
			
			pix[count][11] = -x+r;
			pix[count][10] = -y+r;
			
			pix[count][13] = x+r;
			pix[count][12] = -y+r;
			
			pix[count][15] = -x+r;
			pix[count][14] = y+r;
			d = d + 2*y + 1;
			if (d > 0) {
				x--;
				d -= 2*x;
			}
			count++;
		}
		points = pix;
		return pix;
	}
	/**
	 * 
	 * @return the calculated array
	 * @see #calc()
	 */
	public int[][] getPoints() {
		if (points == null) return calc();
		return points;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * the circle's center is default set to X: 0 Y: 0
	 * @param data
	 * @param offsetX
	 * @param offsetY
	 * @return true if changes were made
	 */
	@Override
	public boolean render(MapData data, int offsetX, int offsetY) {
		final int width = data.getWidth();
		final int height = data.getHeight();
		boolean changes = false;
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < 16; j++) {
				int x = points[i][j++]+offsetX;
				int y = points[i][j]+offsetY;
				if (x < 0 || x >= width) 
					continue;
				if (y < 0 || y >= height) 
					continue;
				if (data.setPixel(x, y, color)) 
					changes = true;
			}
		}
		return changes;
	}
	
	
	public MapColor getColor() {
		return MapColor.byID(color);
	}
	
	public boolean hasShadow() {
		return shadow;
	}

	public void setShadow(boolean value) {
		shadow = value;
	}
	
	public void setColor(MapColor color) {
		this.color = color.getID();
	}
	
	
}
