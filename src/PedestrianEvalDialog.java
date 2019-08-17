import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PedestrianEvalDialog extends JDialog {
	
	private MapGraph mg = null;
	private double scaleRatio = 0.75;
	private int displayMode = 0;
	private MapGraph.MapEdge selectedEdge = null;
	private List<MapGraph.MapVertex> highlightedVertex = new ArrayList<>();
	private List<MapGraph.MapVertex> selectedVertex = new ArrayList<>();
	
	private JPanel infoPane = null;
	private JLabel wayInfo = null;
	private JTextField evaluateInput = null;
	private JButton evaluateBtn = null;
	
	public PedestrianEvalDialog(JFrame frame, String title, MapGraph mg, double scaleRatio) {
		super(frame, title);
		this.mg = mg;
		this.scaleRatio = scaleRatio;
		MapPanel mp = new MapPanel();
		add(mp,BorderLayout.CENTER);

		infoPane = new JPanel();
		infoPane.setBackground(Color.LIGHT_GRAY);
		
		wayInfo = new JLabel();
		evaluateInput = new JTextField(3);
		evaluateBtn = new JButton("���ϱ�");
		
		evaluateInput.setVisible(false);
		evaluateBtn.setVisible(false);
		
		infoPane.add(wayInfo);
		infoPane.add(evaluateInput);
		infoPane.add(evaluateBtn);
		
		add(infoPane,BorderLayout.SOUTH);
		
		setSize(540,700);
		setLocationByPlatform(true);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
	}
	
	class MapPanel extends JPanel {
		private Image mapImage;
		private int w;
		private int h;
		
		public MapPanel() {
			setImage();
			addMouseListener(new MouseAdapter() {
				private Color background;
				private int x;
				private int y;

                @Override
                public void mousePressed(MouseEvent e) {
                	x = (int) (e.getX()/scaleRatio);
                	y = (int) (e.getY()/scaleRatio);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
					if(selectedVertex.size() > 1) {
						selectedEdge = null;
						updateWayInfo();
						selectedVertex.clear();
						highlightedVertex.clear();
						repaint();
						return;
					}
    				for(MapGraph.MapVertex v : mg.getVertexSet()) {
    					if(Math.abs(v.getX()-x) < 15 && Math.abs(v.getY()-y) < 15) {
    						if(selectedVertex.size()==0) {
    							selectedVertex.add(v);
    							highlightedVertex.addAll(v.getNeighbors());
    						}
    						else if(selectedVertex.size()==1) {
    							if(highlightedVertex.contains(v)) {
    								selectedVertex.add(v);
    								highlightedVertex.clear();
    							}
    							else {
    								selectedEdge = null;
    								updateWayInfo();
    								selectedVertex.clear();
    								highlightedVertex.clear();
    							}
    						}
    						repaint();
    						return;
    					}
    				}
					selectedEdge = null;
					updateWayInfo();
					selectedVertex.clear();
					highlightedVertex.clear();
					repaint();
					return;
                }
			});
		}
		
		public void setImage() {
			File f = new File("images/map_image.jpg");
			try {
				mapImage = ImageIO.read(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(mapImage != null) {
				w = (int) (mapImage.getWidth(null)*scaleRatio);
				h = (int) (mapImage.getHeight(null)*scaleRatio);
				g.drawImage(mapImage,0,0,w,h,null);
				for(MapGraph.MapEdge e : mg.getEdgeSet()) {
					MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
					MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
					if(selectedVertex.contains(a1) && selectedVertex.contains(a2)) {
						selectedEdge = e;
						updateWayInfo();
						g.setColor(new Color(255,0,0,255));
					}
					else if((selectedVertex.contains(a1) && highlightedVertex.contains(a2)) || (highlightedVertex.contains(a1) && selectedVertex.contains(a2))) {
						g.setColor(new Color(0,0,255,255));
					}
					else {
						g.setColor(new Color(0,0,0,255));
					}
					g.drawLine((int)(a1.getX()*scaleRatio), (int)(a1.getY()*scaleRatio), (int)(a2.getX()*scaleRatio), (int)(a2.getY()*scaleRatio));
				}
				for(MapGraph.MapVertex v : mg.getVertexSet()) {
					if(selectedVertex.contains(v)) {
						g.setColor(new Color(255,0,0,255));
					}
					else if(highlightedVertex.contains(v)) {
						g.setColor(new Color(0,0,255,255));
					}
					else {
						g.setColor(new Color(0,0,0,255));
					}
					g.fillOval((int)(v.getX()*scaleRatio)-5, (int)(v.getY()*scaleRatio)-5, 10, 10);
					g.drawString(v.getName(), (int)(v.getX()*scaleRatio), (int)(v.getY()*scaleRatio)+10);
				}
			}
		}
	}
	
	public void updateWayInfo() {
		if(selectedEdge == null) {
			wayInfo.setText("");
			evaluateInput.setVisible(false);
			evaluateBtn.setVisible(false);
		}
		else {
			wayInfo.setText("<html>" + selectedEdge.toString().replace("\n","<br/>") + "</html>");
			evaluateInput.setVisible(true);
			evaluateBtn.setVisible(true);
		}
	}
}