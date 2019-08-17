import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SettingsDialog extends JDialog {
	private MapGraph mg = null;
	private double scaleRatio = 0.75;
	public SettingsDialog(JFrame frame, String title, MapGraph mg, double scaleRatio) {
		super(frame, title);
		this.mg = mg;
		this.scaleRatio = scaleRatio;
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(new EmptyBorder(20,50,50,50));
		BoxLayout settingsLayout = new BoxLayout(settingsPanel,BoxLayout.Y_AXIS);
		settingsPanel.setLayout(settingsLayout);
		
		JLabel importanceSet = new JLabel("안전요소 중요도");
		importanceSet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(importanceSet);
		settingsPanel.add(new JPanel());
		
		JLabel markCCTV = new JLabel("CCTV :");	
		JSlider slideCCTV = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markCCTV);
		settingsPanel.add(slideCCTV);
		
		JLabel markShelter = new JLabel("여성안심지킴이집 :");
		JSlider slideShelter = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markShelter);
		settingsPanel.add(slideShelter);
		
		JLabel markConv = new JLabel("24시 편의점 :");
		JSlider slideConv = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markConv);
		settingsPanel.add(slideConv);
		JLabel markWidth = new JLabel("길의 폭 :");	
		JSlider slideWidth = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markWidth);
		settingsPanel.add(slideWidth);
		JLabel markIllum = new JLabel("길의 밝기 :");
		JSlider slideIllum = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markIllum);
		settingsPanel.add(slideIllum);
		
		settingsPanel.add(new JPanel());
		settingsPanel.add(new JPanel());
		
		JLabel dangerSet = new JLabel("위험요소 중요도");			
		dangerSet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(dangerSet);
		settingsPanel.add(new JPanel());
		
		JLabel markAdult = new JLabel("술집/유흥가 :");
		JSlider slideAdult = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markAdult);
		settingsPanel.add(slideAdult);
		JLabel markConst = new JLabel("공사 장소 :");
		JSlider slideConst = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markConst);
		settingsPanel.add(slideConst);
		
		settingsPanel.add(new JPanel());
		settingsPanel.add(new JPanel());
		
		JLabel displaySet = new JLabel("표시 방식");
		displaySet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(displaySet);
		settingsPanel.add(new JPanel());
		JCheckBox seeVertices = new JCheckBox("노드 보이기", false);
		JCheckBox seeEdges = new JCheckBox("간선 보이기", false);
		JCheckBox seeNames = new JCheckBox("이름 보이기", false);
		seeVertices.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if(e.getStateChange()==ItemEvent.DESELECTED);
            }
         });
		settingsPanel.add(seeVertices);
		settingsPanel.add(seeEdges);
		settingsPanel.add(seeNames);
		
		add(settingsPanel, BorderLayout.CENTER);
		
		JPanel scPanel = new JPanel();
		scPanel.setBackground(Color.LIGHT_GRAY);
		
		GridLayout scLayout = new GridLayout(1,2);
		scLayout.setVgap(5);
		scPanel.setLayout(scLayout);
		
		JButton setBtn = new JButton("설정");
		JButton cancelBtn = new JButton("취소");
		JButton adminBtn = new JButton("관리자 페이지");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		scPanel.add(setBtn);
		scPanel.add(cancelBtn);
		scPanel.add(adminBtn);
		
		add(scPanel, BorderLayout.SOUTH);
		
		setSize(500,600);
		setLocationByPlatform(true);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
	}
}
