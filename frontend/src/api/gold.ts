import { get, post, del } from './request';
import type { BaseResponse, PageResult } from '@/types/api';
import type {
  GoldTradeVO,
  GoldStatisticsVO,
  GoldTradeFormDTO,
  GoldQueryParams,
} from '@/types/gold';

// Get gold trade list
export function getGoldList(params?: GoldQueryParams): Promise<BaseResponse<PageResult<GoldTradeVO>>> {
  return get<PageResult<GoldTradeVO>>('/gold/list', params);
}

// Get gold statistics
export function getGoldStatistics(): Promise<BaseResponse<GoldStatisticsVO>> {
  return get<GoldStatisticsVO>('/gold/tabulate');
}

// Add gold trade
export function addGoldTrade(data: GoldTradeFormDTO): Promise<BaseResponse<void>> {
  return post<void>('/gold/add', data);
}

// Update gold trade
export function updateGoldTrade(id: number, data: GoldTradeFormDTO): Promise<BaseResponse<void>> {
  return post<void>(`/gold/update/${id}`, data);
}

// Delete gold trade
export function deleteGoldTrade(id: number): Promise<BaseResponse<void>> {
  return del<void>(`/gold/delete/${id}`);
}

// Get gold trade detail
export function getGoldDetail(id: number): Promise<BaseResponse<GoldTradeVO>> {
  return get<GoldTradeVO>(`/gold/info/${id}`);
}

// Calculate gold data
export function calculateGold(): Promise<BaseResponse<void>> {
  return post<void>('/gold/calculate');
}
