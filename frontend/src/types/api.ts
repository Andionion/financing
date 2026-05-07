// Base API Response Types
export interface BaseResponse<T> {
  code: number | string;
  msg?: string;
  message?: string;
  data: T;
}

// Pagination
export interface PageResult<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}

// MyBatis-Plus IPage (后端返回的分页格式)
export interface IPage<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}

// Base List Response
export interface BaseList<T> {
  list: T[];
  total?: number;
}

// Error Response
export interface ErrorResponse {
  code: number;
  message: string;
  timestamp?: string;
  path?: string;
}
