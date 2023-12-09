package wordle.bot.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import wordle.bot.IWordsController;
import wordle.bot.logic.wordreader.SelectedLanguage;
import wordle.bot.Factory;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JButton;

public class Selector extends JFrame {

	private JPanel contentPane;

	public Selector() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		String[] languages = {"Spanish" , "English" , "Filtered English"};
		JComboBox<String> comboBox = new JComboBox<String>(languages);
		panel.add(comboBox);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener((e) -> {
			int selected = comboBox.getSelectedIndex();
			IWordsController wordsC = Factory.getWordsController();
			
			switch (selected) {
			case 0:
				wordsC.loadLanguage(SelectedLanguage.SPANISH);
				break;
			case 1:
				wordsC.loadLanguage(SelectedLanguage.ENGLISH);
				break;
			case 2:
				wordsC.loadLanguage(SelectedLanguage.ENGLISH_FILTERED);
				break;
			}
			 
		});
		panel.add(btnSelect);
	}

}
