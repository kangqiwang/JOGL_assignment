import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class VCCW03 extends Component implements KeyListener {

	private BufferedImage in, out;
	int width, height;
	File inputFile;
	


	public VCCW03() {
		loadImage();
		addKeyListener(this);

	}

	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paint(Graphics g) {
		g.drawImage(out, 0, 0, null);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Image Processing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		VCCW03 img = new VCCW03();
		frame.add("Center", img);
		frame.pack();
		img.requestFocusInWindow();
		frame.setVisible(true);

	}

	private void processing() {
		
		int wh=width*height;
		int[] barray = new int[wh];
		int[] garray = new int[wh];
		int[] rrray = new int[wh];
		
		
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {	
				Color pixel = new Color(in.getRGB(x, y));	// get the color
				
				
				barray[y*width+x] = pixel.getBlue();    // blue 
				rrray[y*width+x] = pixel.getRed();	    // red component
				garray[y*width+x] = pixel.getGreen();   // green 
				 
			}
//		for (int i=0;i<rrray.length;i++){
//		System.out.println(rrray[i]);}
//		
//		for (int i=0;i<barray.length;i++){
//			System.out.println(barray[i]);}
//		
//		for (int i=0;i<garray.length;i++){
//			System.out.println(garray[i]);}
		
		
//get their neigbor and rank them
		for (int y = 1; y < height-1; y++)
			for (int x = 1; x < width-1; x++) {
		float [] bgroup, rgroup,ggroup ;
		//blue neig
		bgroup = new float[] {
				barray[(y-1)*width+x-1],
				barray[(y-1)*width+x],
				barray[(y-1)*width+x+1],
				barray[y*width+x-1],
				barray[y*width+x],
				barray[y*width+x+1],
				barray[(y+1)*width+x-1],
				barray[(y+1)*width+x],
				barray[(y+1)*width+x+1]	};	
		
		//red neighbor
				rgroup =  new float[] {
						rrray[(y-1)*width+x-1], 
						rrray[(y-1)*width+x],
						rrray[(y-1)*width+x+1],
						rrray[y*width+x-1],
						rrray[y*width+x],
						rrray[y*width+x+1],
						rrray[(y+1)*width+x-1], 
						rrray[(y+1)*width+x], 
						rrray[(y+1)*width+x+1]								
				};
				// green neighbor
				 ggroup = new float[] {
						garray[(y-1)*width+x-1],
						garray[(y-1)*width+x],
						garray[(y-1)*width+x+1],
						garray[y*width+x-1],
						garray[y*width+x],
						garray[y*width+x+1],
						garray[(y+1)*width+x-1],
						garray[(y+1)*width+x],
						garray[(y+1)*width+x+1]								
				};
//			for(int i=0;i<ggroup.length;i++)
//			System.out.println(ggroup[i]);
				
				// rank them and get the median
				 Arrays.sort(bgroup);    

				Arrays.sort(rgroup);     
				Arrays.sort(ggroup);    
				
			
				
			

				out.setRGB(x, y, (new Color( (int)rgroup[rgroup.length/2], (int)ggroup[ggroup.length/2],(int)bgroup[bgroup.length/2] )).getRGB());
				
			}
		
		
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
		
		if (ke.getKeyChar() == 's' || ke.getKeyChar() == 'S') {// Save the processed image
			saveImage();
		} else if (ke.getKeyChar() == 'p' || ke.getKeyChar() == 'P') {// Image Processing
			processing();
		}
	}

	private void loadImage() {
		JFileChooser chooser = new JFileChooser("."); // initial current directory
		chooser.setFileFilter(new ImageFileFilter());
		chooser.setAccessory(new ImagePreview(chooser));
		int rval = chooser.showOpenDialog(null);
		if (rval == JFileChooser.APPROVE_OPTION) {
			inputFile = chooser.getSelectedFile();
			try {
				in = ImageIO.read(inputFile);
				width = in.getWidth();
				height = in.getHeight();
				if (in.getType() != BufferedImage.TYPE_INT_RGB) {
					BufferedImage bi2 = 
							new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					Graphics big = bi2.getGraphics();
					big.drawImage(in, 0, 0, null);
					in = bi2;
				}
				out = in;
			} catch (IOException e) {
				System.out.println("Image could not be read");
				System.exit(1);
			}
		}
		}
	

	private void saveImage() {
		String fileName = inputFile.getName();

		File saveFile = new File("P" + fileName);
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new ImageFileFilter());
		chooser.setAccessory(new ImagePreview(chooser));
		chooser.setSelectedFile(saveFile);
		chooser.setCurrentDirectory(inputFile);
		int rval = chooser.showSaveDialog(null);
		if (rval == JFileChooser.APPROVE_OPTION) {
			saveFile = chooser.getSelectedFile();
			fileName = saveFile.getName();
			int pos = fileName.lastIndexOf('.');
			String ext = fileName.substring(pos + 1);

			/*
			 * Write the processed image in the selected format, to the file
			 * chosen by the user.
			 */
			try {
				ImageIO.write(out, ext, saveFile);
			} catch (IOException ex) {
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

	@Override
	public void keyTyped(KeyEvent e) {


	}

	class ImageFileFilter extends FileFilter {

		private String description;
		private String[] extensions = getFormats(false);
		int count = extensions.length;


		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			String path = file.getAbsolutePath();
			for (int i = 0; i < count; i++) {
				String ext = extensions[i];
				int pointPos = path.length() - ext.length() - 1;
				if (pointPos >= 0 && path.endsWith(ext)
						&& (path.charAt(pointPos) == '.')) {
					return true;
				}
			}
			return false;
		}


		public String getDescription() {

			description = "Image Files (";
			for (int i = 0; i < count - 1; i++) {
				description += "." + extensions[i] + ", ";
			}
			description += "." + extensions[count - 1] + ")";

			return (description);
		}


		public String[] getFormats(boolean io) {
			String[] formats;
			formats = ImageIO.getWriterFormatNames();

			TreeSet<String> formatSet = new TreeSet<String>();
			for (String s : formats) {
				formatSet.add(s.toLowerCase());
			}
			return formatSet.toArray(new String[0]);
		}
	}


	class ImagePreview extends JComponent implements PropertyChangeListener {
		ImageIcon thumbnail = null;
		File file = null;

		public ImagePreview(JFileChooser fc) {
			setPreferredSize(new Dimension(100, 50));
			fc.addPropertyChangeListener(this);
		}

		public void loadImage() {
			if (file == null) {
				thumbnail = null;
				return;
			}

			ImageIcon tmpIcon = new ImageIcon(file.getPath());
			if (tmpIcon != null) {
				if (tmpIcon.getIconWidth() > 90) {
					thumbnail = new ImageIcon(tmpIcon.getImage()
							.getScaledInstance(90, -1, Image.SCALE_DEFAULT));
				} else { // no need to miniaturize
					thumbnail = tmpIcon;
				}
			}
		}

		public void propertyChange(PropertyChangeEvent e) {
			boolean update = false;
			String prop = e.getPropertyName();


			if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
				file = null;
				update = true;


			} else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
				file = (File) e.getNewValue();
				update = true;
			}


			if (update) {
				thumbnail = null;
				if (isShowing()) {
					loadImage();
					repaint();
				}
			}
		}

		protected void paintComponent(Graphics g) {
			if (thumbnail == null) {
				loadImage();
			}
			if (thumbnail != null) {
				int x = getWidth() / 2 - thumbnail.getIconWidth() / 2;
				int y = getHeight() / 2 - thumbnail.getIconHeight() / 2;

				if (y < 0) {
					y = 0;
				}

				if (x < 5) {
					x = 5;
				}
				thumbnail.paintIcon(this, g, x, y);
			}
		}
	}

}
