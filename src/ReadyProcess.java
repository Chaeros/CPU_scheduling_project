
public class ReadyProcess {
	String processName;
	int processOrder;
	double responseRatio;   // (WT+BT)/BT�� �������� �켱������ ����
	int state;	  // -1�̸� ť�� ����� �ϴ� ����, 1�̸� ť�� �־�� �ϴ� ���¸� ��Ī�Ѵ�
	double arrivalTime;
	double burstTime;
	double restJobTime;
	
	ReadyProcess(String processName, int processOrder,double responseRatio)
	{
		this.processName=processName;
		this.processOrder=processOrder;
		this.responseRatio=responseRatio;
		this.state=-1;
	}
}
