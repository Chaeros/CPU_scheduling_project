# CPU_scheduling_project
CPU 스케줄링 알고리즘을 Java Swing을 사용하여 시각화한 프로젝트입니다.

<h2>개발 동기</h2>
학부 운영체제 과목에서 한 학기간 배웠던 내용들 중 하나의 주제를 선정해서 프로젝트를 진행해야 했는데,<br>
CPU 스케줄링 기법을 프로그래밍을 통해 체화하고 싶어 개발하게 됐습니다.<br>

<h2>기술 스택</h2>
JAVA, JAVA SWING

<h2>기능</h2>
- CPU 스케줄링 알고리즘인 FCFS, RR, SPN, SRTN, HRRN을 Java Swing을 통해 GUI로 구현했습니다.<br>
- CPU내 코어의 개수를 1개에서 최대 4개까지 가동 가능하도록 구현했습니다.<br>
- 프로세스의 이름, Arrival Time, Burst Time과 Excute Time을 설정하면<br>
  해당 프로세스가 Arrival Time부터 BurstTime 동안 차지하는 코어가 화면에 나타나집니다.<br>
  이 때, 설정한 Excute Time마다 좌측에서 우측으로 그래프가 한칸씩 생성됩니다.<br>
- SPN과 SRTN 스케줄링 기법에 한하여 선저과 비선점 방식을 채택하여 작동할 수 있도록 했습니다. <br>

<h2>작동 예시</h2>

![image](https://github.com/Chaeros/CPU_scheduling_project/assets/91451735/de244c44-c2ae-404e-a62d-ab266a59cdde)
![image](https://github.com/Chaeros/CPU_scheduling_project/assets/91451735/6f3c67a8-e443-4214-9993-543e150780f2)
![image](https://github.com/Chaeros/CPU_scheduling_project/assets/91451735/22a659a1-984c-4394-befd-6fdcd1ef9675)
![image](https://github.com/Chaeros/CPU_scheduling_project/assets/91451735/ded5d0f5-0f21-4db5-8e33-cfb53d982b6b)
![image](https://github.com/Chaeros/CPU_scheduling_project/assets/91451735/da309ceb-db7d-4961-b7ad-a8b72589c0ed)
