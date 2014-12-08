import java.awt.*;
import javax.swing.*;

class ImagePanel extends JPanel {
	private Image backgroundImage;

	ImagePanel(ImageIcon image) {
		backgroundImage = image.getImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getParent().getSize();
		g.drawImage(backgroundImage, 0, 0, size.width, size.height, null);
	}
}
