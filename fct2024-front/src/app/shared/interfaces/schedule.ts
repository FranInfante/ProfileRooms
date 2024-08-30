import { ScheduleTime } from './EnumScheduleTime'; 

export interface Schedule {
  id: number;
  timeStart: ScheduleTime; 
  timeEnd: ScheduleTime;   
}
