package simulator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		String[] sizesList = { "10", "100", "200", "300", "500", "750", "1000" };
		String[] bnList = { "10000000" };
		String[] dList = { "100", "1000", "10000" };

		final JComboBox streamSize = new JComboBox(sizesList);
		final JComboBox binaryRate = new JComboBox(bnList);
		final JComboBox d = new JComboBox(dList);
		JButton btn = new JButton("Start");

		JFrame frame = new JFrame("sup");
		frame.setSize(new Dimension(300, 100));
		frame.setLayout(new FlowLayout());

		frame.add(streamSize, FlowLayout.LEFT);
		frame.add(binaryRate, FlowLayout.CENTER);
		frame.add(d, FlowLayout.RIGHT);

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
