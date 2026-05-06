import { get, post, del } from './request';
import type { BaseResponse, PageResult } from '@/types/api';
import type {
  HpfRecordVO,
  HpfStatisticsVO,
  HpfRecordFormDTO,
  HpfQueryParams,
} from '@/types/hpf';

// Get HPF record list
export function getHpfList(params?: HpfQueryParams): Promise<BaseResponse<PageResult<HpfRecordVO>>> {
  return get<PageResult<HpfRecordVO>>('/hpf/list', params);
}

// Get HPF statistics
export function getHpfStatistics(belong?: string): Promise<BaseResponse<HpfStatisticsVO>> {
  return get<HpfStatisticsVO>('/hpf/tabulate', { belong });
}

// Add HPF record
export function addHpfRecord(data: HpfRecordFormDTO): Promise<BaseResponse<void>> {
  return post<void>('/hpf/add', data);
}

// Update HPF record
export function updateHpfRecord(id: number, data: HpfRecordFormDTO): Promise<BaseResponse<void>> {
  return post<void>(`/hpf/update/${id}`, data);
}

// Delete HPF record
export function deleteHpfRecord(id: number): Promise<BaseResponse<void>> {
  return del<void>(`/hpf/delete/${id}`);
}

// Get HPF record detail
export function getHpfDetail(id: number): Promise<BaseResponse<HpfRecordVO>> {
  return get<HpfRecordVO>(`/hpf/info/${id}`);
}

// Get HPF by month
export function getHpfByMonth(belong: string, month: string): Promise<BaseResponse<HpfRecordVO>> {
  return get<HpfRecordVO>(`/hpf/month/${belong}/${month}`);
}

// Calculate HPF data
export function calculateHpf(belong: string): Promise<BaseResponse<void>> {
  return post<void>(`/calculate/${belong}`);
}
