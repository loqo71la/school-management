import { IStudent } from "../models/IStudent";

export function newStudent(): IStudent {
    return { idNo: '', firstName: '', lastName: '', gender: true } as IStudent;
}