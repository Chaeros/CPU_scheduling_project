
public class ReadyProcess {
	String processName;
	int processOrder;
	double responseRatio;   // (WT+BT)/BT로 높을수록 우선순위가 높다
	int state;	  // -1이면 큐에 없어야 하는 상태, 1이면 큐에 있어야 하는 상태를 지칭한다
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
