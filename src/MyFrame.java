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
	int selectedSchedulingPolicy=0;//0이면 선점, 1이면 비선점
	
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
	//Gantt Chart 콘테이너
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
	int threadState=0;// 0이면 실행 중인 스레드가 없는 상태, 1이면 있는 상태를 의미한다
	
	MyFrame(){
		setTitle("Process Scheduling Simulator"); // 프레임 제목 이름 메소드
		setContentPane(new MyPanel());
		Container cp = getContentPane();  // 프레임 핸들링 할 수 있도록 객체 생성
		cp.setLayout(null);  // 컨테이너 배치를 보더레이아웃 형태로 하게 하는 메소드
		
		//Ready Queue 콘테이너
		//Container queueContainer = new Container();
		queueContainer.setLayout(new GridLayout(1,15));
		
		//Time table표 생성
		String timeTableColumn[] = {"Process Name","Arrival Time(AT)","Burst Time(BT)"};
		String temp1[][] = null;
		timeModel=new DefaultTableModel(temp1,timeTableColumn);
		timeTable= new ColorTable(timeModel);
		JScrollPane timeTableScroll = new JScrollPane(timeTable);
		
		/*timeTable.getColumn("Delete Button").setCellRenderer(dcr);
		JButton deleteBtn = new JButton();
		deleteBtn.setHorizontalAlignment(JLabel.CENTER)*/
		//timeTable.getColumn("Delete Butoon").setCellEditor(new DefaultCellEditor(deleteBtn));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 테이블셀 렌더러 객체를 생성.
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);              // 생성한 렌더러의 가로정렬을 CENTER로
		for(int i=0;i<timeTable.getColumnCount();i++)
		{
			timeTable.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
		timeTable.getParent().setBackground(Color.white);

		
		//Ready Queue 자료 상태와 색깔 표시
		for(int i=0;i<15;i++)
		{
			queueProcessLabel[i]=new JLabel();
			queueProcessLabel[i].setOpaque(true);
			queueProcessLabel[i].setBackground(Color.WHITE);
			queueProcessLabel[i].setHorizontalAlignment(JLabel.CENTER);
		}
		
		//결과표 생성
		String resultTableColumn[] = {"Process Name","Arrival Time(AT)","Burst Time(BT)","Waiting Time(WT)",
				"Turnaround Time(TT)","Normalized TT(NTT)"};
		String temp[][] = null;
		resultModel=new DefaultTableModel(temp,resultTableColumn);
		resultTable= new ColorTable(resultModel);
		JScrollPane resultTableScroll = new JScrollPane(resultTable);
		DefaultTableCellRenderer dtcr2 = new DefaultTableCellRenderer(); // 테이블셀 렌더러 객체를 생성.
		dtcr2.setHorizontalAlignment(SwingConstants.CENTER);              // 생성한 렌더러의 가로정렬을 CENTER로
		for(int i=0;i<resultTable.getColumnCount();i++)
		{
			resultTable.getColumnModel().getColumn(i).setCellRenderer(dtcr2);
		}
		resultTable.getParent().setBackground(Color.white);

		///각각 컴포넌트들의 절대적 위치 지정///
		
		//최상단 라벨들
		schedulingMethodLabel.setBounds(10,10,100,20);
		processNameLabel.setBounds(120,10,100,20);
		ATLabel.setBounds(230,10,100,20);
		BTLabel.setBounds(340,10,100,20);
		
		//상단에 위치한 스케줄링 알고리즘 콤보박스 및 입력값 텍스트필드, 버튼
		schedulingMethodComboBox.setBounds(10,31,100,20);
		processNameTextField.setBounds(120,31,100,20);
		ATTextField.setBounds(230,31,100,20);
		BTTextField.setBounds(340,31,100,20);
		processAddBtn.setBounds(450,31,120,20);
		processResetBtn.setBounds(580,31,120,20);
		processRestLabel.setBounds(580,31,120,20);
		schedulingStartBtn.setBounds(450,230,250,44);
		
		//Time table표
		timeTableLabel.setBounds(10,53,100,20);
		timeTableScroll.setBounds(10,74,430,200);
		
		//Time quantum
		timeQuantumLabel.setBounds(455,127,120,20);
		timeQuantumTextField.setBounds(575,127,65,20);
		timeUnitLabel.setBounds(640,127,70,20);
		
		//Ready Queue 콘테이너
		queueLabel.setBounds(10,280,100,20);
		queueContainer.setBounds(10,301,700,50);
		
		//Gantt Chart 콘테니어
		ganttChartLabel.setBounds(10,360,100,20);
		ganttRealTimeLabel.setBounds(120,360,80,20);
		ganttRealTimeLabel2.setBounds(190,360,50,20);
		ganttScroll.setBounds(15,383,690,97);
		
		//결과표
		resultTableLabel.setBounds(10,492,100,20);
		resultTableScroll.setBounds(10,513,700,200);
		
		//팀명&버전
		teamNameLabel.setBounds(585,715,200,20);
		
		//프로세서 개수
		processorNumberLabel.setBounds(455,74,120,20);
		processorNumberComboBox.setBounds(575,74,120,20);
		
		//실행시간
		excutionSpeedLabel.setBounds(455,96,120,20);
		excutionSpeedTextField.setBounds(575,96,100,20);
		excutionUnit.setBounds(675,96,20,20);
		
		//스케줄링 정책
		schedulingPolicyLabel.setBounds(455,127,120,20);
		schedulingPolicyComboBox.setBounds(575,127,120,20);
		
		//삽입 이미지
		imageLabel.setBounds(450,160,250,65);
		
		//컴포넌트 색깔 설정
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
		
		///각각 컴포넌트들을  컨테이너에 올림///
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
		timeQuantumLabel.setVisible(false);   // RR 방식 선택 시에만 입력값을 받을 수 있도록 프로그램이 시작할 때는 입력창을 가려준다
		timeQuantumTextField.setVisible(false); 
		timeUnitLabel.setVisible(false);
		
		//Ready Queue콘테이너
		cp.add(queueLabel);
		cp.add(queueContainer);
		for(int i=0;i<15;i++)
		{
			queueContainer.add(queueProcessLabel[i]);
		}
		
		//Gantt Chart 콘테이너
		cp.add(ganttChartLabel);
		cp.add(ganttRealTimeLabel);
		cp.add(ganttRealTimeLabel2);
		cp.add(ganttScroll);
		ganttRealTimeLabel.setVisible(false);
		ganttRealTimeLabel2.setVisible(false);
		
		//Time table표
		cp.add(resultTableLabel);
		cp.add(resultTableScroll);
		
		//팀명&버전
		cp.add(teamNameLabel);
		
		//프로세서 개수
		cp.add(processorNumberLabel);
		cp.add(processorNumberComboBox);
		
		//실행시간
		cp.add(excutionSpeedLabel);
		cp.add(excutionSpeedTextField);
		cp.add(excutionUnit);
		
		//스케줄링 정책
		cp.add(schedulingPolicyLabel);
		cp.add(schedulingPolicyComboBox);
		schedulingPolicyLabel.setVisible(false);
		schedulingPolicyComboBox.setVisible(false);
		
		//이미지
		cp.add(imageLabel);
		
		processAddBtn.addActionListener(new MyButtonActionListener());
		processResetBtn.addActionListener(new MyButtonActionListener());
		schedulingStartBtn.addActionListener(new MyButtonActionListener());
		schedulingMethodComboBox.addActionListener(new MyComboBoxActionListener());
		processorNumberComboBox.addActionListener(new MyComboBoxActionListener());
		schedulingPolicyComboBox.addActionListener(new MyComboBoxActionListener());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임의 X 클릭으로 창을 닫았을 때 프로그램을 종료 시키는 메소드 -> 안하면 닫아도 프로그램은 안 끝난 상태이다
		setSize(730,780); // 프레임 크기 조절 메소드
		setVisible(true); // 프레임 실체화 메소드
		//cp.setBackground(Color.WHITE);      // 프레임 배경색 설정
		
		
		//임시 코드 확인용
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
	
	//테이블 특정셀에 색을 주기위한 자식 클래스 선언
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
	
	//테이블 행마다 버튼을 넣기 위해 클래스 선언
	/*DefaultTableCellRenderer dcr = new DefaultTableCellRenderer()
	{
		public Component getTableCellRendererComponent  // 셀렌더러
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			JButton btn= new JButton();
			btn.setSelected(((Boolean)value).booleanValue());  
			btn.setHorizontalAlignment(JLabel.CENTER);
			return btn;
		}
	};
	
	public class MyDefaultTableCellRenderer // JTaable에서  특정 튜플을 제어하기 위해 메소드 재정의
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
			JButton b = (JButton)e.getSource();  // e가 누른 버튼의 객체를 받아주고 참조변수 b에 대입한다.
			//System.out.print(b.getText());
			
			if(b==processAddBtn) // Time table에 튜플 추가
			{
				if(timeModel.getRowCount()==15) // 최대로 삽입할 수 있는 프로세스 15개로 제한
				{
					JOptionPane.showMessageDialog(null, "최대 15개의 프로세서만 삽입할 수 있습니다.", "Message", JOptionPane.ERROR_MESSAGE);
					//프로세스 입력 후 텍스르필드 비우기
					processNameTextField.setText("");
					ATTextField.setText("");
					BTTextField.setText("");
				}
				else if(processNameTextField.getText().equals("")) // 프로세스 이름 미입력 시
				{
					JOptionPane.showMessageDialog(null, "프로세서 이름을 입력해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(ATTextField.getText().equals("")) // AT값 미입력 시
				{
					JOptionPane.showMessageDialog(null, "Arrival time을 입력해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(BTTextField.getText().equals("")) // BT값 미입력 시
				{
					JOptionPane.showMessageDialog(null, "Burst time을 입력해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else // 문제없이 입력한 프로세스 값 적용 시
				{
					
					String inputStr[]=new String[3];
					inputStr[0]=processNameTextField.getText();
					inputStr[1]=ATTextField.getText();
					inputStr[2]=BTTextField.getText();
					
					timeModel.addRow(inputStr);
					
					
					//프로세스 입력 후 텍스르필드 비우기
					processNameTextField.setText("");
					ATTextField.setText("");
					BTTextField.setText("");
					
				}
			}
			else if(b==processResetBtn) // Time table표, Result table 초기화
			{
				int tupleIndex=timeModel.getRowCount();   // 변수를 따로 선언하여 for문에 넣어주는 이유는 model안의 인덱스 값이 삭제되면서 수시로 변하기 때문이다.
				int i=0;
				for(;i<tupleIndex;i++)
				{
					timeModel.removeRow(0);  // 인덱스를 i말고 0으로 해야 지워지면서 재정렬된 튜플에서 다시 지워 모든 튜플을 지울 수 있다.
				}
				
				//Result Table에 기존 값이 있었더라면 다 지우고 새로운 Result테이블 생성
				int tupleIndex2=resultModel.getRowCount();   // 변수를 따로 선언하여 for문에 넣어주는 이유는 model안의 인덱스 값이 삭제되면서 수시로 변하기 때문이다.
				int t=0;
				for(;t<tupleIndex2;t++)
				{
					resultModel.removeRow(0);  // 인덱스를 i말고 0으로 해야 지워지면서 재정렬된 튜플에서 다시 지워 모든 튜플을 지울 수 있다.
				}
				
				//Ready Queue 비우기
				for(int k=0;k<15;k++)
				{
					queueProcessLabel[k].setText("");
					queueProcessLabel[k].setOpaque(true);
					queueProcessLabel[k].setBackground(Color.WHITE);
					queueProcessLabel[k].setHorizontalAlignment(JLabel.CENTER);
				}
				
				//Gantt Chart 비우기
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
				
				// 텍스르필드 비우기
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
			else if(b==schedulingStartBtn) // 스케줄링 시작
			{
				if(timeModel.getRowCount()==0) 
				{JOptionPane.showMessageDialog(null, "입력된 프로세스가 없습니다.", "Message", JOptionPane.ERROR_MESSAGE);}
				else if(excutionSpeedTextField.getText().equals("")) 
				{JOptionPane.showMessageDialog(null, "실행시간을 입력해주세요.", "Message", JOptionPane.ERROR_MESSAGE);}
				else
				{
					if(schedulingStartBtn.getText().equals("Start"))
					{
						processResetBtn.setVisible(false);
						//Result Table에 기존 값이 있었더라면 다 지우고 새로운 Result테이블 생성
						int tupleIndex=resultModel.getRowCount();   // 변수를 따로 선언하여 for문에 넣어주는 이유는 model안의 인덱스 값이 삭제되면서 수시로 변하기 때문이다.
						int t=0;
						for(;t<tupleIndex;t++)
						{
							resultModel.removeRow(0);  // 인덱스를 i말고 0으로 해야 지워지면서 재정렬된 튜플에서 다시 지워 모든 튜플을 지울 수 있다.
						}
						ganttRealTimeLabel.setVisible(true);
						ganttRealTimeLabel2.setVisible(true);
						
						//기존 Gantt Chart 있었다면 비우기
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
						
						//FCFS2 스케줄링 Start 시
						if(schedulingMethodComboBox.getSelectedItem().equals("FCFS"))
						{
							System.out.println("FCFS2작동");
							FCFS2 fcfs=new FCFS2(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()));
							fcfs.scheduling();
							
							//Result Table 표시
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
							
							
							//간트차트
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
							revalidate(); // 이거 써줘야 생성자에서 컴포넌트 안붙이고도 다른 곳에서 컴포넌트를 붙일 수 있다.
							
							
							thread = new FCFS2Thread(fcfs);
							thread.start();
						}
						
						//RR 스케줄링 Start 시
						if(schedulingMethodComboBox.getSelectedItem().equals("RR"))
						{
							if(timeQuantumTextField.getText().equals(""))
							{
								JOptionPane.showMessageDialog(null, "Quantum time을 입력해주세요.", "Message", JOptionPane.ERROR_MESSAGE);return;
							}
							else if(Integer.parseInt(excutionSpeedTextField.getText())<1)
							{
								JOptionPane.showMessageDialog(null, "1초 보다 작은 시간을 Quantum time으로 입력할 수 없습니다.", "Message", JOptionPane.ERROR_MESSAGE);return;
							}
							timeQuantum=Integer.parseInt(timeQuantumTextField.getText());
							
							System.out.println("RR작동");
							RR rr=new RR(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()),timeQuantum);
							rr.scheduling();
							
							//Result Table 표시
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
							
							
							//간트차트
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
							revalidate(); // 이거 써줘야 생성자에서 컴포넌트 안붙이고도 다른 곳에서 컴포넌트를 붙일 수 있다.
							
							
							thread = new RRThread(rr);
							thread.start();
						}
						
						//SPN2
						if(schedulingMethodComboBox.getSelectedItem().equals("SPN"))
						{
							System.out.println("SPN2작동");
							SPN2 spn2=new SPN2(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()),selectedSchedulingPolicy);
							spn2.scheduling();
							
							//Result Table 표시
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
							
							
							//간트차트
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
							revalidate(); // 이거 써줘야 생성자에서 컴포넌트 안붙이고도 다른 곳에서 컴포넌트를 붙일 수 있다.
							
							
							thread = new SPN2Thread(spn2);
							thread.start();
						}
						
						//SRTN2
						//SRTN 스케줄링 Start 시
						if(schedulingMethodComboBox.getSelectedItem().equals("SRTN"))
						{
							System.out.println("SRTN2작동");
							SRTN2 srtn=new SRTN2(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()),selectedSchedulingPolicy); //스케줄링정책
							srtn.scheduling();
							
							//Result Table 표시
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
							
							
							//간트차트
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
							revalidate(); // 이거 써줘야 생성자에서 컴포넌트 안붙이고도 다른 곳에서 컴포넌트를 붙일 수 있다.
							
							
							thread = new SRTN2Thread(srtn);
							thread.start();
						}
						
						if(schedulingMethodComboBox.getSelectedItem().equals("HRRN"))
						{
							System.out.println("hrrn작동");
							HRRN hrrn=new HRRN(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()));
							hrrn.scheduling();
							
							//Result Table 표시
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
							
							
							//간트차트
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
							revalidate(); // 이거 써줘야 생성자에서 컴포넌트 안붙이고도 다른 곳에서 컴포넌트를 붙일 수 있다.
							
							
							thread = new HRRNThread(hrrn);
							thread.start();
						}
						
						//Team Alogorithm : 코로나 환자 치료(잔여 위험도가 더 높은 환자 우선으로 치료 하는 스케줄링 알고리즘)
						if(schedulingMethodComboBox.getSelectedItem().equals("TEAM"))
						{
							System.out.println("hrrn작동");
							TEAM team=new TEAM(inputProcess,timeModel.getRowCount(),Integer.parseInt(processorNumberComboBox.getSelectedItem().toString()),selectedSchedulingPolicy);
							team.scheduling();
							
							//Result Table 표시
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
							
							
							//간트차트
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
							revalidate(); // 이거 써줘야 생성자에서 컴포넌트 안붙이고도 다른 곳에서 컴포넌트를 붙일 수 있다.
							
							
							thread = new TEAMThread(team);
							thread.start();
						}
						schedulingStartBtn.setText("Stop");
					}
					else if(schedulingStartBtn.getText().equals("Stop"))
					{
						threadState=1;// 0이면 실행 중인 스레드가 없는 상태, 1이면 있는 상태를 의미한다
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
					if(schedulingMethodComboBox.getSelectedItem().equals("RR")) // RR 스케줄링 방식 선택 시
					{
						timeQuantumLabel.setVisible(true);
						timeQuantumTextField.setVisible(true);
						timeUnitLabel.setVisible(true);
					}
					else // RR 외 스케줄링 방식 선택 시
					{
						timeQuantumLabel.setVisible(false);
						timeQuantumTextField.setVisible(false);
						timeUnitLabel.setVisible(false);
						timeQuantumTextField.setText("");
					}
					
					if(schedulingMethodComboBox.getSelectedItem().equals("SPN") || schedulingMethodComboBox.getSelectedItem().equals("SRTN")
							||schedulingMethodComboBox.getSelectedItem().equals("SPN2") ||schedulingMethodComboBox.getSelectedItem().equals("SRTN2")
							||schedulingMethodComboBox.getSelectedItem().equals("TEAM")) //스케줄링 정책 선택 가능한 스케줄링
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
					if(schedulingPolicyComboBox.getSelectedItem().equals("Preemptive")) // 선점 정책 채택 시
					{
						selectedSchedulingPolicy=0;
					}
					else // 비선점 정책 채택 시
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
				if(threadState==1) // Stop버튼 클릭 시 일지정지
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
				
				for(int j=0;j<fcfs.processorNum;j++) //Gantt chart 작업마다 해당 프로세스 색으로 배경칠
				{
					multiGanttLabel[j][i].setBackground(colorSelection(fcfs.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // 스크롤바의 위치를 방금 처리한 작업의 위치로 이동
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<fcfs.processNum;j++) // result table 값 세팅
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
				
				for(int j=0;j<15;j++) // 준비 상태의 프로세스들을 우선순위에 따라 내림차순으로 재정렬
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<fcfs.processNum;j++)
				{
					if(fcfs.readyProcess[i][j]!=null) //준비큐에 있어야할 프로세스들이면
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
				if(threadState==1) // Stop버튼 클릭 시 일지정지
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
				
				for(int j=0;j<rr.processorNum;j++) //Gantt chart 작업마다 해당 프로세스 색으로 배경칠
				{
					multiGanttLabel[j][i].setBackground(colorSelection(rr.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // 스크롤바의 위치를 방금 처리한 작업의 위치로 이동
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<rr.processNum;j++) // result table 값 세팅
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
				
				for(int j=0;j<15;j++) // 준비 상태의 프로세스들을 우선순위에 따라 내림차순으로 재정렬
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<rr.processNum;j++)
				{
					if(rr.readyProcess[i][j]!=null) //준비큐에 있어야할 프로세스들이면
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
				if(threadState==1) // Stop버튼 클릭 시 일지정지
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
				
				for(int j=0;j<spn2.processorNum;j++) //Gantt chart 작업마다 해당 프로세스 색으로 배경칠
				{
					multiGanttLabel[j][i].setBackground(colorSelection(spn2.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // 스크롤바의 위치를 방금 처리한 작업의 위치로 이동
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<spn2.processNum;j++) // result table 값 세팅
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
				
				for(int j=0;j<15;j++) // 준비 상태의 프로세스들을 우선순위에 따라 내림차순으로 재정렬
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<spn2.processNum;j++)
				{
					if(spn2.readyProcess[i][j]!=null) //준비큐에 있어야할 프로세스들이면
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
				if(threadState==1) // Stop버튼 클릭 시 일지정지
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
				
				for(int j=0;j<srtn.processorNum;j++) //Gantt chart 작업마다 해당 프로세스 색으로 배경칠
				{
					multiGanttLabel[j][i].setBackground(colorSelection(srtn.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // 스크롤바의 위치를 방금 처리한 작업의 위치로 이동
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<srtn.processNum;j++) // result table 값 세팅
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
				
				for(int j=0;j<15;j++) // 준비큐의 레이블의 문자열과 배경색을 초기화시킴
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<srtn.processNum;j++)
				{
					if(srtn.readyProcess[i][j]!=null) //준비큐에 있어야할 프로세스들이면
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
				if(threadState==1) // Stop버튼 클릭 시 일지정지
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
				
				for(int j=0;j<hrrn.processorNum;j++) //Gantt chart 작업마다 해당 프로세스 색으로 배경칠
				{
					multiGanttLabel[j][i].setBackground(colorSelection(hrrn.processOrders[j][i]));
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					multiGanttLabel[j][i].setBorder(border);
					if(i>135) // 스크롤바의 위치를 방금 처리한 작업의 위치로 이동
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<hrrn.processNum;j++) // result table 값 세팅
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
				
				for(int j=0;j<15;j++) // 준비 상태의 프로세스들을 우선순위에 따라 내림차순으로 재정렬
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<hrrn.processNum;j++)
				{
					if(hrrn.readyProcess[i][j]!=null) //준비큐에 있어야할 프로세스들이면
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
				if(threadState==1) // Stop버튼 클릭 시 일지정지
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
					if(i>135) // 스크롤바의 위치를 방금 처리한 작업의 위치로 이동
					{
						ganttScroll.getHorizontalScrollBar().setValue(multiGanttLabel[j][i].getX()-670);
					}
				}
				ganttRealTimeLabel2.setText(Integer.toString(i+1)+"sec");
				
				for(int j=0;j<team.processNum;j++) // result table 값 세팅
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
				
				for(int j=0;j<15;j++) // 준비 상태의 프로세스들을 우선순위에 따라 내림차순으로 재정렬
				{
					queueProcessLabel[j].setText("");
					queueProcessLabel[j].setBackground(Color.WHITE);
				}
				
				int line=0;
				for(int j=0;j<team.processNum;j++)
				{
					if(team.readyProcess[i][j]!=null) //준비큐에 있어야할 프로세스들이면
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
	
	class MyPanel extends JPanel  // 큐 박스와 간트차트 테두리 생성
	{ 
		public void paintComponent(Graphics g) 
		{ 
			super.paintComponent(g); 
			g.setColor(Color.BLACK);
			g.drawRect(14,300,692,52); // Ready Queue
			g.drawRect(14,381,692,100); // Gantt Chart
			g.drawRect(450,120,250,35); // 이미지 위 박스
			g.drawLine(0, 0, 730, 0);
		}
	}
	
	public Color colorSelection(int rowNum) // 프로세스 배경색
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
	
	public Color textColorSelection(int rowNum) // 프로세스 배경색
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
	