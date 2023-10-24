import java.awt.*;
import java.awt.Color;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class MyFrame extends JFrame{
	
	JLabel schedulingMethodLabel = new JLabel("Algorithm List");
	JLabel processNameLabel = new JLabel("Process Name");
	JLabel ATLabel = new JLabel("Arrival time");
	JLabel BTLabel = new JLabel("Burst time");
	JLabel timeQuantumLabel = new JLabel("RR Time quantum");
	JLabel timeUnitLabel = new JLabel("second(s)");
	int timeQuantum;
	ImageIcon img = new ImageIcon("koreatechImage.png"); 
	JLabel imageLabel = new JLabel(img);
	
	String [] schedulingMethod = {"FCFS", "RR", "SPN", "SRTN", "HRRN", "TEAM"}; 
	JComboBox schedulingMethodComboBox = new JComboBox(schedulingMethod);
	
	JTextField processNameTextField = new JTextField();
	JTextField ATTextField = new JTextField();
	JTextField BTTextField = new JTextField();
	JTextField timeQuantumTextField = new JTextField();
	
	JButton processAddBtn = new JButton("Add");
	JButton processResetBtn = new JButton("Reset");
	JLabel processRestLabel = new JLabel("Reset");
	JButton schedulingStartBtn = new JButton("Start");
	
	JLabel processorNumberLabel = new JLabel("Processor Number");
	String [] processorNumber = {"1", "2", "3", "4"}; 
	JComboBox processorNumberComboBox = new JComboBox(processorNumber);
	
	JLabel excutionSpeedLabel = new JLabel("Execution Speed");
	JTextField excutionSpeedTextField = new JTextField();
	JLabel excutionUnit = new JLabel("ms");
	int excutionSpeed;
	
	JLabel schedulingPolicyLabel = new JLabel("Scheduling Policy");
	String [] schedulingPolicy = {"Preemptive", "Non-Preemptive"}; 
	JComboBox schedulingPolicyComboBox = new JComboBox(schedulingPolicy);
	int selectedSchedulingPolicy=0;//0�̸� ����, 1�̸� ����
	
	/////////////////////////////////////////////////////////////////
	JLabel timeTableLabel = new JLabel("<Time table>");
	ColorTable timeTable;
	DefaultTableModel timeModel;
	
	
	////////////////////////////////////////////////////////////////
	Container queueContainer = new Container();
	JLabel queueLabel = new JLabel("<Ready queue>");
	JLabel queueProcessLabel[] = new JLabel[15];
	JLabel process1 = new JLabel("");
	JLabel process2 = new JLabel("");
	JLabel process3 = new JLabel("");
	JLabel process4 = new JLabel("");
	JLabel process5 = new JLabel("");
	JLabel process6 = new JLabel("");
	JLabel process7 = new JLabel("");
	JLabel process8 = new JLabel("");
	JLabel process9 = new JLabel("");
	JLabel process10 = new JLabel("");
	JLabel process11 = new JLabel("");
	JLabel process12 = new JLabel("");
	JLabel process13 = new JLabel("");
	JLabel process14 = new JLabel("");
	JLabel process15 = new JLabel("");
	
	JLabel temp12 = new JLabel("");
	
	
	/////////////////////////////////////////////////////////////////
	JLabel ganttChartLabel = new JLabel("<Gantt Chart>");
	JLabel ganttLabel[];
	JLabel multiGanttLabel[][];
	int ganttLabelCount=0;
	int processorNum;
	//Gantt Chart �����̳�
	JLabel ganttRealTimeLabel=new JLabel("Real time=");
	JLabel ganttRealTimeLabel2=new JLabel();
	JPanel ganttContainer= new JPanel();
	
	JScrollPane ganttScroll = new JScrollPane(ganttContainer);
	
	/////////////////////////////////////////////////////////////////
	JLabel resultTableLabel = new JLabel("<Result table>");
	ColorTable resultTable;
	DefaultTableModel resultModel;
		
	/////////////////////////////////////////////////////////////////
	JLabel teamNameLabel = new JLabel("8th team Core_Ver1.0");
	
	Thread thread=null;
	int threadState=0;// 0�̸� ���� ���� �����尡 ���� ����, 1�̸� �ִ� ���¸� �ǹ��Ѵ�
	
	MyFrame(){
		setTitle("Process Scheduling Simulator"); // ������ ���� �̸� �޼ҵ�
		setContentPane(new MyPanel());
		Container cp = getContentPane();  // ������ �ڵ鸵 �� �� �ֵ��� ��ü ����
		cp.setLayout(null);  // �����̳� ��ġ�� �������̾ƿ� ���·� �ϰ� �ϴ� �޼ҵ�
		
		//Ready Queue �����̳�
		//Container queueContainer = new Container();
		queueContainer.setLayout(new GridLayout(1,15));
		
		//Time tableǥ ����
		String timeTableColumn[] = {"Process Name","Arrival Time(AT)","Burst Time(BT)"};
		String temp1[][] = null;
		timeModel=new DefaultTableModel(temp1,timeTableColumn);
		timeTable= new ColorTable(timeModel);
		JScrollPane timeTableScroll = new JScrollPane(timeTable);
		
		/*timeTable.getColumn("Delete Button").setCellRenderer(dcr);
		JButton deleteBtn = new JButton();
		deleteBtn.setHorizontalAlignment(JLabel.CENTER)*/
		//timeTable.getColumn("Delete Butoon").setCellEditor(new DefaultCellEditor(deleteBtn));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ���̺� ������ ��ü�� ����.
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);              // ������ �������� ���������� CENTER��
		for(int i=0;i<timeTable.getColumnCount();i++)
		{
			timeTable.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
		timeTable.getParent().setBackground(Color.white);

		
		//Ready Queue �ڷ� ���¿� ���� ǥ��
		for(int i=0;i<15;i++)
		{
			queueProcessLabel[i]=new JLabel();
			queueProcessLabel[i].setOpaque(true);
			queueProcessLabel[i].setBackground(Color.WHITE);
			queueProcessLabel[i].setHorizontalAlignment(JLabel.CENTER);
		}
		
		//���ǥ ����
		String resultTableColumn[] = {"Process Name","Arrival Time(AT)","Burst Time(BT)","Waiting Time(WT)",
				"Turnaround Time(TT)","Normalized TT(NTT)"};
		String temp[][] = null;
		resultModel=new DefaultTableModel(temp,resultTableColumn);
		resultTable= new ColorTable(resultModel);
		JScrollPane resultTableScroll = new JScrollPane(resultTable);
		DefaultTableCellRenderer dtcr2 = new DefaultTableCellRenderer(); // ���̺� ������ ��ü�� ����.
		dtcr2.setHorizontalAlignment(SwingConstants.CENTER);              // ������ �������� ���������� CENTER��
		for(int i=0;i<resultTable.getColumnCount();i++)
		{
			resultTable.getColumnModel().getColumn(i).setCellRenderer(dtcr2);
		}
		resultTable.getParent().setBackground(Color.white);

		///���� ������Ʈ���� ������ ��ġ ����///
		
		//�ֻ�� �󺧵�
		schedulingMethodLabel.setBounds(10,10,100,20);
		processNameLabel.setBounds(120,10,100,20);
		ATLabel.setBounds(230,10,100,20);
		BTLabel.setBounds(340,10,100,20);
		
		//��ܿ� ��ġ�� �����ٸ� �˰��� �޺��ڽ� �� �Է°� �ؽ�Ʈ�ʵ�, ��ư
		schedulingMethodComboBox.setBounds(10,31,100,20);
		processNameTextField.setBounds(120,31,100,20);
		ATTextField.setBounds(230,31,100,20);
		BTTextField.setBounds(340,31,100,20);
		processAddBtn.setBounds(450,31,120,20);
		processResetBtn.setBounds(580,31,120,20);
		processRestLabel.setBounds(580,31,120,20);
		schedulingStartBtn.setBounds(450,230,250,44);
		
		//Time tableǥ
		timeTableLabel.setBounds(10,53,100,20);
		timeTableScroll.setBounds(10,74,430,200);
		
		//Time quantum
		timeQuantumLabel.setBounds(455,127,120,20);
		timeQuantumTextField.setBounds(575,127,65,20);
		timeUnitLabel.setBounds(640,127,70,20);
		
		//Ready Queue �����̳�
		queueLabel.setBounds(10,280,100,20);
		queueContainer.setBounds(10,301,700,50);
		
		//Gantt Chart ���״Ͼ�
		ganttChartLabel.setBounds(10,360,100,20);
		ganttRealTimeLabel.setBounds(120,360,80,20);
		ganttRealTimeLabel2.setBounds(190,360,50,20);
		ganttScroll.setBounds(15,383,690,97);
		
		//���ǥ
		resultTableLabel.setBounds(10,492,100,20);
		resultTableScroll.setBounds(10,513,700,200);
		
		//����&����
		teamNameLabel.setBounds(585,715,200,20);
		
		//���μ��� ����
		processorNumberLabel.setBounds(455,74,120,20);
		processorNumberComboBox.setBounds(575,74,120,20);
		
		//����ð�
		excutionSpeedLabel.setBounds(455,96,120,20);
		excutionSpeedTextField.setBounds(575,96,100,20);
		excutionUnit.setBounds(675,96,20,20);
		
		//�����ٸ� ��å
		schedulingPolicyLabel.setBounds(455,127,120,20);
		schedulingPolicyComboBox.setBounds(575,127,120,20);
		
		//���� �̹���
		imageLabel.setBounds(450,160,250,65);
		
		//������Ʈ ���� ����
		processAddBtn.setForeground(Color.ORANGE);
		processAddBtn.setBackground(new Color(51,51,153));
		processResetBtn.setForeground(Color.ORANGE);
		processResetBtn.setBackground(new Color(51,51,153));
		schedulingStartBtn.setForeground(Color.ORANGE);
		schedulingStartBtn.setBackground(new Color(51,51,153));
		schedulingMethodComboBox.setBackground(new Color(51,51,153));
		schedulingMethodComboBox.setForeground(Color.ORANGE);
		processorNumberComboBox.setBackground(new Color(51,51,153));
		processorNumberComboBox.setForeground(Color.ORANGE);
		schedulingPolicyComboBox.setBackground(new Color(51,51,153));
		schedulingPolicyComboBox.setForeground(Color.ORANGE);
		cp.setBackground(Color.white);
		
		///���� ������Ʈ����  �����̳ʿ� �ø�///
		cp.add(schedulingMethodLabel);
		cp.add(processNameLabel);
		cp.add(ATLabel);
		cp.add(BTLabel);
		
		cp.add(schedulingMethodComboBox);
		cp.add(processNameTextField);
		cp.add(ATTextField);
		cp.add(BTTextField);
		cp.add(processAddBtn);
		cp.add(processResetBtn);
		cp.add(processRestLabel);
		Border border2 = BorderFactory.createLineBorder(Color.BLACK);
		processRestLabel.setHorizontalAlignment(JLabel.CENTER);
		processRestLabel.setBorder(border2);
		cp.add(schedulingStartBtn);
		
		//Time table
		cp.add(timeTableLabel);
		cp.add(timeTableScroll);
		cp.add(timeUnitLabel);
		
		//Time quantum
		cp.add(timeQuantumLabel);
		cp.add(timeQuantumTextField);
		timeQuantumLabel.setVisible(false);   // RR ��� ���� �ÿ��� �Է°��� ���� �� �ֵ��� ���α׷��� ������ ���� �Է�â�� �����ش�
		timeQuantumTextField.setVisible(false); 
		timeUnitLabel.setVisible(false);
		
		//Ready Queue�����̳�
		cp.add(queueLabel);
		cp.add(queueContainer);
		for(int i=0;i<15;i++)
		{
			queueContainer.add(queueProcessLabel[i]);
		}
		
		//Gantt Chart �����̳�
		cp.add(ganttChartLabel);
		cp.add(ganttRealTimeLabel);
		cp.add(ganttRealTimeLabel2);
		cp.add(ganttScroll);
		ganttRealTimeLabel.setVisible(false);
		ganttRealTimeLabel2.setVisible(false);
		
		//Time tableǥ
		cp.add(resultTableLabel);
		cp.add(resultTableScroll);
		
		//����&����
		cp.add(teamNameLabel);
		
		//���μ��� ����
		cp.add(processorNumberLabel);
		cp.add(processorNumberComboBox);
		
		//����ð�
		cp.add(excutionSpeedLabel);
		cp.add(excutionSpeedTextField);
		cp.add(excutionUnit);
		
		//�����ٸ� ��å
		cp.add(schedulingPolicyLabel);
		cp.add(schedulingPolicyComboBox);
		schedulingPolicyLabel.setVisible(false);
		schedulingPolicyComboBox.setVisible(false);
		
		//�̹���
		cp.add(imageLabel);
		
		processAddBtn.addActionListener(new MyButtonActionListener());
		processResetBtn.addActionListener(new MyButtonActionListener());
		schedulingStartBtn.addActionListener(new MyButtonActionListener());
		schedulingMethodComboBox.addActionListener(new MyComboBoxActionListener());
		processorNumberComboBox.addActionListener(new MyComboBoxActionListener());
		schedulingPolicyComboBox.addActionListener(new MyComboBoxActionListener());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �������� X Ŭ������ â�� �ݾ��� �� ���α׷��� ���� ��Ű�� �޼ҵ� -> ���ϸ� �ݾƵ� ���α׷��� �� ���� �����̴�
		setSize(730,780); // ������ ũ�� ���� �޼ҵ�
		setVisible(true); // ������ ��üȭ �޼ҵ�
		//cp.setBackground(Color.WHITE);      // ������ ���� ����
		
		
		//�ӽ� �ڵ� Ȯ�ο�
		/*String in1[]=new String[3];
		in1[0]="P1";
		in1[1]="0";
		in1[2]="10";
		timeModel.addRow(in1);
		String in2[]=new String[3];
		in2[0]="P2";
		in2[1]="1";
		in2[2]="28";
		timeModel.addRow(in2);
		String in3[]=new String[3];
		in3[0]="P3";
		in3[1]="2";
		in3[2]="6";
		timeModel.addRow(in3);
		String in4[]=new String[3];
		in4[0]="P4";
		in4[1]="3";
		in4[2]="4";
		timeModel.addRow(in4);
		String in5[]=new String[3];
		in5[0]="P5";
		in5[1]="4";
		in5[2]="14";
		timeModel.addRow(in5);*/
	}
	
	
	public static void main(String[] args) {
		MyFrame mf = new MyFrame();
	}
	
	//���̺� Ư������ ���� �ֱ����� �ڽ� Ŭ���� ����
	class ColorTable extends JTable 
	{
		int row;
		int column;
		
		public ColorTable(DefaultTableModel dtm) 
		{// TODO Auto-generated constructor stub
			super(dtm);
		}
		@Override
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column) 
		{// TODO Auto-generated method stub
			JComponent component = (JComponent) super.prepareRenderer(renderer, row, column);
			component.setBackground((column==0)? colorSelection(row):Color.WHITE); 
			component.setForeground((column==0)? textColorSelection(row):Color.BLACK); 
			
			//component.setBackground(row % 2 == 0 ? Color.CYAN : Color.WHITE);
			return component;
		}
	}
	
	//���̺� �ึ�� ��ư�� �ֱ� ���� Ŭ���� ����
	/*DefaultTableCellRenderer dcr = new DefaultTableCellRenderer()
	{
		public Component getTableCellRendererComponent  // ��������
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			JButton btn= new JButton();
			btn.setSelected(((Boolean)value).booleanValue());  
			btn.setHorizontalAlignment(JLabel.CENTER);
			return btn;
		}
	};
	
	public class MyDefaultTableCellRenderer // JTaable����  Ư�� Ʃ���� �����ϱ� ���� �޼ҵ� ������
	extends DefaultTableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
	  Component comp = null;
	  if(column==3){
	  comp = new JButton();

	  }
	  return comp;
	  }
	}*/

	class MyButtonActionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();  // e�� ���� ��ư�� ��ü�� �޾��ְ� �������� b�� �����Ѵ�.
			//System.out.print(b.getText());
			
			if(b==processAddBtn) // Time table�� Ʃ�� �߰�
			{
				if(timeModel.getRowCount()==15) // �ִ�� ������ �� �ִ� ���μ��� 15���� ����
				{
					JOptionPane.showMessageDialog(null, "�ִ� 15���� ���μ����� ������ �� �ֽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
					//���μ��� �Է� �� �ؽ����ʵ� ����
					processNameTextField.setText("");
					ATTextField.setText("");
					BTTextField.setText("");
				}
				else if(processNameTextField.getText().equals("")) // ���μ��� �̸� ���Է� ��
				{
					JOptionPane.showMessageDialog(null, "���μ��� �̸��� �Է����ּ���.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(ATTextField.getText().equals("")) // AT�� ���Է� ��
				{
					JOptionPane.showMessageDialog(null, "Arrival time�� �Է����ּ���.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(BTTextField.getText().equals("")) // BT�� ���Է� ��
				{
					JOptionPane.showMessageDialog(null, "Burst time�� �Է����ּ���.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else // �������� �Է��� ���μ��� �� ���� ��
				{
					
					String inputStr[]=new String[3];
					inputStr[0]=processNameTextField.getText();
					inputStr[1]=ATTextField.getText();
					inputStr[2]=BTTextField.getText();
					
					timeModel.addRow(inputStr);
					
					
					//���μ��� �Է� �� �ؽ����ʵ� ����
					processNameTextField.setText("");
					ATTextField.setText("");
					BTTextField.setText("");
					
				}
			}
			else if(b==processResetBtn) // Time tableǥ, Result table �ʱ�ȭ
			{
				int tupleIndex=timeModel.getRowCount();   // ������ ���� �����Ͽ� for���� �־��ִ� ������ model���� �ε��� ���� �����Ǹ鼭 ���÷� ���ϱ� �����̴�.
				int i=0;
				for(;i<tupleIndex;i++)
				{
					timeModel.removeRow(0);  // �ε����� i���� 0���� �ؾ� �������鼭 �����ĵ� Ʃ�ÿ��� �ٽ� ���� ��� Ʃ���� ���� �� �ִ�.
				}
				
				//Result Table�� ���� ���� �־������ �� ����� ���ο� Result���̺� ����
				int tupleIndex2=resultModel.getRowCount();   // ������ ���� �����Ͽ� for���� �־��ִ� ������ model���� �ε��� ���� �����Ǹ鼭 ���÷� ���ϱ� �����̴�.
				int t=0;
				for(;t<tupleIndex2;t++)
				{
					resultModel.removeRow(0);  // �ε����� i���� 0���� �ؾ� �������鼭 �����ĵ� Ʃ�ÿ��� �ٽ� ���� ��� Ʃ���� ���� �� �ִ�.
				}
				
				//Ready Queue ����
				for(int k=0;k<15;k++)
				{
					queueProcessLabel[k].setText("");
					queueProcessLabel[k].setOpaque(true);
					queueProcessLabel[k].setBackground(Color.WHITE);
					queueProcessLabel[k].setHorizontalAlignment(JLabel.CENTER);
				}
				
				//Gantt Chart ����
				for(int k=0;k<processorNum;k++)
				{
					for(int j=0;j<ganttLabelCount;j++)
					{
						ganttContainer.remove(multiGanttLabel[k][j]);
					}
				}
				
				/*for(int j=0;j<ganttLabelCount;j++)
				{
					ganttContainer.remove(ganttLabel[j]);
				}*/
				repaint();
				revalidate();
				//ganttLabel=null;
				multiGanttLabel=null;
				ganttLabelCount=0;
				ganttRealTimeLabel.setVisible(false);
				ganttRealTimeLabel2.setVisible(false);
				
				// �ؽ����ʵ� ����
				processNameTextField.setText("");
				ATTextField.setText("");
				BTTextField.setText("");
				schedulingStartBtn.setText("Start");
				
				threadState=0;
				if(thread!=null)
				{
					thread.stop();
					thread=null;
				}
				
			}
			else if(b==schedulingStartBtn) // �����ٸ� ����
			{
				if(timeModel.getRowCount()==0) 
				{JOptionPane.showMessageDialog(null, "�Էµ� ���μ����� �����ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);}
				else if(excutionSpeedTextField.getText().equals("")) 
				{JOptionPane.showMessageDialog(null, "����ð��� �Է����ּ���.", "Message", JOptionPane.ERROR_MESSAGE);}
				else
				{
					if(schedulingStartBtn.getText().equals("Start"))
					{
						processResetBtn.setVisible(false);
						//Result Table�� ���� ���� �־������ �� ����� ���ο� Result���̺� ����
						int tupleIndex=resultModel.getRowCount();   // ������ ���� �����Ͽ� for���� �־��ִ� ������ model���� �ε��� ���� �����Ǹ鼭 ���÷� ���ϱ� �����̴�.
						int t=0;
						for(;t<tupleIndex;t++)
						{
							resultModel.removeRow(0);  // �ε����� i���� 0���� �ؾ� �������鼭 �����ĵ� Ʃ�ÿ��� �ٽ� ���� ��� Ʃ���� ���� �� �ִ�.
						}
						ganttRealTimeLabel.setVisible(true);
						ganttRealTimeLabel2.setVisible(true);
						
						//���� Gantt Chart �־��ٸ� ����
						for(int k=0;k<processorNum;k++)
						{
							for(int j=0;j<ganttLabelCount;j++)
							{
								ganttContainer.remove(multiGanttLabel[k][j]);
							}
						}
						repaint();
						revalidate();
						//ganttLabel=null;
						multiGanttLabel=null;
						ganttLabelCount=0;
						
						excutionSpeed=Integer.parseInt(excutionSpeedTextField.getText());
						
						Process[] inputProcess = new Process[timeModel.getRowCount()];
						for(int i=0;i<timeModel.getRowCount();i++)
						{						
							String name=timeTable.getValueAt(i, 0).toString();
							double at=Double.parseDouble(timeTable.getValueAt(i, 1).toString());
							double bt=Double.parseDouble(timeTable.getValueAt(i, 2).toString());
							int order=i;
							
							inputProcess[i]=new Process(name,at,bt,order);
						}
						
						for(int i=0;i<timeModel.getRowCount();i++)
						{
							System.out.println(inputProcess[i].processName);
							System.out.println(inputProcess[i].arrivalTime);
							System.out.println(inputProcess[i].burstTime);
						}
						
						//FCFS2 �����ٸ� Start ��
						if(schedulingMethodComboBox.getSelectedItem().equals("FCFS"))
						{
							System.out.println("FCFS2�۵�");
							FCFS2 fcfs=new FCFS2(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()));
							fcfs.scheduling();
							
							//Result Table ǥ��
							for(int i=0;i<timeModel.getRowCount();i++)
							{
								String inputStr[]=new String[6];
								inputStr[0]= fcfs.process[i].processName;
								inputStr[1]= Double.toString(fcfs.process[i].arrivalTime);
								inputStr[2]= Double.toString(fcfs.process[i].burstTime);
								inputStr[3]=Double.toString(fcfs.process[i].waitingTime);
								inputStr[4]=Double.toString(fcfs.process[i].turnaroundTime);
								inputStr[5]=Double.toString(fcfs.process[i].normalizedTT);
								resultModel.addRow(inputStr);
							}
							
							
							//��Ʈ��Ʈ
							ganttContainer.setLayout(new GridLayout(fcfs.processorNum,fcfs.terminateTime));
							multiGanttLabel = new JLabel[fcfs.processorNum][fcfs.terminateTime];
							//ganttLabel = new JLabel[hrrn.terminateTime];
							ganttLabelCount=fcfs.terminateTime;
							processorNum=fcfs.processorNum;
							for(int j=0;j<fcfs.processorNum;j++)
							{
								for(int i=0;i<fcfs.terminateTime;i++)
								{
									multiGanttLabel[j][i]=new JLabel(" ");
									multiGanttLabel[j][i].setOpaque(true);
									multiGanttLabel[j][i].setBackground(Color.WHITE);
									ganttContainer.add(multiGanttLabel[j][i]);
								}
							}
							repaint();
							revalidate(); // �̰� ����� �����ڿ��� ������Ʈ �Ⱥ��̰� �ٸ� ������ ������Ʈ�� ���� �� �ִ�.
							
							
							thread = new FCFS2Thread(fcfs);
							thread.start();
						}
						
						//RR �����ٸ� Start ��
						if(schedulingMethodComboBox.getSelectedItem().equals("RR"))
						{
							if(timeQuantumTextField.getText().equals(""))
							{
								JOptionPane.showMessageDialog(null, "Quantum time�� �Է����ּ���.", "Message", JOptionPane.ERROR_MESSAGE);return;
							}
							else if(Integer.parseInt(excutionSpeedTextField.getText())<1)
							{
								JOptionPane.showMessageDialog(null, "1�� ���� ���� �ð��� Quantum time���� �Է��� �� �����ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);return;
							}
							timeQuantum=Integer.parseInt(timeQuantumTextField.getText());
							
							System.out.println("RR�۵�");
							RR rr=new RR(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()),timeQuantum);
							rr.scheduling();
							
							//Result Table ǥ��
							for(int i=0;i<timeModel.getRowCount();i++)
							{
								String inputStr[]=new String[6];
								inputStr[0]= rr.process[i].processName;
								inputStr[1]= Double.toString(rr.process[i].arrivalTime);
								inputStr[2]= Double.toString(rr.process[i].burstTime);
								inputStr[3]=Double.toString(rr.process[i].waitingTime);
								inputStr[4]=Double.toString(rr.process[i].turnaroundTime);
								inputStr[5]=Double.toString(rr.process[i].normalizedTT);
								resultModel.addRow(inputStr);
							}
							
							
							//��Ʈ��Ʈ
							ganttContainer.setLayout(new GridLayout(rr.processorNum,rr.terminateTime));
							multiGanttLabel = new JLabel[rr.processorNum][rr.terminateTime];
							//ganttLabel = new JLabel[hrrn.terminateTime];
							ganttLabelCount=rr.terminateTime;
							processorNum=rr.processorNum;
							for(int j=0;j<rr.processorNum;j++)
							{
								for(int i=0;i<rr.terminateTime;i++)
								{
									multiGanttLabel[j][i]=new JLabel(" ");
									multiGanttLabel[j][i].setOpaque(true);
									multiGanttLabel[j][i].setBackground(Color.WHITE);
									ganttContainer.add(multiGanttLabel[j][i]);
								}
							}
							repaint();
							revalidate(); // �̰� ����� �����ڿ��� ������Ʈ �Ⱥ��̰� �ٸ� ������ ������Ʈ�� ���� �� �ִ�.
							
							
							thread = new RRThread(rr);
							thread.start();
						}
						
						//SPN2
						if(schedulingMethodComboBox.getSelectedItem().equals("SPN"))
						{
							System.out.println("SPN2�۵�");
							SPN2 spn2=new SPN2(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()),selectedSchedulingPolicy);
							spn2.scheduling();
							
							//Result Table ǥ��
							for(int i=0;i<timeModel.getRowCount();i++)
							{
								String inputStr[]=new String[6];
								inputStr[0]= spn2.process[i].processName;
								inputStr[1]= Double.toString(spn2.process[i].arrivalTime);
								inputStr[2]= Double.toString(spn2.process[i].burstTime);
								inputStr[3]=Double.toString(spn2.process[i].waitingTime);
								inputStr[4]=Double.toString(spn2.process[i].turnaroundTime);
								inputStr[5]=Double.toString(spn2.process[i].normalizedTT);
								resultModel.addRow(inputStr);
							}
							
							
							//��Ʈ��Ʈ
							ganttContainer.setLayout(new GridLayout(spn2.processorNum,spn2.terminateTime));
							multiGanttLabel = new JLabel[spn2.processorNum][spn2.terminateTime];
							//ganttLabel = new JLabel[hrrn.terminateTime];
							ganttLabelCount=spn2.terminateTime;
							processorNum=spn2.processorNum;
							for(int j=0;j<spn2.processorNum;j++)
							{
								for(int i=0;i<spn2.terminateTime;i++)
								{
									multiGanttLabel[j][i]=new JLabel(" ");
									multiGanttLabel[j][i].setOpaque(true);
									multiGanttLabel[j][i].setBackground(Color.WHITE);
									ganttContainer.add(multiGanttLabel[j][i]);
								}
							}
							repaint();
							revalidate(); // �̰� ����� �����ڿ��� ������Ʈ �Ⱥ��̰� �ٸ� ������ ������Ʈ�� ���� �� �ִ�.
							
							
							thread = new SPN2Thread(spn2);
							thread.start();
						}
						
						//SRTN2
						//SRTN �����ٸ� Start ��
						if(schedulingMethodComboBox.getSelectedItem().equals("SRTN"))
						{
							System.out.println("SRTN2�۵�");
							SRTN2 srtn=new SRTN2(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()),selectedSchedulingPolicy); //�����ٸ���å
							srtn.scheduling();
							
							//Result Table ǥ��
							for(int i=0;i<timeModel.getRowCount();i++)
							{
								String inputStr[]=new String[6];
								inputStr[0]= srtn.process[i].processName;
								inputStr[1]= Double.toString(srtn.process[i].arrivalTime);
								inputStr[2]= Double.toString(srtn.process[i].burstTime);
								inputStr[3]=Double.toString(srtn.process[i].waitingTime);
								inputStr[4]=Double.toString(srtn.process[i].turnaroundTime);
								inputStr[5]=Double.toString(srtn.process[i].normalizedTT);
								resultModel.addRow(inputStr);
							}
							
							
							//��Ʈ��Ʈ
							ganttContainer.setLayout(new GridLayout(srtn.processorNum,srtn.terminateTime));
							multiGanttLabel = new JLabel[srtn.processorNum][srtn.terminateTime];
							//ganttLabel = new JLabel[hrrn.terminateTime];
							ganttLabelCount=srtn.terminateTime;
							processorNum=srtn.processorNum;
							for(int j=0;j<srtn.processorNum;j++)
							{
								for(int i=0;i<srtn.terminateTime;i++)
								{
									multiGanttLabel[j][i]=new JLabel(" ");
									multiGanttLabel[j][i].setOpaque(true);
									multiGanttLabel[j][i].setBackground(Color.WHITE);
									ganttContainer.add(multiGanttLabel[j][i]);
								}
							}
							repaint();
							revalidate(); // �̰� ����� �����ڿ��� ������Ʈ �Ⱥ��̰� �ٸ� ������ ������Ʈ�� ���� �� �ִ�.
							
							
							thread = new SRTN2Thread(srtn);
							thread.start();
						}
						
						if(schedulingMethodComboBox.getSelectedItem().equals("HRRN"))
						{
							System.out.println("hrrn�۵�");
							HRRN hrrn=new HRRN(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()));
							hrrn.scheduling();
							
							//Result Table ǥ��
							for(int i=0;i<timeModel.getRowCount();i++)
							{
								String inputStr[]=new String[6];
								inputStr[0]= hrrn.process[i].processName;
								inputStr[1]= Double.toString(hrrn.process[i].arrivalTime);
								inputStr[2]= Double.toString(hrrn.process[i].burstTime);
								inputStr[3]=Double.toString(hrrn.process[i].waitingTime);
								inputStr[4]=Double.toString(hrrn.process[i].turnaroundTime);
								inputStr[5]=Double.toString(hrrn.process[i].normalizedTT);
								resultModel.addRow(inputStr);
							}
							
							
							//��Ʈ��Ʈ
							ganttContainer.setLayout(new GridLayout(hrrn.processorNum,hrrn.terminateTime));
							multiGanttLabel = new JLabel[hrrn.processorNum][hrrn.terminateTime];
							//ganttLabel = new JLabel[hrrn.terminateTime];
							ganttLabelCount=hrrn.terminateTime;
							processorNum=hrrn.processorNum;
							for(int j=0;j<hrrn.processorNum;j++)
							{
								for(int i=0;i<hrrn.terminateTime;i++)
								{
									multiGanttLabel[j][i]=new JLabel(" ");
									multiGanttLabel[j][i].setOpaque(true);
									multiGanttLabel[j][i].setBackground(Color.WHITE);
									ganttContainer.add(multiGanttLabel[j][i]);
								}
							}
							repaint();
							revalidate(); // �̰� ����� �����ڿ��� ������Ʈ �Ⱥ��̰� �ٸ� ������ ������Ʈ�� ���� �� �ִ�.
							
							
							thread = new HRRNThread(hrrn);
							thread.start();
						}
						
						//Team Alogorithm : �ڷγ� ȯ�� ġ��(�ܿ� ���赵�� �� ���� ȯ�� �켱���� ġ�� �ϴ� �����ٸ� �˰���)
						if(schedulingMethodComboBox.getSelectedItem().equals("TEAM"))
						{
							System.out.println("hrrn�۵�");
							TEAM team=new TEAM(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()),selectedSchedulingPolicy);
							team.scheduling();
							
							//Result Table ǥ��
							for(int i=0;i<timeModel.getRowCount();i++)
							{
								String inputStr[]=new String[6];
								inputStr[0]= team.process[i].processName;
								inputStr[1]= Double.toString(team.process[i].arrivalTime);
								inputStr[2]= Double.toString(team.process[i].burstTime);
								inputStr[3]=Double.toString(team.process[i].waitingTime);
								inputStr[4]=Double.toString(team.process[i].turnaroundTime);
								inputStr[5]=Double.toString(team.process[i].normalizedTT);
								resultModel.addRow(inputStr);
							}
							
							
							//��Ʈ��Ʈ
							ganttContainer.setLayout(new GridLayout(team.processorNum,team.terminateTime));
							multiGanttLabel = new JLabel[team.processorNum][team.terminateTime];
							//ganttLabel = new JLabel[hrrn.terminateTime];
							ganttLabelCount=team.terminateTime;
							processorNum=team.processorNum;
							for(int j=0;j<team.processorNum;j++)
							{
								for(int i=0;i<team.terminateTime;i++)
								{
									multiGanttLabel[j][i]=new JLabel(" ");
									multiGanttLabel[j][i].setOpaque(true);
									multiGanttLabel[j][i].setBackground(Color.WHITE);
									ganttContainer.add(multiGanttLabel[j][i]);
								}
							}
							repaint();
							revalidate(); // �̰� ����� �����ڿ��� ������Ʈ �Ⱥ��̰� �ٸ� ������ ������Ʈ�� ���� �� �ִ�.
							
							
							thread = new TEAMThread(team);
							thread.start();
						}
						schedulingStartBtn.setText("Stop");
					}
					else if(schedulingStartBtn.getText().equals("Stop"))
					{
						threadState=1;// 0�̸� ���� ���� �����尡 ���� ����, 1�̸� �ִ� ���¸� �ǹ��Ѵ�
						schedulingStartBtn.setText("Restart");
						processResetBtn.setVisible(true);
					}
					else if(schedulingStartBtn.getText().equals("Restart"))
					{
						processResetBtn.setVisible(false);
						threadState=0;
						schedulingStartBtn.setText("Stop");
						thread.interrupt();
						/*ThreadNotifier notifier = new ThreadNotifier();
						notifier.start();*/
					}
				}
			}
			
		}
		
		
			
	}
	
	class MyComboBoxActionListener implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox c = (JComboBox)e.getSource();
				if(c==schedulingMethodComboBox)
				{
					if(schedulingMethodComboBox.getSelectedItem().equals("RR")) // RR �����ٸ� ��� ���� ��
					{
						timeQuantumLabel.setVisible(true);
						timeQuantumTextField.setVisible(true);
						timeUnitLabel.setVisible(true);
					}
					else // RR �� �����ٸ� ��� ���� ��
					{
						timeQuantumLabel.setVisible(false);
						timeQuantumTextField.setVisible(false);
						timeUnitLabel.setVisible(false);
						timeQuantumTextField.setText("");
					}
					
					if(schedulingMethodComboBox.getSelectedItem().equals("SPN") || schedulingMethodComboBox.getSelectedItem().equals("SRTN")
							||schedulingMethodComboBox.getSelectedItem().equals("SPN2") ||schedulingMethodComboBox.getSelectedItem().equals("SRTN2")
							||schedulingMethodComboBox.getSelectedItem().equals("TEAM")) //�����ٸ� ��å ���� ������ �����ٸ�
					{
						schedulingPolicyLabel.setVisible(true);
						schedulingPolicyComboBox.setVisible(true);
					}
					else
					{
						schedulingPolicyLabel.setVisible(false);
						schedulingPolicyComboBox.setVisible(false);
					}
				}
				else if(c==schedulingPolicyComboBox)
				{
					if(schedulingPolicyComboBox.getSelectedItem().equals("Preemptive")) // ���� ��å ä�� ��
					{
						selectedSchedulingPolicy=0;
					}
					else // ���� ��å ä�� ��
					{
						selectedSchedulingPolicy=1;
					}
				}
			}
	}
	
	class WorkObject{
		public synchronized void stopMethod()
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	class FCFS2Thread extends Thread{
		FCFS2 fcfs;
		WorkObject wordObject = new WorkObject();
		FCFS2Thread(FCFS2 fcfs)
		{
			this.fcfs=fcfs;
		}
		@Override
		public void run()
		{
			ganttRealTimeLabel2.setText("0sec");
			for(int i=0;i<fcfs.terminateTime;i++)
			{
				if(threadState==1) // Stop��ư Ŭ�� �� ��������
				{
					wordObject.stopMethod();
				}
				//ganttLabel[i].setText(i+"\n\n\n"+".");
				try
				{
					Thread.sleep(excutionSpeed);
				}
				catch (InterruptedException e2) 
				{ e2.printStackTrace();}
				
				for(int j=0;j<fcfs.processorNum;j++) //Gantt chart �۾����� �ش� ���μ��� ������ ���ĥ
				{
					multiGanttLabel[j][i].setBackground(colorSelection(fcfs.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // ��ũ�ѹ��� ��ġ�� ��� ó���� �۾��� ��ġ�� �̵�
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<fcfs.processNum;j++) // result table �� ����
				{
					resultTable.setValueAt(fcfs.realTimeValue[i][j].waitingTime, j, 3);
					if(fcfs.realTimeValue[i][j].turnaroundTime==-1)
					{
						resultTable.setValueAt("-", j, 4);
					}
					else
					{
						resultTable.setValueAt(fcfs.realTimeValue[i][j].turnaroundTime, j, 4);
					}
					if(fcfs.realTimeValue[i][j].normalizedTT==-1)
					{
						resultTable.setValueAt("-", j, 5);
					}
					else
					{
						resultTable.setValueAt(fcfs.realTimeValue[i][j].normalizedTT, j, 5);
					}
				}
				
				for(int j=0;j<15;j++) // �غ� ������ ���μ������� �켱������ ���� ������������ ������
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<fcfs.processNum;j++)
				{
					if(fcfs.readyProcess[i][j]!=null) //�غ�ť�� �־���� ���μ������̸�
					{
						queueProcessLabel[line].setText(fcfs.readyProcess[i][j].processName);
						queueProcessLabel[line].setBackground(colorSelection(fcfs.readyProcess[i][j].processOrder));
						line++;
					}
				}
				
				repaint();
				revalidate();
			}
			schedulingStartBtn.setText("Start");	
			processResetBtn.setVisible(true);
		}
	}
	
	class RRThread extends Thread{
		RR rr;
		WorkObject wordObject = new WorkObject();
		RRThread(RR rr)
		{
			this.rr=rr;
		}
		@Override
		public void run()
		{
			ganttRealTimeLabel2.setText("0sec");
			for(int i=0;i<rr.terminateTime;i++)
			{
				if(threadState==1) // Stop��ư Ŭ�� �� ��������
				{
					wordObject.stopMethod();
				}
				//ganttLabel[i].setText(i+"\n\n\n"+".");
				try
				{
					Thread.sleep(excutionSpeed);
				}
				catch (InterruptedException e2) 
				{ e2.printStackTrace();}
				
				for(int j=0;j<rr.processorNum;j++) //Gantt chart �۾����� �ش� ���μ��� ������ ���ĥ
				{
					multiGanttLabel[j][i].setBackground(colorSelection(rr.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // ��ũ�ѹ��� ��ġ�� ��� ó���� �۾��� ��ġ�� �̵�
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<rr.processNum;j++) // result table �� ����
				{
					resultTable.setValueAt(rr.realTimeValue[i][j].waitingTime, j, 3);
					if(rr.realTimeValue[i][j].turnaroundTime==-1)
					{
						resultTable.setValueAt("-", j, 4);
					}
					else
					{
						resultTable.setValueAt(rr.realTimeValue[i][j].turnaroundTime, j, 4);
					}
					if(rr.realTimeValue[i][j].normalizedTT==-1)
					{
						resultTable.setValueAt("-", j, 5);
					}
					else
					{
						resultTable.setValueAt(rr.realTimeValue[i][j].normalizedTT, j, 5);
					}
				}
				
				for(int j=0;j<15;j++) // �غ� ������ ���μ������� �켱������ ���� ������������ ������
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<rr.processNum;j++)
				{
					if(rr.readyProcess[i][j]!=null) //�غ�ť�� �־���� ���μ������̸�
					{
						queueProcessLabel[line].setText(rr.readyProcess[i][j].processName);
						queueProcessLabel[line].setBackground(colorSelection(rr.readyProcess[i][j].processOrder));
						line++;
					}
				}
				
				repaint();
				revalidate();
			}
			schedulingStartBtn.setText("Start");	
			processResetBtn.setVisible(true);
		}
	}
	
	class SPN2Thread extends Thread{
		SPN2 spn2;
		WorkObject wordObject = new WorkObject();
		SPN2Thread(SPN2 spn2)
		{
			this.spn2=spn2;
		}
		@Override
		public void run()
		{
			ganttRealTimeLabel2.setText("0sec");
			for(int i=0;i<spn2.terminateTime;i++)
			{
				if(threadState==1) // Stop��ư Ŭ�� �� ��������
				{
					wordObject.stopMethod();
				}
				//ganttLabel[i].setText(i+"\n\n\n"+".");
				try
				{
					Thread.sleep(excutionSpeed);
				}
				catch (InterruptedException e2) 
				{ e2.printStackTrace();}
				
				for(int j=0;j<spn2.processorNum;j++) //Gantt chart �۾����� �ش� ���μ��� ������ ���ĥ
				{
					multiGanttLabel[j][i].setBackground(colorSelection(spn2.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // ��ũ�ѹ��� ��ġ�� ��� ó���� �۾��� ��ġ�� �̵�
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<spn2.processNum;j++) // result table �� ����
				{
					resultTable.setValueAt(spn2.realTimeValue[i][j].waitingTime, j, 3);
					if(spn2.realTimeValue[i][j].turnaroundTime==-1)
					{
						resultTable.setValueAt("-", j, 4);
					}
					else
					{
						resultTable.setValueAt(spn2.realTimeValue[i][j].turnaroundTime, j, 4);
					}
					if(spn2.realTimeValue[i][j].normalizedTT==-1)
					{
						resultTable.setValueAt("-", j, 5);
					}
					else
					{
						resultTable.setValueAt(spn2.realTimeValue[i][j].normalizedTT, j, 5);
					}
				}
				
				for(int j=0;j<15;j++) // �غ� ������ ���μ������� �켱������ ���� ������������ ������
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<spn2.processNum;j++)
				{
					if(spn2.readyProcess[i][j]!=null) //�غ�ť�� �־���� ���μ������̸�
					{
						queueProcessLabel[line].setText(spn2.readyProcess[i][j].processName);
						queueProcessLabel[line].setBackground(colorSelection(spn2.readyProcess[i][j].processOrder));
						line++;
					}
				}
				
				repaint();
				revalidate();
			}
			schedulingStartBtn.setText("Start");	
			processResetBtn.setVisible(true);
		}
	}
	
	class SRTN2Thread extends Thread{
		SRTN2 srtn;
		WorkObject wordObject = new WorkObject();
		SRTN2Thread(SRTN2 srtn)
		{
			this.srtn=srtn;
		}
		@Override
		public void run()
		{
			ganttRealTimeLabel2.setText("0sec");
			for(int i=0;i<srtn.terminateTime;i++)
			{
				if(threadState==1) // Stop��ư Ŭ�� �� ��������
				{
					wordObject.stopMethod();
				}
				//ganttLabel[i].setText(i+"\n\n\n"+".");
				try
				{
					Thread.sleep(excutionSpeed);
				}
				catch (InterruptedException e2) 
				{ e2.printStackTrace();}
				
				for(int j=0;j<srtn.processorNum;j++) //Gantt chart �۾����� �ش� ���μ��� ������ ���ĥ
				{
					multiGanttLabel[j][i].setBackground(colorSelection(srtn.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // ��ũ�ѹ��� ��ġ�� ��� ó���� �۾��� ��ġ�� �̵�
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<srtn.processNum;j++) // result table �� ����
				{
					resultTable.setValueAt(srtn.realTimeValue[i][j].waitingTime, j, 3);
					if(srtn.realTimeValue[i][j].turnaroundTime==-1)
					{
						resultTable.setValueAt("-", j, 4);
					}
					else
					{
						resultTable.setValueAt(srtn.realTimeValue[i][j].turnaroundTime, j, 4);
					}
					if(srtn.realTimeValue[i][j].normalizedTT==-1)
					{
						resultTable.setValueAt("-", j, 5);
					}
					else
					{
						resultTable.setValueAt(srtn.realTimeValue[i][j].normalizedTT, j, 5);
					}
				}
				
				for(int j=0;j<15;j++) // �غ�ť�� ���̺��� ���ڿ��� ������ �ʱ�ȭ��Ŵ
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<srtn.processNum;j++)
				{
					if(srtn.readyProcess[i][j]!=null) //�غ�ť�� �־���� ���μ������̸�
					{
						queueProcessLabel[line].setText(srtn.readyProcess[i][j].processName);
						queueProcessLabel[line].setBackground(colorSelection(srtn.readyProcess[i][j].processOrder));
						line++;
					}
				}
				
				repaint();
				revalidate();
			}
			schedulingStartBtn.setText("Start");
			processResetBtn.setVisible(true);
		}
	}
	
	class HRRNThread extends Thread{
		HRRN hrrn;
		WorkObject wordObject = new WorkObject();
		HRRNThread(HRRN hrrn)
		{
			this.hrrn=hrrn;
		}
		@Override
		public void run()
		{
			ganttRealTimeLabel2.setText("0sec");
			for(int i=0;i<hrrn.terminateTime;i++)
			{
				if(threadState==1) // Stop��ư Ŭ�� �� ��������
				{
					wordObject.stopMethod();
				}
				//ganttLabel[i].setText(i+"\n\n\n"+".");
				try
				{
					Thread.sleep(excutionSpeed);
				}
				catch (InterruptedException e2) 
				{ e2.printStackTrace();}
				
				for(int j=0;j<hrrn.processorNum;j++) //Gantt chart �۾����� �ش� ���μ��� ������ ���ĥ
				{
					multiGanttLabel[j][i].setBackground(colorSelection(hrrn.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // ��ũ�ѹ��� ��ġ�� ��� ó���� �۾��� ��ġ�� �̵�
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<hrrn.processNum;j++) // result table �� ����
				{
					resultTable.setValueAt(hrrn.realTimeValue[i][j].waitingTime, j, 3);
					if(hrrn.realTimeValue[i][j].turnaroundTime==-1)
					{
						resultTable.setValueAt("-", j, 4);
					}
					else
					{
						resultTable.setValueAt(hrrn.realTimeValue[i][j].turnaroundTime, j, 4);
					}
					if(hrrn.realTimeValue[i][j].normalizedTT==-1)
					{
						resultTable.setValueAt("-", j, 5);
					}
					else
					{
						resultTable.setValueAt(hrrn.realTimeValue[i][j].normalizedTT, j, 5);
					}
				}
				
				for(int j=0;j<15;j++) // �غ� ������ ���μ������� �켱������ ���� ������������ ������
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<hrrn.processNum;j++)
				{
					if(hrrn.readyProcess[i][j]!=null) //�غ�ť�� �־���� ���μ������̸�
					{
						queueProcessLabel[line].setText(hrrn.readyProcess[i][j].processName);
						queueProcessLabel[line].setBackground(colorSelection(hrrn.readyProcess[i][j].processOrder));
						line++;
					}
				}
				
				repaint();
				revalidate();
			}
			schedulingStartBtn.setText("Start");
			processResetBtn.setVisible(true);
		}
	}
	
	class TEAMThread extends Thread{
		TEAM team;
		WorkObject wordObject = new WorkObject();
		TEAMThread(TEAM team)
		{
			this.team=team;
		}
		@Override
		public void run()
		{
			ganttRealTimeLabel2.setText("0sec");
			for(int i=0;i<team.terminateTime;i++)
			{
				if(threadState==1) // Stop��ư Ŭ�� �� ��������
				{
					wordObject.stopMethod();
				}
				//ganttLabel[i].setText(i+"\n\n\n"+".");
				try
				{
					Thread.sleep(excutionSpeed);
				}
				catch (InterruptedException e2) 
				{ e2.printStackTrace();}
				
				for(int j=0;j<team.processorNum;j++)
				{
					multiGanttLabel[j][i].setBackground(colorSelection(team.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // ��ũ�ѹ��� ��ġ�� ��� ó���� �۾��� ��ġ�� �̵�
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<team.processNum;j++) // result table �� ����
				{
					resultTable.setValueAt(team.realTimeValue[i][j].waitingTime, j, 3);
					if(team.realTimeValue[i][j].turnaroundTime==-1)
					{
						resultTable.setValueAt("-", j, 4);
					}
					else
					{
						resultTable.setValueAt(team.realTimeValue[i][j].turnaroundTime, j, 4);
					}
					if(team.realTimeValue[i][j].normalizedTT==-1)
					{
						resultTable.setValueAt("-", j, 5);
					}
					else
					{
						resultTable.setValueAt(team.realTimeValue[i][j].normalizedTT, j, 5);
					}
				}
				
				for(int j=0;j<15;j++) // �غ� ������ ���μ������� �켱������ ���� ������������ ������
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<team.processNum;j++)
				{
					if(team.readyProcess[i][j]!=null) //�غ�ť�� �־���� ���μ������̸�
					{
						queueProcessLabel[line].setText(team.readyProcess[i][j].processName);
						queueProcessLabel[line].setBackground(colorSelection(team.readyProcess[i][j].processOrder));
						line++;
					}
				}
				
				repaint();
				revalidate();
			}
			schedulingStartBtn.setText("Start");
			processResetBtn.setVisible(true);
		}
	}
	
	class MyPanel extends JPanel  // ť �ڽ��� ��Ʈ��Ʈ �׵θ� ����
	{ 
		public void paintComponent(Graphics g) 
		{ 
			super.paintComponent(g); 
			g.setColor(Color.BLACK);
			g.drawRect(14,300,692,52); // Ready Queue
			g.drawRect(14,381,692,100); // Gantt Chart
			g.drawRect(450,120,250,35); // �̹��� �� �ڽ�
			g.drawLine(0, 0, 730, 0);
		}
	}
	
	public Color colorSelection(int rowNum) // ���μ��� ����
	{
		if(rowNum==0)
		{
			return new Color(51,51,153);
		}
		else if(rowNum==1)
		{
				return Color.ORANGE;
		}
		else if(rowNum==2)
		{
				return Color.YELLOW;
		}
		else if(rowNum==3)
		{
				return Color.RED;
		}
		else if(rowNum==4)
		{
				return Color.BLUE;
		}
		else if(rowNum==5)
		{
				return new Color(47,157,39);
		}
		else if(rowNum==6)
		{
				return Color.MAGENTA;
		}
		else if(rowNum==7)
		{
				return Color.PINK;
		}
		else if(rowNum==8)
		{
				return Color.CYAN;
		}
		else if(rowNum==9)
		{
				return new Color(218,217,255);
		}
		else if(rowNum==10)
		{
				return new Color(243,97,166);
		}
		else if(rowNum==11)
		{
				return new Color(153,0,76);
		}
		else if(rowNum==12)
		{
				return new Color(0,130,153);
		}
		else if(rowNum==13)
		{
				return new Color(196,183,59);
		}
		else if(rowNum==14)
		{
				return new Color(96,0,255);
		}
		else
		{
				return Color.WHITE;
		}
	}
	
	public Color textColorSelection(int rowNum) // ���μ��� ����
	{
		if(rowNum==2 || rowNum==7 || rowNum==8|| rowNum==9)
		{
			return Color.BLACK;
		}
		else
		{
			return Color.WHITE;
		}
		
	}
}
	