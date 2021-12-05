import { IClazz } from "../models/IClazz";

export function newClazz(): IClazz {
    return { code: '', title: '', description: '', enable: true } as IClazz;
}