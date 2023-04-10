import { IStudent } from './IStudent';

export interface IClazz {
  id: string;
  code: string;
  title: string;
  description: string;
  type: string;
  totalAssigned: number;
  enable: boolean;
  teacherId?: string;
  students: IStudent[];
  latitude: number;
  longitude: number;
  createdBy: string;
  createdAt: string;
  updatedBy: string;
  updatedAt: string;
}
