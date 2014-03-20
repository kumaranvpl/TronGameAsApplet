import java.awt.*;
import java.awt.event.*;

public class calculator extends java.applet.Applet implements ActionListener {
	TextField txtTotal = new TextField("");
    Button button[] = new Button[10];
	Button divide = new Button("/");
	Button mult = new Button("*");
	Button plus = new Button ("+");
	Button minus = new Button("-");
	Button isequalto = new Button("=");
	Button clear = new Button("CA");
	double num ,numtemp ;
	int counter;
	String strnum = "",strnumtemp = "" ;
	String op = "";

	public void operation() {
	 counter ++;
		 if (counter == 1) {
		  numtemp = num;
		  strnum = "";
		  num = 0;
		 }else{
		  if (op == "+") numtemp += num;
		  else if (op == "-") numtemp -= num;
		  else if (op == "*") numtemp = numtemp * num;
		  else if (op == "/") numtemp = numtemp / num;
		  strnumtemp = Double.toString(numtemp);
		  txtTotal.setText(strnumtemp);
		  strnum = "";
		  num = 0;
		 }
	}

	public void init() {
	 setLayout(null);

	 plus.setBackground(Color.blue);
	 plus.setForeground(Color.white);
     minus.setBackground(Color.blue);
	 minus.setForeground(Color.white);
     divide.setBackground(Color.blue);
	 divide.setForeground(Color.white);
	 isequalto.setBackground(Color.blue);
	 isequalto.setForeground(Color.white);
	 mult.setBackground(Color.blue);
	 mult.setForeground(Color.white);
	 clear.setBackground(Color.blue);
	 clear.setForeground(Color.red);

	 for(int i = 0;i <= 9; i ++) {
		 button[i] = new Button(String.valueOf(i));
		 button[i].setBackground(Color.orange);
		 button[i].setForeground(Color.blue);
	 }

	 button[1].setBounds(0,53,67,53);
	 button[2].setBounds(67,53,67,53);
	 button[3].setBounds(134,53,67,53);
	 button[4].setBounds(0,106,67,53);
	 button[5].setBounds(67,106,67,53);
	 button[6].setBounds(134,106,67,53);
	 button[7].setBounds(0,159,67,53);
	 button[8].setBounds(67,159,67,53);
	 button[9].setBounds(134,159,67,53);

	 for (int i = 1;i <= 9; i ++) {
		 add(button[i]);
	 }

	 txtTotal.setBounds(0,0,200,53);
	 add(txtTotal);
	 plus.setBounds(0,212,67,53);
	 add(plus);
	 button[0].setBounds(67,212,67,53);
	 add(button[0]);
	 minus.setBounds(134,212,67,53);
	 add(minus);
	 divide.setBounds(134,264,67,53);
	 add(divide);
	 isequalto.setBounds(67,264,67,53);
	 add(isequalto);
	 mult.setBounds(0,264,67,53);
	 add(mult);
	 add(clear);
	}

	public void start() {
	 for(int i = 0;i <= 9; i ++) {
		 button[i].addActionListener(this);
	 }
	 plus.addActionListener(this);
	 minus.addActionListener(this);
	 divide.addActionListener(this);
	 mult.addActionListener(this);
	 isequalto.addActionListener(this);
	 clear.addActionListener(this);
	}

	public void stop() {
	 for(int i = 0;i <= 9; i ++) {
		 button[i].addActionListener(null);
	 }
	 plus.addActionListener(null);
	 minus.addActionListener(null);
	 divide.addActionListener(null);
	 mult.addActionListener(null);
	 isequalto.addActionListener(null);
	 clear.addActionListener(null);
	}

	public void actionPerformed(ActionEvent e) {
		for(int i = 0;i <= 9; i++) {
			if (e.getSource() == button[i]) {
			   play(getCodeBase(),i + ".au");
			   strnum += Integer.toString(i);
			   txtTotal.setText(strnum);
			   num = Double.valueOf(strnum).doubleValue();
			}
		}

        if (e.getSource() == plus) {
		 operation();
		 op = "+";
		}

		if (e.getSource() == minus) {
		 operation();
		 op = "-";
		}

		if (e.getSource() == divide) {
		 operation();
		 op = "/";
		}

		if (e.getSource() == mult) {
		 operation();
		 op = "*";
		}

		if (e.getSource() == isequalto) {
		 if (op == "+") numtemp += num;
		 else if (op == "-") numtemp -= num;
		 else if (op == "*") numtemp = numtemp * num;
		 else if (op == "/") numtemp = numtemp / num;

		 strnumtemp = Double.toString(numtemp);
		 txtTotal.setText(strnumtemp);
		 strnumtemp = "";
		 numtemp = 0;
		 strnum = "";
		 num = 0;
		 counter = 0;
		}

		if (e.getSource() == clear) {
		 txtTotal.setText("0");
		 strnumtemp = "";
		 numtemp = 0;
		 strnum = "";
		 num = 0;
		 counter = 0;
		}
	}
}