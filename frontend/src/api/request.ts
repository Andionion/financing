import axios from 'axios';
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { ElMessage } from 'element-plus';
import type { BaseResponse } from '@/types/api';

// Create axios instance
const api: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/financing',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor
api.interceptors.request.use(
  (config) => {
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor
api.interceptors.response.use(
  (response: AxiosResponse<BaseResponse<any>>) => {
    const res = response.data;
    
    // 后端成功码为 "0" 或 0，兼容多种格式
    const codeStr = String(res.code);
    if (codeStr !== "0" && codeStr !== "200") {
      const errorMsg = (res as any).msg || res.message || '请求失败';
      ElMessage.error(errorMsg);
      return Promise.reject(new Error(errorMsg));
    }
    
    return response;
  },
  (error) => {
    // Network error or other error
    const message = (error.response?.data as any)?.msg || error.response?.data?.message || error.message || '网络错误';
    ElMessage.error(message);
    return Promise.reject(error);
  }
);

// Generic request method
export function request<T>(config: AxiosRequestConfig): Promise<BaseResponse<T>> {
  return api.request<any, AxiosResponse<BaseResponse<T>>>(config).then(res => res.data);
}

// GET request
export function get<T>(url: string, params?: any): Promise<BaseResponse<T>> {
  return request<T>({
    method: 'GET',
    url,
    params,
  });
}

// POST request
export function post<T>(url: string, data?: any): Promise<BaseResponse<T>> {
  return request<T>({
    method: 'POST',
    url,
    data,
  });
}

// PUT request
export function put<T>(url: string, data?: any): Promise<BaseResponse<T>> {
  return request<T>({
    method: 'PUT',
    url,
    data,
  });
}

// DELETE request
export function del<T>(url: string): Promise<BaseResponse<T>> {
  return request<T>({
    method: 'DELETE',
    url,
  });
}

export default api;
