package bayesian;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class BayesianMain {
	
	
	public static int k = 3;
	public static ArrayList<String> spamList = new ArrayList<String>();
	public static ArrayList<String> hamList = new ArrayList<String>();
	
	String [] spams = {"offer","is","secret","click","secret","link","secret","sports","link"};
	String [] hams = {"play","sports","today","went","play","sports","secret","sports","event","sports","is","today","sports","cost","money"};
	
	public BayesianMain(String msg){
		for(int i=0;i<spams.length;i++){
			spamList.add(spams[i]);
		}
		for(int i=0;i<hams.length;i++){
			hamList.add(hams[i]);
		}
		
		
		
		
//		System.out.println((spamList.size()+hamList.size()));
//		System.out.println(probabilitySpamGivenMsg(msg));
		init();
		checkProbability.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(message.getText().equals("")){
					result.setText("Please write a message");
				}else{
					k = Integer.parseInt(laplaceConstant.getText());
					if(probabilitySpamGivenMsg(message.getText())>0.5){
						result.setText("SPAM!  ("+probabilitySpamGivenMsg(message.getText())+")");
						System.out.println(probabilitySpamGivenMsg(message.getText()));
					}else{
						result.setText("HAM!  ("+probabilitySpamGivenMsg(message.getText())+")");
						System.out.println(probabilitySpamGivenMsg(message.getText()));
					}
				}
			}
		});
		
		
		
	}
	JTextField message = new JTextField();
	JLabel result = new JLabel("Result");
	JButton checkProbability = new JButton("Check Probability");
	JTextField laplaceConstant = new JTextField("1");
	JLabel laplaceLabel = new JLabel("Laplace Constant: ");
	
	public void init(){
		JFrame frame = new JFrame();
		frame.setLayout(null);
		frame.setBounds(100,100,500,600);
		
		JPanel listPanel = new JPanel(new GridLayout(0,2));
		JPanel title = new JPanel (new GridLayout(0,2));
		
		title.add(new JLabel("SPAM"));title.add(new JLabel("HAM"));
		title.setBounds(0,0,500,30);
		listPanel.setBounds(0,30,500,300);
		
		JList<String> spamListView = new JList<String>(spams);
		JList<String> hamListView = new JList<String>(hams);
		
		JLabel label = new JLabel("Write Message");
		label.setBounds(0,340,90,30);
		message.setBounds(100,340,200,30);
		frame.add(label);frame.add(message);
		
		checkProbability.setBounds(320,340,150,30);
		result.setBounds(10,380,300,200);
		Font font = new Font("Times New Roman", Font.BOLD, 40);
		result.setFont(font);
		frame.add(result);
		
		frame.add(checkProbability);
		laplaceLabel.setBounds(10,370,120,30);
		laplaceConstant.setBounds(140,370,30,30);
		frame.add(laplaceConstant);frame.add(laplaceLabel);
		
		listPanel.add(spamListView);listPanel.add(hamListView);
		frame.add(listPanel);frame.add(title);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		
		frame.show();
		
		
		
	}
	
	
	public static double probabilitySpamGivenMsg(String msg){
		String[] words = msg.split("\\s+");
		for (int i = 0; i < words.length; i++) {
		    // You may want to check for a non-word character before blindly
		    // performing a replacement
		    // It may also be necessary to adjust the character class
		    words[i] = words[i].replaceAll("[^\\w]", "");
		}
		double probability = 1;
		double probability2 = 1;
		for(int i=0;i<words.length;i++){
			probability *= probabilityMsgGivenSpam(words[i]);
			probability2 *=  probabilityMsgGivenHam(words[i]);
//			System.out.println(words[i]+"  ");
		}
		
		probability *= probabilitySpam();
		probability2 *=probabilityHam();
		return probability/(probability+probability2);
		
		
		
	}
	public static int nWords(){
		int n = 0;
		ArrayList<String> temp = new ArrayList<String>();
		for(int i=0;i<spamList.size();i++){
			boolean exists = false;
			for(int j=0;j<temp.size();j++){
				if(spamList.get(i).equals(temp.get(j))){
					exists = true;
				}
			}
			if(!exists) n++;
		}
		return n;
	}
	public static double probabilityMsgGivenSpam(String msg){
		int count = 0;
		for(int i=0;i<spamList.size();i++){
			if(msg.equals(spamList.get(i))){
				count++;
			}
		}
		
		return (double)(count+k)/(spamList.size()+k*nWords());
	}
	public static double probabilitySpam(){
		
		return (double)(spamList.size()+k)/((spamList.size()+hamList.size())+k*2);
		
	}
	public static double probabilityMsgGivenHam(String msg){
		int count = 0;
		for(int i=0;i<hamList.size();i++){
			if(msg.equals(hamList.get(i))){
				count++;
			}
		}
		return (double)(count+k)/(hamList.size()+k*nWords());
	}
	
	
	public static double probabilityHam(){
		return (double)(hamList.size()+k)/((spamList.size()+hamList.size())+k*2);
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new BayesianMain("secret");
		
		
	}

}
