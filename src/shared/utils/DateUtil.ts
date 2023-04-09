export function toDate(strDate: string) {
  const date = new Date(strDate);
  return date.toDateString();
}
