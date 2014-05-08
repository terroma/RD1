package simulator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

	public static void main(String[] args) {

		String[] sizesList = {"3200" ,"10", "100", "200", "300", "500", "750", "1000" };
		String[] bnList = { "10000", "10000000" };
		String[] dList = { "5000000", "100", "1000", "10000" };

		final JComboBox streamSize = new JComboBox(sizesList);
		final JComboBox binaryRate = new JComboBox(bnList);
		final JComboBox d = new JComboBox(dList);
		JButton btn = new JButton("Start");
  
		
		
		JFrame frame = new JFrame("sup");
		frame.setSize(new Dimension(500, 200));
		frame.setLayout(new GridLayout(0,3, 5,20));

		
		
		frame.add(new JLabel("Data Size"));
		frame.add(new JLabel("Bin. rate"));
		frame.add(new JLabel("Distance"));
		
		frame.add(streamSize);
		frame.add(binaryRate);
		frame.add(d);
		
		
	
		
		frame.add(new JLabel());
		frame.add(btn);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int size=Integer.parseInt((String) streamSize.getSelectedItem());
				double rate= Integer.parseInt((String) binaryRate.getSelectedItem());
				double dist = Integer.parseInt((String) d.getSelectedItem());
				
				TxRxSystem TxRx = new TxRxSystem(size, rate, dist);
				TxRx.init();
			}
		});

		

	}

}
