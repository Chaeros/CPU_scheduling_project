
public class Process {
	int use=0;          // ���μ��� ��� ����
	String processName;   // ���μ��� �̸�
	double arrivalTime;    // �����ð�
	double burstTime;      // ����ð�
	double waitingTime;       // ���ð�
	double realBurstTime;  // ����Ÿ�� ����ð�
	double turnaroundTime; // ��ȯ�ð�
	double normalizedTT;   // ����ȭ�� ��ȯ�ð�
	int processOrder;      // ���μ��� ����
	int RRTimeCount=0;       // RR�����ٸ������� ���ӵ� �۾��ð��� ������
	
	Process()
	{
		
	}
	Process(String name,Double at, Double bt, int processOrder)
	{
		this.use=0;
		this.processName=name;
		this.arrivalTime=at;
		this.burstTime=bt;
		this.waitingTime=0;
		this.realBurstTime=0;
		this.processOrder=processOrder;
		this.turnaroundTime=-1;
		this.normalizedTT=-1;
	}
}
