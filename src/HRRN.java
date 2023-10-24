import java.util.LinkedList;
import java.util.Queue;

public class HRRN {
	Process process[] = new Process[15];
	int burstSum=0; // 총 실행시간
	int processNum; // 프로세스 개수
	int processorNum; // 프로세서 개수
	int terminateTime; // 모든 프로세스 작업 종료시간
	
	int processOrders[][]; // Gantt Chart에 사용될 정보 [프로세서번호][작업초단위]
	RealTimeValue realTimeValue[][];  // Result table에 사용될 정보 [작업초단위][프로세스번호]
	ReadyProcess readyProcess[][]; // Ready Queue에 사용될 정보 [작업초단위][프로세스번호]
	Queue<Process> processQueue = new LinkedList<Process>();
	Queue<Process> tempQueue = new LinkedList<Process>();
	
	HRRN(Process process[], int processNum, int processorNum)
	{
		for(int i=0;i<processNum;i++)
		{
			this.process[i] = new Process(process[i].processName,
					process[i].arrivalTime,process[i].burstTime,process[i].processOrder);
			burstSum+=process[i].burstTime;
			burstSum+=process[i].arrivalTime;
		}
		this.processNum=processNum;
		this.processorNum=processorNum;
	}
	
	void scheduling()
	{
		Process priority[]= new Process[processorNum];
		processOrders= new int[4][burstSum];
		realTimeValue= new RealTimeValue[burstSum][processNum];
		readyProcess= new ReadyProcess[burstSum][];
		
		//간트 차트에 표기할 매작업에 따른 프로세서가 수행하고있는 프로세스를 저장할 배열의 값을 -1로 초기화
		for(int i=0;i<processorNum;i++)
		{
			for(int j=0;j<burstSum;j++)
			{
				processOrders[i][j]=-1;
			}
		}
		
		for(int currentTime=0;currentTime<burstSum;currentTime++) // 총 실행시간만큼 반복
		{
			for(int i=0;i<processNum;i++) //AT가 current time보다 작거나 같아지거나 혹은 작업이 끝나지 않은 프로세스를 준비큐에 삽입
			{
				if(process[i].use==0 && process[i].realBurstTime!=process[i].burstTime && process[i].arrivalTime==currentTime)
				{
					processQueue.offer(process[i]);
				}
			}
			
			int tempQueueSize=tempQueue.size();
			for(int i=0;i<tempQueueSize;i++)
			{
				if(!tempQueue.isEmpty()) //준비 큐에 프로세스가 있는 경우
				{
					processQueue.offer(tempQueue.poll());
				}
			}
			
			//준비큐의 프로세스들을 우선순위로 재배열
			Process tempProcess[]=new Process[processQueue.size()];
			int tempProcessNumber=processQueue.size();
			for(int i=0;i<tempProcessNumber;i++)
			{
				tempProcess[i]=processQueue.poll();
			}
			
			for(int j=0;j<tempProcessNumber-1;j++)
			{
				for(int k=j+1;k<tempProcessNumber;k++)
				{
					if((tempProcess[j].burstTime+tempProcess[j].waitingTime)/tempProcess[j].burstTime<
							(tempProcess[k].burstTime+tempProcess[k].waitingTime)/tempProcess[k].burstTime)
					{
						Process temp=tempProcess[j];
						tempProcess[j]=tempProcess[k];
						tempProcess[k]=temp;
					}
				}
			}
			
			for(int i=0;i<tempProcessNumber;i++)
			{
				processQueue.offer(tempProcess[i]);
			}
			
			for(int processor=0;processor<processorNum;processor++) // 사용할 프로세서 개수만큼 반복
			{
				if(priority[processor]==null) //우선순위에 어떤 프로세서도 할당되지 않은 상태일 땐
				{
					if(!processQueue.isEmpty()) //준비 큐에 프로세스가 있는 경우
					{
						priority[processor]=processQueue.poll();
						priority[processor].use=1;
					}
				}
				
				if(priority[processor]!=null) // 우선순위로 할당된 프로세스가 종료되었건 아니건 할당만 되어있으면 작동하는 작업
				{
					if(priority[processor].use==1) // 간트차트 표기를 위한 코드, use가 1이면 프로세서 점유 중이므로 색표기하고 0이면 점유 중이 아니므로 흰색으로 표기
					{
						processOrders[processor][currentTime]=priority[processor].processOrder; //프로세서별 할당된 프로세스 번호를 매작업마다 저장
					}
					else
					{
						processOrders[processor][currentTime]=-1; //해당작업시간에 프로세서에 할당된 프로세스가 없다는 의미로 간트차트에 흰바탕을 표기하기 위한 값
					}
					
					if(priority[processor].use==1) {++priority[processor].realBurstTime;} //해당 프로세서에 할당된 우선순위 프로세스 작업량 1증가시킴
					if(priority[processor].realBurstTime==priority[processor].burstTime && priority[processor].use==1) // 해당 프로세스의 작업이 종료되면
					{
						// 프로세스 스케줄링 결과 저장
						priority[processor].turnaroundTime=currentTime+1-priority[processor].arrivalTime;
						priority[processor].normalizedTT=(double)priority[processor].turnaroundTime/(double)priority[processor].burstTime;
						priority[processor].use=0;
						priority[processor]=null;
					}
					
				}
			}
			
			for(int j=0;j<processNum;j++)  // 프로세스 대기시간 연산
			{
				if(process[j].use==0 && process[j].burstTime!=process[j].realBurstTime && currentTime>=process[j].arrivalTime)
				{
					process[j].waitingTime++;
				}
			}
			
			readyProcess[currentTime]=new ReadyProcess[processNum];
			for(int j=0;j<processNum;j++)  // 준비큐에 있는 프로세스
			{				
				if(j<processQueue.size())
				{
					Process temp=processQueue.poll();
					System.out.println("이건뭘까..."+temp+"//"+processQueue.size());
					System.out.println("["+temp.processOrder+"프로세스"+temp.RRTimeCount+"]");
					readyProcess[currentTime][j]=new ReadyProcess(temp.processName,temp.processOrder,
							((double)temp.arrivalTime+(double)temp.burstTime)/(double)temp.burstTime);
					readyProcess[currentTime][j].state=1;
					readyProcess[currentTime][j].arrivalTime=temp.arrivalTime;
					processQueue.offer(temp);
				}
				else
				{
					readyProcess[currentTime][j]=null;
				}
			}
			
			
			for(int j=0;j<processNum;j++)  // 현재 수행시간에서의 각 프로세서별 WT,TT,NTT 저장
			{
				realTimeValue[currentTime][j]=new RealTimeValue();
				realTimeValue[currentTime][j].waitingTime=process[j].waitingTime;
				realTimeValue[currentTime][j].turnaroundTime=process[j].turnaroundTime;
				realTimeValue[currentTime][j].normalizedTT=process[j].normalizedTT;
			}
			
			//만약 모든 프로세스들의 작업이 종로되었을 경우 총 작업시간을 저장하고 스케줄링을 중단시킴
			int confirm=0;
			for(int j=0;j<processNum;j++)
			{
				if(process[j].burstTime==process[j].realBurstTime)
				{
					confirm++;
				}
			}
			if(confirm==processNum)
			{
				terminateTime=currentTime+1;
				for(int j=0;j<processNum;j++)  // 현재 수행시간에서의 각 프로세서별 WT,TT,NTT 저장
				{
					realTimeValue[currentTime][j]=new RealTimeValue();
					realTimeValue[currentTime][j].waitingTime=process[j].waitingTime;
					realTimeValue[currentTime][j].turnaroundTime=process[j].turnaroundTime;
					realTimeValue[currentTime][j].normalizedTT=process[j].normalizedTT;
				}
				System.out.println("끝");
				break;
			}
		}
	}
}