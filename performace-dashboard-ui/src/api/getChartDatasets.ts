import ApiService from "../utils/api";

export async function getChartDatasets(traceIds: string[]) {
  let requestParam = traceIds.reduce(joinStrings);
  let result = await ApiService.get(
    `/api/performanceDashboard/data/chart?traceIds=${requestParam}`
  );
  return result;
}

// function to join each string elements
export function joinStrings(accumulator: string, currentValue: string) {
  return accumulator + "," + currentValue;
}
