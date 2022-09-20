import { IStudent } from './IStudent';

export interface IClazz {
  id: string;
  code: string;
  title: string;
  type: string;
  description: string;
  teacher?: string;
  students: IStudent[];
  enable: boolean;
  assigned: number;
  createdAt: string;
  createdBy: string;
  updatedAt: string;
  updatedBy: string;
}
