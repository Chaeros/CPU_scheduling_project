
public class Process {
	int use=0;          // 프로세스 사용 여부
	String processName;   // 프로세스 이름
	double arrivalTime;    // 도착시간
	double burstTime;      // 실행시간
	double waitingTime;       // 대기시간
	double realBurstTime;  // 리얼타임 실행시간
	double turnaroundTime; // 반환시간
	double normalizedTT;   // 정규화된 반환시간
	int processOrder;      // 프로세서 순서
	int RRTimeCount=0;       // RR스케줄링에서의 연속된 작업시간을 보여줌
	
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
