import { get, post, del } from './request';
import type { BaseResponse, BaseList } from '@/types/api';
import type {
  FundTradeInfoVO,
  FundIndexVO,
  FundStatisticsVO,
  FundTradeFormDTO,
} from '@/types/fund';

// Get all owners
export function getOwners(): Promise<BaseResponse<string[]>> {
  return get<string[]>('/owners');
}

// Get fund index data
export function getFundIndex(): Promise<BaseResponse<BaseList<FundIndexVO>>> {
  return get<BaseList<FundIndexVO>>('/fund/index');
}

// Get fund trade info by belong and fund code
export function getFundInfo(belong: string, fundCode: string): Promise<BaseResponse<FundTradeInfoVO>> {
  return get<FundTradeInfoVO>(`/fund/info/${belong}/${fundCode}`);
}

// Get fund trade list by belong
export function getFundTradeList(belong: string): Promise<BaseResponse<FundTradeInfoVO[]>> {
  return get<FundTradeInfoVO[]>(`/fund/list/${belong}`);
}

// Get fund statistics
export function getFundStatistics(belong: string): Promise<BaseResponse<FundStatisticsVO>> {
  return get<FundStatisticsVO>(`/fund/tabulate/${belong}`);
}

// Add fund trade
export function addFundTrade(data: FundTradeFormDTO): Promise<BaseResponse<void>> {
  return post<void>('/fund/add', data);
}

// Update fund trade
export function updateFundTrade(id: number, data: FundTradeFormDTO): Promise<BaseResponse<void>> {
  return post<void>(`/fund/update/${id}`, data);
}

// Delete fund trade
export function deleteFundTrade(id: number): Promise<BaseResponse<void>> {
  return del<void>(`/fund/delete/${id}`);
}

// Calculate fund data
export function calculateFund(belong: string): Promise<BaseResponse<void>> {
  return post<void>(`/calculate/${belong}`);
}
