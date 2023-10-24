import java.util.LinkedList;
import java.util.Queue;

public class HRRN {
	Process process[] = new Process[15];
	int burstSum=0; // �� ����ð�
	int processNum; // ���μ��� ����
	int processorNum; // ���μ��� ����
	int terminateTime; // ��� ���μ��� �۾� ����ð�
	
	int processOrders[][]; // Gantt Chart�� ���� ���� [���μ�����ȣ][�۾��ʴ���]
	RealTimeValue realTimeValue[][];  // Result table�� ���� ���� [�۾��ʴ���][���μ�����ȣ]
	ReadyProcess readyProcess[][]; // Ready Queue�� ���� ���� [�۾��ʴ���][���μ�����ȣ]
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
		
		//��Ʈ ��Ʈ�� ǥ���� ���۾��� ���� ���μ����� �����ϰ��ִ� ���μ����� ������ �迭�� ���� -1�� �ʱ�ȭ
		for(int i=0;i<processorNum;i++)
		{
			for(int j=0;j<burstSum;j++)
			{
				processOrders[i][j]=-1;
			}
		}
		
		for(int currentTime=0;currentTime<burstSum;currentTime++) // �� ����ð���ŭ �ݺ�
		{
			for(int i=0;i<processNum;i++) //AT�� current time���� �۰ų� �������ų� Ȥ�� �۾��� ������ ���� ���μ����� �غ�ť�� ����
			{
				if(process[i].use==0 && process[i].realBurstTime!=process[i].burstTime && process[i].arrivalTime==currentTime)
				{
					processQueue.offer(process[i]);
				}
			}
			
			int tempQueueSize=tempQueue.size();
			for(int i=0;i<tempQueueSize;i++)
			{
				if(!tempQueue.isEmpty()) //�غ� ť�� ���μ����� �ִ� ���
				{
					processQueue.offer(tempQueue.poll());
				}
			}
			
			//�غ�ť�� ���μ������� �켱������ ��迭
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
			
			for(int processor=0;processor<processorNum;processor++) // ����� ���μ��� ������ŭ �ݺ�
			{
				if(priority[processor]==null) //�켱������ � ���μ����� �Ҵ���� ���� ������ ��
				{
					if(!processQueue.isEmpty()) //�غ� ť�� ���μ����� �ִ� ���
					{
						priority[processor]=processQueue.poll();
						priority[processor].use=1;
					}
				}
				
				if(priority[processor]!=null) // �켱������ �Ҵ�� ���μ����� ����Ǿ��� �ƴϰ� �Ҵ縸 �Ǿ������� �۵��ϴ� �۾�
				{
					if(priority[processor].use==1) // ��Ʈ��Ʈ ǥ�⸦ ���� �ڵ�, use�� 1�̸� ���μ��� ���� ���̹Ƿ� ��ǥ���ϰ� 0�̸� ���� ���� �ƴϹǷ� ������� ǥ��
					{
						processOrders[processor][currentTime]=priority[processor].processOrder; //���μ����� �Ҵ�� ���μ��� ��ȣ�� ���۾����� ����
					}
					else
					{
						processOrders[processor][currentTime]=-1; //�ش��۾��ð��� ���μ����� �Ҵ�� ���μ����� ���ٴ� �ǹ̷� ��Ʈ��Ʈ�� ������� ǥ���ϱ� ���� ��
					}
					
					if(priority[processor].use==1) {++priority[processor].realBurstTime;} //�ش� ���μ����� �Ҵ�� �켱���� ���μ��� �۾��� 1������Ŵ
					if(priority[processor].realBurstTime==priority[processor].burstTime && priority[processor].use==1) // �ش� ���μ����� �۾��� ����Ǹ�
					{
						// ���μ��� �����ٸ� ��� ����
						priority[processor].turnaroundTime=currentTime+1-priority[processor].arrivalTime;
						priority[processor].normalizedTT=(double)priority[processor].turnaroundTime/(double)priority[processor].burstTime;
						priority[processor].use=0;
						priority[processor]=null;
					}
					
				}
			}
			
			for(int j=0;j<processNum;j++)  // ���μ��� ���ð� ����
			{
				if(process[j].use==0 && process[j].burstTime!=process[j].realBurstTime && currentTime>=process[j].arrivalTime)
				{
					process[j].waitingTime++;
				}
			}
			
			readyProcess[currentTime]=new ReadyProcess[processNum];
			for(int j=0;j<processNum;j++)  // �غ�ť�� �ִ� ���μ���
			{				
				if(j<processQueue.size())
				{
					Process temp=processQueue.poll();
					System.out.println("�̰ǹ���..."+temp+"//"+processQueue.size());
					System.out.println("["+temp.processOrder+"���μ���"+temp.RRTimeCount+"]");
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
			
			
			for(int j=0;j<processNum;j++)  // ���� ����ð������� �� ���μ����� WT,TT,NTT ����
			{
				realTimeValue[currentTime][j]=new RealTimeValue();
				realTimeValue[currentTime][j].waitingTime=process[j].waitingTime;
				realTimeValue[currentTime][j].turnaroundTime=process[j].turnaroundTime;
				realTimeValue[currentTime][j].normalizedTT=process[j].normalizedTT;
			}
			
			//���� ��� ���μ������� �۾��� ���εǾ��� ��� �� �۾��ð��� �����ϰ� �����ٸ��� �ߴܽ�Ŵ
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
				for(int j=0;j<processNum;j++)  // ���� ����ð������� �� ���μ����� WT,TT,NTT ����
				{
					realTimeValue[currentTime][j]=new RealTimeValue();
					realTimeValue[currentTime][j].waitingTime=process[j].waitingTime;
					realTimeValue[currentTime][j].turnaroundTime=process[j].turnaroundTime;
					realTimeValue[currentTime][j].normalizedTT=process[j].normalizedTT;
				}
				System.out.println("��");
				break;
			}
		}
	}
}